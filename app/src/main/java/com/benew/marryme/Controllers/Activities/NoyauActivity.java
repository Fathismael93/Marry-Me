package com.benew.marryme.Controllers.Activities;

import androidx.fragment.app.Fragment;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.Controllers.Fragments.TchatFragment;
import com.benew.marryme.Controllers.Fragments.VoirFragment;
import com.benew.marryme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class NoyauActivity extends BaseActivity {

    private Fragment tchatFragment;
    private Fragment voirFragment;

    @BindView(R.id.activity_main_bottom_navigation) BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayout() { return R.layout.activity_noyau; }

    @Override
    protected void configuration() {
        this.configureBottomView();
        this.showTchatFragment();
    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->
                updateMainFragment(item.getItemId()));
    }

    private boolean updateMainFragment(int itemId) {
        switch (itemId) {
            case R.id.tchat:
                this.showTchatFragment();
                break;
            case R.id.voir:
                this.showVoirFragment();
                break;
        }
        return true;
    }

    private void showTchatFragment() {
        if (this.tchatFragment == null) this.tchatFragment = TchatFragment.newInstance();
        this.startTransaction(tchatFragment);
    }

    private void showVoirFragment() {
        if (this.voirFragment == null) this.voirFragment = VoirFragment.newInstance();
        this.startTransaction(voirFragment);
    }

    private void startTransaction(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
}