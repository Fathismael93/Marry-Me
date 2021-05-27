package com.benew.marryme.Controllers.Activities;

import android.widget.RadioGroup;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.benew.marryme.UTILS.Prevalent;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.benew.marryme.API.StartNewActivityAPI.startNewActivity;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getFemaleUserDocumentReference;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getMaleUserDocumentReference;
import static com.benew.marryme.UTILS.Constants.FEMALE_GENDER;
import static com.benew.marryme.UTILS.Constants.MALE_GENDER;

public class MaritalStatusActivity extends BaseActivity {

    @BindView(R.id.work_answer_group) RadioGroup workGroup;
    @BindView(R.id.work_place) TextInputLayout workPlaceInput;
    @BindView(R.id.bio) TextInputLayout bioInput;

    String workAnswer = "", workPlace = "", bio = "";

    @Override
    protected int getLayout() { return R.layout.activity_marital_status; }

    @Override
    protected void configuration() {

    }

    @OnClick(R.id.to_next_button)
    void onClickNext() {

        switch (workGroup.getCheckedRadioButtonId()) {
            case R.id.yes_group:
                workAnswer = "yes";
                break;
            case R.id.no_group:
                workAnswer = "no";
                break;
        }

        workPlace = workPlaceInput.getEditText().getText().toString().trim();
        bio = bioInput.getEditText().getText().toString().trim();

        saveDataOnDatabase(workAnswer, workPlace, bio);
    }

    private void saveDataOnDatabase(String workAnswer, String workPlace, String bio) {
        Map actualPersonalStatusMap = new HashMap();
        actualPersonalStatusMap.put("work_answer", workAnswer);
        actualPersonalStatusMap.put("work_place", workPlace);
        actualPersonalStatusMap.put("bio", bio);

        Prevalent.currentUserOnline.setBio(bio);
        Prevalent.currentUserOnline.setWork_answer(workAnswer);
        Prevalent.currentUserOnline.setWork_place(workPlace);

        switch (Prevalent.currentUserOnline.getGender()) {
            case MALE_GENDER:
                getMaleUserDocumentReference(Prevalent.currentUserOnline.getMail()).update(actualPersonalStatusMap).addOnSuccessListener(o -> startNewActivity(MaritalStatusActivity.this, NoyauActivity.class));
                break;
            case FEMALE_GENDER:
                getFemaleUserDocumentReference(Prevalent.currentUserOnline.getMail()).update(actualPersonalStatusMap).addOnSuccessListener(o -> startNewActivity(MaritalStatusActivity.this, NoyauActivity.class));
                break;
        }
    }
}