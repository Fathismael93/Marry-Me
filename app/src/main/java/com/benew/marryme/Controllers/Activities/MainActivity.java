package com.benew.marryme.Controllers.Activities;

import android.content.Intent;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.rootView) RelativeLayout rootView;

    TashieLoader tashie;

    @Override
    protected int getLayout() { return R.layout.activity_main; }

    @Override
    protected void configuration() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        tashie = new TashieLoader(
                this, 5,
                30, 10,
                ContextCompat.getColor(this, R.color.purple_200));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());
        tashie.setY(300f);
        tashie.setX(180f);

        if (user != null) {
            Intent intent = new Intent(MainActivity.this, NoyauActivity.class);
            startActivity(intent);
            finish();
        }
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

                rootView.setAlpha(0.3f);
                rootView.addView(tashie);

                if (response.isNewUser()) {
                    Intent intent = new Intent(MainActivity.this, InfoGeneralActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, NoyauActivity.class);
                    startActivity(intent);
                }
                finish();
                rootView.setAlpha(1f);
                rootView.removeView(tashie);

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