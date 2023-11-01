package com.android.call_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.call_app.Db.User;
import com.android.call_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText usernameInputSignUp;
    private EditText passwordInputSignUp;
    private EditText re_passwordInputSignUp;
    private AppCompatButton signupBtn;
    private TextView toLogin;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mDatabase = FirebaseDatabase.getInstance().getReference("dataUser");
        initView();
        toLoginPage();
        signUpProcess();
    }

    private void signUpProcess() {
        this.signupBtn.setOnClickListener(view -> {
            String usernameID = String.valueOf(this.usernameInputSignUp.getText());
            String password  =String.valueOf(this.passwordInputSignUp.getText());
            String rePassword = String.valueOf(this.re_passwordInputSignUp.getText());
            if(usernameID.equals("")){
                Toast.makeText(SignUpActivity.this, "Username is empty!", Toast.LENGTH_SHORT).show();
            }
            else {
                if(!password.equals(rePassword)){
                    Toast.makeText(SignUpActivity.this, "password not match!", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User(usernameID, password);
                    mDatabase.child(usernameID+"_"+password).setValue(user);
                    Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void toLoginPage() {
        this.toLogin.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });
    }

    private void initView() {
        this.usernameInputSignUp = findViewById(R.id.usernameInputSignUp);
        this.passwordInputSignUp = findViewById(R.id.passwordInputSignUp);
        this.re_passwordInputSignUp = findViewById(R.id.re_passwordInputSignUp);
        this.signupBtn = findViewById(R.id.signupBtn);
        this.toLogin = findViewById(R.id.toLogin);
    }
}