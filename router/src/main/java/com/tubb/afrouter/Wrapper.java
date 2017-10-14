package com.tubb.afrouter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import com.tubb.afrouter.annotations.ActivityName;
import com.tubb.afrouter.annotations.ParamKey;
import com.tubb.afrouter.internal.Utils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

public abstract class Wrapper<T> {

    private static final String REQ_CODE_KEY_NAME = "reqCode";
    private static final String ACTION_KEY_NAME = "action";
    private static final String OPTIONS_KEY_NAME = "options";
    private static final String ERROR_ACTIVITY_CLASS_NAME_META_NAME = "com.tubb.afrouter.ERROR_ACTIVITY_CLASS_NAME";

    protected String mErrorActivityClassName;

    protected T mSender;
    protected Method mMethod;
    protected Object[] mMethodArgs;

    protected String mTargetClassName;
    protected String mAction;
    protected Integer mReqCode;
    protected Bundle mOptions;
    protected Bundle mExtras = new Bundle();
    protected Intent mIntent = new Intent();

    protected Wrapper(T sender) {
        mSender = sender;
    }

    protected Wrapper(T sender, Method method, Object... args) {
        mSender = sender;
        mMethod = method;
        mMethodArgs = args;
    }

    public abstract Context getContext();

    public void start() {
        parseAnnotation();
        if (mTargetClassName == null && mAction == null) {
            throw new IllegalArgumentException("ActivityName and Action must be indicate one.");
        }
        mErrorActivityClassName = Utils.getMetaData(getContext(), ERROR_ACTIVITY_CLASS_NAME_META_NAME);
    }

    public void setClassName(String className) {
        mTargetClassName = className;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public void addFlags(int flags) {
        mIntent.addFlags(flags);
    }

    public Intent getIntent() {
        return mIntent;
    }

    void parseAnnotation() {
        Annotation[] methodAnnotations = mMethod.getAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (annotation instanceof ActivityName) {
                mTargetClassName = ((ActivityName)annotation).value();
            }
        }
        Type[] types = mMethod.getGenericParameterTypes();
        Annotation[][] parameterAnnotationsArray = mMethod.getParameterAnnotations();
        for(int i = 0, len = types.length; i < len; i++) {
            String key = null;
            Annotation[] parameterAnnotations = parameterAnnotationsArray[i];
            for(Annotation annotation : parameterAnnotations) {
                if (annotation instanceof ParamKey) {
                    key = ((ParamKey)annotation).value();
                    break;
                }
            }
            parseParameterAnnotation(types[i], key, mMethodArgs[i]);
        }
    }

    void setMethod(Method method) {
        mMethod = method;
    }

    void setMethodArgs(Object[] args) {
        mMethodArgs = args;
    }

    private void parseParameterAnnotation(Type type, String key, Object arg) {
        if (REQ_CODE_KEY_NAME.equals(key)) {
            if (!(arg instanceof Integer)) {
                throw new IllegalArgumentException(REQ_CODE_KEY_NAME + " param must be Integer type");
            } else {
                mReqCode = (Integer)arg;
            }
        } else if (ACTION_KEY_NAME.equals(key)) {
            if (!(arg instanceof String)) {
                throw new IllegalArgumentException(ACTION_KEY_NAME + " param must be String type");
            } else {
                mAction = String.valueOf(arg);
            }
        } else if (OPTIONS_KEY_NAME.equals(key)) {
            if (!(arg instanceof Bundle)) {
                throw new IllegalArgumentException(OPTIONS_KEY_NAME + " param must be Bundle type");
            } else {
                mOptions = (Bundle) arg;
            }
        } else {
            Class<?> rawParameterType = getRawType(type);
            if (rawParameterType == String.class) {
                mExtras.putString(key, arg.toString());
            } else if (rawParameterType == String[].class) {
                mExtras.putStringArray(key, (String[])arg);
            } else if (rawParameterType == int.class || rawParameterType == Integer.class) {
                mExtras.putInt(key, Integer.parseInt(arg.toString()));
            } else if (rawParameterType == int[].class || rawParameterType == Integer[].class) {
                mExtras.putIntArray(key, (int[])arg);
            } else if (rawParameterType == short.class || rawParameterType == Short.class) {
                mExtras.putShort(key, Short.parseShort(arg.toString()));
            } else if (rawParameterType == short[].class || rawParameterType == Short[].class) {
                mExtras.putShortArray(key, (short[])arg);
            } else if (rawParameterType == long.class || rawParameterType == Long.class) {
                mExtras.putLong(key, Long.parseLong(arg.toString()));
            } else if (rawParameterType == long[].class || rawParameterType == Long[].class) {
                mExtras.putLongArray(key, (long[])arg);
            } else if (rawParameterType == char.class) {
                mExtras.putChar(key, arg.toString().toCharArray()[0]);
            } else if (rawParameterType == char[].class) {
                mExtras.putCharArray(key, arg.toString().toCharArray());
            } else if (rawParameterType == double.class || rawParameterType == Double.class) {
                mExtras.putDouble(key, Double.parseDouble(arg.toString()));
            } else if (rawParameterType == double[].class || rawParameterType == Double[].class) {
                mExtras.putDoubleArray(key, (double[])arg);
            } else if (rawParameterType == float.class || rawParameterType == Float.class) {
                mExtras.putFloat(key, Float.parseFloat(arg.toString()));
            } else if (rawParameterType == float[].class || rawParameterType == Float[].class) {
                mExtras.putFloatArray(key, (float[])arg);
            } else if (rawParameterType == byte.class || rawParameterType == Byte.class) {
                mExtras.putByte(key, Byte.parseByte(arg.toString()));
            } else if (rawParameterType == byte[].class || rawParameterType == Byte[].class) {
                mExtras.putByteArray(key, (byte[])arg);
            } else if (rawParameterType == boolean.class || rawParameterType == Boolean.class) {
                mExtras.putBoolean(key, Boolean.parseBoolean(arg.toString()));
            } else if (rawParameterType == boolean[].class || rawParameterType == Boolean[].class) {
                mExtras.putBooleanArray(key, (boolean[]) arg);
            } else if (rawParameterType == Bundle.class) {
                mExtras.putBundle(key, (Bundle) arg);
            } else if (rawParameterType == SparseArray.class) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType)type;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments == null || actualTypeArguments.length != 1) {
                        throw new RuntimeException("SparseArray must be declare Generic");
                    }
                    Type actualTypeArgument = actualTypeArguments[0];
                    if (actualTypeArgument instanceof Class) {
                        Class<?>[] interfaces = ((Class)actualTypeArgument).getInterfaces();
                        for(Class<?> interfaceClass : interfaces) {
                            if (interfaceClass == Parcelable.class) {
                                mExtras.putSparseParcelableArray(key, (SparseArray<Parcelable>) arg);
                                return;
                            }
                        }
                        throw new RuntimeException("SparseArray must be declare Parcelable Generic");
                    }
                } else {
                    throw new RuntimeException("SparseArray must be declare Parcelable Generic");
                }
            } else if (rawParameterType == ArrayList.class) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType)type;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments(); // 泛型类型数组
                    if (actualTypeArguments == null || actualTypeArguments.length != 1) {
                        throw new RuntimeException("ArrayList must be declare Generic");
                    }
                    Type actualTypeArgument = actualTypeArguments[0]; // 获取第一个泛型类型
                    if (actualTypeArgument == String.class) {
                        mExtras.putStringArrayList(key, (ArrayList<String>) arg);
                    } else if (actualTypeArgument == Integer.class) {
                        mExtras.putIntegerArrayList(key, (ArrayList<Integer>) arg);
                    } else if (actualTypeArgument == CharSequence.class) {
                        mExtras.putCharSequenceArrayList(key, (ArrayList<CharSequence>) arg);
                    } else if (actualTypeArgument instanceof Class) {
                        Class<?>[] interfaces = ((Class)actualTypeArgument).getInterfaces();
                        for(Class<?> interfaceClass : interfaces) {
                            if (interfaceClass == Parcelable.class) {
                                mExtras.putParcelableArrayList(key, (ArrayList<Parcelable>) arg);
                                return;
                            }
                        }
                        throw new RuntimeException("ArrayList must be declare Parcelable Generic");
                    }
                } else {
                    throw new RuntimeException("ArrayList must be declare Parcelable Generic");
                }
            } else {
                if (rawParameterType.isArray()) { // Parcelable[]
                    Class<?>[] interfaces = rawParameterType.getComponentType().getInterfaces();
                    for(Class<?> interfaceClass : interfaces) {
                        if (interfaceClass == Parcelable.class) {
                            mExtras.putParcelableArray(key, (Parcelable[])arg);
                            return;
                        }
                    }
                    throw new RuntimeException("Object[] must be implements Parcelable");
                } else {
                    Class<?>[] interfaces = rawParameterType.getInterfaces();
                    for(Class<?> interfaceClass : interfaces) {
                        if (interfaceClass == Serializable.class) {
                            mExtras.putSerializable(key, (Serializable)arg);
                        } else if (interfaceClass == Parcelable.class) {
                            mExtras.putParcelable(key, (Parcelable)arg);
                        } else {
                            throw new RuntimeException("Bundle not support for " + key + " param");
                        }
                    }
                }
            }
        }
    }

    private Class<?> getRawType(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }
}
