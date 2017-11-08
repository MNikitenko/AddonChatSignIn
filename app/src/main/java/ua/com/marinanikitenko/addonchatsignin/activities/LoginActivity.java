package ua.com.marinanikitenko.addonchatsignin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ua.com.marinanikitenko.addonchatsignin.R;
import ua.com.marinanikitenko.addonchatsignin.auth.GoogleAuth;
import ua.com.marinanikitenko.addonchatsignin.auth.RegisterHelper;
import ua.com.marinanikitenko.addonchatsignin.utils.GenericTextWatcher;

import static ua.com.marinanikitenko.addonchatsignin.model.Log.d;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,FirebaseAuth.AuthStateListener {
    private String TAG = LoginActivity.class.getSimpleName() + ":";
    private Button register;
    private Button login;
    private Button googleSignIn;
    private Button logOut;
    private EditText mEmail;
    private EditText mPassword;
    private TextInputLayout errorEmail;
    private TextInputLayout errorPassword;
    private TextView userEmail;
    private RegisterHelper registerHelper;
    private int authState;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private GoogleAuth googleAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //init firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        initViews();
    }

    private void initViews(){
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        userEmail = (TextView)findViewById(R.id.user_email);

        errorEmail = (TextInputLayout) findViewById(R.id.view_email);
        errorPassword = (TextInputLayout) findViewById(R.id.view_pass);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        googleSignIn = (Button) findViewById(R.id.googleAuth);
        logOut = (Button) findViewById(R.id.logOut);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        googleSignIn.setOnClickListener(this);
        logOut.setOnClickListener(this);

        //set custom textwatcher for watch when user enter text, watches if error enabled and turns it off
        mEmail.addTextChangedListener(new GenericTextWatcher(mEmail,errorEmail));
        mPassword.addTextChangedListener(new GenericTextWatcher(mPassword,errorPassword));

        startConnection();
    }

    private void startConnection(){
        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        //Initalize google auth helper
        googleAuth = new GoogleAuth(LoginActivity.this);
        //Init register helper
        registerHelper = new RegisterHelper(this);
        registerHelper.setListener((FirebaseAuth.AuthStateListener) LoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                authState = 0;
                registerHelper.startHelper(
                        mEmail.getText().toString().trim(),
                        mPassword.getText().toString().trim(),
                        errorEmail,
                        errorPassword,
                        authState);

                break;

            case R.id.login:
                authState = 1;
                registerHelper.startHelper(
                        mEmail.getText().toString().trim(),
                        mPassword.getText().toString().trim(),
                        errorEmail,
                        errorPassword,
                        authState);
                break;

            case R.id.googleAuth:
                googleAuth.setListener((FirebaseAuth.AuthStateListener) LoginActivity.this);
                googleAuth.signIn();
                break;

            case R.id.logOut:
                googleAuth.setListener((FirebaseAuth.AuthStateListener) LoginActivity.this);
                googleAuth.signOut();
                break;
        }
    }


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.getCurrentUser().getEmail();
            userEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        } else{
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        googleAuth.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser != null) {
            userEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
        if(mUser == null){
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
