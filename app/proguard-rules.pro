# Add project specific ProGuard rules here.
# By default, Android Studio adds common rules from the SDK.
# You can add rules here to fine-tune how code is obfuscated,
# optimized, and kept for your application.

# If you put your default ProGuard rules in the Consumer Proguard file
# of your library, they are automatically applied to your consuming
# application.
# See http://developer.android.com/tools/help/proguard.html for more details.

# AdMob specific rules
-keep public class com.google.android.gms.ads.** { 
  public *;
}
-keep public class com.google.ads.** { 
  public *;
}
-keep public class com.google.android.gms.common.ConnectionResult {
    public *;
}
-keep public class com.google.android.gms.common.GooglePlayServicesUtil {
    public *;
}

# Hilt specific rules (usually handled by Hilt's own consumer-proguard-rules.pro)
# But good to have if issues arise.
-keep class * implements dagger.hilt.android.HiltViewModel
-keep class * implements dagger.hilt.android.internal.managers.ActivityComponentManager.GeneratedComponent
-keep class * implements dagger.hilt.android.internal.managers.FragmentComponentManager.GeneratedComponent
-keep class * implements dagger.hilt.android.internal.managers.ViewComponentManager.GeneratedComponent
-keep class * implements dagger.hilt.android.internal.managers.ServiceComponentManager.GeneratedComponent
-keep class * implements dagger.hilt.android.internal.managers.BroadcastReceiverComponentManager.GeneratedComponent
-keep class * implements dagger.hilt.android.internal.managers.ContentProviderComponentManager.GeneratedComponent
-keep class * implements dagger.hilt.android.internal.managers.ApplicationComponentManager.GeneratedComponent

# Room specific rules (Room usually provides its own rules)
# -keep class com.androidforge.streakflow.data.local.entity.** { *; }
# -keep class com.androidforge.streakflow.data.local.dao.** { *; }
# -keep class * extends androidx.room.RoomDatabase { *; }