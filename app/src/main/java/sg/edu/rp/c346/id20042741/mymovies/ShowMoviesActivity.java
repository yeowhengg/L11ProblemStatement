package sg.edu.rp.c346.id20042741.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowMoviesActivity extends AppCompatActivity {
    ListView lvMovies;
    CustomAdapter aaMovie;
    ArrayList<Movies> alMovies;
    Button btnFilter;
    Spinner spinnerYear;
    ArrayAdapter<String> saMovie;
    Boolean filter = false;
    ArrayList<String> allRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movies);

        lvMovies = findViewById(R.id.lvMovies);
        btnFilter = findViewById(R.id.btnShowPG13);
        spinnerYear = findViewById(R.id.spinnerID);

        alMovies = new ArrayList<Movies>();

        dynamicRefresh();
        spinnerSetYear();

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ShowMoviesActivity.this, ModifyMoviesActivity.class);
                i.putExtra("obj", alMovies.get(position));
                startActivity(i);
                finish();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter) {
                    btnFilter.setText("ALL RATINGS");
                } else {
                    btnFilter.setText("SHOW ALL PG-13 MOVIES");
                }
                dynamicRefresh();
                aaMovie.notifyDataSetChanged();
                filter = !filter;
            }
        });

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper db = new DBHelper(ShowMoviesActivity.this);
                alMovies = db.filterByRating((String) parent.getItemAtPosition(position));
                aaMovie = new CustomAdapter(ShowMoviesActivity.this, R.layout.row, alMovies);
                lvMovies.setAdapter(aaMovie);
                aaMovie.notifyDataSetChanged();
                String currRating = (String) parent.getItemAtPosition(position);

                if(!currRating.equalsIgnoreCase("ALL RATINGS")){
                    btnFilter.setText("SHOW ALL RATINGS");
                    filter = false;
                }else{
                    btnFilter.setText("SHOW ALL PG-13 MOVIES");
                    filter = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void spinnerSetYear(){
        DBHelper db = new DBHelper(ShowMoviesActivity.this);
        allRatings = new ArrayList<String>();
        allRatings = db.returnDistinctRating();
        allRatings.add(0, "ALL RATINGS");
        saMovie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allRatings);
        spinnerYear.setAdapter(saMovie);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle retrMsg = getIntent().getExtras();
        String msg = "";
        if(retrMsg != null){
            msg = retrMsg.getString("msg", "-");
            Toast.makeText(ShowMoviesActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    public void dynamicRefresh(){
        DBHelper db = new DBHelper(ShowMoviesActivity.this);
        alMovies = db.filterPGRating(filter);
        aaMovie = new CustomAdapter(this, R.layout.row, alMovies);
        lvMovies.setAdapter(aaMovie);
        if(alMovies.isEmpty()) Toast.makeText(ShowMoviesActivity.this, "Nothing to display", Toast.LENGTH_SHORT).show();

    }

}