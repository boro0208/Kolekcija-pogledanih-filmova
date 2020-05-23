package rs.gomex.kolekcijapogledanihfilmova.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

import rs.gomex.kolekcijapogledanihfilmova.helper.db.MovieDb;

public class DBHelper extends OrmLiteSqliteOpenHelper {


    public static final String DB_NAME = "movie.db";
    private static final int DB_VERSION = 2;
    private Dao<MovieDb, Integer> movieDao;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, MovieDb.class);

        } catch (SQLException | java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, MovieDb.class, true);
            onCreate(database, connectionSource);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<MovieDb, Integer> getMovieDao() throws java.sql.SQLException {
        if (movieDao == null) {
            movieDao = getDao(MovieDb.class);
        }
        return movieDao;
    }

    @Override
    public void close() {
        movieDao = null;
        super.close();
    }

    public void addItem(String movieId,String poster,String title,String genre,String year,String runtime,String plot, String language, String awards){
        MovieDb movie = new MovieDb();
        movie.setMovieId(movieId);
        movie.setPoster(poster);
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setRuntime(runtime);
        movie.setPlot(plot);
        movie.setLanguage(language);
        movie.setAwards(awards);
        try {
            getMovieDao().create(movie);
        } catch (java.sql.SQLException e) {
            e.getMessage();
        }
    }

    public List<MovieDb> getAll() throws java.sql.SQLException {

        List<MovieDb> movie = getMovieDao().queryForAll();

        return movie;

    }

    public MovieDb getMovieById(int id) throws java.sql.SQLException {
        MovieDb movieDb = getMovieDao().queryForId(id);

        return movieDb;
    }

    public void deleteMovieById(int id) throws java.sql.SQLException {
        getMovieDao().deleteById(id);
    }

    public void deleteAll() throws java.sql.SQLException {
        DeleteBuilder<MovieDb, Integer> deleteBuilder = getMovieDao().deleteBuilder();
        deleteBuilder.where().isNotNull("id");
        deleteBuilder.delete();
    }


}
