package ua.com.marinanikitenko.addonchatsignin.auth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ua.com.marinanikitenko.addonchatsignin.R;

import static ua.com.marinanikitenko.addonchatsignin.model.Log.d;



/**
 * Created by Marina on 10.07.2017.
 *
 * Helper for register/logIn user in firebase by any email & password
 */

public class EmailAuth {
    private String TAG = EmailAuth.class.getSimpleName() + ":";
    private FirebaseAuth mFirebaseAuth;
    private Activity mActivity;


    public EmailAuth(Activity activity) {
        mActivity = activity;
        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    /**
     * Used for register user in firebase bu email & password, with listener informs activity about register user state
     * Accepts already validated fields
     * @param email
     * @param password
     * @param authStateListener
     *
     */
    public void register(String email, String password, final FirebaseAuth.AuthStateListener authStateListener) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authStateListener.onAuthStateChanged(FirebaseAuth.getInstance());
                            //mActivity.finish();
                        }
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            authStateListener.onAuthStateChanged(FirebaseAuth.getInstance());

                        }
                    }
                });
    }

    /**
     * Used whan user was registered before, helps signIn in firebase, with listener informs activity about signIn user state
     * Accepts already validated fields
     *
     * @param email
     * @param password
     * @param authStateListener
     */

    public void signIn(String email, String password, final FirebaseAuth.AuthStateListener authStateListener){
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            authStateListener.onAuthStateChanged(FirebaseAuth.getInstance());
                            //mActivity.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(mActivity, mActivity.getString(R.string.error_login) + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
