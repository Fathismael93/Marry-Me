package com.benew.marryme.Controllers.Activities;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class NoyauActivity extends BaseActivity {

    @BindView(R.id.activity_main_bottom_navigation) BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayout() { return R.layout.activity_noyau; }

    @Override
    protected void configuration() {
        this.configureBottomView();
    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->
                updateMainFragment(item.getItemId()));
    }

    private boolean updateMainFragment(int itemId) {
        switch (itemId) {
            case R.id.tchat:
                break;
            case R.id.search:
                break;
        }
        return true;
    }
}