package com.tubb.afrouter.internal;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;

import com.tubb.afrouter.Wrapper;

import java.lang.reflect.Method;

public class FragmentWrapper extends Wrapper<Fragment> {

    public FragmentWrapper(Fragment sender) {
        super(sender);
    }

    public FragmentWrapper(Fragment sender, Method method, Object... args) {
        super(sender, method, args);
    }

    @Override
    public Context getContext() {
        return mSender.getActivity();
    }

    @SuppressLint("NewApi")
    @Override
    public void start() {
        super.start();
        if (mTargetClassName != null) {
            mIntent.setClassName(mSender.getActivity(), mTargetClassName);
        }
        if (mAction != null) {
            mIntent.setAction(mAction);
        }
        mIntent.putExtras(mExtras);
        if (!Utils.isIntentSafe(mSender.getActivity().getPackageManager(), mIntent)) {
            mIntent.setClassName(mSender.getActivity(), mErrorActivityClassName);
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
