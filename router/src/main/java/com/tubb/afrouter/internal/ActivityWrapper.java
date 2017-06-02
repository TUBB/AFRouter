package com.tubb.afrouter.internal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.tubb.afrouter.Wrapper;

import java.lang.reflect.Method;

/**
 * Created by tubingbing on 17/5/26.
 */

public class ActivityWrapper extends Wrapper<Activity> {

    public ActivityWrapper(Activity sender) {
        super(sender);
    }

    public ActivityWrapper(Activity sender, Method method, Object... args) {
        super(sender, method, args);
    }

    @Override
    public Context getContext() {
        return mSender;
    }

    @SuppressLint("NewApi")
    @Override
    public void start() {
        super.start();
        if (mTargetClassName != null) {
            mIntent.setClassName(mSender, mTargetClassName);
        }
        if (mAction != null) {
            mIntent.setAction(mAction);
        }
        mIntent.putExtras(mExtras);
        if (!Utils.isIntentSafe(mSender.getPackageManager(), mIntent)) {
            mIntent.setClassName(mSender, mErrorActivityClassName);
        }
        if (mReqCode != null) {
            if (Utils.jellyBean() && mOptions != null) {
                mSender.startActivityForResult(mIntent, mReqCode, mOptions);
            } else {
                mSender.startActivityForResult(mIntent, mReqCode);
            }
        } else {
            if (Utils.jellyBean() && mOptions != null) {
                mSender.startActivity(mIntent, mOptions);
            } else {
                mSender.startActivity(mIntent);
            }
        }
    }
}
