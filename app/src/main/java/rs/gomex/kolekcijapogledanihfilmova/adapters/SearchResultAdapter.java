package rs.gomex.kolekcijapogledanihfilmova.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import rs.gomex.kolekcijapogledanihfilmova.R;
import rs.gomex.kolekcijapogledanihfilmova.model.Movie;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.CustomViewHolder> {
    private Context context;
    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;

    public SearchResultAdapter(Context context, List<Movie> movies, OnMovieClickListener onMovieClickListener) {
        this.context = context;
        this.movies = movies;
        this.onMovieClickListener = onMovieClickListener;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;

        TextView txtTitle, txtDate;
        private ImageView coverImage;
        OnMovieClickListener onMovieClickListener;

        CustomViewHolder(View itemView, OnMovieClickListener onMovieClickListener) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.tvTitle);
            txtDate = mView.findViewById(R.id.tvDate);
            coverImage = mView.findViewById(R.id.ivMovie);
            itemView.setOnClickListener(this);
            this.onMovieClickListener = onMovieClickListener;
        }

        @Override
        public void onClick(View v) {
            onMovieClickListener.OnMovieClick(movies.get(getAdapterPosition()).getImdbID(),movies.get(getAdapterPosition()).getTitle());
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_movie, parent, false);
        return new CustomViewHolder(view, onMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

        holder.txtTitle.setText(movies.get(position).getTitle());
        holder.txtDate.setText(movies.get(position).getYear());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(movies.get(position).getPoster())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnMovieClickListener {
        void OnMovieClick(String id,String film);
    }
}
