package com.willowtreeapps.namegame.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.willowtreeapps.namegame.R;

public class NameGameProfileView extends LinearLayout {
    private TextView mTextView;
    private ImageView mImageView;

    public NameGameProfileView(Context context) {
        super(context);
    }

    // Use: Override View(Context, AttributeSet) when inflating Views from XML.
    public NameGameProfileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NameGameProfileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        inflate(getContext(), R.layout.name_game_profile_view, this);
        mTextView = findViewById(R.id.main_text);
        mImageView = findViewById(R.id.main_image);
    }

    public void setText(String text) {
        requestLayout();
        invalidate();
        mTextView.setText(text);

    }

    public void setImage(Drawable drawable) {
        requestLayout();
        invalidate();
        mImageView.setImageDrawable(drawable);

    }

    public String getText() {
        return (String) mTextView.getText();
    }

    public TextView getTextView() {
        return mTextView;
    }

    public ImageView getImageView() {
        return mImageView;
    }


}
