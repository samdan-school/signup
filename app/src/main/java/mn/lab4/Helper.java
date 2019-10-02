package mn.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import mn.lab4.db.AppDatabase;

abstract class Helper extends AppCompatActivity implements View.OnClickListener {
    static final String USERNAME = "USERNAME";
    static final String PASSWORD = "PASSWORD";

    protected AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "lab-4").build();
    }

    public void saveState(Intent intent, String stateName, boolean bSave, Bundle... state) {
    }

    public void restoreState(String stateName, State... states) {
    }

    class State {
        View view;
        View view2;
        String key;

        State(View view, String key) {
            this.view = view;
            this.key = key;
        }

        State(View view) {
            this.view = view;
        }

        State(View view, View view2) {
            this.view = view;
            this.view2 = view2;
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}