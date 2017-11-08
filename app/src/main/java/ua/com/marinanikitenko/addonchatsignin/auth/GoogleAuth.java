package ua.com.marinanikitenko.addonchatsignin.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


import ua.com.marinanikitenko.addonchatsignin.R;
import ua.com.marinanikitenko.addonchatsignin.activities.LoginActivity;
import ua.com.marinanikitenko.addonchatsignin.application.ChatApp;

import static ua.com.marinanikitenko.addonchatsignin.model.Log.d;



/**
 * Created by Marina on 11.06.2017.
 *
 * Helper for register/login with current Google account
 */

public class GoogleAuth implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private String TAG = GoogleAuth.class.getSimpleName() + ":";
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    public static final int RC_SIGN_IN = 007;
    private Activity mActivity;
    private Context context;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mListener;

    public GoogleAuth(LoginActivity loginActivity){
        this.mActivity = loginActivity;
        context = ChatApp.getContext();
        startClient(mActivity);
        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    // Configure Google Sign In
    public void startClient(Activity activity){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * Used for signIn with google, show dialog with choose google account
     *
     */
    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    /**
     *Register FirebaseAuthStateListener for tracing/notificate result user logIn or not, return to activity authstate
     *@param authStateListener
     *
     */

    public void setListener(FirebaseAuth.AuthStateListener authStateListener){
        this.mListener = authStateListener;
    }

    /*
     * SignOut from firebase, show toast with result of action
     *
     */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public GoogleApiClient getGoogleApiClient(){
        return mGoogleApiClient;
    }

    /**
     *Get google user account from onActivityResult(), get credentials for firebase auth
     *
     * @param acct
     */

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mListener.onAuthStateChanged(FirebaseAuth.getInstance());
                            //mActivity.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            mListener.onAuthStateChanged(FirebaseAuth.getInstance());
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}


    public void disconnect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void connect() {
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {}

    @Override
    public void onConnectionSuspended(int i) {}

    /**
     *Get result from google auth from RegisterActivity to current method
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GoogleAuth.RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
            }
        }
    }
}


