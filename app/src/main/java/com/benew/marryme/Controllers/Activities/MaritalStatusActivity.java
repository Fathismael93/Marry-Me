package com.benew.marryme.Controllers.Activities;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MaritalStatusActivity extends BaseActivity {

    @BindView(R.id.work_answer_group) RadioGroup workGroup;
    @BindView(R.id.dropdown_marital_status) AutoCompleteTextView dropdownMaritalStatus;
    @BindView(R.id.mariages) TextInputLayout mariagesInput;
    @BindView(R.id.childrens) TextInputLayout childrensInput;

    private String choosenStatus;
    List allStatus;
    //VARIABLE POUR CONFIGURER L'ADAPTER DU DROPDOWN
    ArrayAdapter<String> adapter;

    String workAnswer = "", mariages = "", childrens = "";

    @Override
    protected int getLayout() { return R.layout.activity_marital_status; }

    @Override
    protected void configuration() {
        allStatus = new ArrayList<>();
        allStatus.add("Célibataire");
        allStatus.add("Marié(e)");
        allStatus.add("Divorcé(e)");

        //CONFIGURATION DE L'ADAPTER QUI VA AFFICHER LA LISTE DES DIFFERENTS DESSERTS
        adapter = new ArrayAdapter<>(this, R.layout.dropdown_marital_status, allStatus);
        dropdownMaritalStatus.setAdapter(adapter);
        dropdownMaritalStatus.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            //RECUPERATION DE LA CATEGORIE SELECTIONNEE
            choosenStatus = item.toString();
        });
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

        mariages = mariagesInput.getEditText().getText().toString().trim();
        childrens = childrensInput.getEditText().getText().toString().trim();

        saveDataOnDatabase(workAnswer, choosenStatus, mariages, childrens);
    }

    @OnClick(R.id.ignorer_textView)
    void onClickIgnorer() {
        choosenStatus = "";

        saveDataOnDatabase(workAnswer, choosenStatus, mariages, childrens);
    }

    private void saveDataOnDatabase(String workAnswer, String choosenStatus, String mariages, String childrens) {
        Map actualPersonalStatusMap = new HashMap();
        actualPersonalStatusMap.put("work_answer", workAnswer);
        actualPersonalStatusMap.put("actual_martital_status", choosenStatus);
        actualPersonalStatusMap.put("mariages", mariages);
        actualPersonalStatusMap.put("childrens", childrens);

        /*getUserDocumentReference(Prevalent.currentUserOnline.getMail()).update(actualPersonalStatusMap).addOnSuccessListener(o -> {
            Intent intent = new Intent(MaritalStatusActivity.this, NoyauActivity.class);
            startActivity(intent);
        });*/
    }
}