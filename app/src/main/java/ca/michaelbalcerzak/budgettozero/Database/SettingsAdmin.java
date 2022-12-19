package ca.michaelbalcerzak.budgettozero.Database;

import android.content.Context;

public class SettingsAdmin extends MainDatabase{

    public SettingsAdmin(Context context) {
        super(context);
    }

    public boolean clearHistoryOnReset(){
        return false;
    }
}
