package com.example.fdb;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

//All activities are extending BaseActivity to show the navigation menu on them
public class EditFilmActivity extends BaseActivity
{
    EditText edit_titleEditTextV;
    EditText edit_yearEditTextV;
    EditText edit_lengthEditTextV;
    EditText edit_characterEditTextV;
    Spinner edit_genreSpinner;
    Spinner edit_scoreSpinner;
    CheckBox edit_watchedCheckBox;
    Button edit_updateButton;
    Button edit_cancelButton;

    DBHandler fdbH;
    SQLiteDatabase fdb;

    String titleEditString;
    String characterEditString;
    String existingTitle="";
    Boolean watched;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.activity_edit_film);

        String passedRecord = getIntent().getExtras().getString("title");

        //initialise the database
        initDB();

        //setting up controls
        setupControls();

        //retrieving values form database and setting to layout views
        ArrayList<String> filmRecord = fdbH.getFilmSingleRecord(fdb, passedRecord);
        existingTitle = filmRecord.get(0);
        edit_titleEditTextV.setText(existingTitle);
        edit_yearEditTextV.setText(filmRecord.get(1));

        edit_genreSpinner.setSelection(((ArrayAdapter)edit_genreSpinner.getAdapter()).getPosition(filmRecord.get(2)));
        edit_lengthEditTextV.setText(filmRecord.get(3));
        edit_characterEditTextV.setText(filmRecord.get(4));
        if (filmRecord.get(5).equals("1") || filmRecord.get(5).equals("true") )
        {
            watched = true;
        }
        else
        {
            watched = false;
        }

        edit_watchedCheckBox.setChecked(watched);
        edit_scoreSpinner.setSelection(((ArrayAdapter)edit_scoreSpinner.getAdapter()).getPosition(filmRecord.get(6)));
    }//protected void onCreate

    private void setupControls()
    {
        edit_titleEditTextV = findViewById(R.id.edit_titleEditText);
        edit_yearEditTextV = findViewById(R.id.edit_yearEditText);
        edit_lengthEditTextV = findViewById(R.id.edit_lengthEditText);
        edit_characterEditTextV = findViewById(R.id.edit_characterEditTxtView);
        edit_genreSpinner = findViewById(R.id.edit_genreSpinner);
        edit_scoreSpinner = findViewById(R.id.edit_scoreSpinner);
        edit_watchedCheckBox = findViewById(R.id.edit_watchedCheckBox);

        edit_updateButton = findViewById(R.id.edit_updateButton);
        edit_updateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Checking if the user input is valid by calling the validInput method
                if (validInput()) {
//                    Toast.makeText(EditFilmActivity.this, "Valid", Toast.LENGTH_SHORT).show();

                    titleEditString = edit_titleEditTextV.getText().toString(); //after edit if any
                    characterEditString = edit_characterEditTextV.getText().toString();

                    sanitizeSingleQuotes();

                    //Updating the values in the database

                    if(true)
                    {

                    }
                    else {

                    }

                    Boolean insertSuccess = fdbH.updateFilmData(fdb, existingTitle,
                            titleEditString,
                            edit_yearEditTextV.getText().toString(),
                            edit_genreSpinner.getSelectedItem().toString(),
                            edit_lengthEditTextV.getText().toString(),
                            characterEditString,
                            edit_watchedCheckBox.isChecked(),
                            edit_scoreSpinner.getSelectedItem().toString()
                            );

                    //checking if data successfully inserted in the databse
                    if(insertSuccess)
                    {
                        // Switching to Filmography activity
                        Intent intent = new Intent(getBaseContext(), FilmsActivity.class);
                        startActivity(intent);
                    }
                    else
                    //insert validation, only unique film titles are allowed
                    {
                        edit_titleEditTextV.setError("Title already exists, duplicates not allowed");
                    }

                } //if(validInput())
                else
                {
                    Toast.makeText(EditFilmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_cancelButton = findViewById(R.id.edit_cancelButton);
        edit_cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

    }//private void setupControls()

    private void sanitizeSingleQuotes()
    {
        //replacing the single quotes with back ticks to avoid SQLite querry errors
        titleEditString = titleEditString.replaceAll("'", "`");
        characterEditString = characterEditString.replaceAll("'", "`");
    } //private void sanitizeSingleQuotes()

    private void initDB()
    {
        // Initiating the DBHandler Class
        fdbH = new DBHandler(this);

        // Retrieving a readable and writeable database
        fdb = fdbH.getWritableDatabase();

    }//private void initDB()

    private boolean validInput()
    {
        Boolean check =true;
        //checking user provided a title (required)
        if(edit_titleEditTextV.getText().toString().length()==0)
        {
            edit_titleEditTextV.setError("Film title is required");
            check = false;
        }
        // parallel checking for valid input
        if (edit_yearEditTextV.getText().toString().length()<=4 && edit_yearEditTextV.getText().toString().length()>0)
        {
            //checking if value of year is valid
            int year = Integer.parseInt(edit_yearEditTextV.getText().toString());
            if ( (year >2022) || (year< 1894))
            {
                edit_yearEditTextV.setError("Year should be between 1894 and 2022");
                check = false;
            }
        }

        //return the value of the check, true in case the above conditions are not met
        return check;

    }//private boolean validInput()

}//public class EditFilmActivity extends BaseActivity
