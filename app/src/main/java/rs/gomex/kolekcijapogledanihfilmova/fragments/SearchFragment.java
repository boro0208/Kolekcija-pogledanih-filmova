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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.gomex.kolekcijapogledanihfilmova.R;
import rs.gomex.kolekcijapogledanihfilmova.adapters.SearchResultAdapter;
import rs.gomex.kolekcijapogledanihfilmova.model.Movie;
import rs.gomex.kolekcijapogledanihfilmova.model.Search;
import rs.gomex.kolekcijapogledanihfilmova.network.EndPoint;
import rs.gomex.kolekcijapogledanihfilmova.network.RetrofitClientInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchResultAdapter.OnMovieClickListener{

    EditText searchText;
    Button btnSearch;
    List<Movie> movies;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        searchText = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        searchMovie();
    }

    private void searchMovie(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchText.getText().toString().trim();
                getMovies(text);
            }
        });
    }

    private void getMovies(String name){
        Map<String, String> query = new HashMap<String, String>();
        query.put("apikey", "c5ca7131");
        query.put("s", name);

        EndPoint service = RetrofitClientInstance.getRetrofitInstance().create(EndPoint.class);
        Call<Search> call = service.getSearchResult(query);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (response.code() == 200) {
                    Search search = response.body();

                    movies = new ArrayList<>();

                    for(Movie m : search.getMovies()) {
                        movies.add(m);
                    }

                    generateDataList(movies);

                    search.getResponse();
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Movie> movieList) {
        RecyclerView recyclerView = getView().findViewById(R.id.rvMovie);
        SearchResultAdapter adapter = new SearchResultAdapter(getContext(),movieList,SearchFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnMovieClick(String id,String film) {

        Toast.makeText(getContext(), ""+film+" uspesno dodat", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        MovieDetailFragment detailFragment= new MovieDetailFragment();
        detailFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrame, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}
