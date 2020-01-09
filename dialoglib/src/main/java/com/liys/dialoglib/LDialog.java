package com.liys.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description: 万能Dialog
 * @Author: liys
 * @CreateDate: 2020/1/8 15:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/1/8 15:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LDialog extends Dialog{

    private Context context;
    private SparseArray<View> views = new SparseArray<>();
    private int layoutId = 0;
    private int width = 0;
    private int height = 0;
    private int bgRadius = 0; //背景圆角
    private int bgColor = Color.WHITE; //背景颜色

    public LDialog(@NonNull Context context) {
        this(context, R.layout.dialog_confirm);
    }

    public LDialog(@NonNull Context context, int layoutId) {
        this(context, layoutId, R.style.LDialog);
    }

    public LDialog(@NonNull Context context, int layoutId, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(getRoundRectDrawable(DensityUtils.dp2px(context, 5), Color.WHITE));

        width = (int)(ScreenUtils.getWidthPixels(context)*0.8);
        height = WindowManager.LayoutParams.WRAP_CONTENT;
        setWidthHeight();
        getWindow().setWindowAnimations(R.style.dialog_alpha);
//        getWindow().setWindowAnimations(R.style.dialog_translate);
    }

    public LDialog with(){
        show();
        dismiss();
        return this;
    }

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置动画>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public LDialog setAnimationsStyle(int style){
        getWindow().setWindowAnimations(style);
        return this;
    }

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置位置>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 设置位置
     * @param gravity
     * @param offX
     * @param offY
     */
    public LDialog setGravity(int gravity, int offX, int offY){
        setGravity(gravity);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.x = offX;
        layoutParams.y = offY;
        getWindow().setAttributes(layoutParams);
        return this;
    }
    public LDialog setGravity(int gravity){
        getWindow().setGravity(gravity);
        return this;
    }


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置背景>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private LDialog setbg(){
        getWindow().setBackgroundDrawable(getRoundRectDrawable(bgRadius, bgColor));
        return this;
    }

    /**
     * 设置背景颜色
     * @return
     */
    public LDialog setBgColor(int color){
        bgColor = color;
        return setbg();
    }

    public LDialog setBgColorRes(int colorRes){
        bgColor = context.getResources().getColor(colorRes);
        return setbg();
    }

    /**
     *  设置背景圆角
     * @param bgRadius
     */
    public LDialog setBgRadius(int bgRadius){
        this.bgRadius = DensityUtils.dp2px(context, bgRadius);
        return setbg();
    }

    /**
     *  设置背景圆角
     * @param bgRadius
     */
    public LDialog setBgRadiusPX(int bgRadius){
        this.bgRadius = bgRadius;
        return setbg();
    }



//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置宽高>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 设置宽高
     */
    private LDialog setWidthHeight(){
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width;
        lp.height = height;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public LDialog setWidth(int width){
        this.width = DensityUtils.dp2px(context, width);
        return setWidthHeight();
    }

    public LDialog setWidthPX(int width){
        this.width = width;
        return setWidthHeight();
    }

    public LDialog setHeight(int height){
        this.height = DensityUtils.dp2px(context, height);
        return setWidthHeight();
    }
    public LDialog setHeightPX(int height){
        this.height = height;
        return setWidthHeight();
    }

    /**
     * 设置宽占屏幕的比例
     * @param widthRatio
     */
    public LDialog setWidthRatio(double widthRatio){
        width = (int)(ScreenUtils.getWidthPixels(context)*widthRatio);
        setWidthHeight();
        return this;
    }

    /**
     * 设置高占屏幕的比例
     * @param heightRatio
     */
    public LDialog setHeightRatio(double heightRatio){
        height = (int)(ScreenUtils.getHeightPixels(context)*heightRatio);
        setWidthHeight();
        return this;
    }

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 设置监听
     * @param onClickListener
     * @param viewIds
     */
    public LDialog setOnClickListener(final DialogOnClickListener onClickListener, int... viewIds){
        final LDialog lDialog = this;
        for (int i = 0; i < viewIds.length; i++) {
            getView(viewIds[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, lDialog);
                }
            });
        }
        return this;
    }

    /**
     * 设置 关闭dialog的按钮
     * @param viewId
     * @return
     */
    public LDialog setCancelBtn(int viewId){
        getView(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }


//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置常见属性>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public LDialog setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    /**
     * Will set the image of an ImageView from a resource id.
     *
     * @param viewId     The view id.
     * @param imageResId The image resource id.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * Will set background color of a view.
     *
     * @param viewId The view id.
     * @param color  A color, not a resource id.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * Will set background of a view.
     *
     * @param viewId        The view id.
     * @param backgroundRes A resource to use as a background.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * Will set text color of a TextView.
     *
     * @param viewId    The view id.
     * @param textColor The text color (not a resource id).
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }


    /**
     * Will set the image of an ImageView from a drawable.
     *
     * @param viewId   The view id.
     * @param drawable The image drawable.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * Add an action to set the image of an image view. Can be called multiple times.
     */
    public LDialog setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Add an action to set the alpha of a view. Can be called multiple times.
     * Alpha between 0-1.
     */
    public LDialog setAlpha(@IdRes int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or GONE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for GONE.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setGone(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
     *
     * @param viewId  The view id.
     * @param visible True for VISIBLE, false for INVISIBLE.
     * @return The BaseViewHolder for chaining.
     */
    public LDialog setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public static GradientDrawable getRoundRectDrawable(int radius, int color){
        //左上、右上、右下、左下的圆角半径
        float[] radiuss = {radius, radius, radius, radius, radius, radius, radius, radius};
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(radiuss);
        drawable.setColor(color!=0 ? color : Color.TRANSPARENT);
        return drawable;
    }
//    public static GradientDrawable getRoundRectDrawable(int radius, int color, boolean isFill, int strokeWidth){
//        //左上、右上、右下、左下的圆角半径
//        float[] radiuss = {radius, radius, radius, radius, radius, radius, radius, radius};
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setCornerRadii(radiuss);
//        drawable.setColor(isFill ? color : Color.TRANSPARENT);
//        drawable.setStroke(isFill ? 0 : strokeWidth, color);
//        return drawable;
//    }

    public interface DialogOnClickListener{
        void onClick(View v, LDialog lDialog);
    }
}
