package sg.edu.rp.c346.id20042741.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MOVIE = "movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_YEAR = "year";
    

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovieMOVIETableSQL = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY " +
                        "AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER ) ", TABLE_MOVIE,
                COLUMN_ID,  COLUMN_TITLE, COLUMN_GENRE, COLUMN_RATING, COLUMN_YEAR);

        db.execSQL(createMovieMOVIETableSQL);
        Log.i("info", "created table");

        //dummy record
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, "Ju On");
        values.put(COLUMN_GENRE, "Horror");
        values.put(COLUMN_RATING, "PG13");
        values.put(COLUMN_YEAR, "2014");
        
        db.insert(TABLE_MOVIE, null, values);

        Log.i("info", "dummy created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

    public long insertData(String title, String singer, String rating, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE,  singer);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_YEAR, year);
        long result = db.insert(TABLE_MOVIE, null, values);
        return result;
    }

    public long updateData(Movies updateData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, updateData.getTitle());
        values.put(COLUMN_GENRE, updateData.getGenre());
        values.put(COLUMN_RATING, updateData.getRating());
        values.put(COLUMN_YEAR, updateData.getYear());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(updateData.get_id())};
        long result = db.update(TABLE_MOVIE, values, condition, args);
        db.close();
        return result;
    }

    public long deleteData (int indexToDelete){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(indexToDelete)};
        long result = db.delete(TABLE_MOVIE, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Movies> getPGRating(Boolean filter){
        ArrayList<Movies> movieAL = new ArrayList<Movies>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_RATING, COLUMN_YEAR};
        String conditions = filter ? COLUMN_RATING + "= ?" : null;
        String[] args = filter ? new String[]{"PG13"} : null;
        Cursor cursor = db.query(TABLE_MOVIE, columns, conditions, args, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                String rating = cursor.getString(3);
                int year = cursor.getInt(4);
                Movies movie = new Movies(id, title, genre, rating, year);
                movieAL.add(movie);

            }while(cursor.moveToNext());

            cursor.close();
            db.close();
        }
        return movieAL;
    }

    public ArrayList<Movies> filterByRating (String filteredRating){

        if(filteredRating.equalsIgnoreCase("ALL RATINGS")){
            return getPGRating(false);
        }else{
            ArrayList<Movies> alFilteredMovie = new ArrayList<Movies>();
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_RATING, COLUMN_YEAR};
            String conditions = COLUMN_RATING + "= ?";
            String[] args = {filteredRating};
            Cursor cursor = db.query(TABLE_MOVIE, columns, conditions, args, null, null, null, null);

            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(0);
                    String title = cursor.getString(1);
                    String genre = cursor.getString(2);
                    String rating = cursor.getString(3);
                    int year = cursor.getInt(4);
                    Movies movie = new Movies(id, title, genre, rating, year);

                    alFilteredMovie.add(movie);

                }while(cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return alFilteredMovie;
        }

    }

    public ArrayList<String> returnDistinctRating (){

        ArrayList<String> alDisRating = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"rating"};
        Cursor cursor = db.query(true, TABLE_MOVIE, columns, null, null, null ,null, null, null);

        if(cursor.moveToFirst()){
            do{
                String rating = cursor.getString(0);
                alDisRating.add(rating);
            }while(cursor.moveToNext());
        }

        return alDisRating;
    }
}
