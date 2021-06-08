package com.benew.marryme.Controllers.Activities;

import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.FirebaseUsage.FirestoreUsage;
import com.benew.marryme.R;
import com.benew.marryme.Receivers.ConnectivityReceiver;
import com.benew.marryme.UTILS.Prevalent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.benew.marryme.API.GettingPictureAPI.chooseImageFromPhone;
import static com.benew.marryme.API.GettingPictureAPI.handleResponse;
import static com.benew.marryme.API.LoadingConfiguration.configureLoading;
import static com.benew.marryme.API.LoadingConfiguration.startLoading;
import static com.benew.marryme.API.LoadingConfiguration.stopLoading;
import static com.benew.marryme.API.StartNewActivityAPI.startNewActivity;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getFemaleUserDocumentReference;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getMaleUserDocumentReference;
import static com.benew.marryme.UTILS.Constants.FEMALE_GENDER;
import static com.benew.marryme.UTILS.Constants.FEMALE_USERS_COLLECTION;
import static com.benew.marryme.UTILS.Constants.MALE_GENDER;
import static com.benew.marryme.UTILS.Constants.MALE_USERS_COLLECTION;
import static com.benew.marryme.UTILS.Constants.RC_CHOOSE_PHOTO;
import static com.benew.marryme.UTILS.Constants.RC_IMAGE_PERMS;

public class InterestedForActivity extends BaseActivity {

    // 1 - STATIC DATA FOR PICTURE
    @BindView(R.id.rootView) RelativeLayout rootView;
    @BindView(R.id.profil_picture_user) ImageView imageView;
    @BindView(R.id.add_picture_user) TextView newPicture;

    // 1 - Uri of image selected by user
    private Uri uriImageSelected;

    FirebaseAuth mAuth;
    FirebaseUser user;

    TashieLoader tashie;
    boolean isConnected;

    @Override
    protected int getLayout() { return R.layout.activity_interested_for; }

    @Override
    protected void configuration() {
        isConnected = ConnectivityReceiver.isConnected(InterestedForActivity.this);

        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
        }

        tashie = configureLoading(InterestedForActivity.this);
    }

    //EN CLIQUANT SUR IMPORTER PHOTO, VERIFICATION SI PERMISSION D'ACCEDER A LA GALERIE ET PUIS DIRECTION VERS LA GALERIE POUR RECUP UNE PHOTO
    @OnClick(R.id.add_picture_user)
    // 5 - Calling the appropriate method
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickGetPicFromGalery() { chooseImageFromPhone(this, this, RC_IMAGE_PERMS, RC_CHOOSE_PHOTO); }

    // TRAITEMENT SI PERMISSION A LA GALERIE ACCORDEE OU PAS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 2 - Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // TRAITEMENT SI PHOTO RECUPEREE OU PAS
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 6 - Calling the appropriate method after activity result
        uriImageSelected = handleResponse(this, this, imageView, RC_CHOOSE_PHOTO, requestCode, resultCode, data);
    }

    @OnClick(R.id.to_next_button)
    void onClickNext() {
        if (uriImageSelected == null) Toasty.info(this, "Vous devez obligatoirement mettre une photo !").show();
        else {
            startLoading(rootView, tashie);
            saveData();
        }
    }

    private void saveData() {

        Map choiceMap = new HashMap();

        String gender = "";
        switch (Prevalent.currentUserOnline.getGender()) {
            case MALE_GENDER:
                gender = MALE_USERS_COLLECTION;
                break;
            case FEMALE_GENDER:
                gender = FEMALE_USERS_COLLECTION;
                break;
        }

        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else {
            StorageReference userProfilePicture = FirestoreUsage.getUserPictureReference(Prevalent.currentUserOnline.getMail(), gender).child("profile_picture.jpg");
            userProfilePicture.putFile(uriImageSelected).addOnSuccessListener(this, taskSnapshot -> {
                String pathImageSavedInFirebaseStorage = Objects.requireNonNull(taskSnapshot.getMetadata()).getPath();

                choiceMap.put("profile_picture", pathImageSavedInFirebaseStorage);
                Prevalent.currentUserOnline.setProfile_picture(pathImageSavedInFirebaseStorage);

                switch (Prevalent.currentUserOnline.getGender()) {
                    case MALE_GENDER:
                        getMaleUserDocumentReference(Prevalent.currentUserOnline.getMail()).update(choiceMap).addOnSuccessListener(o -> startNewActivity(InterestedForActivity.this, MaritalStatusActivity.class));
                        break;
                    case FEMALE_GENDER:
                        getFemaleUserDocumentReference(Prevalent.currentUserOnline.getMail()).update(choiceMap).addOnSuccessListener(o -> startNewActivity(InterestedForActivity.this, MaritalStatusActivity.class));
                        break;
                }
            });
        }

        stopLoading(rootView, tashie);
    }
}