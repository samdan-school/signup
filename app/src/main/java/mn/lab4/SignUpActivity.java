package mn.lab4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mn.lab4.db.User;

public class SignUpActivity extends Helper {
    private TextView tvName;
    private EditText etRePass;
    private EditText etAge;
    private EditText etSex;
    private EditText etPhoneNumber;
    private DatePicker dpDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FindTask().execute(getExtraIntent(USERNAME));

        setContentView(R.layout.sign_up);

        tvName = findViewById(R.id.tvName);
        etRePass = findViewById(R.id.etRePass);
        etAge = findViewById(R.id.etAge);
        etSex = findViewById(R.id.etSex);
        etPhoneNumber = findViewById(R.id.etPhoneNum);
        dpDob = findViewById(R.id.dpDob);

        tvName.setText(getExtraIntent(USERNAME));

        (findViewById(R.id.btnSave)).setOnClickListener(this);
        (findViewById(R.id.btnBack)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBack) {
            onBackPressed();
            return;
        }
        if (!getExtraIntent(PASSWORD).equals(getStringEditText(etRePass))) {
            showToast("Password incorrect!");
            return;
        }
        if ((editTextIsEmpty(etRePass) || editTextIsEmpty(etAge) || editTextIsEmpty(etSex) || editTextIsEmpty(etPhoneNumber))) {
            showToast("All fields should be filled!");
            return;
        }

        User user = new User();
        user.name = getExtraIntent(USERNAME);
        user.password = getExtraIntent(PASSWORD);
        user.age = Integer.parseInt(getStringEditText(etAge));
        user.sex = getStringEditText(etSex);
        user.phoneNumber = Integer.parseInt(getStringEditText(etPhoneNumber));
        user.dob = new GregorianCalendar(
                dpDob.getYear(),
                dpDob.getMonth(),
                dpDob.getDayOfMonth()).getTime();
        try {
            new InsertTask().execute(user);
        } catch (Exception e) {
            Log.e("Sign up", e.toString());
            onBackPressed();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertTask extends AsyncTask<User, Void, User> {
        @Override
        protected User doInBackground(User... users) {
            db.userDao().insertUser(users[0]);
            return users[0];
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            progress(user);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FindTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {
            return db.userDao().findByName(strings[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user.name.equals(getExtraIntent(USERNAME))) {
                showToast("Name is taken!");
                onBackPressed();
            }
        }
    }

    private void progress(User user) {
        showToast("Sign up success!");

        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.putExtra(USERNAME, user.name);
        intent.putExtra(PASSWORD, user.password);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
