package com.benew.marryme.Controllers.Activities;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.benew.marryme.Bases.BaseActivity;
import com.benew.marryme.Controllers.Fragments.TchatFragment;
import com.benew.marryme.Controllers.Fragments.VoirFragment;
import com.benew.marryme.R;
import com.benew.marryme.Receivers.ConnectivityReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.benew.marryme.API.StartNewActivityAPI.startNewActivity;

public class NoyauActivity extends BaseActivity {

    private Fragment tchatFragment;
    private Fragment voirFragment;

    @BindView(R.id.activity_main_bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseUser user;
    boolean isConnected;

    @Override
    protected int getLayout() { return R.layout.activity_noyau; }

    @Override
    protected void configuration() {
        isConnected = ConnectivityReceiver.isConnected(NoyauActivity.this);

        this.configureBottomView();
        this.showTchatFragment();

        setSupportActionBar(toolbar);

        if (!isConnected)
            Toasty.info(this, "Connexion interrompue ").show();
        else {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
        }

        toolbar.setTitle(user.getDisplayName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.logout:
                if (!isConnected)
                    Toasty.info(this, "Connexion interrompue ").show();
                else {
                    mAuth.signOut();
                    startNewActivity(NoyauActivity.this, MainActivity.class);
                }
                finish();
                break;
        }
        return true;
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