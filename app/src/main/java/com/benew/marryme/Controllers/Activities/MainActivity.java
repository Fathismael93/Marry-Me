package com.benew.marryme.Controllers.Activities;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.benew.marryme.Receivers.ConnectivityReceiver;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.benew.marryme.API.GetUserDataAPI.getUserData;
import static com.benew.marryme.API.LoadingConfiguration.configureLoading;
import static com.benew.marryme.API.StartNewActivityAPI.startNewActivity;

public class MainActivity extends BaseActivity {

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.rootView) RelativeLayout rootView;

    TashieLoader tashie;

    FirebaseUser user;
    boolean isConnected;

    @Override
    protected int getLayout() { return R.layout.activity_main; }

    @Override
    protected void configuration() {

        isConnected = ConnectivityReceiver.isConnected(MainActivity.this);

        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
        }

        tashie = configureLoading(MainActivity.this);

        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else {
            if (user != null)
                getUserData(this, this, user);
        }

    }

    @OnClick(R.id.login_with_google_button)
    void onClickLoginWithGoogle() {
        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else
            this.startSignInWithGoogle();
    }

    @OnClick(R.id.login_with_facebook_button)
    void onClickLoginWithFacebook() {
        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else
            this.startSignInActivity();
    }

    @OnClick(R.id.login_with_mail_button)
    void onClickLoginWithMail() {
        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else
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

                if (response.isNewUser()) {

                    if (!isConnected)
                        Toasty.info(this, "Connexion interrompue ").show();
                    else
                        startNewActivity(MainActivity.this, InfoGeneralActivity.class);

                    finish();
                }
                else {
                    if (!isConnected)
                        Toasty.info(this, "Connexion interrompue ").show();
                    else {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        getUserData(this, this, user);
                    }
                }

            } else { // ERRORS
                if (response == null) {
                    Toasty.info(this, "Connexion annulée").show();
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toasty.info(this, "Vous n'avez pas de connexion internet").show();
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toasty.info(this, "Une erreur inconnue est survenue! Veuillez réessayer plutard").show();
                }
            }
        }
    }
}