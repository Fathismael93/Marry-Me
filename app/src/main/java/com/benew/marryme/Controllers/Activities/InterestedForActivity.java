package com.benew.marryme.Controllers.Activities;

import android.content.Intent;
import android.widget.CheckBox;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.benew.marryme.UTILS.Prevalent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.benew.marryme.FirebaseUsage.FirestoreUsage.getUserDocumentReference;

public class InterestedForActivity extends BaseActivity {

    @BindView(R.id.relation_checkbox) CheckBox relationCheckbox;
    @BindView(R.id.mariage_checkbox) CheckBox mariageCheckbox;

    @Override
    protected int getLayout() { return R.layout.activity_interested_for; }

    @Override
    protected void configuration() {

    }

    @OnClick(R.id.to_next_button)
    void onClickNext() {
        if (!relationCheckbox.isChecked() && !mariageCheckbox.isChecked())
            Toasty.info(this, "Vous devez au moins choisir une case").show();
        else
            saveData();
    }

    private void saveData() {

        Map choiceMap = new HashMap();
        choiceMap.put("relation", relationCheckbox.isChecked());
        choiceMap.put("mariage", mariageCheckbox.isChecked());

        getUserDocumentReference(Prevalent.currentUserOnline.getPhone()).update(choiceMap).addOnSuccessListener(o -> {
            Intent intent = new Intent(InterestedForActivity.this, MaritalStatusActivity.class);
            startActivity(intent);
        });
    }
}