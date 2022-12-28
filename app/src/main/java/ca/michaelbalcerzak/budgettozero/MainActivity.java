package ca.michaelbalcerzak.budgettozero;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import ca.michaelbalcerzak.budgettozero.Database.CategoryAdmin;
import ca.michaelbalcerzak.budgettozero.Database.PurchaseAdmin;
import ca.michaelbalcerzak.budgettozero.Database.SettingsAdmin;
import ca.michaelbalcerzak.budgettozero.databinding.ActivityMainBinding;
import ca.michaelbalcerzak.budgettozero.ui.AddCategory;
import ca.michaelbalcerzak.budgettozero.ui.AddPurchase;
import ca.michaelbalcerzak.budgettozero.ui.EditCategory;
import ca.michaelbalcerzak.budgettozero.ui.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    final String SUMMARY = "Summary";
    private AppBarConfiguration mAppBarConfiguration;
    private Button _selectedCategoryLabel;
    private int _categoryIndex = 0;
    private ArrayList<CategoryInfoStruct> _allCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ca.michaelbalcerzak.budgettozero.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            if (_allCategories.size() == 0) {
                Toast.makeText(this, R.string.purchase_no_category,
                        Toast.LENGTH_LONG).show();
            } else {
                Intent openAddNewPurchase = new Intent(MainActivity.this, AddPurchase.class);
                openAddNewPurchase.putExtra("categoryPk", _allCategories.get(_categoryIndex).getCategoryPk());
                startActivity(openAddNewPurchase);
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent openSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(openSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void createCategoryHeader() {
        _selectedCategoryLabel = findViewById(R.id.selectedCategoryHeader);
        registerForContextMenu(_selectedCategoryLabel);
        _allCategories = new CategoryAdmin(this).getAllCategories();
        if (!_allCategories.isEmpty()) {
            _allCategories.add(0, new CategoryInfoStruct(null, getResources().getString(R.string.Summary), null, null, null));
            _selectedCategoryLabel.setText(getResources().getString(R.string.Summary));
        }

        ImageButton _nextCategory = findViewById(R.id.nextCategory);
        _nextCategory.setOnClickListener(view -> {
            if (++_categoryIndex >= _allCategories.size()) {
                _categoryIndex = 0;
            }
            updateHeaderOnArrowClick();
        });
        ImageButton _previousCategory = findViewById(R.id.previousCategory);
        _previousCategory.setOnClickListener(view -> {
            if (--_categoryIndex < 0) {
                _categoryIndex = _allCategories.size() - 1;
            }
            updateHeaderOnArrowClick();
        });
    }

    private void updateHeaderOnArrowClick() {
        if (!_allCategories.isEmpty()) {
            _selectedCategoryLabel.setText(_allCategories.get(_categoryIndex).getName());
            updateDisplayForCategory(_allCategories.get(_categoryIndex).getName());
        }
    }

    private void updateBreakdown(String categoryName) {
        TextView textviewStartingAmount = findViewById(R.id.textView_startingAmount);
        TextView textviewRemainingAmount = findViewById(R.id.textView_remainingAmmount);

        CategoryAdmin categoryAdmin = new CategoryAdmin(this);
        if (categoryName.equals(SUMMARY)) {
            textviewStartingAmount.setText(categoryAdmin.getSumOfTotalBudgets());
            textviewRemainingAmount.setText(categoryAdmin.getSumOfRemainingBudgetAmounts());
        } else {
            CategoryInfoStruct categoryByName = categoryAdmin.getCategoryByName(categoryName);
            if (categoryByName != null) {
                textviewStartingAmount.setText(categoryByName.getBudgetAmount());
                textviewRemainingAmount.setText(categoryByName.getRemainingBudgetAmount());
            }
        }
    }

    private void updateRecentHistory(String category) {
        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(this);
        ArrayList<PurchaseInfoStruct> mostRecentPurchases;
        if (category.equals(SUMMARY)) {
            mostRecentPurchases = purchaseAdmin.get25MostRecentPurchases();
        } else {
            mostRecentPurchases = purchaseAdmin.get25MostRecentPurchasesForCategory(category);
        }
        PurchaseHistoryListView adapter = new PurchaseHistoryListView(this, mostRecentPurchases);
        ListView historyList = findViewById(R.id.historyList);
        historyList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createCategoryHeader();
        updateDisplayForCategory(SUMMARY);
    }

    private void updateDisplayForCategory(String category) {
        updateBreakdown(category);
        updateRecentHistory(category);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.selectedCategoryHeader) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent openAddNewCategory = new Intent(MainActivity.this, AddCategory.class);
                startActivity(openAddNewCategory);
                return true;
            case R.id.edit:
                if (_allCategories.size() == 0 || _allCategories.get(_categoryIndex).getCategoryPk() == null) {
                    Toast.makeText(this, R.string.edit_summary_error,
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent openEditNewCategory = new Intent(MainActivity.this, EditCategory.class);
                    openEditNewCategory.putExtra("categoryPk", _allCategories.get(_categoryIndex).getCategoryPk());
                    startActivity(openEditNewCategory);
                }
                return true;
            case R.id.reset:
                showResetConfirm();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showResetConfirm() {

        SettingsAdmin settingsAdmin = new SettingsAdmin(this);
        boolean clearHistoryOnReset = settingsAdmin.clearHistoryOnReset();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.reset_confirm_title) + " " + _allCategories.get(_categoryIndex).getName());
        if (clearHistoryOnReset) {
            builder.setMessage(R.string.reset_confirm_clear_history);
        } else {
            builder.setMessage(R.string.reset_confirm_spend_only);
        }
        builder.setPositiveButton(R.string.reset_confirm_confirm,
                (dialog, which) -> {
                    if (clearHistoryOnReset) {
                        PurchaseAdmin purchaseAdmin = new PurchaseAdmin(getApplicationContext());
                        purchaseAdmin.deleteAllPurchasesForCategory(_allCategories.get(_categoryIndex).getName());
                    }
                    CategoryAdmin categoryAdmin = new CategoryAdmin(getApplicationContext());
                    categoryAdmin.resetRemainingBudgetForCategory(_allCategories.get(_categoryIndex).getCategoryPk());
                    updateDisplayForCategory(_allCategories.get(_categoryIndex).getName());
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}