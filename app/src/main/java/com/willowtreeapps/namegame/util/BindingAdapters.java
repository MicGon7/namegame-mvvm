package com.willowtreeapps.namegame.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BindingAdapter;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.willowtreeapps.namegame.R;
import com.willowtreeapps.namegame.model.Profile;
import com.willowtreeapps.namegame.view.NameGameProfileView;

import java.util.List;
import java.util.Objects;

// TODO: Add javadoc annotations.

public class BindingAdapters {
    private static final Interpolator OVERSHOOT = new OvershootInterpolator();
    private static final int SCALE_ANIM_DURATION = 800;
    private static final int FADE_ANIM_DURATION = 900;
    private static final int SCALE_ANIM_START_DELAY = 100;
    private static final int FADE_ANIM_START_DELAY = 2000;
    private static final String HTTPS_PREFIX = "https://";
    private static final String MAT_MODE_TEXT = "MAT(T) MODE!";
    private static final String FLIP_MODE_TEXT = "FLIP MODE!";

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    // TODO: Randomize index of faded views
    @BindingAdapter({"scaleVisible", "fadeVisible", "gameProfiles", "correctProfilePosition"})
    public static void setProfiles(final LinearLayout layout, boolean scaleVisible,
                                   boolean fadeVisible, List<Profile> gameProfiles,
                                   int correctProfilePosition) {

        if (gameProfiles != null) {
            int n = layout.getChildCount();

            setupProfiles(layout, gameProfiles);

            if (scaleVisible) {
                if (!fadeVisible) {
                    for (int i = 0; i < n; i++) {
                        scaleView(layout.getChildAt(i), i);
                    }
                }
                if (fadeVisible) {
                    for (int i = 0; i < n; i++) {
                        View v = layout.getChildAt(i);
                        v.setAlpha(1);
                        if (i != correctProfilePosition) {
                            fadeView(v, i);
                        }
                    }
                }
            }
        }
    }

    private static void setupProfiles(LinearLayout layout, List<Profile> gameProfiles) {
        for (int i = 0; i < gameProfiles.size(); i++) {
            NameGameProfileView profileView = (NameGameProfileView) layout.getChildAt(i);

            String displayName = gameProfiles.get(i).getFirstName() + " " + gameProfiles.get(i).getLastName();
            profileView.setText(displayName);


            //noinspection deprecation
            ViewTarget viewTarget = new ViewTarget<NameGameProfileView, BitmapDrawable>(profileView) {
                @Override
                public void onResourceReady(@NonNull BitmapDrawable bitmap,
                                            Transition<? super BitmapDrawable> transition) {
                    this.view.setImage(bitmap);
                }
            };

            int imageSize = (int) Ui.convertDpToPixel(100,
                    Objects.requireNonNull(profileView.getContext()));
            String url = HTTPS_PREFIX + gameProfiles.get(i).getHeadshot().getUrl();

            //noinspection unchecked
            GlideApp
                    .with(profileView.getContext())
                    .load(url)
                    .placeholder(R.drawable.ic_face_white_48dp)
                    .override(imageSize, imageSize)
                    .circleCrop()
                    .into(viewTarget);
        }
    }

    private static void scaleView(View v, int i) {
        v.setClickable(false);
        v.clearAnimation();
        v.animate().cancel();
        v.animate().setInterpolator(OVERSHOOT);
        v.animate().setDuration(SCALE_ANIM_DURATION);
        v.setVisibility(View.VISIBLE);
        v.setScaleX(0);
        v.setScaleY(0);
        v.setAlpha(1);
        v.animate()
                .scaleX(1)
                .scaleY(1)
                .setStartDelay(SCALE_ANIM_START_DELAY * i)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setClickable(true);
                    }
                });
    }

    private static void fadeView(View v, int i) {
        v.animate().cancel();
        v.animate()
                .setDuration(FADE_ANIM_DURATION)
                .alpha(0)
                .setStartDelay(FADE_ANIM_START_DELAY * i)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setAlpha(1);
                        v.setVisibility(View.INVISIBLE);
                    }
                });
    }

    //TODO: Refactor/Remove the following two methods since they are only being used for correct profile display
    @BindingAdapter("profileDisplayName")
    public static void setDisplayName(NameGameProfileView profileView, Profile profile) {
        if (profile != null) {

            String displayName = profile.getFirstName() + " " + profile.getLastName();
            profileView.setText(displayName);
        }
    }

    @BindingAdapter(("profileImageUrl"))
    public static void setProfileImageUrl(NameGameProfileView profileView, String url) {
        if (url != null) {
            int imageSize = (int) Ui.convertDpToPixel(100,
                    Objects.requireNonNull(profileView.getContext()));
            String formattedUrl = HTTPS_PREFIX + url;

            GlideApp.with(profileView.getContext())
                    .load(formattedUrl)
                    .placeholder(R.drawable.ic_face_white_48dp)
                    .error(R.drawable.ic_android_sad)
                    .override(imageSize, imageSize)
                    .centerCrop()
                    .circleCrop()
                    .into(profileView.getImageView());

            scaleView(profileView, 1);
        }
    }

    @BindingAdapter(("showProfileName"))
    public static void setProfileImageUrl(NameGameProfileView profileView, boolean showName) {
        TextView textView = profileView.getTextView();
        ImageView imageView = profileView.getImageView();

        textView.setVisibility(showName ? View.VISIBLE : View.GONE);
        imageView.setVisibility(showName ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter({"specialRound", "specialRoundText"})
    public static void setSpecialRoundText(TextView textView, boolean specialRound, int roundType) {
        textView.setVisibility(specialRound ? View.VISIBLE : View.GONE);
        if (textView.getTag() == null) {
            textView.setTag(true);
        } else {
            switch (roundType) {
                case RoundTypes.MATT_MODE:
                    textView.setText(MAT_MODE_TEXT);
                    break;
                case RoundTypes.FLIP_MODE:
                    textView.setText(FLIP_MODE_TEXT);
            }

            textView.invalidate();
        }
    }

}

