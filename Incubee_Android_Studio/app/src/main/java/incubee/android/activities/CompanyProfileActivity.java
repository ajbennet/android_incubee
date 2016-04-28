package incubee.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import incubee.android.R;
import incubee.android.fragment.SavedProjects;

/**
 * Copyright © 2016 Zonoff, Inc.  All Rights Reserved.
 */
public class CompanyProfileActivity extends BaseActivity{

    private static final String TAG = "CompanyProfileActivity";

    public static Intent getIntent(Context context) {

        Intent intent = new Intent(context, CompanyProfileActivity.class);
        return intent;
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(CompanyProfileActivity.getIntent(activity.getApplicationContext()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profiles);

        setupSavedProjectsView();
    }

    private void setupSavedProjectsView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.saved_projects, new SavedProjects(), SavedProjects.TAG)
                .commit();
    }
}
