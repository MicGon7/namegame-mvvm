package com.willowtreeapps.namegame.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.willowtreeapps.namegame.R;

public class ErrorDialogFragment extends DialogFragment {

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onTryAgainClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallbacks = (Callbacks) getTargetFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Error. Please check internet connection")
                .setPositiveButton(R.string.dialog_positive_btn_text,
                        (dialogInterface, i) -> mCallbacks.onTryAgainClicked())
                .create();
    }
}
