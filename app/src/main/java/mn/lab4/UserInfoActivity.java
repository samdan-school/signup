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

import java.util.Calendar;
import java.util.GregorianCalendar;

import mn.lab4.db.User;

public class UserInfoActivity extends Helper {
    private TextView tvName;
    private EditText etAge;
    private EditText etSex;
    private EditText etPhoneNumber;
    private DatePicker dpDob;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new FindTask().execute(getExtraIntent(USERNAME));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBack) {
            onBackPressed();
            return;
        }
        if (editTextIsEmpty(etAge) || editTextIsEmpty(etSex) || editTextIsEmpty(etPhoneNumber)) {
            return;
        }

        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.btnUpdate:
                User newUser = user;
                newUser.age = Integer.parseInt(getStringEditText(etAge));
                newUser.sex = getStringEditText(etSex);
                newUser.phoneNumber = Integer.parseInt(getStringEditText(etPhoneNumber));
                newUser.dob = new GregorianCalendar(
                        dpDob.getYear(),
                        dpDob.getMonth(),
                        dpDob.getDayOfMonth()).getTime();
                new UpdateTask().execute(newUser);
                return;
            case R.id.btnChangePass:
                intent.setClass(this, ChangePasswordActivity.class);
                break;
            default:
                intent.setClass(this, this.getClass());
        }

        startActivity(intent);
    }

    @Override
    protected void initialize() {
        setContentView(R.layout.user_info);

        tvName = findViewById(R.id.tvName);
        etAge = findViewById(R.id.etAge);
        etSex = findViewById(R.id.etSex);
        etPhoneNumber = findViewById(R.id.etPhoneNum);
        dpDob = findViewById(R.id.dpDob);

        (findViewById(R.id.btnUpdate)).setOnClickListener(this);
        (findViewById(R.id.btnChangePass)).setOnClickListener(this);
        (findViewById(R.id.btnBack)).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        goHome();
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
            progress(user);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateTask extends AsyncTask<User, Void, User> {
        @Override
        protected User doInBackground(User... users) {
            db.userDao().updateUser(users[0]);
            return users[0];
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            progress(user);
        }
    }

    private void progress(User user) {
        if (user == null) {
            onBackPressed();
            return;
        }

        if (!user.password.equals(getExtraIntent(PASSWORD))) {
            onBackPressed();
            return;
        }

        if (this.user == null) {
            this.user = user;
            initialize();
        }

        setDefaults(PREF_USERNAME, user.name);
        setDefaults(PREF_PASSWORD, user.password);

        tvName.setText(user.name);
        etAge.setText(user.age + "");
        etSex.setText(user.sex + "");
        etPhoneNumber.setText(user.phoneNumber + "");

        Calendar cal = Calendar.getInstance();
        cal.setTime(user.dob);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dpDob.updateDate(year, month, day);
    }
}
