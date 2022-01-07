package ca.michaelbalcerzak.budgettozero.ui;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import ca.michaelbalcerzak.budgettozero.R;

public class AddCategory extends Activity {
    EditText _displayName, _make, _model, _year, _odometer;
    RadioButton _km, _mile, _liters, _usGallons, _imperialGallons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ca.michaelbalcerzak.budgettozero.R.layout.addnewcategory);
        setupVariables();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private void setupVariables(){

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.budget_frequency_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
/*        _displayName = findViewById(R.id.editTextDisplayName);
        _make = findViewById(R.id.editTextmake);
        _model = findViewById(R.id.editTextModel);
        _year = findViewById(R.id.editTextYear);
        _odometer = findViewById(R.id.editTextOdometer);
        _km = findViewById(R.id.radioButtonDistanceKM);
        _mile = findViewById(R.id.radioButtonDistanceMiles);
        _liters = findViewById(R.id.radioButtonFillupLiters);
        _usGallons = findViewById(R.id.radioButtonFillUpUSGallon);
        _imperialGallons = findViewById(R.id.radioButtonFillUpImperialGallon);*/
    }



    public void saveVehicleClicked(View view) {
    }
}
