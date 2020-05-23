package rs.gomex.kolekcijapogledanihfilmova.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.gomex.kolekcijapogledanihfilmova.R;
import rs.gomex.kolekcijapogledanihfilmova.helper.DBHelper;
import rs.gomex.kolekcijapogledanihfilmova.helper.db.MovieDb;
import rs.gomex.kolekcijapogledanihfilmova.model.Movie;
import rs.gomex.kolekcijapogledanihfilmova.network.EndPoint;
import rs.gomex.kolekcijapogledanihfilmova.network.RetrofitClientInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    private String id;
    private ImageView detailPoster;
    private TextView detailTitle,detailType,detailYear,detailRuntime,detailPlot,detailGenre,detailLanguage,detailAwards;
    private Movie movie;
    private DBHelper dbHelper;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_movie_detail, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = getArguments().getString("id");
        detailPoster = view.findViewById(R.id.ivDetailPoster);
        detailTitle = view.findViewById(R.id.tvDetailTitle);
        detailType = view.findViewById(R.id.tvDetailType);
        detailYear = view.findViewById(R.id.tvDetailYear);
        detailRuntime = view.findViewById(R.id.tvDetailRunTime);
        detailPlot = view.findViewById(R.id.tvDetailPlot);
        detailGenre = view.findViewById(R.id.tvDetailGenre);
        detailLanguage = view.findViewById(R.id.tvDetailLanguage);
        detailAwards = view.findViewById(R.id.tvDetailAwards);

        getMovie(id);

    }

    private void getMovie(final String id){
        Map<String, String> query = new HashMap<String, String>();
        query.put("apikey", "c5ca7131");
        query.put("i", id);

        EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
        Call<Movie> call = service.getMovieDetail(query);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {
                    movie = response.body();

                    Picasso.Builder builder = new Picasso.Builder(getContext());
                    builder.downloader(new OkHttp3Downloader(getContext()));
                    builder.build().load(movie.getPoster())
                            .placeholder((R.drawable.ic_launcher_background))
                            .error(R.drawable.ic_launcher_background)
                            .into(detailPoster);

                    detailTitle.setText(movie.getTitle());
                    detailType.setText(movie.getType());
                    detailYear.setText(movie.getYear());
                    detailAwards.setText(movie.getAwards());
                    detailGenre.setText(movie.getGenre());
                    detailRuntime.setText(movie.getRuntime());
                    detailLanguage.setText(movie.getLanguage());
                    detailPlot.setText(movie.getPlot());


                    dbHelper = new DBHelper(getContext());
                    dbHelper.addItem(id,movie.getPoster(),movie.getTitle(),movie.getGenre(),movie.getYear(),movie.getRuntime(),movie.getPlot(),movie.getLanguage(),movie.getAwards());

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
//                dbHelper = new DBHelper(getContext());
//                try {
//                    dbHelper.deleteMovieById(Integer.valueOf(id));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
