package rs.gomex.kolekcijapogledanihfilmova.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.List;

import rs.gomex.kolekcijapogledanihfilmova.R;
import rs.gomex.kolekcijapogledanihfilmova.adapters.FavoriteMoviesAdapter;
import rs.gomex.kolekcijapogledanihfilmova.helper.DBHelper;
import rs.gomex.kolekcijapogledanihfilmova.helper.db.MovieDb;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment implements FavoriteMoviesAdapter.OnFavoriteMovieClickListener{

    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            getFavoriteMovies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getFavoriteMovies() throws SQLException {
        DBHelper db = new DBHelper(getContext());

        List<MovieDb> movieDbList = db.getAll();

        generateDataList(movieDbList);
    }

    private void generateDataList(List<MovieDb> movieList) {
        RecyclerView recyclerView = getView().findViewById(R.id.rvFavorite);
        FavoriteMoviesAdapter adapter = new FavoriteMoviesAdapter(getContext(),movieList,FavoriteMoviesFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnMovieClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        FavoriteDetailFragment detailFragment= new FavoriteDetailFragment();
        detailFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrame, detailFragment)
                .addToBackStack(null)
                .commit();

    }
}
