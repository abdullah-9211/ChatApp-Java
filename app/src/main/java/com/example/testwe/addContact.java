package com.example.testwe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class addContact extends AppCompatActivity {
    Button addContact;
    EditText Cname;
    EditText Cemail;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addContact = findViewById(R.id.addContactB);
        Cname = findViewById(R.id.inputname2);
        Cemail = findViewById(R.id.inputemail3);
        mAuth = FirebaseAuth.getInstance();
    }
}