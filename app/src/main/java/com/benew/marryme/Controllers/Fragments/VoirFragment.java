package com.benew.marryme.Controllers.Fragments;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.DefaultItemAnimator;

import com.benew.marryme.Adapters.UsersAdapter;
import com.benew.marryme.Bases.BaseFragment;
import com.benew.marryme.FirebaseUsage.FirestoreUsage;
import com.benew.marryme.Modals.User;
import com.benew.marryme.R;
import com.benew.marryme.UTILS.Prevalent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import butterknife.BindView;

import static com.benew.marryme.UTILS.Constants.MALE_GENDER;

public class VoirFragment extends BaseFragment implements CardStackListener {

    @BindView(R.id.card_stack_view) CardStackView cardStackView;
    @BindView(R.id.skip_button) FloatingActionButton skip;
    @BindView(R.id.rewind_button) FloatingActionButton rewind;
    @BindView(R.id.like_button) FloatingActionButton like;

    CardStackLayoutManager manager;
    UsersAdapter adapter;

    public VoirFragment() {}

    public static VoirFragment newInstance() { return new VoirFragment(); }

    @Override
    public int getLayout() { return R.layout.fragment_voir; }

    @Override
    public void configuration() {

        configureUsersAdapter();

        setupCardStackView();
        setupButton();
    }

    private void configureUsersAdapter() {
        manager = new CardStackLayoutManager(getContext(), this);

        if (Prevalent.currentUserOnline.getGender().equals(MALE_GENDER))
            adapter = new UsersAdapter(optionsFemale, getContext());
        else
            adapter = new UsersAdapter(optionsMale, getContext());

        adapter.notifyDataSetChanged();
    }


    private void setupCardStackView() {
        initialize();
    }

    private void setupButton() {
        skip.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();

            manager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });


        rewind.setOnClickListener(v -> {
            RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new DecelerateInterpolator())
                    .build();

            manager.setRewindAnimationSetting(setting);
            cardStackView.rewind();
        });


        like.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();

            manager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });
    }

    private void initialize() {
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }

    //GETTING MALES
    Query queryMale = FirestoreUsage.getMaleUsersCollectionReference().orderBy("name");
    FirestoreRecyclerOptions<User> optionsMale = new FirestoreRecyclerOptions.Builder<User>().setQuery(queryMale, User.class).setLifecycleOwner(this).build();

    //GETTING FEMALE
    Query queryFemale = FirestoreUsage.getFemaleUsersCollectionReference().orderBy("name");
    FirestoreRecyclerOptions<User> optionsFemale = new FirestoreRecyclerOptions.Builder<User>().setQuery(queryFemale, User.class).setLifecycleOwner(this).build();
}