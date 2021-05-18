package com.benew.marryme.Controllers.Activities;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.firebase.ui.auth.AuthUI;

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
}