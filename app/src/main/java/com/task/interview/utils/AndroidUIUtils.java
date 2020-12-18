package com.task.interview.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import com.task.interview.R;


public final class AndroidUIUtils {


    public static void setStatusBarColorRes(@NonNull Activity activity, @ColorRes int statusbarColor) {
        setStatusBarColor(activity, activity.getResources().getColor(statusbarColor));
    }


    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusbarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            if (window == null) return;
            // set status bar icons color to White or Black based on status bar's color
            int contrastColor = getContrastColor(statusbarColor);
            setStatusBarTheme(activity, contrastColor == Color.WHITE);

            // on APIs lower than M (basically 21 and 22), status bar's color shouldn't be white
            // so set a darker color so that icons will be visible
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && statusbarColor == Color.WHITE)
                statusbarColor = activity.getResources().getColor(R.color.colorAccent);

            // set status bar color
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusbarColor);
        }
    }

    public static int getStatusBarColor(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            if (window == null) {
                return -1;
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            return window.getStatusBarColor();
        }
        return -1;
    }

    private static int getContrastColor(@ColorInt int colorIntValue) {
        int red = Color.red(colorIntValue);
        int green = Color.green(colorIntValue);
        int blue = Color.blue(colorIntValue);
        double lum = (((0.299 * red) + ((0.587 * green) + (0.114 * blue))));
        return lum > 186 ? Color.BLACK : Color.WHITE;
    }

    private static void setStatusBarTheme(final Activity activity, final boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Fetch the current flags.
            final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
            // Update the SystemUiVisibility dependening on whether we want a Light or Dark theme.
            activity.getWindow().getDecorView().setSystemUiVisibility(isDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        }
    }



}
