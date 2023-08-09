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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText email_txt, password_txt, confirm_password_txt;
    private Button register_btn, login_btn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initM();
    }

    private void initM() {
        email_txt = (EditText) findViewById(R.id.email_txt);
        password_txt = (EditText) findViewById(R.id.password_txt);
        confirm_password_txt = (EditText) findViewById(R.id.confirm_password_txt);
        register_btn = (Button) findViewById(R.id.register_btn);
        login_btn = (Button) findViewById(R.id.login_btn);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_txt.getText().toString().isEmpty() ||
                        !password_txt.getText().toString().isEmpty() ||
                        !confirm_password_txt.getText().toString().isEmpty())
                {
                    if (password_txt.getText().toString().contentEquals(confirm_password_txt.getText().toString()))
                    {
                        signUp();
                    }
                    {
                        Toast.makeText(Register.this, "Type Same Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Register.this, "Enter Correct Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUp() {
        final String email = email_txt.getText().toString().trim();
        final String password = password_txt.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save user data in the Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference userRef = mDatabase.child("users").child(userId);
                            userRef.child("email").setValue(email);

                            Toast.makeText(Register.this, "Succesful Signup", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "Invalid Password Should be 6 Characters", Toast.LENGTH_SHORT).show();                        }
                    }
                });
    }
}