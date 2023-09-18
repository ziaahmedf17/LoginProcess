package com.ziaahmedf17.loginprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private EditText email_txt, password_txt;
    private Button registerBtn, loginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initM();
    }

    private void initM() {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        email_txt = (EditText) findViewById(R.id.email_txt);
        password_txt = (EditText) findViewById(R.id.password_txt);
        registerBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_txt.getText().toString().isEmpty() ||
                        !password_txt.getText().toString().isEmpty())
                {
                    login();
                }
                else {
                    Toast.makeText(Login.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void login() {
        String email = email_txt.getText().toString().trim();
        String password = password_txt.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();


                            Toast.makeText(Login.this, "Succesful Login", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("errrrr", task.getException().toString());
                            Toast.makeText(Login.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();                        }
                    }
                });
    }
}