package jeffhatfieldapps.resumeapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Animations {
    private static final String TAG = "Animations";

    public static final int TOUCH_DELAY = 50;

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Drawable new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageDrawable(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    public static Animator.AnimatorListener listenerWithWindowFlagClear(final Activity activity, final int delay){
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                if (delay > 0)
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "onAnimationEnd");
                            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    }, delay);
                else {
                    Log.d(TAG, "onAnimationEnd");
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }

    public static ObjectAnimator fadeInAnimation(View view, int animDuration, int delay) {
        return fadeInAnimation(view, 1, animDuration, delay);
    }

    public static ObjectAnimator fadeInAnimation(View view, float percent, int animDuration, int delay){
        ObjectAnimator fadeOutAnim = ObjectAnimator.ofFloat(view, "alpha", percent);
        fadeOutAnim.setDuration(animDuration);
        fadeOutAnim.setStartDelay(delay);
        return fadeOutAnim;
    }

    public static ObjectAnimator fadeOutAnimation(View view, int animDuration, int delay) {
        return fadeOutAnimation(view, 0, animDuration, delay);
    }

    public static ObjectAnimator fadeOutAnimation(View view, float percent, int animDuration, int delay) {
        ObjectAnimator fadeOutAnim = ObjectAnimator.ofFloat(view, "alpha", percent);
        fadeOutAnim.setDuration(animDuration);
        fadeOutAnim.setStartDelay(delay);
        return fadeOutAnim;
    }

    public static AnimatorSet crossFadeAnimation(View viewIn, View viewOut, int animDuration, int delay){
        ObjectAnimator fadeIn = fadeInAnimation(viewIn, animDuration, delay);
        ObjectAnimator fadeOut = fadeOutAnimation(viewOut, animDuration, delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fadeIn, fadeOut);
        return animatorSet;
    }

    public static ObjectAnimator translateAnimation(View view, String direction, int target, int animDuration, int delay){

        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(view, direction, target);
        translateAnimation.setDuration(animDuration);
        translateAnimation.setStartDelay(delay);

        return translateAnimation;
    }

    public static ObjectAnimator slideToRightAnimation(final View view, int animDuration, int delay) {

        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "x", 320);
        anim.setDuration(animDuration);
        anim.setStartDelay(delay);

        return anim;
    }

    public static AnimatorSet mkMainActivityIntroAnimation(View splitViewTop, View splitViewBottom, TextView myNext, TextView mySocial, TextView myLooks, View downArrow, boolean useDelay) {
        int translateDuration = 400;
        int translateDelay = useDelay ? 400 : 0;

        int splitDuration = 450;
        int splitDelay = translateDuration + translateDelay+ 100;

        int textDelayFactor = 150;
        int textDuration = splitDuration - textDelayFactor;
        int textDelay = splitDelay + textDelayFactor;

        float topTarget = splitViewTop.getY();
        float bottomTarget = splitViewBottom.getY();

        splitViewTop.setX(-320);
        splitViewTop.setY(320/2);
        splitViewBottom.setX(-320);
        splitViewBottom.setY(320/2);

        myNext.setAlpha(0);
        mySocial.setAlpha(0);
        myLooks.setAlpha(0);
        downArrow.setAlpha(0);

        ObjectAnimator translateXTopSplit = ObjectAnimator.ofFloat(splitViewTop, View.X, 0);
        translateXTopSplit.setDuration(translateDuration);
        translateXTopSplit.setStartDelay(translateDelay);

        ObjectAnimator translateXBottomSplit = ObjectAnimator.ofFloat(splitViewBottom, View.X, 0);
        translateXBottomSplit.setDuration(translateDuration);
        translateXBottomSplit.setStartDelay(translateDelay);

        ObjectAnimator splitTopAnim = ObjectAnimator.ofFloat(splitViewTop,  View.Y, topTarget);
        splitTopAnim.setDuration(splitDuration);
        splitTopAnim.setStartDelay(splitDelay);
        splitTopAnim.setInterpolator(new DecelerateInterpolator(1.25f));

        ObjectAnimator splitBottomAnim = ObjectAnimator.ofFloat(splitViewBottom, View.Y, bottomTarget);
        splitBottomAnim.setDuration(splitDuration);
        splitBottomAnim.setStartDelay(splitDelay);
        splitBottomAnim.setInterpolator(new DecelerateInterpolator(1.25f));

        ObjectAnimator myNextAnim = ObjectAnimator.ofFloat(myNext, View.ALPHA, 1);
        myNextAnim.setDuration(textDuration);
        myNextAnim.setStartDelay(textDelay);

        ObjectAnimator mySocialAnim = ObjectAnimator.ofFloat(mySocial, View.ALPHA, 1);
        mySocialAnim.setDuration(textDuration);
        mySocialAnim.setStartDelay(textDelay);

        ObjectAnimator myLooksAnim = ObjectAnimator.ofFloat(myLooks, View.ALPHA, 1);
        myLooksAnim.setDuration(textDuration);
        myLooksAnim.setStartDelay(textDelay);

        ObjectAnimator downArrowAnim = ObjectAnimator.ofFloat(downArrow, View.ALPHA, 1);
        downArrowAnim.setDuration(textDuration);
        downArrowAnim.setStartDelay(textDelay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateXTopSplit, translateXBottomSplit);
        animatorSet.playTogether(splitTopAnim, splitBottomAnim);
        animatorSet.playTogether(myNextAnim, mySocialAnim, myLooksAnim, downArrowAnim);

        return animatorSet;
    }

    public static AnimatorSet mkMainActivityTransitionToMyLooks(View[] splits, View[] buttons){

        ObjectAnimator[] splitsAnimations = new ObjectAnimator[splits.length];
        for(int i = 0; i < splits.length; i++){
            splitsAnimations[i] = ObjectAnimator.ofFloat(splits[i], View.TRANSLATION_X, 320);
            splitsAnimations[i].setDuration(350);
        }

        ObjectAnimator[] buttonsAnimations = new ObjectAnimator[buttons.length];
        for(int i = 0; i < buttons.length; i++){
            buttonsAnimations[i] = ObjectAnimator.ofFloat(buttons[i], View.ALPHA, 0f);
            buttonsAnimations[i].setDuration(200);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(splitsAnimations);
        animatorSet.playTogether(buttonsAnimations);

        return animatorSet;

    }

    public static AnimatorSet expandFromCenterAnimatorSet(final View topView, View bottomView, final int topViewHeight, final float factor, int topTarget, int bottomTarget, int itemsAnimDuration, int delay) {

        ObjectAnimator topAnim = ObjectAnimator.ofFloat(topView, "y", topTarget);
        topAnim.setDuration(itemsAnimDuration);
        topAnim.setStartDelay(delay);
        topAnim.setInterpolator(new DecelerateInterpolator(1.25f));
        topAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nextY = ((float) animation.getAnimatedValue());
                if(topView.getHeight() == 0){
                    nextY -= 2;
                }
                Log.d(TAG, "Current Y: " + topView.getY() + ", next Y: " + nextY + ", current Height: " + topView.getHeight());
                if (nextY < 320 / 2 + 1f && topView.getHeight() < topViewHeight) {
                    ViewGroup.LayoutParams layoutParams = topView.getLayoutParams();
                    layoutParams.height = Math.round((320 / 2 + 1f - nextY) * factor);
                    if(layoutParams.height > topViewHeight)
                        layoutParams.height = topViewHeight;
                    topView.setLayoutParams(layoutParams);
                    //Log.d(TAG, "New height: " + layoutParams.height);
                }
            }
        });

        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(bottomView, "y", bottomTarget);
        bottomAnim.setDuration(itemsAnimDuration);
        bottomAnim.setStartDelay(delay);
        bottomAnim.setInterpolator(new DecelerateInterpolator(1.25f));


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(topAnim);
        animatorSet.play(bottomAnim);

        return animatorSet;
    }

    public static AnimatorSet expandAnimatorSet(View topView, View bottomView, int animDuration, int delay, boolean direction) {

        ObjectAnimator topAnim = ObjectAnimator.ofFloat(topView, "y", direction ? -(320 / 2 - topView.getY()) : (float) -(320 / 2 + topView.getHeight()));

        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(bottomView, "y", direction ? (float) (320 * 1.5) : (320 + (bottomView.getY() - 320 / 2)));

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(topAnim, bottomAnim);
        animSet.setDuration(animDuration);
        animSet.setStartDelay(delay);
        animSet.setInterpolator(new AccelerateInterpolator(1.1f));

        return animSet;
    }

    public static AnimatorSet expandAndEnterAnimatorSet(View splitView, View topView, View bottomView, View enterView, int splitAnimDuration, int delay, int animDifference, int enterAnimDuration, boolean direction) {

        int topBottomAnimDuration = splitAnimDuration - delay;
        int topBottomDelay = delay + animDifference;
        int enterDelay = topBottomDelay + topBottomAnimDuration * 3 / 5;

        ObjectAnimator splitAnim = ObjectAnimator.ofFloat(splitView, "y", direction ? -2 : 320 + 2);
        splitAnim.setInterpolator(new AccelerateInterpolator(1.35f));
        splitAnim.setDuration(splitAnimDuration);
        splitAnim.setStartDelay(delay);

        ObjectAnimator topAnim = ObjectAnimator.ofFloat(topView, "y", direction ? -(320 / 2 - topView.getY()) : (float) -(320 / 2 + topView.getHeight()));
        topAnim.setDuration(topBottomAnimDuration);
        topAnim.setStartDelay(topBottomDelay);
        topAnim.setInterpolator(new AccelerateInterpolator(1.1f));

        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(bottomView, "y", direction ? (float) (320 * 1.5) : (320 + (bottomView.getY() - 320 / 2)));
        bottomAnim.setDuration(topBottomAnimDuration);
        bottomAnim.setStartDelay(topBottomDelay);
        bottomAnim.setInterpolator(new AccelerateInterpolator(1.1f));

        PropertyValuesHolder scaleXEnterProp = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder scaleYEnterProp = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        PropertyValuesHolder alphaEnterProp = PropertyValuesHolder.ofFloat("alpha", 0.4f, 1f);
        ObjectAnimator enterAnim = ObjectAnimator.ofPropertyValuesHolder(enterView, scaleXEnterProp, scaleYEnterProp, alphaEnterProp);
        enterAnim.setDuration(enterAnimDuration);
        enterAnim.setStartDelay(enterDelay);
        enterAnim.setInterpolator(new DecelerateInterpolator(1.75f));


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(splitAnim);
        animatorSet.play(topAnim);
        animatorSet.play(bottomAnim);
        animatorSet.play(enterAnim);

        return animatorSet;
    }

    public static AnimatorSet fadeInTopBottomAnimatorSet(View splitView, View topView, View bottomView, int animDuration, int delay) {

        ObjectAnimator splitAlphaAnim = ObjectAnimator.ofFloat(splitView, "alpha", 1, 0);
        ObjectAnimator topAlphaAnim = ObjectAnimator.ofFloat(topView, "alpha", 0, 1);
        ObjectAnimator bottomAlphaAnim = ObjectAnimator.ofFloat(bottomView, "alpha", 0, 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(topAlphaAnim, bottomAlphaAnim, splitAlphaAnim);
        animatorSet.setDuration(animDuration);
        animatorSet.setStartDelay(delay);

        return animatorSet;
    }

    public static ObjectAnimator scaleUpAndFadeOutAnimation(View view, int animDuration, int delay) {

        PropertyValuesHolder scaleXExitProp = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f);
        PropertyValuesHolder scaleYExitProp = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f);
        PropertyValuesHolder alphaExitProp = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(view, scaleXExitProp, scaleYExitProp, alphaExitProp);
        anim.setDuration(animDuration);
        anim.setStartDelay(delay);
        anim.setInterpolator(new AccelerateInterpolator(1.25f));

        return anim;
    }

    public static ObjectAnimator scaleUpAndFadeInAnimation(View view, int animDuration, int delay) {

        PropertyValuesHolder scaleXEnterProp = PropertyValuesHolder.ofFloat("scaleX", 0.75f, 1f);
        PropertyValuesHolder scaleYEnterProp = PropertyValuesHolder.ofFloat("scaleY", 0.75f, 1f);
        PropertyValuesHolder alphaEnterProp = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(view, scaleXEnterProp, scaleYEnterProp, alphaEnterProp);
        anim.setDuration(animDuration);
        anim.setStartDelay(delay);
        anim.setInterpolator(new DecelerateInterpolator(1.5f));

        return anim;
    }

    public static AnimatorSet mkBackButtonAnimation(final ProgressBar ringProgressBar, View xView, int animDuration, int delay) {
        ValueAnimator progressAnim = ValueAnimator.ofInt(1, 100);
        progressAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ringProgressBar.setProgress((Integer) animation.getAnimatedValue());
            }
        });

        PropertyValuesHolder scaleXProp = PropertyValuesHolder.ofFloat("scaleX", 0.4f, 1f);
        PropertyValuesHolder scaleYProp = PropertyValuesHolder.ofFloat("scaleY", 0.4f, 1f);
        PropertyValuesHolder rotationProp = PropertyValuesHolder.ofFloat("rotation", 0f, 180f);
        ObjectAnimator xAnim = ObjectAnimator.ofPropertyValuesHolder(xView, scaleXProp, scaleYProp, rotationProp);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(progressAnim, xAnim);
        animatorSet.setDuration(animDuration);
        animatorSet.setStartDelay(delay);

        return animatorSet;
    }

    public static AnimatorSet translateTopAndBottom(View textView, int textTarget, View leftButton, View rightButton, int buttonTarget, int animDuration, int delay){

        ObjectAnimator fadeInAnim = fadeInAnimation(textView, (int)(delay + animDuration*0.25), 0);

        ObjectAnimator textAnim = translateAnimation(textView, "y", textTarget, animDuration, delay);
        textAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator leftAnim = translateAnimation(leftButton, "y", buttonTarget, animDuration, delay);
        leftAnim.setInterpolator(new DecelerateInterpolator(1.25f));

        ObjectAnimator rightAnim = translateAnimation(rightButton, "y", buttonTarget, animDuration, (int)(delay*1.5));
        rightAnim.setInterpolator(new DecelerateInterpolator(1.25f));

        AnimatorSet introAnimSet = new AnimatorSet();
        introAnimSet.play(fadeInAnim);
        introAnimSet.play(textAnim);
        introAnimSet.play(leftAnim);
        introAnimSet.play(rightAnim);

        return introAnimSet;
    }

    public static AnimatorSet turnCoverOn(View coverView, TextView coverTextView, int animDuration, int delay){
        ObjectAnimator coverAlphaAnim = fadeInAnimation(coverView, 0.75f, animDuration, delay);
        ObjectAnimator coverTextAlphaAnim = fadeInAnimation(coverTextView, animDuration, delay);

        AnimatorSet coverAnimatorSet = new AnimatorSet();
        coverAnimatorSet.playTogether(coverAlphaAnim, coverTextAlphaAnim);

        return coverAnimatorSet;
    }

    public static AnimatorSet turnCoverOff(View coverView, TextView coverTextView, int animDuration, int delay){
        ObjectAnimator coverAlphaAnim = fadeOutAnimation(coverView, animDuration, delay);
        ObjectAnimator coverTextAlphaAnim = fadeOutAnimation(coverTextView, animDuration, delay);

        AnimatorSet coverAnimatorSet = new AnimatorSet();
        coverAnimatorSet.playTogether(coverAlphaAnim, coverTextAlphaAnim);

        return coverAnimatorSet;
    }

    public static ObjectAnimator bounceTranslateAnimation(View view, String direction, int target, int animationDuration, int delay){
        ObjectAnimator translateAnimation = translateAnimation(view, direction, target, animationDuration, delay);
        translateAnimation.setInterpolator(new AnticipateInterpolator(1.2f));
        return translateAnimation;
    }

    public static AnimatorSet backButtonAnimation(final ProgressBar ringProgressBar, View xView, int animDuration, int delay) {
        ValueAnimator progressAnim = ValueAnimator.ofInt(1, 100);
        progressAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ringProgressBar.setProgress((Integer) animation.getAnimatedValue());
            }
        });

        PropertyValuesHolder scaleXProp = PropertyValuesHolder.ofFloat("scaleX", 0.4f, 1f);
        PropertyValuesHolder scaleYProp = PropertyValuesHolder.ofFloat("scaleY", 0.4f, 1f);
        PropertyValuesHolder rotationProp = PropertyValuesHolder.ofFloat("rotation", 0f, 180f);
        ObjectAnimator xAnim = ObjectAnimator.ofPropertyValuesHolder(xView, scaleXProp, scaleYProp, rotationProp);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(progressAnim, xAnim);
        animatorSet.setDuration(animDuration);
        animatorSet.setStartDelay(delay);

        return animatorSet;
    }

    public static AnimatorSet mkConfirmationPromptIntro(final View textLayout, final View cancelButton, final View confirmButton){

        int firstStartDelay = 300;
        int secondStartDelay = 450;
        int duration = 500;
        DecelerateInterpolator buttonInterpolator = new DecelerateInterpolator(1.25f);

        final ObjectAnimator cancelTranslate = ObjectAnimator.ofFloat(cancelButton, "translationY", 220, 0);
        cancelTranslate.setDuration(duration);
        cancelTranslate.setStartDelay(firstStartDelay);
        cancelTranslate.setInterpolator(buttonInterpolator);
        cancelTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                cancelButton.setAlpha(1f);
            }

            @Override
            public void onAnimationEnd(Animator animator) {}

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        ObjectAnimator confirmTranslate = ObjectAnimator.ofFloat(confirmButton, "translationY", 220, 0);
        confirmTranslate.setDuration(duration);
        confirmTranslate.setStartDelay(secondStartDelay);
        confirmTranslate.setInterpolator(buttonInterpolator);
        confirmTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                confirmButton.setAlpha(1f);
            }

            @Override
            public void onAnimationEnd(Animator animator) {}

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        ObjectAnimator textLayoutTranslate = ObjectAnimator.ofFloat(textLayout, "translationY", -160, 0);
        textLayoutTranslate.setDuration(duration);
        textLayoutTranslate.setStartDelay(firstStartDelay);
        textLayoutTranslate.setInterpolator(new AccelerateDecelerateInterpolator());
        textLayoutTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                textLayout.setAlpha(1f);
            }

            @Override
            public void onAnimationEnd(Animator animator) {}

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(cancelTranslate);
        animatorSet.play(confirmTranslate);
        animatorSet.play(textLayoutTranslate);

        return animatorSet;
    }

    public static ObjectAnimator scaleUpOverShoot(View view, float interpolator, int animationDuration, int delay){
        PropertyValuesHolder scaleXProp = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder scaleYProp = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXProp, scaleYProp);
        animator.setDuration(animationDuration);
        animator.setStartDelay(delay);
        animator.setInterpolator(new OvershootInterpolator(interpolator));

        return animator;
    }

    public static ObjectAnimator scaleDownAnticipate(View view, float interpolator, int animationDuration, int delay){
        PropertyValuesHolder scaleXProp = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder scaleYProp = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXProp, scaleYProp);
        animator.setDuration(animationDuration);
        animator.setStartDelay(delay);
        animator.setInterpolator(new AnticipateInterpolator(interpolator));

        return animator;
    }

    public static ObjectAnimator mkInstagramLoading(final View circle, int duration, int delay){
        PropertyValuesHolder scaleXProp = PropertyValuesHolder.ofFloat("scaleX", 0.2f, 0.66f,  1, 0.66f, 0.2f);
        PropertyValuesHolder scaleYProp = PropertyValuesHolder.ofFloat("scaleY", 0.2f, 0.66f,  1, 0.66f, 0.2f);
        PropertyValuesHolder alphaProp = PropertyValuesHolder.ofFloat("alpha", 0, 0.66f,  1, 0.66f, 0);
        PropertyValuesHolder translateYProp = PropertyValuesHolder.ofFloat("translationX", 120);


        final ObjectAnimator startAnim = ObjectAnimator.ofPropertyValuesHolder(circle, scaleXProp, scaleYProp, alphaProp, translateYProp);
        startAnim.setDuration(duration);
        startAnim.setStartDelay(delay);
        startAnim.setRepeatCount(ValueAnimator.INFINITE);
        startAnim.setRepeatMode(ValueAnimator.RESTART);
        startAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {
                circle.setX(89);
                circle.setAlpha(0f);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return startAnim;
    }

    public static AnimatorSet mkMainSwipeTransition(final View mySocialText, final View myLooksText, final View myModesText, final View splitTop, final View splitBottom, int duration, int delay){

        ObjectAnimator mySocialTextFadeOut = fadeOutAnimation(mySocialText, duration-25, delay);
        ObjectAnimator myLooksTextFadeOut = fadeOutAnimation(myLooksText, duration-25, delay);
        ObjectAnimator myModesTextFadeOut = fadeOutAnimation(myModesText, duration-25, delay);
        ObjectAnimator translateTopAnim = slideToRightAnimation(splitTop, duration, delay);
        ObjectAnimator translateBottomAnim = slideToRightAnimation(splitBottom, duration, delay);

        AnimatorSet mySocialTransitionAnimSet = new AnimatorSet();
        mySocialTransitionAnimSet.playTogether(mySocialTextFadeOut, myLooksTextFadeOut, myModesTextFadeOut, translateTopAnim, translateBottomAnim);
        mySocialTransitionAnimSet.setInterpolator(new AccelerateInterpolator(1.1f));

        return mySocialTransitionAnimSet;
    }

    public static AnimatorSet mkMyModesTransition(final View mySocialText, final View myLooksText, final View myModesText, final View splitTop, final View splitBottom, int duration, int delay){
        int firstDuration = (int) (duration * 3f / 4f);

        ObjectAnimator mySocialTextFadeOut = fadeOutAnimation(mySocialText, firstDuration-25, delay);
        ObjectAnimator myLooksTextFadeOut = fadeOutAnimation(myLooksText, firstDuration-25, delay);
        ObjectAnimator myModesTextFadeOut = fadeOutAnimation(myModesText, firstDuration-25, delay);
        ObjectAnimator translateTopAnim = slideToRightAnimation(splitTop, firstDuration, delay);
        ObjectAnimator translateBottomAnim = ObjectAnimator.ofFloat(splitBottom, "y", 320/2);
        translateBottomAnim.setDuration(duration);
        translateBottomAnim.setStartDelay(delay);


        AnimatorSet mySocialTransitionAnimSet = new AnimatorSet();
        mySocialTransitionAnimSet.playTogether(mySocialTextFadeOut, myLooksTextFadeOut, myModesTextFadeOut, translateTopAnim, translateBottomAnim);
        mySocialTransitionAnimSet.setInterpolator(new AccelerateInterpolator(1.1f));

        return mySocialTransitionAnimSet;
    }

    public static AnimatorSet mkMySocialIntroAnim(final View igIcon, final View igText, int duration, int delay) {
        PropertyValuesHolder iconAlphaProp = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder iconTranslateYProp = PropertyValuesHolder.ofFloat("translationY", 10);

        ObjectAnimator iconAnim = ObjectAnimator.ofPropertyValuesHolder(igIcon, iconAlphaProp, iconTranslateYProp);
        iconAnim.setDuration(duration);
        iconAnim.setStartDelay(delay);

        PropertyValuesHolder textAlphaProp = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder textTranslateYProp = PropertyValuesHolder.ofFloat("translationY", 20);

        ObjectAnimator textAnim = ObjectAnimator.ofPropertyValuesHolder(igText, textAlphaProp, textTranslateYProp);
        textAnim.setDuration(duration);
        textAnim.setStartDelay(delay+100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(iconAnim);
        animatorSet.play(textAnim);

        return animatorSet;
    }

    public static AnimatorSet mkGridPhotoToPreview(View photo, View grid, int fadeOutDuration, int translateDuration, int delay){
        PropertyValuesHolder photoScaleXProp = PropertyValuesHolder.ofFloat("scaleX", 1, (112f/90f));
        PropertyValuesHolder photoScaleYProp = PropertyValuesHolder.ofFloat("scaleY", 1, (112f/90f));
        PropertyValuesHolder photoTranslateXProp = PropertyValuesHolder.ofFloat("x", 92);
        PropertyValuesHolder photoTranslateYProp = PropertyValuesHolder.ofFloat("y", 84);

        ObjectAnimator photoAnim = ObjectAnimator.ofPropertyValuesHolder(photo, photoScaleXProp, photoScaleYProp, photoTranslateXProp, photoTranslateYProp);
        photoAnim.setDuration(translateDuration);
        photoAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator gridFadeOutAnim = fadeOutAnimation(grid, fadeOutDuration, delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(gridFadeOutAnim, photoAnim);

        return animatorSet;
    }

    public static AnimatorSet mkMyNextInfoEnterTransition(TextView info, ImageView downArrow) {

        PropertyValuesHolder translateYProp = PropertyValuesHolder.ofFloat("translationY", 15);
        PropertyValuesHolder alphaEnterProp = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ObjectAnimator infoAnim = ObjectAnimator.ofPropertyValuesHolder(info, translateYProp, alphaEnterProp);
        ObjectAnimator downArrowAnim = ObjectAnimator.ofFloat(downArrow, "alpha", 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(infoAnim, downArrowAnim);
        animatorSet.setDuration(300);
        animatorSet.setStartDelay(300);

        return animatorSet;
    }

    public static ObjectAnimator translateDownAlphaIn(View view, float translate, int duration, int delay){
        PropertyValuesHolder translateProp = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, translate, 0);
        PropertyValuesHolder alphaProp = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, translateProp, alphaProp);
        animator.setDuration(duration);
        animator.setStartDelay(delay);

        return animator;
    }
}
