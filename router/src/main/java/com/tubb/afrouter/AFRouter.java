package com.tubb.afrouter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tubb.afrouter.internal.ActivityWrapper;
import com.tubb.afrouter.internal.ContextWrapper;
import com.tubb.afrouter.internal.Fragment4Wrapper;
import com.tubb.afrouter.internal.FragmentWrapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class AFRouter {

    private static AFRouter instance;

    private AFRouter() {}

    public static synchronized AFRouter getInstance() {
        if (instance == null) {
            instance = new AFRouter();
        }
        return instance;
    }

    public <T> T create(@NonNull Class<T> service, @NonNull Context sender) {
        return create(service, sender, null);
    }

    public <T> T create(@NonNull Class<T> service, @NonNull Activity sender) {
        return create(service, sender, null);
    }

    public <T> T create(@NonNull Class<T> service, @NonNull Fragment sender) {
        return create(service, sender, null);
    }

    public <T> T create(@NonNull Class<T> service, @NonNull android.support.v4.app.Fragment sender) {
        return create(service, sender, null);
    }

    public <T> T create(@NonNull Class<T> service, @NonNull Context sender, @Nullable Interceptor interceptor) {
        return create(service, new ContextWrapper(sender), interceptor);
    }

    public <T> T create(@NonNull Class<T> service, @NonNull Activity sender, @Nullable Interceptor interceptor) {
        return create(service, new ActivityWrapper(sender), interceptor);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public <T> T create(@NonNull Class<T> service, @NonNull Fragment sender, @Nullable Interceptor interceptor) {
        return create(service, new FragmentWrapper(sender), interceptor);
    }

    public <T> T create(@NonNull Class<T> service, @NonNull android.support.v4.app.Fragment sender, @Nullable Interceptor interceptor) {
        return create(service, new Fragment4Wrapper(sender), interceptor);
    }

    private <T> T create(final Class<T> service, final Wrapper wrapper, final Interceptor interceptor) {
        Object proxyInstance = Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                        wrapper.setMethod(method);
                        wrapper.setMethodArgs(args);
                        Class returnType = method.getReturnType();
                        if (returnType == void.class) {
                            if (interceptor == null || !interceptor.intercept(wrapper)) {
                                wrapper.start();
                            }
                            return null;
                        } else if (returnType == Wrapper.class) {
                            return wrapper;
                        } else {
                            throw new RuntimeException("method return type only support 'void' or 'Wrapper'");
                        }
                    }
                });
        return (T)proxyInstance;
    }
}
