package org.devio.rn.splashscreen;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.devio.rn.splashscreen.animation.AnimationUtils;

import java.lang.ref.WeakReference;

/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class SplashScreen {
    private static Dialog mSplashDialog;
    private static WeakReference<Activity> mActivity;

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final int themeResId) {
        if (activity == null) return;
        mActivity = new WeakReference<Activity>(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!activity.isFinishing()) {
                    mSplashDialog = new Dialog(activity, themeResId);
                    mSplashDialog.setContentView(R.layout.launch_screen);
                    mSplashDialog.setCancelable(false);

                    Animation fadeIn = AnimationUtils.getFadeInAnimation();
                    RotateAnimation rotate = AnimationUtils.getRotateAnimation();

                    int loadingImageId = activity.getResources().getIdentifier("loadingImage", "id", activity.getPackageName());
                    int loadingTitleId = activity.getResources().getIdentifier("loadingTitle", "id", activity.getPackageName());
                    int loadingTextId = activity.getResources().getIdentifier("loadingText", "id", activity.getPackageName());
                    ImageView image = mSplashDialog.findViewById(loadingImageId);
                    if(image != null) {
                        AnimationSet set = new AnimationSet(false);
                        set.addAnimation(fadeIn);
                        set.addAnimation(rotate);
                        image.startAnimation(set);
                    }
                    TextView title = mSplashDialog.findViewById(loadingTitleId);
                    if(title != null) {
                        title.startAnimation(fadeIn);
                    }
                    TextView text = mSplashDialog.findViewById(loadingTextId);
                    if(text != null) {
                        text.startAnimation(fadeIn);
                    }
                    if (!mSplashDialog.isShowing()) {
                        mSplashDialog.show();
                    }
                }
            }
        });
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity, final boolean fullScreen) {
        int resourceId = fullScreen ? R.style.SplashScreen_Fullscreen : R.style.SplashScreen_SplashTheme;

        show(activity, resourceId);
    }

    /**
     * 打开启动屏
     */
    public static void show(final Activity activity) {
        show(activity, false);
    }

    /**
     * 关闭启动屏
     */
    public static void hide(Activity activity) {
        if (activity == null) {
            if (mActivity == null) {
                return;
            }
            activity = mActivity.get();
        }

        if (activity == null) return;

        final Activity _activity = activity;

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSplashDialog != null && mSplashDialog.isShowing()) {
                    boolean isDestroyed = false;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        isDestroyed = _activity.isDestroyed();
                    }

                    if (!_activity.isFinishing() && !isDestroyed) {
                        mSplashDialog.dismiss();
                    }
                    mSplashDialog = null;
                }
            }
        });
    }
}
