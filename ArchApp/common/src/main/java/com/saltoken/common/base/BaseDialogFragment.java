package com.saltoken.common.base;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class BaseDialogFragment extends AppCompatDialogFragment {


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof OnDismissListener) {
            ((OnDismissListener) parentFragment).onDismiss(this);
            return;
        }
        final Activity activity = getActivity();
        if (activity instanceof OnDismissListener) {
            ((OnDismissListener) activity).onDismiss(this);
        }
    }

    public interface OnDismissListener {
        void onDismiss(@NonNull DialogFragment dialogFragment);
    }
}
