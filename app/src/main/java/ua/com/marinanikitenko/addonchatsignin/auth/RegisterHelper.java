package ua.com.marinanikitenko.addonchatsignin.auth;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;

import com.google.firebase.auth.FirebaseAuth;

import ua.com.marinanikitenko.addonchatsignin.R;
import ua.com.marinanikitenko.addonchatsignin.utils.EmailValidator;


/**
 *
 * Created by Marina on 10.07.2017.
 *
 * Helper for validate email & password fields, analize login or create user in firebase
 */

public class RegisterHelper {
    private String TAG = RegisterHelper.class.getSimpleName() + ":";
    private Activity mActivity;
    private boolean isEmailValid;
    private boolean isPsswordValid;
    private EmailValidator emailValidator;
    private EmailAuth emailAuth;
    private FirebaseAuth.AuthStateListener mListener;

    public RegisterHelper(Activity activity) {
        mActivity = activity;
    }

    /**
     *Register FirebaseAuthStateListener for tracing/notificate result user logIn or not, return to activity authstate
     *@param authStateListener
     *
     */
    public void setListener(FirebaseAuth.AuthStateListener authStateListener){
        this.mListener = authStateListener;
    }

    /**
     * Get data from activity,validate fields email & password, analize  following action register or login with email
     *
     * @param email
     * @param password
     * @param errorEmail
     * @param errorPassword
     * @param authState
     */
    public void startHelper(String email, String password, TextInputLayout errorEmail, TextInputLayout errorPassword, int authState ){
        emailValidator = new EmailValidator();
        isEmailValid = emailValidator.validate(email);
        isPsswordValid = emailValidator.validatePassword(password);

        if(!isEmailValid){
            errorEmail.setError(mActivity.getString(R.string.error_email));
        }

        if(!isPsswordValid){
            errorPassword.setError(mActivity.getString(R.string.error_pass));
        }

        if(isEmailValid && isPsswordValid) {
            emailAuth = new EmailAuth(mActivity);

            switch (authState) {
                case 0:
                    register(email, password, mListener);
                    break;
                case 1:
                    login(email, password, mListener);
                    break;
            }
        }
    }

    /**
     *Start register user in firebase userslist
     *
     * @param email
     * @param password
     * @param authStateListener
     */

    private void register(String email, String password, FirebaseAuth.AuthStateListener authStateListener){
        emailAuth.register(email, password,authStateListener);
    }

    /**
     *Start login existing user in firebase
     *
     * @param email
     * @param password
     * @param authStateListener
     */
    private void login(String email, String password, FirebaseAuth.AuthStateListener authStateListener){
        emailAuth.signIn(email, password,authStateListener);
    }
}
