package com.willowtreeapps.namegame.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.viewmodel.NameGameViewModel;
import com.willowtreeapps.namegame.viewmodel.NameGameViewModelFactory;

import javax.inject.Inject;


public class NameGameActivity extends AppCompatActivity {

    private static final String FRAG_TAG = "NameGameFragmentTag";


    @Inject
    NameGameViewModelFactory mNameGameViewModelFactory;

    private NameGameViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_game_activity);

        NameGameApplication.get(this).component().inject(this);

        mViewModel = createViewModel();

        if (savedInstanceState == null) {
            createFragment();
        }
    }

    private NameGameViewModel createViewModel() {
        return ViewModelProviders.of(this, mNameGameViewModelFactory)
                .get(NameGameViewModel.class);
    }

    private void createFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, NameGameFragment.newInstance(), FRAG_TAG)
                .commit();
    }
}