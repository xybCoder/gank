
#忽略警告
-dontwarn
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
 #预校验
-dontpreverify
 #混淆时是否记录日志
-verbose
 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#不混淆行数,方便debug
-keepattributes SourceFile,LineNumberTable
#不混淆注解
-keepattributes *Annotation*

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


# Keep the support library
-keep class android.support.** { *; }
-keep interface android.support.** { *; }

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
public *;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class android.support.v8.renderscript.** { *; }
# ActiveAndroid
-keep class com.activeandroid.** { *; }
-keep class com.activeandroid.**.** { *; }
-keep class * extends com.activeandroid.Model
-keep class * extends com.activeandroid.serializer.TypeSerializer

-keepattributes Signature
-keep class **.R$* {*;}
-ignorewarnings

 -keepattributes InnerClasses
#删除log代码
 -assumenosideeffects class android.util.Log {
           public static int v(...);
           public static int i(...);
           public static int w(...);
           public static int d(...);
           public static int e(...);
       }

-keep public class com.xybcoder.gank..R$*{
		public static final int *;
}

-keep class com.xybcoder.gank.BuildConfig { *; }
-keep public class * extends android.os.Binder
-dontwarn java.lang.invoke.*



# GSON
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# butterknife
-dontwarn butterknife.internal.**
-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
           @butterknife.* <fields>;
       }
-keepclasseswithmembernames class * {
           @butterknife.* <methods>;
       }
# okhttp
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-keep class okio.**{*;}
-keep class com.squareuo.haha.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class com.squareup.otto.**{*;}
-keep class com.squareup.**{*;}
 -keepclassmembers class ** {
           @com.squareup.otto.Subscribe public *;
           @com.squareup.otto.Produce public *;
       }
# retrofit
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
# RxJava:
-dontwarn rx.**
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**
# umeng
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep class com.umeng.** { *; }
-keep public class * extends com.umeng.**
-keep class com.alimana.**{*;}
-keep class com.u.upd.**{*;}
-keep class com.umeng.update.**{*;}


#glid
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

-keep class uk.co.senab.**{*;}
-keep class com.daimajia.**{*;}
-keep class com.commit451.**{*;}
-keep class com.github.castorflex.smoothprogressbar**{*;}
-keep class io.reactivex.**{*;}
-keep class com.squareup.leakcanary.**{*;}
-keep class com.android.support.**{*;}



