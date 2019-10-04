package mn.lab4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends Helper {
    TextView etName;
    EditText etOldPass;
    EditText etNewPass;
    EditText etReNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        etName = findViewById(R.id.etName);
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        etReNewPass = findViewById(R.id.etReNewPass);

        etName.setText(getDefaults(PREF_USERNAME));

        (findViewById(R.id.btnUpdate)).setOnClickListener(this);
        (findViewById(R.id.btnBack)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBack) {
            onBackPressed();
            return;
        }
        if (editTextIsEmpty(etOldPass) || editTextIsEmpty(etNewPass) || editTextIsEmpty(etReNewPass)) {
            return;
        }

        if (!getDefaults(PREF_PASSWORD).equals(getStringEditText(etOldPass))) {
            // TODO Old pass is wrong
            return;
        }
        if (!getStringEditText(etNewPass).equals(getStringEditText(etReNewPass))) {
            // TODO new pass is wrong
            return;
        }

        new UpdateTask().execute(getDefaults(PREF_USERNAME), getStringEditText(etNewPass));

        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            db.userDao().updatePassword(strings[0], strings[1]);
            return null;
        }
    }
}
