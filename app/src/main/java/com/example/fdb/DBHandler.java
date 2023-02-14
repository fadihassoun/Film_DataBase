package com.example.fdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    //The database constants
    private static final String DB_NAME = "actorFilmDB.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_A = "actor_table";
    private static final String COLUMN_ID_A = "_idA";
    private static final String COLUMN_ACTOR_NAME = "actor_name";
    private static final String COLUMN_ACTOR_INFO = "actor_info";
    private static final String COLUMN_ACTOR_PHOTO = "actor_photo";
    private static final String TABLE_F = "film_table";
    private static final String COLUMN_ID_F = "_idF";
    private static final String COLUMN_FILM_TITLE = "title";
    private static final String COLUMN_YEAR = "release_year";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_LENGTH = "length";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_WATCHED = "watched";
    private static final String COLUMN_SCORE = "user_score";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    } //public DBHandler(Context context)

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statements to create two tables, TABLE_A for actor and TABLE_F for films
        String createTableAStatement = "CREATE TABLE " + TABLE_A + " (" +
                COLUMN_ID_A + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACTOR_NAME + " VARCHAR(255), " +
                COLUMN_ACTOR_INFO + " TEXT, " +
                COLUMN_ACTOR_PHOTO + " BLOB);";

        String createTableFStatement = "CREATE TABLE " + TABLE_F + " (" +
                COLUMN_ID_F + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILM_TITLE + " VARCHAR(255) UNIQUE, " +
                COLUMN_YEAR + " VARCHAR(255), " +
                COLUMN_GENRE + " VARCHAR(255), " +
                COLUMN_LENGTH + " VARCHAR(255), " +
                COLUMN_ROLE + " VARCHAR(255), " +
                COLUMN_WATCHED + " BOOLEAN, " +
                COLUMN_SCORE + " INT);";

        db.execSQL(createTableAStatement);
        db.execSQL(createTableFStatement);
    } //public void onCreate(SQLiteDatabase db)

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Debug Message
        Log.w("LOG_TAG", "Upgrading database resets all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_A + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_F + ";");
        onCreate(db);

    }//public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

    //This checks if the Actor table already exists and not empty
    public boolean actorTableExists(SQLiteDatabase db)
    {
        String firstCheckStatement = "SELECT COUNT (" + COLUMN_ACTOR_NAME + ") FROM " + TABLE_A + ";";
        Cursor cursor = db.rawQuery(firstCheckStatement, null);
        if (cursor != null) // if the result is returned
        {
            if (cursor.moveToFirst()) // if the result is not empty
            {
                int i = cursor.getInt(0);
                if (i != 0)
                {
                    cursor.close();
                    return true;
                }
            }
        }
        cursor.close();
        return false;
    }//public boolean actorTableExists(SQLiteDatabase db)

    public void insertActorInfoInTable(SQLiteDatabase db, String name, String info, byte[] imageArray)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ACTOR_NAME, name);
        cv.put(COLUMN_ACTOR_INFO, info);
        cv.put(COLUMN_ACTOR_PHOTO, imageArray);
        db.insert(TABLE_A, null, cv);
    } // public void insertRecordInActorTable


    // to retrieve an array of objects of three : 2 strings and a bitmap
    public Object[] getActorData(SQLiteDatabase db)
    {
        String name = "No actor chosen";
        String info = "No info could be retrieved";
        byte[] image= new byte[0];
        Object[] objArray = new Object[3];

        String getActorStatement = "SELECT * FROM actor_table WHERE _idA = '1'";
        Cursor cursor = db.rawQuery(getActorStatement, null);
        if (cursor != null) // if the result is returned
        {
            if (cursor.moveToFirst()) // if the result is not empty
            {
                name = cursor.getString(1);
                objArray[0] = (!name.equals(""))? name:"No actor saved";
                info = cursor.getString(2);
                objArray[1] = info;
                image = cursor.getBlob(3);
                //converting Blob to Bitmap image format
                Bitmap bm = BitmapFactory.decodeByteArray(image, 0 ,image.length);
                objArray[2] = bm;
            }
        }
        return objArray;
    }//getActorData


    public boolean insertFilmData (SQLiteDatabase db, String title, String year, String genre, String length, String role, Boolean watched, String score)
    {
        Boolean success= true;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FILM_TITLE, title.toUpperCase());
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_GENRE, genre);
        cv.put(COLUMN_LENGTH, length);
        cv.put(COLUMN_ROLE, role);
        cv.put(COLUMN_WATCHED, watched);
        cv.put(COLUMN_SCORE, score);
        //todo to throw exception if not successful (was a problem trying to catch an error)
        try {
            db.insertOrThrow(TABLE_F, null, cv);
        }
        catch (SQLiteConstraintException e)
        {
            success =false;
        }

        return success;

    }//public void insertFilmData


    public ArrayList<String> getFilmData(SQLiteDatabase db)
    {
        ArrayList<String> filmsArrayList = new ArrayList<>();

        String result = "";
        String watchedOrNot="";

        Cursor c = db.rawQuery("SELECT * FROM " +
                TABLE_F +
                ";", null);

        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    if (c.getString(6).equals("1") || c.getString(6).equals("true"))
                    {
                        watchedOrNot = "Yes";
                    }
                    else if(c.getString(6).equals("0") || c.getString(6).equals("false"))
                    {
                        watchedOrNot = "No";
                    }

                    result = c.getString(1) + "\t\n\n" +
                            "│ Year: " + c.getString(2) + "\t        " +
                            "│ Genre: "+ c.getString(3) + "\t\n" +
                            "│ Length: "+ c.getString(4) + "min\t" +
                            "│ Character: "+ c.getString(5) + "\t\n" +
                            "│ Watched: "+ watchedOrNot + "\t    " +
                            "│ My Score: "+ c.getString(7) +"\n";
                    // spaces are added to improve alignment

                    filmsArrayList.add(result);

                } while (c.moveToNext());
            }
            else
            {//

            }
        }

        c.close();

        return filmsArrayList;
    } //public ArrayList getFilmData(SQLiteDatabase db)

    public ArrayList<String> getFilmSingleRecord(SQLiteDatabase db, String title)
    {
        ArrayList<String> filmArrayList = new ArrayList<>();
        String year ="";
        String length ="";
        String genre ="";
        String character ="";
        String score = "";


        Cursor c = db.rawQuery("SELECT * FROM " +
                TABLE_F + " WHERE " +COLUMN_FILM_TITLE + " = '" +title+"';", null);

        if (c != null)
        {
            if (c.moveToFirst())
            {
                    filmArrayList.add(title);
                    year =  c.getString(2);
                    filmArrayList.add(year);
                    genre = c.getString(3);
                    filmArrayList.add(genre);
                    length = c.getString(4);
                    filmArrayList.add(length);
                    character = c.getString(5);
                    filmArrayList.add(character);
                    filmArrayList.add(c.getString(6));
                    score = c.getString(7);
                    filmArrayList.add(score);
            }
        }
        c.close();

        return filmArrayList;

    } //public ArrayList getFilmData(SQLiteDatabase db)


    public void deleteFilmFromTable(SQLiteDatabase db, String itemToDelete)
    {
        String deleteStatement = "DELETE FROM "+TABLE_F+
                " WHERE title = '" +itemToDelete +"'";

        db.execSQL(deleteStatement);


    }//public void deleteFilmFromTable

    public Boolean updateFilmData(SQLiteDatabase db, String existingTitle, String title, String year, String genre, String length, String character, Boolean watched, String score)
    {
        Boolean success= true;

        String updateFilmStatement = "UPDATE "+TABLE_F+
                " SET "+
                COLUMN_FILM_TITLE +" = '"+ title +"', "+
                COLUMN_YEAR +" = '"+ year +"', "+
                COLUMN_GENRE +" = '"+ genre +"', "+
                COLUMN_LENGTH +" = '"+ length +"', "+
                COLUMN_ROLE +" = '"+ character +"', "+
                COLUMN_WATCHED +" = '"+ watched +"', "+
                COLUMN_SCORE +" = '"+ score +"' "+
                "WHERE "+ COLUMN_FILM_TITLE + " ='" +existingTitle+ "'; ";

        try
        {
            db.execSQL(updateFilmStatement);
        }
        catch (SQLiteConstraintException e)
        {
            e.printStackTrace();
            success =false;
        }

        return success;

    }//public void updateFilmData

    public ArrayList<String> searchFilmByTitle(SQLiteDatabase db, String title) {
        ArrayList<String> searchFilmByTitleList = new ArrayList<>();

        String result = "";
        String watchedOrNot = "";

        //This query searches for part of string after, uppercase is chosen for better display
        Cursor c = db.rawQuery("SELECT * FROM " +
                TABLE_F + " WHERE " + COLUMN_FILM_TITLE + " LIKE '%" + title.toUpperCase() + "%';", null);

        if (c != null) {
            if (c.moveToFirst()) {

                do
                {

                    if (c.getString(6).equals("1") || c.getString(6).equals("true"))
                    {
                        watchedOrNot = "Yes";
                    }
                    else if(c.getString(6).equals("0") || c.getString(6).equals("false"))
                    {
                        watchedOrNot = "No";
                    }

                result = c.getString(1) + "\t\n\n" +
                        "│ Year: " + c.getString(2) + "\t        " +
                        "│ Genre: " + c.getString(3) + "\t\n" +
                        "│ Length: " + c.getString(4) + "min\t" +
                        "│ Character: " + c.getString(5) + "\t\n" +
                        "│ Watched: " + watchedOrNot + "\t    " +
                        "│ My Score: " + c.getString(7) + "\n";
                // spaces are added to improve alignment

                searchFilmByTitleList.add(result);

            } while (c.moveToNext()) ;

        } else {
            //future message that no result found
        }
    }


        c.close();

        return searchFilmByTitleList;
    }//public ArrayList<String> searchFilmByTitle

    public ArrayList<String> searchFilmByYear(SQLiteDatabase db, String year)
    {
        ArrayList<String> searchFilmByYearList = new ArrayList<>();

        String result = "";
        String watchedOrNot = "";

        //This query searches for part of string after, uppercase is chosen for better display
        Cursor c = db.rawQuery("SELECT * FROM " +
                TABLE_F + " WHERE " + COLUMN_YEAR + " LIKE '" + year + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {

                do
                {
                    if (c.getString(6).equals("1") || c.getString(6).equals("true"))
                    {
                        watchedOrNot = "Yes";
                    }
                    else if(c.getString(6).equals("0") || c.getString(6).equals("false"))
                    {
                        watchedOrNot = "No";
                    }

                    result = c.getString(1) + "\t\n\n" +
                            "│ Year: " + c.getString(2) + "\t        " +
                            "│ Genre: " + c.getString(3) + "\t\n" +
                            "│ Length: " + c.getString(4) + "min\t" +
                            "│ Character: " + c.getString(5) + "\t\n" +
                            "│ Watched: " + watchedOrNot + "\t    " +
                            "│ My Score: " + c.getString(7) + "\n";
                    // spaces are added to improve alignment

                    searchFilmByYearList.add(result);

                } while (c.moveToNext()) ;

            } else {
                //for future: message that no result found
            }
        }


        c.close();

        return searchFilmByYearList;
    }//public ArrayList<String> searchFilmByYear

    public ArrayList<String> searchFilmByGenre(SQLiteDatabase db, String genre)
    {
        ArrayList<String> searchFilmByGenreList = new ArrayList<>();

        String result = "";
        String watchedOrNot = "";

        //This query searches for part of string after, uppercase is chosen for better display
        Cursor c = db.rawQuery("SELECT * FROM " +
                TABLE_F + " WHERE " + COLUMN_GENRE + " LIKE '%" + genre + "%';", null);

        if (c != null) {
            if (c.moveToFirst()) {

                do
                {

                    if (c.getString(6).equals("1") || c.getString(6).equals("true"))
                    {
                        watchedOrNot = "Yes";
                    }
                    else if(c.getString(6).equals("0") || c.getString(6).equals("false"))
                    {
                        watchedOrNot = "No";
                    }

                    result = c.getString(1) + "\t\n\n" +
                            "│ Year: " + c.getString(2) + "\t        " +
                            "│ Genre: " + c.getString(3) + "\t\n" +
                            "│ Length: " + c.getString(4) + "min\t" +
                            "│ Character: " + c.getString(5) + "\t\n" +
                            "│ Watched: " + watchedOrNot + "\t    " +
                            "│ My Score: " + c.getString(7) + "\n";
                    // spaces are added to improve alignment

                    searchFilmByGenreList.add(result);

                } while (c.moveToNext()) ;

            } else {
                //future message that no result found

            }
        }

        c.close();

        return searchFilmByGenreList;
    }//public ArrayList<String> searchFilmByGenre

} //class

