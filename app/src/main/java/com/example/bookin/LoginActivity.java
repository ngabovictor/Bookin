package com.example.bookin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Context context;

    EditText emailView, passwordView;
    Button loginBtn;
    Button registerBtn;
    ProgressBar loginProgressBar, registerProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        firebaseAuth = FirebaseAuth.getInstance();

        emailView = findViewById(R.id.email_view);
        passwordView = findViewById(R.id.password_view);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
        loginProgressBar = findViewById(R.id.progress_bar_login);
        registerProgressBar = findViewById(R.id.progress_bar_register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailView.getText().toString();
                password = passwordView.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    isLoading(true, "login");
                    login(email, password);
                } else {
                    Toast.makeText(context, "Email and password should not be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = emailView.getText().toString();
                password = passwordView.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    isLoading(true, "register");
                    register(email, password);
                } else {
                    Toast.makeText(context, "Email and password should not be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void isLoading(boolean loading, String action){
        if (loading){
            emailView.setEnabled(false);
            passwordView.setEnabled(false);
            loginBtn.setEnabled(false);
            registerBtn.setEnabled(false);

            if (action.equals("login")){
                loginBtn.setVisibility(View.GONE);
                loginProgressBar.setVisibility(View.VISIBLE);
            } else if (action.equals("register")){
                registerBtn.setVisibility(View.GONE);
                registerProgressBar.setVisibility(View.VISIBLE);
            }
        } else {
            emailView.setEnabled(true);
            passwordView.setEnabled(true);
            loginBtn.setEnabled(true);
            registerBtn.setEnabled(true);
            loginBtn.setVisibility(View.VISIBLE);
            registerBtn.setVisibility(View.VISIBLE);
            loginProgressBar.setVisibility(View.GONE);
            registerProgressBar.setVisibility(View.GONE);
        }
    }

    private void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
                    isLoading(false, "login");
                    Toast.makeText(context, task.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void register(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
                    isLoading(false, "register");
                    Toast.makeText(context, task.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
