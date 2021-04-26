package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    static boolean valid = true;
    int RC_SIGN_IN = 0;
    String Name, Email, Password, cnfmPassword;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText et_sName = findViewById(R.id.et_Name);
        EditText et_sEmail = findViewById(R.id.et_Email);
        EditText et_sPassword = findViewById(R.id.et_Password);
        EditText et_scPassword = findViewById(R.id.et_ConfirmPassword);
        Button bt_signup = findViewById(R.id.bt_signup);
        LoginButton fbButton = findViewById(R.id.login_button);

        //Registering with callback manager

        CallbackManager callbackManager = CallbackManager.Factory.create();
        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        SignInButton gsignInButton = findViewById(R.id.sign_in_button);
        TextView tvLogin = findViewById(R.id.tv_loginscreen);


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name  = et_sName.getText().toString();
                Email = et_sEmail.getText().toString();
                Password = et_sPassword.getText().toString();
                cnfmPassword = et_scPassword.getText().toString();


                if(Name.length() != 0 && valid != false){
                    et_sName.setError(null);
                    valid = true;
                }else{
                    et_sName.setError("Enter Name");
                    valid = false;
                }

                if(Email.length() !=0 && valid != false){
                    et_sEmail.setError(null);
                    valid = true;
                }else{
                    et_sEmail.setError("Enter Email");
                    valid = false;
                }

                if(Password.length() != 0 && valid != false){
                    valid = true;
                    et_sPassword.setError(null);
                }else{
                    et_sPassword.setError("Enter Password");
                    valid = false;
                }

                if(cnfmPassword.length() != 0 && valid != false){
                    valid = true;
                    et_scPassword.setError(null);
                }else{
                    et_scPassword.setError("Confirm Password");
                    valid = false;
                }

                if(valid != false){
                    if(Password != "" && cnfmPassword!= ""){
                        if(Password.equals(cnfmPassword))
                        {
                            et_scPassword.setError("null");
                            valid = true;
                        }else{
                            et_scPassword.setError("Password doesnt match");
                            valid = false;
                        }
                    }
                }

                  if(valid == true){
                    Toast.makeText(MainActivity.this, "Hello there", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Enter mandatory data", Toast.LENGTH_SHORT).show();
                    valid = true;
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginPage();
            }
        });

        gsignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }

            private void signIn() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    private void gotoLoginPage() {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        // Check for existing Google Sign In account, if the user is already signed in
//// the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//    }
}