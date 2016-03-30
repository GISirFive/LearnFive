package com.telchina.pub.construction.base;

import android.app.Activity;

/**
 * Created by GISirFive on 2016-3-21.
 */
public interface ILifeRecycler {

    /**
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    void onResume();

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    void onPause();

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link Activity#onStop()} and before {@link android.support.v4.app.Fragment#onDetach()}.
     */
    void onDestroy();

}
