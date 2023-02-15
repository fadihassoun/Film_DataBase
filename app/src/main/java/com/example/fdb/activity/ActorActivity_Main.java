package com.example.fdb.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fdb.DBHandler;
import com.example.fdb.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//All activities are extending BaseActivity to show the navigation menu on them
public class ActorActivity_Main extends BaseActivity {
    //Actor layout parameters
    ImageView actorImageView;
    Drawable actorImage;
    TextView actorNameTextV;
    TextView actorInfoTextV;
    TextView actorImageTextV;
    String actorNameString = "";
    String actorInfoString = "";
    Button addActorButton;
    //Dialog layout parameter
    Dialog dialog;
    Context context = this;
    AutoCompleteTextView actorNameDialogEdit;
    //Database parameters
    DBHandler fdbH;
    SQLiteDatabase fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.activity_actor);

        setupControls();

        //initiating the database
        initDB();

        /* first this checks if the Actor table already exists and not empty; if it doesn't exist,
        a dialog will show to ask the user for the actor name, and then webscraping is used to extract
        the information and image of the actor */
        if (!fdbH.actorTableExists(fdb)) {
            Toast.makeText(this, "Enter the actor's name", Toast.LENGTH_LONG).show();

            dialog.show();
            //disabling back press until the ADD button is pressed
            dialog.setCancelable(false);

            // getting array (of top 1000 actors) from array resources to help user with the input
            Resources res = getResources();
            String[] actorsList = res.getStringArray(R.array.actorsList);
            // creating an Array Adapter for the actors list
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, actorsList);
            // setting the autocomplete string array on the View
            actorNameDialogEdit.setAdapter(adapter);

            addActorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //checking if the name is blank
                    if (actorNameDialogEdit.getText().length() == 0) {
                        actorNameDialogEdit.setError("Name cannot be blank");
                    } else /**/ {
                        //return to the main activity
                        dialog.dismiss();

                        //getting actor name from dialog editView and setting it to a string then to the text view for first app run
                        actorNameString = actorNameDialogEdit.getText().toString();
                        actorNameTextV.setText(actorNameString);

                        //web scrape actor info and photo from net
                        scrapeActorInfo();
                    }
                }
            });//addActorButton.setOnClickListener(new View.OnClickListener()

        } // if (!fdbH.actorTableExists(fdb))

        else {
            // if the database table exits, this will retrieve the actor name, info, and image and set them to the views
            actorNameString = (String) fdbH.getActorData(fdb)[0];
            actorNameTextV.setText(actorNameString);


            actorInfoString = (String) fdbH.getActorData(fdb)[1];
            actorInfoTextV.setText(actorInfoString);

            Bitmap rBm = (Bitmap) fdbH.getActorData(fdb)[2];
            actorImageView.setImageBitmap(rBm);
        }//else

    }//protected void onCreate(Bundle savedInstanceState)

    private void setupControls() {
        actorNameTextV = findViewById(R.id.actorNameTextView);
        actorInfoTextV = findViewById(R.id.actorInfoTextV);
        actorInfoTextV.setMovementMethod(new ScrollingMovementMethod());
        actorImageView = findViewById(R.id.actorImageV);
        actorImageTextV = findViewById(R.id.actorImagetextView);
        // Dialog controls
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_actor_dialog);
        actorNameDialogEdit = dialog.findViewById(R.id.autoCompleteTextView);
        addActorButton = dialog.findViewById(R.id.addActorButton);
        //assigning the default image to the variable in case no image was found through web-scarping
        actorImage = actorImageView.getDrawable();
    }//private void setupControls()

    private void initDB() {
        // Initiate the DBHandler Class
        fdbH = new DBHandler(this);

        // Constructing a readable and writeable database
        fdb = fdbH.getWritableDatabase();

    }//private void initDB()

    private void scrapeActorInfo() {
        actorInfoTextV.setText("Retrieving actor information please Wait...");
        // replacing space with '-' for webscraping
        String preparedName = actorNameString.replaceAll(" ", "_");

        //The thread is to retrieve information about the actor from the web using Jsoup
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlString = "https://en.wikipedia.org/wiki/" + preparedName;
                    Document scrapeDoc = Jsoup.connect(urlString).get();

                    //trying to find the image in the specified CSS selector
                    try {
                        // getting the actor image
                        Elements images = scrapeDoc.select("#mw-content-text > div.mw-parser-output > table.infobox.biography.vcard > tbody > tr:nth-child(2) > td > a > img");
                        String imageHref = "";
                        for (Element image : images) {
                            imageHref = image.absUrl("src");
                        }
                        //downloading image into a drawable
                        actorImage = Drawable.createFromStream((InputStream) new URL(imageHref).getContent(), "src");


                    } catch (IOException imageError) {
                        Log.w("Image not found", imageError.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                actorImageTextV.setText("Image Not Found");
                            }
                        });

                    }//catch (IOException imageError)


                    Elements paragraphs = scrapeDoc.select("#mw-content-text > div.mw-parser-output > p");
                    //#mw-content-text > div.mw-parser-output > p:nth-child
                    int i = 0;

                    for (Element p : paragraphs) {
                        if (i < 5)
                        //get only the first 5 paragraphs
                        {
                            actorInfoString = "\n" + actorInfoString + p.text();
                        }
                        i++;

                    }//for

                    actorInfoString = actorInfoString.trim(); // to remove leading white space
                    actorInfoString = actorInfoString.replaceAll("\\[.?.?.?\\]", "") + " [1]." + "\n \n" + "Reference: \n[1]: Wikipedia, " + ", " + scrapeDoc.title() + " [Online]. Available: " + urlString; // to remove citations and add reference to Wikipedia at the end


                    //edit the view in the UI thread and store Actor info into database
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            actorInfoTextV.setText(actorInfoString);

                            //assigning the retrieved image to the ImageView
                            actorImageView.setImageDrawable(actorImage);

                            // assign image to a format that can be stored in the database

                            //compressing the image and converting it to a byte[] array
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            Bitmap bitmapImage = ((BitmapDrawable) actorImage).getBitmap();
                            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] imageByteArray = bos.toByteArray();

                            //storing the info into the database
                            fdbH.insertActorInfoInTable(fdb, actorNameString, actorInfoString, imageByteArray);
                        } //run
                    });//runOnUiThread

                }//try
                catch (IOException err) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //message if error occurs in retrieving the information from the web
                            actorInfoTextV.setText("Could not retrieve info from the internet: " + err.getMessage());
                            dialog.show();
                            actorNameDialogEdit.setError("Not found, try again");
                            Toast.makeText(getBaseContext(), "Check your actor name and try again", Toast.LENGTH_LONG).show();
                        }//public void run()
                    });
                    Log.w("Scraping Error", err.getMessage());
                }//catch
            }//run

        }).start();// Thread


    }//private void scraperActorInfo()

}//public class ActorActivity_Main extends BaseActivity

