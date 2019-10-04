package mn.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import mn.lab4.db.AppDatabase;

abstract class Helper extends AppCompatActivity implements View.OnClickListener {
    static final String USERNAME = "USERNAME";
    static final String PASSWORD = "PASSWORD";
    static final String PREF_USERNAME = "PREF_USERNAME";

    protected AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lab-4").build();
    }

    public boolean editTextIsEmpty(EditText editText) {
        return (editText.getText().toString().trim().length() == 0);
    }

    public String getStringEditText(EditText editText) {
        String text = "";
        if (!editTextIsEmpty(editText)) {
            text = editText.getText().toString();
        }
        return text;
    }

    public String getExtraIntent(String name) {
        String text = "";
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            text = bundle.getString(name);
        }

        return text;
    }

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setDefaults(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getDefaults(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getString(key, null);
    }

    protected void initialize() {
    }
}