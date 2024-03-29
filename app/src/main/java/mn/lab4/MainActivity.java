package mn.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Helper {
    private EditText etName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPass);

        (findViewById(R.id.btnLogin)).setOnClickListener(this);
        (findViewById(R.id.btnSignup)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (editTextIsEmpty(etName) || editTextIsEmpty(etPassword)) {
            showToast("All fields should be filled!");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(USERNAME, getStringEditText(etName));
        intent.putExtra(PASSWORD, getStringEditText(etPassword));

        switch (view.getId()) {
            case R.id.btnLogin:
                intent.setClass(this, UserInfoActivity.class);
                break;
            case R.id.btnSignup:
                intent.setClass(this, SignUpActivity.class);
                break;
            default:
                intent.setClass(this, this.getClass());
        }

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onResume() {
        super.onResume();
        String username = getDefaults(PREF_USERNAME);
        if (username != null && username.trim().length() > 0) {
            etName.setText(username);
            showToast("Welcome back " + username + "!");
        }
    }
}
