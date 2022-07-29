package sg.edu.rp.c346.id20042741.mymovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id; // layout id for the row the id for the custom layout (android.R.layout.row)
    ArrayList<Movies> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movies> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id,parent, false);

        TextView movieTitle = rowView.findViewById(R.id.movieTitleRow);
        TextView movieGenre = rowView.findViewById(R.id.movieGenreRow);
        TextView movieYear = rowView.findViewById(R.id.movieYearRow);
        ImageView movieRating = rowView.findViewById(R.id.movieRatingRow);

        // Obtain the android version information based on the position
        // this arraylist is retrieved from the arraylist we had created inside this class and parsed from the main class
        // since this method will be called a # of times, the position of each Row will be different and starting from 0
        // it will be safe to assume that we can populate the data based on the new position of the listview
        Movies currentVersion = movieList.get(position);

        // Set values to the TextView to display the corresponding information
        movieTitle.setText(currentVersion.getTitle());
        movieGenre.setText(currentVersion.getGenre());

        if(currentVersion.getRating().equalsIgnoreCase("G"))
            movieRating.setImageResource(R.drawable.rating_g);

        if(currentVersion.getRating().equalsIgnoreCase("PG"))
            movieRating.setImageResource(R.drawable.rating_pg);

        if(currentVersion.getRating().equalsIgnoreCase("PG13"))
            movieRating.setImageResource(R.drawable.rating_pg13);

        if(currentVersion.getRating().equalsIgnoreCase("NC16"))
            movieRating.setImageResource(R.drawable.rating_nc16);

        if(currentVersion.getRating().equalsIgnoreCase("M18"))
            movieRating.setImageResource(R.drawable.rating_m18);

        if(currentVersion.getRating().equalsIgnoreCase("R21"))
            movieRating.setImageResource(R.drawable.rating_r21);

        movieYear.setText(String.valueOf(currentVersion.getYear()));

        return rowView;
    }
}