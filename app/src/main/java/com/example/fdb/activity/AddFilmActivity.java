package com.example.fdb.activity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fdb.DBHandler;
import com.example.fdb.R;

//All activities are extending BaseActivity to show the navigation menu on them
public class AddFilmActivity extends BaseActivity {

    EditText add_titleEditTextV;
    EditText add_yearEditTextV;
    EditText add_lengthEditTextV;
    EditText add_characterEditTextV;
    Spinner add_genreSpinner;
    Spinner add_scoreSpinner;
    CheckBox add_watchedCheckBox;
    Button add_addButton;
    Button add_cancelButton;

    String titleEditString;
    String characterEditString;


    DBHandler fdbH;
    SQLiteDatabase fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.activity_add_film);

        //initiating the database
        initDB();

        setupControls();

        /* When the Add button is clicked, the input is validated, then the data is added to the database and the Filmography is updated */
        add_addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Checking if the user input is valid by calling the validInput method
                if(validInput())
                {
//                    Toast.makeText(AddFilmActivity.this, "Valid", Toast.LENGTH_SHORT).show();
                    titleEditString = add_titleEditTextV.getText().toString();
                    characterEditString = add_characterEditTextV.getText().toString();

                    sanitizeSingleQuotes();


                    //Inserting the values in the database

                    Boolean insertSuccess = fdbH.insertFilmData(fdb,
                                titleEditString,
                                add_yearEditTextV.getText().toString(),
                                add_genreSpinner.getSelectedItem().toString(),
                                add_lengthEditTextV.getText().toString(),
                                characterEditString,
                                add_watchedCheckBox.isChecked(),
                                add_scoreSpinner.getSelectedItem().toString()
                                );

                    //checking if data successfully inserted in the databse
                    if(insertSuccess)
                    {
                        // Switching to the Filmography activity
                        Intent intent = new Intent(getBaseContext(), FilmsActivity.class);
                        startActivity(intent);
                    }
                    else
                    //insert validation, only unique film titles are allowed
                    {
                        add_titleEditTextV.setError("Title already exists, duplicates not allowed");
                    }

                } //if(validInput())
                else
                {
                    Toast.makeText(AddFilmActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_cancelButton = findViewById(R.id.add_cancelButton);
        add_cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

    }   //protected void onCreate(Bundle savedInstanceState)

    private void sanitizeSingleQuotes()
    {
        //replacing the single quotes with back ticks to avoid SQLite querry errors
        titleEditString = titleEditString.replaceAll("'", "`");
        characterEditString = characterEditString.replaceAll("'", "`");

    }//private void sanitizeSingleQuotes()

    private void setupControls()
    {
        add_titleEditTextV = findViewById(R.id.add_titleEditText);
        add_yearEditTextV = findViewById(R.id.add_yearEditText);
        add_lengthEditTextV = findViewById(R.id.add_lengthEditText);
        add_characterEditTextV = findViewById(R.id.add_characterEditTxtView);
        add_genreSpinner = findViewById(R.id.add_genreSpinner);
        add_scoreSpinner = findViewById(R.id.add_scoreSpinner);
        add_watchedCheckBox = findViewById(R.id.add_watchedCheckBox);

        add_addButton = findViewById(R.id.add_addButton);
    }//private void setupControls()

    //Validating input for film records
    private boolean validInput()
    {
        Boolean check =true;
        //checking user provided a title (required)
        if(add_titleEditTextV.getText().toString().length()==0)
        {
            add_titleEditTextV.setError("Film title is required");
            check = false;
        }
        // parallel checking for valid year input
        if (add_yearEditTextV.getText().toString().length()<=4 && add_yearEditTextV.getText().toString().length()>0)
        {
            //checking if value of year is valid
            int year = Integer.parseInt(add_yearEditTextV.getText().toString());
            if ( (year >2022) || (year< 1894))
            {
                //displaying error message if outside the acceptable year range
                add_yearEditTextV.setError("Year should be between 1894 and 2022");
                check = false;
            }
        }
        //return the value of the check: true in case the above conditions are not met
        return check;
    }//private boolean validInput()

    private void initDB()
    {
        // Initiating the DBHandler Class
        fdbH = new DBHandler(this);

        // Retrieving a readable and writeable database
        fdb = fdbH.getWritableDatabase();

    }//private void initDB()

}//public class AddFilmActivity extends BaseActivity