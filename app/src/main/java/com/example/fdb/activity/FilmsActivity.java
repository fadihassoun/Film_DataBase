package com.example.fdb.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.fdb.DBHandler;
import com.example.fdb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FilmsActivity extends BaseActivity
{
    ListView filmsListView;
    ArrayList<String> filmArrayList;
    ArrayAdapter filmsAdapter;
    FloatingActionButton filmograghy_floatingAddButton;
    Button deleteFilmConfirmButton;
    Button deleteFilmBackButton;

    DBHandler fdbH;
    SQLiteDatabase fdb;

    String[] substring;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.activity_films);

        filmsListView = findViewById(R.id.filmListView);
        filmograghy_floatingAddButton = findViewById(R.id.filmography_floatingAddButton);
        //Dialog for delete
        Dialog dialog = new Dialog(FilmsActivity.this);
        dialog.setContentView(R.layout.delete_confirm_dialog);
        deleteFilmConfirmButton = dialog.findViewById(R.id.deletefilmConfirmButton);
        deleteFilmBackButton = dialog.findViewById(R.id.deleteFilmBackButton);

        initDB();

        setupFilmList();

        // the add film floating button
        filmograghy_floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             Intent intent =  new Intent(getBaseContext(), AddFilmActivity.class);
             startActivity(intent);
            }//filmograghy_floatingAddButton.setOnClickListener
        });

        filmsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String item =(String) parent.getItemAtPosition(position);

                //the popup menu
                PopupMenu popupMenu = new PopupMenu(FilmsActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.films_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        //splitting the record to individual strings to make queries on the title (index 0)
                        substring = item.split("\t");
                        if  (menuItem.getItemId()==R.id.edit_film)
                        {
                            Intent intent = new Intent(getBaseContext(), EditFilmActivity.class);
                            //sending a string with the film title to the Edit Film Activity
                            intent.putExtra("title", substring[0]);
                            startActivity(intent);

                            return true;
                        }
                        else if (menuItem.getItemId()==R.id.delete_film)
                        {
                            dialog.show();
                            return true;
                        }
                        return false;
                    }//public boolean onMenuItemClick(MenuItem menuItem)
                });//popupMenu.setOnMenuItemClickListener
                popupMenu.show();
            }
        });//filmsListView.setOnItemClickListener

        deleteFilmConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fdbH.deleteFilmFromTable(fdb, substring[0]);

                dialog.dismiss();

                //updating the films list method
                setupFilmList();
            }
        });//deleteFilmConfirmButton.setOnClickListener

        deleteFilmBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });// deleteFilmBackButton.setOnClickListener

    }// protected void onCreate(Bundle savedInstanceState)

    private void setupFilmList()
    {
        filmArrayList = new ArrayList<>();
        filmArrayList.addAll(fdbH.getFilmData(fdb));
        if (filmArrayList.isEmpty())
        {
            Toast.makeText(this, "Your list is empty... click the (+) button to add new films to your database.\n", Toast.LENGTH_LONG).show();
        }
        filmsAdapter = new ArrayAdapter(this, R.layout.list_textview, filmArrayList);
        filmsListView.setAdapter(filmsAdapter);
    }//private void setupFilmList()

    private void initDB()
    {
        // Initiate the DBHandler Class
        fdbH = new DBHandler(this);

        // Retrieve a readable and writeable database
        fdb = fdbH.getWritableDatabase();

    }//private void initDB()

    @Override
    protected void onRestart()
    {
        super.onRestart();
        //refreshing the films list
        setupFilmList();
    }


    //adding a search menu to the layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) (menuItem.getActionView());
        searchView.setQueryHint("Search by Film Title...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                //starting the search activity on text change
                startActivity(new Intent(getBaseContext(), SearchActivity.class));
                return true;
            }
        });

        return true;
    }

}//public class FilmsActivity extends BaseActivity