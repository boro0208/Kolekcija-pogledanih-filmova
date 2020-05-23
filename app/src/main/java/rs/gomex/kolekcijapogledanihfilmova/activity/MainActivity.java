package rs.gomex.kolekcijapogledanihfilmova.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.SQLException;

import rs.gomex.kolekcijapogledanihfilmova.R;
import rs.gomex.kolekcijapogledanihfilmova.fragments.FavoriteMoviesFragment;
import rs.gomex.kolekcijapogledanihfilmova.fragments.SearchFragment;
import rs.gomex.kolekcijapogledanihfilmova.helper.DBHelper;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ImageView closeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureNavigationDrawer();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setDisplayHomeAsUpEnabled(true);


        FavoriteMoviesFragment detailFragment= new FavoriteMoviesFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrame, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.nav_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return false;
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navigationView);

        View headerview = navView.getHeaderView(0);
        closeMenu =  headerview.findViewById(R.id.closeMenu);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment f = null;
                int itemId = menuItem.getItemId();

                if (itemId == R.id.podesavanja) {

                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.pretraga) {
                    f = new SearchFragment();
                }
                if (itemId == R.id.mojiFilmovi) {
                    f = new FavoriteMoviesFragment();
                }
                if (itemId == R.id.obrisi) {
                    showDialog();
                }
                if (f != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrame, f).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });

        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button dialogButton = dialog.findViewById(R.id.btn_yes);
        Button dialogButton2 = dialog.findViewById(R.id.btn_no);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbHelper = new DBHelper(MainActivity.this);
                try {
                    dbHelper.deleteAll();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Kolekcija filmova je uspesno obrisana", Toast.LENGTH_SHORT).show();
            }
        });

        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        } );

        dialog.show();

    }
}
