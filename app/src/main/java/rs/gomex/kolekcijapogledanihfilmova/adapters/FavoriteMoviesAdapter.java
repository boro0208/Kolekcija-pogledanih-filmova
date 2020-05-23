package rs.gomex.kolekcijapogledanihfilmova.adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import rs.gomex.kolekcijapogledanihfilmova.helper.db.MovieDb;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.CustomViewHolder> {

    private Context context;
    private List<MovieDb> movies;
    private OnFavoriteMovieClickListener onFavoriteMovieClickListener;

    public FavoriteMoviesAdapter(Context context, List<MovieDb> movies, OnFavoriteMovieClickListener onFavoriteMovieClickListener) {
        this.context = context;
        this.movies = movies;
        this.onFavoriteMovieClickListener = onFavoriteMovieClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;

        TextView txtTitle,txtDate;
        private ImageView coverImage;
        OnFavoriteMovieClickListener onFavoriteMovieClickListener;

        CustomViewHolder(View itemView, OnFavoriteMovieClickListener onFavoriteMovieClickListener) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.tvTitle);
            txtTitle.setTypeface(txtTitle.getTypeface(), Typeface.BOLD);
            txtDate = mView.findViewById(R.id.tvDate);
            txtDate.setTypeface(txtDate.getTypeface(), Typeface.ITALIC);
            coverImage = mView.findViewById(R.id.ivMovie);
            itemView.setOnClickListener(this);
            this.onFavoriteMovieClickListener = onFavoriteMovieClickListener;
        }

        @Override
        public void onClick(View v) {
            onFavoriteMovieClickListener.OnMovieClick(movies.get(getAdapterPosition()).getId());
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_movie, parent, false);
        return new CustomViewHolder(view,onFavoriteMovieClickListener);
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

    public interface OnFavoriteMovieClickListener {
        void OnMovieClick(int id);
    }
}
