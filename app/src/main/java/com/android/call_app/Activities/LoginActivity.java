package com.android.call_app.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.call_app.Db.User;
import com.android.call_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameInputLogin;
    private EditText passwordInputLogin;
    private AppCompatButton loginBtn;
    private TextView toSignUp;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference("dataUser");
        firebaseAuth = FirebaseAuth.getInstance();
        initView();
        toSignUpPage();
        loginProcess();
    }


    private void loginProcess() {
        this.loginBtn.setOnClickListener(view -> {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String email = String.valueOf(LoginActivity.this.usernameInputLogin.getText());
            String password = String.valueOf(LoginActivity.this.passwordInputLogin.getText());
            if(!email.matches(emailPattern)){
                LoginActivity.this.usernameInputLogin.setError("Email is not valid!");
            }
            if(password.length() < 6){
                LoginActivity.this.passwordInputLogin.setError("Password is not valid!");
            }
            else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void toSignUpPage() {
        this.toSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private void initView() {
        this.usernameInputLogin = findViewById(R.id.usernameInputLogin);
        this.passwordInputLogin = findViewById(R.id.passwordInputLogin);
        this.loginBtn = findViewById(R.id.loginBtn);
        this.toSignUp = findViewById(R.id.toSignUp);
    }
}