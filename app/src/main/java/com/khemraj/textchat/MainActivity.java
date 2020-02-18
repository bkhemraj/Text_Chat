package com.khemraj.textchat;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button signUp;
    Boolean loginModeActive = false;
    TextView toggleLoginSignup;

    public void redirectIfLoggedIn(){
        if(ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);
            this.finish();
        }
    }



    public void toggleLoginMode(View view){
        Log.i("Login", "Toggled");

        if(loginModeActive){
            loginModeActive = false;
            toggleLoginSignup.setText("Or, Login");
            signUp.setText("Sign up");
            setTitle("TextChat Sign-Up");


        }else {
            loginModeActive = true;
            toggleLoginSignup.setText("Or, Sign-up");
            signUp.setText("Login");
            setTitle("TextChat Login");

        }
    }


    public void signupLogin(View view){

        if(loginModeActive){

            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null){

                        Log.i("Login", "Successful");
                        redirectIfLoggedIn();

                    }else {
                        String message = e.getMessage();
                        if(message.toLowerCase().contains("java")){
                            message = e.getMessage().substring(e.getMessage().indexOf(" "));
                        }
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

                    }

                }
            });


        }else {

            ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        Log.i("Signup", "Successful");
                        redirectIfLoggedIn();

                    } else {

                        String message = e.getMessage();
                        if(message.toLowerCase().contains("java")){
                            message = e.getMessage().substring(e.getMessage().indexOf(" "));
                        }
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TextChat Sign-Up");


        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUp = findViewById(R.id.button);
        toggleLoginSignup = findViewById(R.id.toggleLoginTextView);
        redirectIfLoggedIn();
    }
}
