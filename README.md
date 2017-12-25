AFRouter
=========
组件和模块间Activity路由框架，通过动态代理技术实现，轻量、灵活

特性
======== 
 
 * 支持绝大多数参数类型（Intent可携带的数据类型）
 * 专为组件和模块间Activity路由设计，组件化UI路由解耦
 * 支持options（Android 5.0 启动Activity方式）
 * 支持Context、Activity和Fragment作为调用者（Activity.startActivity()、Context.startActivity()和Fragment.startActivity()）
 * 支持设置request code（startActivityForResult()）
 * 提供拦截器（Interceptor），全局过滤Activity
 * 支持安全启动Activity（未找到目标Activity时路由到默认的Activity）
 * 可以得到Intent包装类Wrapper，然后可以自己作处理
 * 支持Android 2.3及以上版本

使用
=====

### 添加依赖
```groovy
dependencies {
    api 'com.tubb.afrouter:afrouter:0.3.1'
}
```

### 定义Router Service
首先要定义`Router Service`，告诉框架如何来启动一个Activity，直接来看示例
```java
public interface AFRouterService {
    /**
     * 以Activity类全名的方式来启动Activity
     */
    @ActivityName("com.tubb.afrouter.sample.NormalActivity") // 类全名
    void start();
    /**
     * 指定Action的方式来启动Activity
     * @param action intent action
     * @param reqCode startActivityForResult() request code
     */
    void actionStart(@ParamKey("action") String action, @ParamKey("reqCode") int reqCode);
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    void forResult(@ParamKey("reqCode") int reqCode);
    @ActivityName("com.tubb.afrouter.sample.BackStartActivity")
    void backStart();
    /**
     * 自己手动处理Activity的启动
     * @return Intent Wrapper
     */
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    Wrapper returnTypeStart();

    /**
     * 携带options
     * @param bundle 5.0 options
     */
    @ActivityName("com.tubb.afrouter.sample.NormalActivity")
    void activityOptionsStart(@ParamKey("options") Bundle bundle);
    /**
     * 支持携带的参数类型
     */
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
```

### 快速使用
定义了`Router Service`后，后面的调用流程将非常简单，一两行代码即可启动Activity
```java
// Router Service可以保存在全局变量中
AFRouterService afService = AFRouter.getInstance().create(AFRouterService.class, this);
afService.forResult(FOR_RESULT_CODE);
```

### 通过Wrapper类使用
得到框架对Intent包装类Wrapper，然后由自己来处理
```java
Wrapper wrapper = afService.returnTypeStart();
// wrapper.addFlags();
// wrapper.setAction();
// wrapper.setClassName();
// // 真实的Intent
// wrapper.getIntent();
wrapper.start();
```

### 拦截过滤
支持全局拦截，可以过滤掉一些Activity
```java
afService = AFRouter.getInstance().create(AFRouterService.class, this, new Interceptor() {
    @Override
    public boolean intercept(Wrapper wrapper) {
        Toast.makeText(wrapper.getContext(), "Interceptor return true", Toast.LENGTH_SHORT).show();
        return true;
    }
});
afService.backStart();
```

### 框架内置的几个参数
```xml
"action"    Intent Action   如果传了这个参数，`Intent.setAction()`将会被调用
"reqCode"   startActivityForResult() request code   如果传了这个参数，最终调用的是`startActivityForResult()`
"options"   startActivity(Intent intent, Bundle options) 如果传了这个参数，`startActivity(Intent intent, Bundle options)`或`startActivityForResult(Intent intent, Bundle options)`将被调用
```

### 配置默认Activity（找不到目标Activity时显示的Activity，避免APP崩溃）
```xml
<meta-data
    tools:replace="android:value"
    android:name="com.tubb.afrouter.ERROR_ACTIVITY_CLASS_NAME"
    android:value="[默认Activity的类全名（包名.类名）]"/>
```

### 混淆配置
``` groovy
-keep class com.tubb.afrouter.annotations.** { *; }
```

详细使用请参照Demo工程，强烈建议clone下来查看

License
-------

    Copyright 2017 TUBB

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
