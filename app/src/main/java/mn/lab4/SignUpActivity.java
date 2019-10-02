package mn.lab4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.GregorianCalendar;

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
        if ((editTextIsEmpty(etRePass) && editTextIsEmpty(etAge) && editTextIsEmpty(etSex) && editTextIsEmpty(etPhoneNumber))
                ||
                !getExtraIntent(PASSWORD).equals(getStringEditText(etRePass))) {
            return;
        }

        switch (view.getId()) {
            case R.id.btnSave:
                Log.i("Sign up", "user dsadasdasdasdasdasdasdasdasdasdas");
                // TODO Create user
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
//                    user = .addUser(db, user);
                } catch (Exception e) {
                    Log.e("Sign up", e.toString());
                }

                break;
            default:
                onBackPressed();
        }
    }
}