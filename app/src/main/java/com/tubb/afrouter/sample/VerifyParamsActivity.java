package com.tubb.afrouter.sample;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by tubingbing on 17/5/26.
 */

public class VerifyParamsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_params);

        /**
         @ParamKey("strP") String strP, @ParamKey("strsP") String[] strsP,
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
         */

        /** please debug the code, then see the value */
        String strP = getIntent().getStringExtra("strP");
        String[] strsP = getIntent().getStringArrayExtra("strsP");
        int intP = getIntent().getIntExtra("intP", -1);
        int[] intsP = getIntent().getIntArrayExtra("intsP");
        short shortP = getIntent().getShortExtra("shortP", Short.MIN_VALUE);
        short[] shortsP = getIntent().getShortArrayExtra("shortsP");
        long longP = getIntent().getLongExtra("longP", -1L);
        long[] longsP = getIntent().getLongArrayExtra("longsP");
        char charP = getIntent().getCharExtra("charP", Character.MIN_VALUE);
        char[] charsP = getIntent().getCharArrayExtra("charsP");
        double doubleP = getIntent().getDoubleExtra("doubleP", Double.MIN_VALUE);
        double[] doublesP = getIntent().getDoubleArrayExtra("doublesP");
        float floatP = getIntent().getFloatExtra("floatP", Float.MIN_VALUE);
        float[] floatsP = getIntent().getFloatArrayExtra("floatsP");
        byte byteP = getIntent().getByteExtra("byteP", Byte.MIN_VALUE);
        byte[] bytesP = getIntent().getByteArrayExtra("bytesP");
        boolean booleanP = getIntent().getBooleanExtra("booleanP", Boolean.FALSE);
        boolean[] booleansP = getIntent().getBooleanArrayExtra("booleansP");
        Bundle bundleP = getIntent().getBundleExtra("bundleP");
        SparseArray<ParcelableEntity> sparseArrayP = getIntent().getExtras().getSparseParcelableArray("sparseArrayP");
        ArrayList<Integer> arrayListIntP = getIntent().getIntegerArrayListExtra("arrayListIntP");
        ArrayList<String> arrayListStringP = getIntent().getStringArrayListExtra("arrayListStringP");
        ArrayList<CharSequence> arrayListCharSequenceP = getIntent().getCharSequenceArrayListExtra("arrayListCharSequenceP");
        ArrayList<ParcelableEntity> arrayListParcelableP = getIntent().getParcelableArrayListExtra("arrayListParcelableP");
        Parcelable[] parcelableArrayP = getIntent().getParcelableArrayExtra("parcelableArrayP");
        ParcelableEntity parcelableP = getIntent().getParcelableExtra("parcelableP");
        SerializableEntity serializableP = (SerializableEntity) getIntent().getSerializableExtra("serializableP");
    }
}
