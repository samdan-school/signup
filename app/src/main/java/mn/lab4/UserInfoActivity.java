package mn.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import mn.lab4.db.User;

public class UserInfoActivity extends Helper {
    private EditText etAge;
    private EditText etSex;
    private EditText etPhoneNumber;
    private DatePicker dpDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = UserRepository.find(db, getExtraIntent(USERNAME));
        if (user == null) {
            onBackPressed();
            return;
        }

        if (user.password.equals(getExtraIntent(PASSWORD))) {
            onBackPressed();
            return;
        }

        setContentView(R.layout.user_info);

        etAge = findViewById(R.id.etAge);
        etSex = findViewById(R.id.etSex);
        etPhoneNumber = findViewById(R.id.etPhoneNum);
        dpDob = findViewById(R.id.dpDob);

        (findViewById(R.id.btnUpdate)).setOnClickListener(this);
        (findViewById(R.id.btnChangePass)).setOnClickListener(this);
        (findViewById(R.id.btnBack)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBack) {
            onBackPressed();
            return;
        }
        if (editTextIsEmpty(etAge) && editTextIsEmpty(etSex) && editTextIsEmpty(etPhoneNumber)) {
            return;
        }

        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.btnUpdate:
                // TODO update user
                break;
            case R.id.btnChangePass:
                intent.setClass(this, ChangePasswordActivity.class);
                break;
            default:
                intent.setClass(this, this.getClass());
        }

        startActivity(intent);
    }
}
