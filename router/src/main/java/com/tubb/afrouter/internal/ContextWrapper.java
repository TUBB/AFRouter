package com.tubb.afrouter.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.tubb.afrouter.Wrapper;

import java.lang.reflect.Method;

public class ContextWrapper extends Wrapper<Context> {

    public ContextWrapper(Context sender) {
        super(sender);
    }

    public ContextWrapper(Context sender, Method method, Object... args) {
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
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtras(mExtras);
        if (!Utils.isIntentSafe(mSender.getPackageManager(), mIntent)) {
            mIntent.setClassName(mSender, mErrorActivityClassName);
        }
        if (mReqCode != null) {
            throw new IllegalArgumentException("You may be used the wrong AFRouter.create() method.");
        }
        if (Utils.jellyBean() && mOptions != null) {
            mSender.startActivity(mIntent, mOptions);
        } else {
            mSender.startActivity(mIntent);
        }
    }
}
