package com.tubb.afrouter.internal;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.tubb.afrouter.Wrapper;

import java.lang.reflect.Method;

public class Fragment4Wrapper extends Wrapper<Fragment> {

    public Fragment4Wrapper(Fragment sender) {
        super(sender);
    }

    public Fragment4Wrapper(Fragment sender, Method method, Object... args) {
        super(sender, method, args);
    }

    @Override
    public Context getContext() {
        return mSender.getActivity();
    }

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
