package incubee.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by samuh on 10/17/2015.
 *
 * Base Fragment for all the fragments in the app
 */
public class BaseFragment extends Fragment {

    protected Context mAppContext;

    @Override
    public void onAttach(Activity context) {

        super.onAttach(context);

        mAppContext = context.getApplicationContext();
    }
}
