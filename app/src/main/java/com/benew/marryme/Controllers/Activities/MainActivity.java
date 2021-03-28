package com.benew.marryme.Controllers.Activities;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.Modals.User;
import com.benew.marryme.R;
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

public class MainActivity extends BaseActivity {

    @BindView(R.id.number_user) TextInputLayout numberUserInput;
    @BindView(R.id.password_user) TextInputLayout passwordUserInput;

    //FirebaseAuth mAuth;

    //VARIABLE POUR INITIALISER LE CHARGEMENT
    LoadToast toast;

    String numberUser, passwordUser /*, verificationCodeBySystem*/;

    String noNumber, falseNumber, absencePass, moinsQue5;

    @Override
    protected int getLayout() { return R.layout.activity_main; }

    @Override
    protected void configuration() {
        //mAuth = FirebaseAuth.getInstance();

        noNumber = "Veuillez renseigner votre numéro de téléphone !";
        falseNumber = "Ce numéro est incorrect !";
        absencePass = "Le mot de passe est obligatoire !";
        moinsQue5 = "Le mot de passe doit être supérieur à 5 caractères !";
    }

    @OnClick(R.id.login_button)
    void onClickLogin() {
        toast = new LoadToast(this);
        toast.setBackgroundColor(getResources().getColor(R.color.black));
        toast.setTranslationY(450);

        if (!validateNumber(numberUserInput, noNumber, falseNumber) | !validatePassword(passwordUserInput, absencePass, moinsQue5))
            return;

        toast.show();

        numberUser = numberUserInput.getEditText().getText().toString();
        passwordUser = passwordUserInput.getEditText().getText().toString();

        getUserDocumentReference(numberUser).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();

            assert documentSnapshot != null;
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);

                BCrypt.Result result = BCrypt.verifyer().verify(passwordUser.toCharArray(), user.getPassword());

                if (result.verified)
                    Toasty.info(MainActivity.this, "Connecté !").show();
                else
                    Toasty.info(MainActivity.this, "Mot de passe incorrecte !").show();

                toast.hide();
            } else {
                String passwordHashed = BCrypt.withDefaults().hashToString(12, passwordUser.toCharArray());

                Map newUserMap = new HashMap();
                newUserMap.put("phone", numberUser);
                newUserMap.put("password",passwordHashed);

                getUserDocumentReference(numberUser).set(newUserMap).addOnSuccessListener(aVoid -> toast.hide());
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

            //verificationCodeBySystem = s;
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