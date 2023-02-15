package com.example.fdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;

import com.example.fdb.R;

import java.util.ArrayList;

public class SearchActivity extends FilmsActivity {
    Button searchByTitleBtn;
    Button searchByYearBtn;
    Button searchByGenreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.activity_search);

        //setup controls
        searchByTitleBtn = findViewById(R.id.searchByTitleBtn);
        searchByYearBtn = findViewById(R.id.searchByYearBtn);
        searchByGenreBtn = findViewById(R.id.searchByGenreBtn);

    }//protected void onCreate(Bundle savedInstanceState)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.expandActionView();
        SearchView searchView = (SearchView) (menuItem.getActionView());

        //this the default search by title then the user can chose to search by year and by genre
        searchByTitle(searchView);

        searchByYearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQueryHint("Search by Film Year...");
                searchView.onActionViewExpanded();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        //refresh the Film List
                        ArrayList<String> searchByYearList = fdbH.searchFilmByYear(fdb, s);
                        filmArrayList.clear();
                        filmArrayList.addAll(searchByYearList);
                        filmsAdapter = new ArrayAdapter(SearchActivity.this, R.layout.list_textview, filmArrayList);
                        filmsListView.setAdapter(filmsAdapter);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        refreshFilmsListView();
                        return false;
                    }
                });

            }
        });//searchByYearBtn.setOnClickListener

        searchByTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByTitle(searchView);

            }
        });//searchByTitleBtn.setOnClickListener

        searchByGenreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQueryHint("Search by Film Genre...");
                searchView.onActionViewExpanded();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        ArrayList<String> searchByGenreList = fdbH.searchFilmByGenre(fdb, s);
                        filmArrayList.clear();
                        filmArrayList.addAll(searchByGenreList);
                        filmsAdapter = new ArrayAdapter(SearchActivity.this, R.layout.list_textview, filmArrayList);
                        filmsListView.setAdapter(filmsAdapter);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        refreshFilmsListView();
                        return false;
                    }
                });

            }
        });//searchByTitleBtn.setOnClickListener


        //setting actions when the search button is pressed (expanded)
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                startActivity(new Intent(getBaseContext(), FilmsActivity.class));
                return true;
            }
        });//menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener()
        return true;
    }//public boolean onCreateOptionsMenu(Menu menu)

    private void searchByTitle(SearchView searchView) {
        searchView.setQueryHint("Search by Film Title...");
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<String> searchByTitleList = fdbH.searchFilmByTitle(fdb, s);
                filmArrayList.clear();
                filmArrayList.addAll(searchByTitleList);
                filmsAdapter = new ArrayAdapter(SearchActivity.this, R.layout.list_textview, filmArrayList);
                filmsListView.setAdapter(filmsAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                refreshFilmsListView();
                return false;
            }
        });
    }//private void searchByTitle(SearchView searchView)

    private void refreshFilmsListView() {
        //refresh the film list
        filmArrayList.clear();
        filmArrayList.addAll(fdbH.getFilmData(fdb));
        filmsAdapter = new ArrayAdapter(SearchActivity.this, R.layout.list_textview, filmArrayList);
        filmsListView.setAdapter(filmsAdapter);
    }//private void refreshFilmsListView()

}//public class SearchActivity extends FilmsActivity