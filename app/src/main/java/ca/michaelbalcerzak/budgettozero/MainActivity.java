package ca.michaelbalcerzak.budgettozero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.databinding.ActivityMainBinding;
import ca.michaelbalcerzak.budgettozero.ui.AddCategory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView _selectedCategoryLabel;
    private int _categoryIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.michaelbalcerzak.budgettozero.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        createCategoryHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void createCategoryHeader(){
        _selectedCategoryLabel = findViewById(R.id.selectedCategoryLabel);
        ArrayList<CategoryInfoStruct> allCategories = new CategoryAdmin(this).getAllCategories();
        if(!allCategories.isEmpty()){
            allCategories.add(0, new CategoryInfoStruct(null, getResources().getString(R.string.Summary), null, null));
            _selectedCategoryLabel.setText(getResources().getString(R.string.Summary));
        }
        ImageButton addNewCategory = findViewById(R.id.addNewCategory);
        addNewCategory.setOnClickListener(view -> {
            //  if(getVehiclePk() != -1) {
            Intent openAddNewCategory = new Intent(MainActivity.this, AddCategory.class);
            //openAddFillUp.putExtra("vehiclePK", _vehicles.get(_vehicleIndex).getVehiclePK());
            startActivity(openAddNewCategory);
            // }
        });
        ImageButton _nextCategory = findViewById(R.id.nextCategory);
        _nextCategory.setOnClickListener(view -> {
            if(++_categoryIndex == allCategories.size()){
                _categoryIndex = 0;
            }
            _selectedCategoryLabel.setText(allCategories.get(_categoryIndex).getName());
        });
        ImageButton _previousCategory = findViewById(R.id.previousCategory);
        _previousCategory.setOnClickListener(view -> {
            if(--_categoryIndex < 0){
                _categoryIndex = allCategories.size() - 1;
            }
            _selectedCategoryLabel.setText(allCategories.get(_categoryIndex).getName());
        });
    }
}