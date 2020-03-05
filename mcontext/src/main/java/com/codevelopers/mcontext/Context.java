package com.codevelopers.mcontext;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class Context {

    private FragmentActivity activity;
    private Fragment fragment;

    public Context(@NonNull FragmentActivity activity) {

        this.activity = activity;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Context(@NonNull Fragment fragment) {

        this.fragment = fragment;
    }


    public android.content.Context getContext() {

        if (isFragment())
            return getFragment().getContext();


        return activity.getApplicationContext();
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode) {

        if (requestCode < 0)
            return;

        if (activity != null)
            activity.startActivityForResult(intent, requestCode);
        else if (fragment != null)
            fragment.startActivityForResult(intent, requestCode);
    }

    public boolean hasPermission(@NonNull String permission) {


        if (permission.trim().isEmpty())
            return false;


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        return getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(@NonNull String[] permission, int requestCode) {

        if (requestCode < 0)
            return;


        for (String mPermission : permission) {

            if (mPermission == null || mPermission.trim().isEmpty())
                throw new RuntimeException("is null or empty permission");
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (activity != null)
            activity.requestPermissions(permission, requestCode);
        else if (fragment != null)
            fragment.requestPermissions(permission, requestCode);
    }

    public boolean isFragment() {

        return fragment != null;
    }

    public boolean isActivity() {

        return activity != null;
    }
}
