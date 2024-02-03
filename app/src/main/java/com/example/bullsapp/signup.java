package com.example.bullsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    //Variables
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Hooks to all xml element in activity_sign_up.xml
        regName = findViewById(R.id.regName);
        regUsername = findViewById(R.id.regUsername);
        regEmail = findViewById(R.id.regEmail);
        regPhoneNo = findViewById(R.id.regPhoneNo);
        regPassword = findViewById(R.id.regPassword);
        regBtn = findViewById(R.id.regBtn);
        regToLoginBtn = findViewById(R.id.regToLoginBtn);



        regToLoginBtn.setOnClickListener((view) ->{
            Intent intent = new Intent(signup.this, Login.class);
            Pair[] pairs = new Pair[2];
            pairs[0] =new Pair<View, String>(regUsername, "username_tran");
            pairs[1] =new Pair<View, String>(regPassword, "password_tran");

            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(signup.this,pairs);
            startActivity(intent,options.toBundle());
        });

    }//onCreate Method End.



    private Boolean validationName(){
        String value=regName.getEditText().getText().toString();

        if (value.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationUsername(){
        String value=regUsername.getEditText().getText().toString();
        String nowhiteSpace= "\\A\\w{4,20}\\z";

        if (value.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (value.length()>=25) {
            regUsername.setError("Username too long");
            return false;
        } else if (!value.matches(nowhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationEmail(){
        String value=regEmail.getEditText().getText().toString();
        String nowhiteSpace= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (value.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }  else if (!value.matches(nowhiteSpace)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationPhoneNo(){
        String value=regPhoneNo.getEditText().getText().toString();

        if (value.isEmpty()){
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }  else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validationPassword(){
        String value=regPassword.getEditText().getText().toString();
        String passwordvalidation= "^(?=.*[a-zA-Z])(?=\\S+$).{4,}$";


        if (value.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(passwordvalidation)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

    //Save data in Firebase on button click.
    public void registerUser(View view){

        if (!validationName() | !validationUsername() | !validationPhoneNo() | !validationEmail() | !validationPassword()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user");


        String name=regName.getEditText().getText().toString();
        String username=regUsername.getEditText().getText().toString();
        String email=regEmail.getEditText().getText().toString();
        String phoneno=regPhoneNo.getEditText().getText().toString();
        String password=regPassword.getEditText().getText().toString();


//        Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
//        intent.putExtra("phoneno", phoneno);
//        startActivity(intent);

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneno, password);
        reference.child(username).setValue(helperClass);

        Toast.makeText(this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
    //Register Button method end.

}