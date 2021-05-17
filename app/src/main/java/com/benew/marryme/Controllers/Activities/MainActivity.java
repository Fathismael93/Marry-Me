package com.benew.marryme.Controllers.Activities;

import android.content.Intent;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.Modals.User;
import com.benew.marryme.R;
import com.benew.marryme.UTILS.Prevalent;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.HashMap;
import java.util.Map;

import at.favre.lib.crypto.bcrypt.BCrypt;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.benew.marryme.API.ValidationAPI.validateNumber;
import static com.benew.marryme.API.ValidationAPI.validatePassword;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getUserDocumentReference;
import static com.benew.marryme.UTILS.Constants.FALSE_NUMBER_ERROR;
import static com.benew.marryme.UTILS.Constants.NO_NUMBER_ERROR;
import static com.benew.marryme.UTILS.Constants.NO_PASSWORD_ERROR;
import static com.benew.marryme.UTILS.Constants.PASS_LESS_THAN_5_ERROR;
import static com.benew.marryme.UTILS.Constants.WRONG_PASSWORD;

public class MainActivity extends BaseActivity {

    @BindView(R.id.number_user) TextInputLayout numberUserInput;
    @BindView(R.id.password_user) TextInputLayout passwordUserInput;

    //FirebaseAuth mAuth;

    //VARIABLE POUR INITIALISER LE CHARGEMENT
    LoadToast toast;

    String numberUser, passwordUser /*, verificationCodeBySystem*/;

    @Override
    protected int getLayout() { return R.layout.activity_main; }

    @Override
    protected void configuration() {
        //mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.login_button)
    void onClickLogin() {
        toast = new LoadToast(this);
        toast.setBackgroundColor(getResources().getColor(R.color.black));
        toast.setTranslationY(450);

        if (!validateNumber(numberUserInput, NO_NUMBER_ERROR, FALSE_NUMBER_ERROR) | !validatePassword(passwordUserInput, NO_PASSWORD_ERROR, PASS_LESS_THAN_5_ERROR))
            return;

        toast.show();

        numberUser = numberUserInput.getEditText().getText().toString().trim();
        passwordUser = passwordUserInput.getEditText().getText().toString().trim();

        getUserDocumentReference(numberUser).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();

            assert documentSnapshot != null;
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);

                BCrypt.Result result = BCrypt.verifyer().verify(passwordUser.toCharArray(), user.getPassword());

                if (result.verified) {
                    Prevalent.currentUserOnline = user;
                    startActivity(new Intent(MainActivity.this, NoyauActivity.class));
                } else
                    Toasty.info(MainActivity.this, WRONG_PASSWORD).show();

                toast.hide();
            } else {
                String passwordHashed = BCrypt.withDefaults().hashToString(12, passwordUser.toCharArray());

                Map newUserMap = new HashMap();
                newUserMap.put("phone", numberUser);
                newUserMap.put("password",passwordHashed);

                getUserDocumentReference(numberUser).set(newUserMap).addOnSuccessListener(aVoid -> {
                    Prevalent.currentUserOnline = new User(numberUser, passwordHashed);
                    startActivity(new Intent(MainActivity.this, InfoGeneralActivity.class));
                    toast.hide();
                });
            }
        });

        //sendVerificationCodeToUser(numberUser);
    }

    /*private void sendVerificationCodeToUser(String numberUser) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+253" + numberUser)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null)
                verifyCode(code);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toasty.error(MainActivity.this, e.getMessage(), Toasty.LENGTH_LONG).show();
            Log.i("NUMBER", "signInTheUserByCredentials: " + e.getMessage());
        }
    };

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);

        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful())
                        Toasty.success(MainActivity.this, "Réussi !").show();
                    else
                        Toasty.info(MainActivity.this, task.getException().getMessage()).show();
                });
    }*/
}