package sg.edu.rp.c346.id20042741.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    TextView tvTest;
    Button btnShow, btnInsert;
    EditText etMovieTitle, etMovieGenre, etYear;
    Spinner ratingsSpinner;
    ArrayAdapter ratingAdapter;
    String[] allRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShow = findViewById(R.id.btnShowList);
        btnInsert = findViewById(R.id.btnInsert);
        tvTest = findViewById(R.id.tvTest);
        etMovieTitle = findViewById(R.id.etMovieTitle);
        etMovieGenre = findViewById(R.id.etMovieGenre);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsert);
        ratingsSpinner = findViewById(R.id.spinnerRating);

        spinnerSetRatings();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieTitle = etMovieTitle.getText().toString();
                String genre = etMovieGenre.getText().toString();
                TextView textview = (TextView) ratingsSpinner.getSelectedView();
                String rating = textview.getText().toString();

                int year = 0;
                String pattern = "(19|20)(\\d){2}";
                Boolean regex = Pattern.matches(pattern, etYear.getText().toString());

                if(movieTitle.isEmpty() || genre.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill up the details!", Toast.LENGTH_SHORT).show();
                }else{
                    if(regex) {
                        year = Integer.parseInt(etYear.getText().toString());
                        DBHelper db = new DBHelper(MainActivity.this);
                        long result = db.insertData(movieTitle, genre, rating, year);

                        if(result != -1) {
                            Toast.makeText(MainActivity.this, "Successfully inserted a new record!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                    else Toast.makeText(MainActivity.this, "Ensure year starts from 19 or 20 and followed by 2 digits!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DBHelper(MainActivity.this);
                Intent i = new Intent(MainActivity.this, ShowMoviesActivity.class);
                startActivity(i);
            }
        });
    }

    public void spinnerSetRatings(){
        allRatings = new String[]{"G", "PG", "PG13", "NC16", "M18", "R21"};
        ratingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allRatings);
        ratingsSpinner.setAdapter(ratingAdapter);
    }
}