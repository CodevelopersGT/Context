package com.codevelopers.mcontext;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Context {

    private Activity activity;
    private Fragment fragment;

    public Context(@NonNull Activity activity){

        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Context(@NonNull Fragment fragment){

        this.fragment = fragment;
    }



    public android.content.Context getContext() {

        if (isFragment())
            return getFragment().getContext();


        return activity.getApplicationContext();
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode){

        if (requestCode < 0)
            return;

        if (activity != null)
            activity.startActivityForResult(intent, requestCode);
        else if (fragment != null)
            fragment.startActivityForResult(intent, requestCode);
    }

    public boolean hasPermission(@NonNull String permission){


        if (permission.trim().isEmpty())
            return false;


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        return getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(@NonNull String permission, int requestCode){

        if (requestCode < 0)
            return;

        if (permission.trim().isEmpty())
            return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (activity != null)
            activity.requestPermissions(new String[]{permission}, requestCode);
        else if (fragment != null)
            fragment.requestPermissions(new String[]{permission}, requestCode);
    }

    public boolean isFragment(){

        return fragment != null;
    }

    public boolean isActivity(){

        return activity != null;
    }
}
