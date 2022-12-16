package ca.michaelbalcerzak.budgettozero;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
import ca.michaelbalcerzak.budgettozero.databinding.ActivityMainBinding;
import ca.michaelbalcerzak.budgettozero.ui.AddCategory;
import ca.michaelbalcerzak.budgettozero.ui.AddPurchase;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Button _selectedCategoryLabel;
    private int _categoryIndex = 0;
    private ArrayList<CategoryInfoStruct> _allCategories;
    final String SUMMARY = "Summary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.michaelbalcerzak.budgettozero.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            Intent openAddNewPurchase = new Intent(MainActivity.this, AddPurchase.class);
            openAddNewPurchase.putExtra("categoryPk", _allCategories.get(_categoryIndex).getCategoryPk());
            startActivity(openAddNewPurchase);
                });
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
        updateDisplayForCategory(SUMMARY);
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
        _selectedCategoryLabel = findViewById(R.id.selectedCategoryHeader);
        _allCategories = new CategoryAdmin(this).getAllCategories();
        if(!_allCategories.isEmpty()){
            _allCategories.add(0, new CategoryInfoStruct(null, getResources().getString(R.string.Summary), null, null, null));
            _selectedCategoryLabel.setText(getResources().getString(R.string.Summary));
        }

        _selectedCategoryLabel.setOnClickListener(view -> {
            Intent openAddNewCategory = new Intent(MainActivity.this, AddCategory.class);
            startActivity(openAddNewCategory);
        });
        ImageButton _nextCategory = findViewById(R.id.nextCategory);
        _nextCategory.setOnClickListener(view -> {
            if(++_categoryIndex == _allCategories.size()){
                _categoryIndex = 0;
            }
            _selectedCategoryLabel.setText(_allCategories.get(_categoryIndex).getName());
            updateDisplayForCategory(_allCategories.get(_categoryIndex).getName());
        });
        ImageButton _previousCategory = findViewById(R.id.previousCategory);
        _previousCategory.setOnClickListener(view -> {
            if(--_categoryIndex < 0){
                _categoryIndex = _allCategories.size() - 1;
            }
            _selectedCategoryLabel.setText(_allCategories.get(_categoryIndex).getName());
            updateDisplayForCategory(_allCategories.get(_categoryIndex).getName());
        });
    }

    private void updateBreakdown(String categoryName){
        TextView textviewStartingAmount = findViewById(R.id.textView_startingAmount);
        TextView textviewRemainingAmount = findViewById(R.id.textView_remainingAmmount);

        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        if (categoryName.equals(SUMMARY)){
            textviewStartingAmount.setText(categoryAdmin.getSumOfTotalBudgets());
            textviewRemainingAmount.setText(categoryAdmin.getSumOfRemainingBudgetAmounts());
        }
        else {
            CategoryInfoStruct categoryByName = categoryAdmin.getCategoryByName(categoryName);
            if (categoryByName != null) {
                textviewStartingAmount.setText(categoryByName.getBudgetAmount());
                textviewRemainingAmount.setText(categoryByName.getRemainingBudgetAmount());
            }
        }
    }

    private void updateRecentHistory(String category){
        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(this);
        ArrayList<PurchaseInfoStruct> mostRecentPurchases;
        if(category.equals(SUMMARY)) {
            mostRecentPurchases = purchaseAdmin.get25MostRecentPurchases();
        } else{
            mostRecentPurchases = purchaseAdmin.get25MostRecentPurchasesForCategory(category);
        }
        PurchaseHistoryListView adapter=new PurchaseHistoryListView(this, mostRecentPurchases);
        ListView historyList = findViewById(R.id.historyList);
        historyList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createCategoryHeader();
        updateDisplayForCategory(SUMMARY);
    }

    private void updateDisplayForCategory(String category){
        updateBreakdown(category);
        updateRecentHistory(category);
    }
}