package com.benew.marryme.Controllers.Activities;

import android.content.Intent;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @Override
    protected int getLayout() { return R.layout.activity_main; }

    @Override
    protected void configuration() {

    }

    @OnClick(R.id.login_with_google_button)
    void onClickLoginWithGoogle() {
        this.startSignInWithGoogle();
    }

    @OnClick(R.id.login_with_facebook_button)
    void onClickLoginWithFacebook() {
        this.startSignInActivity();
    }

    @OnClick(R.id.login_with_mail_button)
    void onClickLoginWithMail() {
        this.startSignInWithMail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 4 - Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void startSignInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    private void startSignInWithGoogle() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    private void startSignInWithMail() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    // 3 - Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS

            } else { // ERRORS
                if (response == null) {

                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {

                }
            }
        }
    }
}