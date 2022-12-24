package ca.michaelbalcerzak.budgettozero.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import ca.michaelbalcerzak.budgettozero.Database.SettingsAdmin;
import ca.michaelbalcerzak.budgettozero.R;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SettingsAdmin settingsAdmin = new SettingsAdmin(this);
        if(settingsAdmin.clearHistoryOnReset()){
            Switch clearSettingsOnReset = findViewById(R.id.switchClearHistoryOnReset);
            clearSettingsOnReset.setChecked(true);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    public void saveSettingsClicked(View view) {
        SettingsAdmin settingsAdmin = new SettingsAdmin(this);
        Switch clearSettingsOnReset = findViewById(R.id.switchClearHistoryOnReset);
        if(clearSettingsOnReset.isChecked()){
            settingsAdmin.enableSettings(new String[] {settingsAdmin.CLEAR_HISTORY_ON_RESET});
        } else{
            settingsAdmin.disableSettings(new String[] {settingsAdmin.CLEAR_HISTORY_ON_RESET});
        }
        finish();
    }
}
