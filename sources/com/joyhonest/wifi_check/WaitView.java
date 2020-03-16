package com.joyhonest.wifi_check;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WaitView extends LinearLayout {
    private TextView TitleViewA = ((TextView) findViewById(C0287R.C0289id.tipTextView));
    Animation hyperspaceJumpAnimation;
    private ImageView spaceshipImage = ((ImageView) findViewById(C0287R.C0289id.img));

    public WaitView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(C0287R.layout.loading_dialog, this);
        this.hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, C0287R.anim.loading_animation);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            this.spaceshipImage.startAnimation(this.hyperspaceJumpAnimation);
        } else {
            this.spaceshipImage.clearAnimation();
        }
    }

    private void setTitleView(String str) {
        this.TitleViewA.setText(str);
    }

    private void setTitleView(int i) {
        this.TitleViewA.setText(i);
    }
}
