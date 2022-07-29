package sg.edu.rp.c346.id20042741.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ModifyMoviesActivity extends AppCompatActivity {
    TextView tvTest;
    EditText etMovieId, etMovieTile, etMovieGenre, etMovieYear;
    Button btnUpdate, btnDelete, btnCancel;
    Spinner currRating;
    ArrayAdapter<String> ratingAdapter;
    String[] ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_movies);

        Intent getObj = getIntent();
        Movies data = (Movies) getObj.getSerializableExtra("obj");

        Intent i = new Intent(ModifyMoviesActivity.this, ShowMoviesActivity.class);

        ratings = new String[] {"G", "PG", "PG13", "NC16", "M18", "R21"};

        etMovieId = findViewById(R.id.etMovieID);
        etMovieTile = findViewById(R.id.etMovieTitle);
        etMovieGenre = findViewById(R.id.etGenre);
        etMovieYear = findViewById(R.id.etYear2);
        currRating = findViewById(R.id.modifySpinnerID);
        ratingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ratings);
        currRating.setAdapter(ratingAdapter);

        for(int j = 0; j < ratings.length; j++){
            if(ratings[j].equalsIgnoreCase(data.getRating()))
                currRating.setSelection(j);
        }

        btnUpdate = findViewById(R.id.btnUpdate2);
        btnDelete = findViewById(R.id.btnDelete2);
        btnCancel = findViewById(R.id.btnCancel);

        etMovieId.setText(String.format("%d",data.get_id()));
        etMovieTile.setText(data.getTitle());
        etMovieGenre.setText(data.getGenre());
        etMovieYear.setText(String.format("%d", data.getYear()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = data.get_id();
                String title = etMovieTile.getText().toString();
                String genre = etMovieGenre.getText().toString();
                TextView textView = (TextView)currRating.getSelectedView();
                String rating = textView.getText().toString();

                int year = Integer.parseInt(etMovieYear.getText().toString());
                Movies movie = new Movies(id, title, genre, rating, year);

                DBHelper db = new DBHelper (ModifyMoviesActivity.this);
                long result = db.updateData(movie);

                if(result != -1){
                    i.putExtra("msg", "Successfully updated");
                    startActivity(i);
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(ModifyMoviesActivity.this);
                int indexToDelete = data.get_id();
                long result = db.deleteData(indexToDelete);

                if(result != -1){
                    i.putExtra("msg", "Successfully deleted!");
                    startActivity(i);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("msg", "cancelled");
                startActivity(i);
                finish();
            }
        });



    }
}