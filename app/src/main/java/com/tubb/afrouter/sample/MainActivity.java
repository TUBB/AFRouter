package com.tubb.afrouter.sample;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.tubb.afrouter.AFRouter;
import com.tubb.afrouter.Interceptor;
import com.tubb.afrouter.Wrapper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int FOR_RESULT_CODE= 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewClick(View view) {
        AFRouterService afService = AFRouter.getInstance().create(AFRouterService.class, this);
        switch (view.getId()) {
            case R.id.start:
                afService.start();
                break;
            case R.id.startFor:
                afService.forResult(FOR_RESULT_CODE);
                break;
            case R.id.backStart:
                startService(new Intent(this, BackStartService.class));
                break;
            case R.id.actionStart:
                afService.actionStart("com.tubb.afrouter.AF_ROUTER", FOR_RESULT_CODE);
                break;
            case R.id.interceptor:
                afService = AFRouter.getInstance().create(AFRouterService.class, this, new Interceptor() {
                    @Override
                    public boolean intercept(Wrapper wrapper) {
                        Toast.makeText(wrapper.getContext(), "Interceptor return true", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                afService.backStart();
                break;
            case R.id.returnType:
                Wrapper wrapper = afService.returnTypeStart();
                wrapper.start();
                break;
            case R.id.activityOptions:
                Bundle bundle = ActivityOptionsCompat.makeBasic().toBundle();
                afService.activityOptionsStart(bundle);
                break;
            case R.id.verifyParams:
                verifyParams(afService);
                break;
        }
    }

    private void verifyParams(AFRouterService afService) {
        SparseArray<ParcelableEntity> sparseArrayP= new SparseArray<>();
        sparseArrayP.put(0, new ParcelableEntity("AFRouter"));

        /** error */
//        SparseArray<Long> sparseArrayP= new SparseArray<>();
//        sparseArrayP.put(0, 100L);

        ArrayList<Integer> arrayListIntP = new ArrayList<>();
        arrayListIntP.add(Integer.MAX_VALUE);

        ArrayList<String> arrayListStringP = new ArrayList<>();
        arrayListStringP.add("AFRouter");

        ArrayList<CharSequence> arrayListCharSequenceP = new ArrayList<>();
        arrayListCharSequenceP.add("AFRouter");

        ArrayList<ParcelableEntity> arrayListParcelableP = new ArrayList<>();
        arrayListParcelableP.add(new ParcelableEntity("AFRouter"));

        ParcelableEntity[] parcelableArrayP = new ParcelableEntity[]{new ParcelableEntity("AFRouter")};

        afService.verifyParams(
                "AFRouter", new String[]{"AFRouter"},
                Integer.MAX_VALUE, new int[]{Integer.MAX_VALUE},
                Short.MAX_VALUE, new short[]{Short.MAX_VALUE},
                Long.MAX_VALUE, new long[]{Long.MAX_VALUE},
                Character.MAX_VALUE, new char[]{Character.MAX_VALUE},
                Double.MAX_VALUE, new double[]{Double.MAX_VALUE},
                Float.MAX_VALUE, new float[]{Float.MAX_VALUE},
                Byte.MAX_VALUE, new byte[]{Byte.MAX_VALUE},
                Boolean.TRUE, new boolean[]{Boolean.TRUE},
                Bundle.EMPTY,
                sparseArrayP,
                arrayListIntP,
                arrayListStringP,
                arrayListCharSequenceP,
                arrayListParcelableP,
                parcelableArrayP,
                new ParcelableEntity("AFRouter"),
                new SerializableEntity("AFRouter")
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FOR_RESULT_CODE) {
                Toast.makeText(this, "forResult test success", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
