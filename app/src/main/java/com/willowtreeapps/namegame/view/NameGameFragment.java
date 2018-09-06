package com.willowtreeapps.namegame.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.core.NameGameApplication;
import com.willowtreeapps.namegame.databinding.NameGameFragmentBinding;
import com.willowtreeapps.namegame.model.Profile;
import com.willowtreeapps.namegame.util.StatusTypes;
import com.willowtreeapps.namegame.viewmodel.NameGameViewModel;
import com.willowtreeapps.namegame.viewmodel.NameGameViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class NameGameFragment extends Fragment implements ErrorDialogFragment.Callbacks {
    public static final String DIALOG_TRY_AGAIN = "tryAgain";
    private static final int REQUEST_TRY_AGAIN = 0;

    @Inject
    NameGameViewModelFactory mNameGameViewModelFactory;

    private NameGameFragmentBinding mBinding;
    private NameGameViewModel mViewModel;
    ErrorDialogFragment mErrorDialogFragment;

    public static NameGameFragment newInstance() {
        return new NameGameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        NameGameApplication.get(Objects.requireNonNull(getActivity())).component().inject(this);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.name_game_fragment,
                container, false);

        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = createViewModel();
        mViewModel.setLoading(true);
        mViewModel.loadData();

        mBinding.setViewModel(mViewModel);
        mBinding.setAnimateProfilesIn(true);
        mBinding.setLifecycleOwner(this);

        observeViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.resumeGame();
    }

    private void observeViewModel() {
        LiveData<String> mResponseCodeLiveData = mViewModel.getLiveResponseStatus();
        LiveData<Integer> mSelectedProfileLiveData = mViewModel.getSelectedProfileLiveData();

        final Observer<String> responseStatusObserver = integer ->
                showUI(Objects.requireNonNull(integer));

        final Observer<Integer> selectedProfileObserver = position -> {
            if (position != null) {
                View v = mBinding.faceContainer.getChildAt(position);
                if (position != mViewModel.getCorrectProfilePosition()) {
                    v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.wobble));
                }
            }
        };

        mResponseCodeLiveData.observe(this, responseStatusObserver);
        mSelectedProfileLiveData.observe(this, selectedProfileObserver);
    }

    private void showUI(String status) {
        List<Profile> gameProfiles = mViewModel.getGameProfiles().getValue();
        mViewModel.setLoading(false);
        if (StatusTypes.OK.equals(status)) {
            if (gameProfiles == null) {
                mViewModel.startGame();
            }

        } else {
            if (mErrorDialogFragment == null && gameProfiles == null) {
                Toast.makeText(getContext(), "Cannot connect to server", Toast.LENGTH_SHORT).show();
                mErrorDialogFragment = new ErrorDialogFragment();
                FragmentManager manager = getFragmentManager();
                mErrorDialogFragment.setTargetFragment(NameGameFragment.this, REQUEST_TRY_AGAIN);
                mErrorDialogFragment.show(Objects.requireNonNull(manager), DIALOG_TRY_AGAIN);
            }
        }
    }

    private NameGameViewModel createViewModel() {
        return ViewModelProviders.of(Objects.requireNonNull(getActivity()), mNameGameViewModelFactory)
                .get(NameGameViewModel.class);
    }

    @Override
    public void onTryAgainClicked() {
        if (mErrorDialogFragment != null) {
            if (mViewModel.getGameProfiles().getValue() == null) {
                mViewModel.setLoading(true);
                mViewModel.loadData();
            } else {
                mErrorDialogFragment.dismissAllowingStateLoss();
            }
        }
    }
}

