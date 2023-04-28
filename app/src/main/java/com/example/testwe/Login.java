package com.example.testwe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText emailinput2;
    EditText passwordinput2;
    TextView SignUpHere;
    FirebaseAuth mAuth;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.mainTheme));
        setContentView(R.layout.activity_login);

        emailinput2 = findViewById(R.id.inputEmail2);
        passwordinput2 = findViewById(R.id.inputPass2);
        btnLogin =findViewById(R.id.logInbutton);
        SignUpHere =findViewById(R.id.SignUpHere);

        mAuth =FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(view ->{
            loginUser();
        });

        SignUpHere.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, SignUp.class));
        });

    }

    private void loginUser() {

        String email2 = emailinput2.getText().toString();
        String password2= passwordinput2.getText().toString();

        if (TextUtils.isEmpty(email2)){
            emailinput2.setError("Email cannot be empty");
            emailinput2.requestFocus();
        }else if(TextUtils.isEmpty(password2)){
            passwordinput2.setError("Password cannot be empty");
            passwordinput2.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "User Logged in Successfully" , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainScreen.class));
                    }else{
                        Toast.makeText(Login.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}