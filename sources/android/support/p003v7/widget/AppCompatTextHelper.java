package android.support.p003v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p000v4.content.res.ResourcesCompat.FontCallback;
import android.support.p000v4.widget.AutoSizeableTextView;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p003v7.appcompat.C0200R;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* renamed from: android.support.v7.widget.AppCompatTextHelper */
class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private boolean mAsyncFontPending;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mStyle = 0;
    private final TextView mView;

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    /* access modifiers changed from: 0000 */
    @SuppressLint({"NewApi"})
    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        boolean z;
        boolean z2;
        AttributeSet attributeSet2 = attributeSet;
        int i2 = i;
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet2, C0200R.styleable.AppCompatTextHelper, i2, 0);
        int resourceId = obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (obtainStyledAttributes.hasValue(C0200R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (obtainStyledAttributes.hasValue(C0200R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (obtainStyledAttributes.hasValue(C0200R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (obtainStyledAttributes.hasValue(C0200R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (VERSION.SDK_INT >= 17) {
            if (obtainStyledAttributes.hasValue(C0200R.styleable.AppCompatTextHelper_android_drawableStart)) {
                this.mDrawableStartTint = createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_drawableStart, 0));
            }
            if (obtainStyledAttributes.hasValue(C0200R.styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.mDrawableEndTint = createTintInfo(context, appCompatDrawableManager, obtainStyledAttributes.getResourceId(C0200R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
            }
        }
        obtainStyledAttributes.recycle();
        boolean z3 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean z4 = true;
        ColorStateList colorStateList3 = null;
        if (resourceId != -1) {
            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, C0200R.styleable.TextAppearance);
            if (z3 || !obtainStyledAttributes2.hasValue(C0200R.styleable.TextAppearance_textAllCaps)) {
                z2 = false;
                z = false;
            } else {
                z = obtainStyledAttributes2.getBoolean(C0200R.styleable.TextAppearance_textAllCaps, false);
                z2 = true;
            }
            updateTypefaceAndStyle(context, obtainStyledAttributes2);
            if (VERSION.SDK_INT < 23) {
                ColorStateList colorStateList4 = obtainStyledAttributes2.hasValue(C0200R.styleable.TextAppearance_android_textColor) ? obtainStyledAttributes2.getColorStateList(C0200R.styleable.TextAppearance_android_textColor) : null;
                colorStateList = obtainStyledAttributes2.hasValue(C0200R.styleable.TextAppearance_android_textColorHint) ? obtainStyledAttributes2.getColorStateList(C0200R.styleable.TextAppearance_android_textColorHint) : null;
                if (obtainStyledAttributes2.hasValue(C0200R.styleable.TextAppearance_android_textColorLink)) {
                    colorStateList3 = obtainStyledAttributes2.getColorStateList(C0200R.styleable.TextAppearance_android_textColorLink);
                }
                ColorStateList colorStateList5 = colorStateList4;
                colorStateList2 = colorStateList3;
                colorStateList3 = colorStateList5;
            } else {
                colorStateList2 = null;
                colorStateList = null;
            }
            obtainStyledAttributes2.recycle();
        } else {
            colorStateList2 = null;
            colorStateList = null;
            z2 = false;
            z = false;
        }
        TintTypedArray obtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet2, C0200R.styleable.TextAppearance, i2, 0);
        if (z3 || !obtainStyledAttributes3.hasValue(C0200R.styleable.TextAppearance_textAllCaps)) {
            z4 = z2;
        } else {
            z = obtainStyledAttributes3.getBoolean(C0200R.styleable.TextAppearance_textAllCaps, false);
        }
        if (VERSION.SDK_INT < 23) {
            if (obtainStyledAttributes3.hasValue(C0200R.styleable.TextAppearance_android_textColor)) {
                colorStateList3 = obtainStyledAttributes3.getColorStateList(C0200R.styleable.TextAppearance_android_textColor);
            }
            if (obtainStyledAttributes3.hasValue(C0200R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList = obtainStyledAttributes3.getColorStateList(C0200R.styleable.TextAppearance_android_textColorHint);
            }
            if (obtainStyledAttributes3.hasValue(C0200R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList2 = obtainStyledAttributes3.getColorStateList(C0200R.styleable.TextAppearance_android_textColorLink);
            }
        }
        if (VERSION.SDK_INT >= 28 && obtainStyledAttributes3.hasValue(C0200R.styleable.TextAppearance_android_textSize) && obtainStyledAttributes3.getDimensionPixelSize(C0200R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes3);
        obtainStyledAttributes3.recycle();
        if (colorStateList3 != null) {
            this.mView.setTextColor(colorStateList3);
        }
        if (colorStateList != null) {
            this.mView.setHintTextColor(colorStateList);
        }
        if (colorStateList2 != null) {
            this.mView.setLinkTextColor(colorStateList2);
        }
        if (!z3 && z4) {
            setAllCaps(z);
        }
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
        this.mAutoSizeTextHelper.loadFromAttributes(attributeSet2, i2);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
            int[] autoSizeTextAvailableSizes = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
            if (autoSizeTextAvailableSizes.length > 0) {
                if (((float) this.mView.getAutoSizeStepGranularity()) != -1.0f) {
                    this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
                } else {
                    this.mView.setAutoSizeTextTypeUniformWithPresetSizes(autoSizeTextAvailableSizes, 0);
                }
            }
        }
        TintTypedArray obtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(context, attributeSet2, C0200R.styleable.AppCompatTextView);
        int dimensionPixelSize = obtainStyledAttributes4.getDimensionPixelSize(C0200R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
        int dimensionPixelSize2 = obtainStyledAttributes4.getDimensionPixelSize(C0200R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
        int dimensionPixelSize3 = obtainStyledAttributes4.getDimensionPixelSize(C0200R.styleable.AppCompatTextView_lineHeight, -1);
        obtainStyledAttributes4.recycle();
        if (dimensionPixelSize != -1) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, dimensionPixelSize);
        }
        if (dimensionPixelSize2 != -1) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != -1) {
            TextViewCompat.setLineHeight(this.mView, dimensionPixelSize3);
        }
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        this.mStyle = tintTypedArray.getInt(C0200R.styleable.TextAppearance_android_textStyle, this.mStyle);
        boolean z = false;
        if (tintTypedArray.hasValue(C0200R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(C0200R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int i = tintTypedArray.hasValue(C0200R.styleable.TextAppearance_fontFamily) ? C0200R.styleable.TextAppearance_fontFamily : C0200R.styleable.TextAppearance_android_fontFamily;
            if (!context.isRestricted()) {
                final WeakReference weakReference = new WeakReference(this.mView);
                try {
                    this.mFontTypeface = tintTypedArray.getFont(i, this.mStyle, new FontCallback() {
                        public void onFontRetrievalFailed(int i) {
                        }

                        public void onFontRetrieved(@NonNull Typeface typeface) {
                            AppCompatTextHelper.this.onAsyncTypefaceReceived(weakReference, typeface);
                        }
                    });
                    if (this.mFontTypeface == null) {
                        z = true;
                    }
                    this.mAsyncFontPending = z;
                } catch (NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.mFontTypeface == null) {
                String string = tintTypedArray.getString(i);
                if (string != null) {
                    this.mFontTypeface = Typeface.create(string, this.mStyle);
                }
            }
            return;
        }
        if (tintTypedArray.hasValue(C0200R.styleable.TextAppearance_android_typeface)) {
            this.mAsyncFontPending = false;
            int i2 = tintTypedArray.getInt(C0200R.styleable.TextAppearance_android_typeface, 1);
            if (i2 == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
            } else if (i2 == 2) {
                this.mFontTypeface = Typeface.SERIF;
            } else if (i2 == 3) {
                this.mFontTypeface = Typeface.MONOSPACE;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onAsyncTypefaceReceived(WeakReference<TextView> weakReference, Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            TextView textView = (TextView) weakReference.get();
            if (textView != null) {
                textView.setTypeface(typeface, this.mStyle);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onSetTextAppearance(Context context, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, C0200R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(C0200R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(obtainStyledAttributes.getBoolean(C0200R.styleable.TextAppearance_textAllCaps, false));
        }
        if (VERSION.SDK_INT < 23 && obtainStyledAttributes.hasValue(C0200R.styleable.TextAppearance_android_textColor)) {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(C0200R.styleable.TextAppearance_android_textColor);
            if (colorStateList != null) {
                this.mView.setTextColor(colorStateList);
            }
        }
        if (obtainStyledAttributes.hasValue(C0200R.styleable.TextAppearance_android_textSize) && obtainStyledAttributes.getDimensionPixelSize(C0200R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAllCaps(boolean z) {
        this.mView.setAllCaps(z);
    }

    /* access modifiers changed from: 0000 */
    public void applyCompoundDrawablesTints() {
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (VERSION.SDK_INT < 17) {
            return;
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
        }
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable != null && tintInfo != null) {
            AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
        }
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList tintList = appCompatDrawableManager.getTintList(context, i);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void setTextSize(int i, float f) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(i, f);
        }
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    /* access modifiers changed from: 0000 */
    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int i, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(i, f);
    }

    /* access modifiers changed from: 0000 */
    public void setAutoSizeTextTypeWithDefaults(int i) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(i);
    }

    /* access modifiers changed from: 0000 */
    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
    }

    /* access modifiers changed from: 0000 */
    public void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] iArr, int i) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
    }

    /* access modifiers changed from: 0000 */
    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    /* access modifiers changed from: 0000 */
    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    /* access modifiers changed from: 0000 */
    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    /* access modifiers changed from: 0000 */
    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    /* access modifiers changed from: 0000 */
    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }
}