package com.example.testwe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testwe.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    EditText nameInput;
    EditText fnameInput;
    EditText emailInput;
    EditText bioInput;
    EditText passInput;
    Button btnRegister;
    TextView LoginHere;
    FirebaseAuth mAuth;
    ActivitySignUpBinding binding;
    ImageView shouldveBeenButton;
    FirebaseDatabase database;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Window window = this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(this.getResources().getColor(R.color.mainTheme));
//        setContentView(R.layout.activity_sign_up);

        binding =ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        nameInput=findViewById(R.id.nameInput);
        fnameInput=findViewById(R.id.fnameInput);
        emailInput=findViewById(R.id.emailInput);
        bioInput=findViewById(R.id.bioInput);
        passInput=findViewById(R.id.passwordInput);
        btnRegister = findViewById(R.id.sign_up_button);
        LoginHere = findViewById(R.id.loginHere);

        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view ->{
            createUser();
        });
        LoginHere.setOnClickListener(view ->{
            startActivity(new Intent(SignUp.this, Login.class));
        });




        binding.shoudveBeenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            Uri sFile =data.getData();
            binding.profileImage.setImageURI(sFile);

//            final StorageReference reference = storage.getReference().child("profile_profic")
//                    .child(FirebaseAuth.getInstance().getUid());

//            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                                    .child("profilePic").setValue(uri.toString());
//                        }
//                    });
//                }
//            });
        }
    }

    private void createUser() {
        String name = nameInput.getText().toString();
        String fame = fnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String bio = bioInput.getText().toString();
        String password = passInput.getText().toString();

        if (TextUtils.isEmpty(email)){
            emailInput.setError("Email cannot be empty");
            emailInput.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            passInput.setError("Password cannot be empty");
            passInput.requestFocus();
        }
        else if(TextUtils.isEmpty(name)){
            nameInput.setError("Name cannot be empty");
            nameInput.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = task.getResult().getUser();
                        String userid = user.getUid();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User users = snapshot.getValue(User.class);
                                        Picasso.get()
                                                .load(users.getPicture())
                                                .placeholder(R.drawable.signup_background_img)
                                                .into(binding.profileImage);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                        User insertUser = new User(userid, name, fame, email, bio, password, "default", "");


                        reference.setValue(insertUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUp.this, Login.class));
                                    finish();
                                }
                            }
                        });

                    }
                    else{
                        Toast.makeText(SignUp.this, "" +  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


}