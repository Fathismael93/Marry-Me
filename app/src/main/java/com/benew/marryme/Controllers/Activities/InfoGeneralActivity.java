package com.benew.marryme.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.RadioGroup;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.benew.marryme.UTILS.Prevalent;
import com.google.android.material.textfield.TextInputLayout;
import com.ybs.countrypicker.CountryPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.benew.marryme.API.ValidationAPI.validateString;
import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getUserDocumentReference;
import static com.benew.marryme.UTILS.Constants.BIRTHDAY_FORMAT;
import static com.benew.marryme.UTILS.Constants.COUNTRY_PICKER_DIALOG_TITLE;
import static com.benew.marryme.UTILS.Constants.FEMALE_GENDER;
import static com.benew.marryme.UTILS.Constants.MALE_GENDER;

public class InfoGeneralActivity extends BaseActivity{

    DatePickerDialog.OnDateSetListener date;
    Calendar calendar;

    CountryPicker picker;

    @BindView(R.id.birthday_user) TextInputLayout birthdayUserInput;
    @BindView(R.id.country_user) TextInputLayout countryUserInput;
    @BindView(R.id.complete_name_user) TextInputLayout nameUserInput;
    @BindView(R.id.adress_user) TextInputLayout adressUserInput;
    @BindView(R.id.city_user) TextInputLayout cityUserInput;
    @BindView(R.id.birthplace_user) TextInputLayout birthplaceUserInput;
    @BindView(R.id.gender_group) RadioGroup genderRadioGroup;

    String gender;

    @Override
    protected int getLayout() { return R.layout.activity_info_general; }

    @Override
    protected void configuration() {
        calendar = Calendar.getInstance();
        date = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        };

        birthdayUserInput.getEditText().setOnClickListener(v -> new DatePickerDialog(InfoGeneralActivity.this, date,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());

        picker = CountryPicker.newInstance(COUNTRY_PICKER_DIALOG_TITLE);  // dialog title
        picker.setListener((name, code, dialCode, flagDrawableResID) -> {
            countryUserInput.getEditText().setText(name);
            picker.dismiss();
        });

        countryUserInput.getEditText().setOnClickListener(v -> openPicker());
    }

    @OnClick(R.id.to_next_button)
    void onClickNext() {

        if (!validateString(nameUserInput, "erreur") | !validateString(adressUserInput, "erreur") | !validateString(cityUserInput, "erreur")
        | !validateString(birthdayUserInput,"erreur") | !validateString(countryUserInput, "erreur"))
            return;

        if (genderRadioGroup.getCheckedRadioButtonId() != R.id.male_group && genderRadioGroup.getCheckedRadioButtonId() != R.id.female_group)
            Toasty.info(this, "Quel est votre sexe ?").show();
        else {
            switch (genderRadioGroup.getCheckedRadioButtonId()) {
                case R.id.male_group:
                    gender = MALE_GENDER;
                    break;
                case R.id.female_group:
                    gender = FEMALE_GENDER;
                    break;
            }
            saveDataInfoDatabase();
        }
    }

    private void updateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BIRTHDAY_FORMAT, Locale.FRANCE);
        birthdayUserInput.getEditText().setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void openPicker(){
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
    }

    private void saveDataInfoDatabase() {
        String nameUser = nameUserInput.getEditText().getText().toString().trim();
        String adressUser = adressUserInput.getEditText().getText().toString().trim();
        String cityUser = cityUserInput.getEditText().getText().toString().trim();
        String birthdayUser = birthdayUserInput.getEditText().getText().toString().trim();
        String countryUser = countryUserInput.getEditText().getText().toString().trim();
        String birthplaceUser = birthplaceUserInput.getEditText().getText().toString().trim();

        Map infoGeneralMap = new HashMap();
        infoGeneralMap.put("name", nameUser);
        infoGeneralMap.put("adress", adressUser);
        infoGeneralMap.put("city", cityUser);
        infoGeneralMap.put("birthday", birthdayUser);
        infoGeneralMap.put("country", countryUser);
        infoGeneralMap.put("birthplace", birthplaceUser);
        infoGeneralMap.put("gender", gender);

        getUserDocumentReference(Prevalent.currentUserOnline.getPhone()).update(infoGeneralMap).addOnSuccessListener(o -> {
            Intent intent = new Intent(InfoGeneralActivity.this, InterestedForActivity.class);
            startActivity(intent);
        });
    }
}