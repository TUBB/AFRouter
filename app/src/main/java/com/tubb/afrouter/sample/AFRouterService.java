package com.tubb.afrouter.sample;

import android.os.Bundle;
import android.util.SparseArray;

import com.tubb.afrouter.annotations.ActivityName;
import com.tubb.afrouter.annotations.ParamKey;
import com.tubb.afrouter.Wrapper;

import java.util.ArrayList;

/**
 * Created by tubingbing on 17/5/26.
 */

public interface AFRouterService {
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    void start();
    void actionStart(@ParamKey("action") String action, @ParamKey("reqCode") int reqCode);
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    void forResult(@ParamKey("reqCode") int reqCode);
    @ActivityName("com.tubb.afrouter.sample.BackStartActivity")
    void backStart();
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    Wrapper returnTypeStart();
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    void activityOptionsStart(@ParamKey("options") Bundle bundle);

    @ActivityName("com.tubb.afrouter.sample.VerifyParamsActivity")
    void verifyParams(@ParamKey("strP") String strP, @ParamKey("strsP") String[] strsP,
                      @ParamKey("intP") int intP, @ParamKey("intsP") int[] intsP,
                      @ParamKey("shortP") short shortP, @ParamKey("shortsP") short[] shortsP,
                      @ParamKey("longP") long longP, @ParamKey("longsP") long[] longsP,
                      @ParamKey("charP") char charP, @ParamKey("charsP") char[] charsP,
                      @ParamKey("doubleP") double doubleP, @ParamKey("doublesP") double[] doublesP,
                      @ParamKey("floatP") float floatP, @ParamKey("floatsP") float[] floatsP,
                      @ParamKey("byteP") byte byteP, @ParamKey("bytesP") byte[] bytesP,
                      @ParamKey("booleanP") boolean booleanP, @ParamKey("booleansP") boolean[] booleansP,
                      @ParamKey("bundleP") Bundle bundleP,
                      @ParamKey("sparseArrayP") SparseArray<ParcelableEntity> sparseArrayP,
                      @ParamKey("arrayListIntP") ArrayList<Integer> arrayListIntP,
                      @ParamKey("arrayListStringP") ArrayList<String> arrayListStringP,
                      @ParamKey("arrayListCharSequenceP") ArrayList<CharSequence> arrayListCharSequenceP,
                      @ParamKey("arrayListParcelableP") ArrayList<ParcelableEntity> arrayListParcelableP,
                      @ParamKey("parcelableArrayP") ParcelableEntity[] parcelableArrayP,
                      @ParamKey("parcelableP") ParcelableEntity parcelableP,
                      @ParamKey("serializableP") SerializableEntity serializableP
    );
}
