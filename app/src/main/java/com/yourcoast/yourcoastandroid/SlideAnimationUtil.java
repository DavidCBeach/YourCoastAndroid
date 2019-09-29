package com.yourcoast.yourcoastandroid;


import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

public class SlideAnimationUtil {

    /**
     * Animates a view so that it slides in from the left of it's container.
     *
     * @param context
     * @param view
     */
    public static void slideInFromLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }
    public static void slideInFromBottom(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_top);
    }
    public static void slideInFromTop(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_top);
    }
    public static void slideInToTop(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_top);
    }
    public static void slideInFromTopSlow(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_top_slow);
    }
    public static void slideInFromTopActual(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_top_actual);
    }
    public static void slideInToTopActual(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_top_actual);
    }

    /**
     * Animates a view so that it slides from its current position, out of view to the left.
     *
     * @param context
     * @param view
     */
    public static void slideOutToLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_left);
    }

    /**
     * Animates a view so that it slides in the from the right of it's container.
     *
     * @param context
     * @param view
     */
    public static void slideInFromRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_right);
    }

    /**
     * Animates a view so that it slides from its current position, out of view to the right.
     *
     * @param context
     * @param view
     */
    public static void slideOutToRight(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_right);
    }

    /**
     * Runs a simple animation on a View with no extra parameters.
     *
     * @param context
     * @param view
     * @param animationId
     */
    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(
                context, animationId
        ));
    }

}
