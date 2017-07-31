package com.ane.expresstokenapp.widget.common;

import android.view.View;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener{

    private int mThrottleFirstTime = 600;
    private long mLastClickTime = 0;

    public NoDoubleClickListener() {
    }

    public NoDoubleClickListener(int throttleFirstTime) {
        mThrottleFirstTime = throttleFirstTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > mThrottleFirstTime) {
            mLastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
