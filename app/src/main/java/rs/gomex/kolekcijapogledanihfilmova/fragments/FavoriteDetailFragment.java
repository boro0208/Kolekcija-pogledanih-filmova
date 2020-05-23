package rs.gomex.kolekcijapogledanihfilmova.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;

import rs.gomex.kolekcijapogledanihfilmova.R;
import rs.gomex.kolekcijapogledanihfilmova.helper.DBHelper;
import rs.gomex.kolekcijapogledanihfilmova.helper.db.MovieDb;
import rs.gomex.kolekcijapogledanihfilmova.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteDetailFragment extends Fragment {

    private int id;
    private ImageView detailPoster;
    private TextView detailTitle,detailType,detailYear,detailRuntime,detailPlot,detailGenre,detailLanguage,detailAwards;
    private Movie movie;
    private DBHelper dbHelper;

    public FavoriteDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = getArguments().getInt("id");
        detailPoster = view.findViewById(R.id.ivDetailPoster);
        detailTitle = view.findViewById(R.id.tvDetailTitle);
        detailType = view.findViewById(R.id.tvDetailType);
        detailYear = view.findViewById(R.id.tvDetailYear);
        detailRuntime = view.findViewById(R.id.tvDetailRunTime);
        detailPlot = view.findViewById(R.id.tvDetailPlot);
        detailGenre = view.findViewById(R.id.tvDetailGenre);
        detailLanguage = view.findViewById(R.id.tvDetailLanguage);
        detailAwards = view.findViewById(R.id.tvDetailAwards);

        try {
            getMovieById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getMovieById(int id) throws SQLException {
        DBHelper db = new DBHelper(getContext());

        MovieDb movieDb =db.getMovieById(id);

        detailTitle.setText(movieDb.getTitle());
        detailRuntime.setText(movieDb.getRuntime());
        detailYear.setText(movieDb.getYear());

        detailPlot.setText(movieDb.getPlot());
        detailGenre.setText(movieDb.getGenre());
        detailLanguage.setText(movieDb.getLanguage());
        detailAwards.setText(movieDb.getAwards());

        Picasso.Builder builder = new Picasso.Builder(getContext());
        builder.downloader(new OkHttp3Downloader(getContext()));
        builder.build().load(movieDb.getPoster())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(detailPoster);

    }
}
