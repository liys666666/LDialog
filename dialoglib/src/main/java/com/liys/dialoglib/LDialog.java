package com.liys.dialoglib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;

/**
 * @Description: 万能Dialog
 * @Author: liys
 * @CreateDate: 2020/1/8 15:07
 * @UpdateUser: liys
 * @UpdateDate: 2020/12/1 18:00
 * @UpdateRemark: 更新版本: V2.0.1  https://www.jianshu.com/p/4ab3462b3056
 * @Version: 1.0
 */
public class LDialog extends AppCompatDialog {

    protected Context context;
    protected SparseArray<View> views = new SparseArray<>();
    protected List<Integer> cancelIds = new ArrayList<>();
    protected LDialogRootView controlView;
    protected int layoutId = 0;
    protected int width = 0;
    protected int height = 0;

    protected BgBean bgBean = new BgBean(); //背景属性对象

    private LDialog(@NonNull Context context) {
        this(context, R.layout.dialog_confirm);
    }

    private LDialog(@NonNull Context context, int layoutId) {
        this(context, layoutId, R.style.LDialog);
    }

    private LDialog(@NonNull Context context, int layoutId, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.layoutId = layoutId;
    }

    /**
     * 获取对象
     * @param context
     * @return
     */
    public static LDialog newInstance(@NonNull Context context){
        return new LDialog(context).with();
    }
    public static LDialog newInstance(@NonNull Context context, int layoutId){
        return new LDialog(context, layoutId).with();
    }
    public static LDialog newInstance(@NonNull Context context, int layoutId, int themeResId){
        return new LDialog(context, layoutId, themeResId).with();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlView = new LDialogRootView(context);
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        controlView.addView(view);
        setContentView(controlView);
        init();
    }

    protected void init() {
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(getRoundRectDrawable(dp2px(context, 0), Color.TRANSPARENT));

        width = (int)(ScreenUtils.getWidthPixels(context)*0.8);
        height = WindowManager.LayoutParams.WRAP_CONTENT;
        setWidthHeight();
        getWindow().setWindowAnimations(R.style.li_dialog_default);
//        getWindow().setWindowAnimations(R.style.dialog_translate);
    }

    protected LDialog with(){
        show();
        dismiss();
        return this;
    }

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置动画>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 自定义动画
     * @param style
     * @return
     */
    public LDialog setAnimationsStyle(int style){
        getWindow().setWindowAnimations(style);
        return this;
    }

    /**
     * 内置动画
     * @param styleType 类型
     * @return
     */
    public LDialog setAnimations(@LAnimationsType.AAnimationsType String styleType){
        int style = -1;
        switch (styleType){
            case LAnimationsType.DEFAULT: //默认
                style = R.style.li_dialog_default;
                break;
            case LAnimationsType.SCALE:
                style = R.style.li_dialog_scale;
                break;
            case LAnimationsType.LEFT:
                style = R.style.li_dialog_translate_left;
                break;
            case LAnimationsType.TOP:
                style = R.style.li_dialog_translate_top;
                break;
            case LAnimationsType.RIGHT:
                style = R.style.li_dialog_translate_right;
                break;
            case LAnimationsType.BOTTOM:
                style = R.style.li_dialog_translate_bottom;
                break;
        }
        if(style!=-1){
            setAnimationsStyle(style);
        }
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


    /**
     * 遮罩透明度
     * @param value 0-1f
     * @return
     */
    public LDialog setMaskValue(float value){
        getWindow().setDimAmount(value);
        return this;
    }

//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置背景>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * 刷新背景
     * @return
     */
    protected LDialog refreshBg(){
//        getWindow().setBackgroundDrawable(getRoundRectDrawable(bgRadius, bgColor));
        controlView.setBackground(bgBean.getRoundRectDrawable());
        controlView.setBgRadius(
                bgBean.left_top_radius,
                bgBean.right_top_radius,
                bgBean.right_bottom_radius,
                bgBean.left_bottom_radius);
        return this;
    }

    /**
     * 设置背景颜色
     * @return
     */
    public LDialog setBgColor(@ColorInt int color){
        bgBean.color = color;
        return refreshBg();
    }
    public LDialog setBgColor(@Size(min=1) String colorString){
        bgBean.color = Color.parseColor(colorString);
        return refreshBg();
    }

    /**
     * 渐变背景
     * @param orientation
     * @param colors
     * @return
     */
    public LDialog setBgColor(GradientDrawable.Orientation orientation, @ColorInt int... colors){
        bgBean.gradientsOrientation = orientation;
        bgBean.gradientsColors = colors;
        return refreshBg();
    }
    public LDialog setBgColor(GradientDrawable.Orientation orientation, @Size(min=1) String... colorStrings){
        bgBean.gradientsOrientation = orientation;
        if(colorStrings==null){
            return this;
        }
        bgBean.gradientsColors = new int[colorStrings.length];
        for (int i = 0; i < bgBean.gradientsColors.length; i++) {
            bgBean.gradientsColors[i] = Color.parseColor(colorStrings[i]);
        }
        return refreshBg();
    }

    public LDialog setBgColorRes(@ColorRes int colorRes){
        bgBean.color = context.getResources().getColor(colorRes);
        return refreshBg();
    }
    public LDialog setBgColorRes(GradientDrawable.Orientation orientation, @Size(min=1) String... colorRes){
        bgBean.gradientsOrientation = orientation;
        bgBean.gradientsColors = new int[colorRes.length];
        for (int i = 0; i < colorRes.length; i++) {
            bgBean.gradientsColors[i] = Color.parseColor(colorRes[i]);
        }
        return refreshBg();
    }
    public LDialog setBgColorRes(GradientDrawable.Orientation orientation, @ColorRes int... colorRes){
        bgBean.gradientsOrientation = orientation;
        bgBean.gradientsColors = new int[colorRes.length];
        for (int i = 0; i < colorRes.length; i++) {
            bgBean.gradientsColors[i] = getColor(colorRes[i]);
        }
        return refreshBg();
    }

    /**
     *  设置背景圆角
     * @param bgRadius
     */
    public LDialog setBgRadius(float bgRadius){
        setBgRadius(bgRadius, bgRadius, bgRadius, bgRadius);
        return refreshBg();
    }
    public LDialog setBgRadius(float left_top_radius, float right_top_radius, float right_bottom_radius, float left_bottom_radius){
        bgBean.left_top_radius = dp2px(context, left_top_radius);
        bgBean.right_top_radius = dp2px(context, right_top_radius);
        bgBean.right_bottom_radius = dp2px(context, right_bottom_radius);
        bgBean.left_bottom_radius = dp2px(context, left_bottom_radius);
        return refreshBg();
    }

    /**
     *  设置背景圆角
     * @param bgRadius
     */
    public LDialog setBgRadiusPX(int bgRadius){
        setBgRadiusPX(bgRadius, bgRadius, bgRadius, bgRadius);
        return refreshBg();
    }
    public LDialog setBgRadiusPX(float left_top_radius, float right_top_radius, float right_bottom_radius, float left_bottom_radius){
        bgBean.left_top_radius = left_top_radius;
        bgBean.right_top_radius = right_top_radius;
        bgBean.right_bottom_radius = right_bottom_radius;
        bgBean.left_bottom_radius = left_bottom_radius;
        return refreshBg();
    }



//    >>>>>>>>>>>>>>>>>>>>>>>>>>>>设置宽高>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 设置宽高(精确)
     */
    private LDialog setWidthHeight(){
//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = width;
//        lp.height = height;
//        dialogWindow.setAttributes(lp);
        ViewGroup.LayoutParams layoutParams = controlView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        controlView.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 精确宽
     * @param width
     * @return
     */
    public LDialog setWidth(int width){
        this.width = dp2px(context, width);
        return setWidthHeight();
    }

    public LDialog setWidthPX(int width){
        this.width = width;
        return setWidthHeight();
    }

    /**
     * 最大宽
     * @param width
     * @return
     */
    public LDialog setMaxWidth(int width){
        setMaxWidthPX(dp2px(context, width));
        return this;
    }
    public LDialog setMaxWidthPX(int width){
        controlView.setMaxWidth(width);
        return this;
    }

    /**
     * 最小宽
     * @param width
     * @return
     */
    public LDialog setMinWidth(int width){
        setMinWidthPX(dp2px(context, width));
        return this;
    }
    public LDialog setMinWidthPX(int width){
        controlView.setMinimumWidth(width);
        return this;
    }



    /**
     * 精确高度
     * @param height
     * @return
     */
    public LDialog setHeight(int height){
        this.height = dp2px(context, height);
        return setWidthHeight();
    }
    public LDialog setHeightPX(int height){
        this.height = height;
        return setWidthHeight();
    }

    /**
     * 最大高度
     * @param height
     * @return
     */
    public LDialog setMaxHeight(int height){
        setMaxHeightPX(dp2px(context, height));
        return this;
    }
    public LDialog setMaxHeightPX(int height){
        controlView.setMaxHeight(height);
        return this;
    }

    /**
     * 最小高度
     * @param height
     * @return
     */
    public LDialog setMinHeight(int height){
        setMinHeightPX(dp2px(context, height));
        return this;
    }
    public LDialog setMinHeightPX(int height){
        controlView.setMinimumHeight(height);
        return this;
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
    public LDialog setMaxWidthRatio(double widthRatio){
        return setMaxWidthPX((int)(ScreenUtils.getWidthPixels(context)*widthRatio));
    }
    public LDialog setMinWidthRatio(double widthRatio){
        return setMinWidthPX((int)(ScreenUtils.getWidthPixels(context)*widthRatio));
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
    public LDialog setMaxHeightRatio(double heightRatio){
        return setMaxHeightPX((int)(ScreenUtils.getWidthPixels(context)*heightRatio));
    }
    public LDialog setMinHeightRatio(double heightRatio){
        return setMinHeightPX((int)(ScreenUtils.getWidthPixels(context)*heightRatio));
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
            if(cancelIds.contains(viewIds[i])){
                getView(viewIds[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(v, lDialog);
                        lDialog.dismiss();
                    }
                });
            }else{
                getView(viewIds[i]).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(v, lDialog);
                    }
                });
            }

        }
        return this;
    }

    /**
     * 设置 关闭dialog的按钮
     * @param viewIds
     * @return
     */
    public LDialog setCancelBtn(int... viewIds){
        for (int i = 0; i < viewIds.length; i++) {
            if(cancelIds.contains(viewIds[i])){
               continue;
            }
            cancelIds.add(viewIds[i]);
            getView(viewIds[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
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
     * 设置文字大小
     * @param viewId
     * @param size (单位：SP)
     * @return
     */
    public LDialog setTextSize(@IdRes int viewId, float size) {
        setTextSizePX(viewId, sp2px(context, size));
        return this;
    }
    public LDialog setTextSizePX(@IdRes int viewId, float size) {
        TextView view = getView(viewId);
        view.setTextSize(sp2px(context, size));
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
//    public static GradientDrawable getRoundRectDrawable(int radius, int color){
//        //左上、右上、右下、左下的圆角半径
//        float[] radiuss = {radius, radius, radius, radius, radius, radius, radius, radius};
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setCornerRadii(radiuss);
//        drawable.setColor(color!=0 ? color : Color.TRANSPARENT);
//        return drawable;
//    }
//    public static GradientDrawable getRoundRectDrawable(int radius, int color, boolean isFill, int strokeWidth){
//        //左上、右上、右下、左下的圆角半径
//        float[] radiuss = {radius, radius, radius, radius, radius, radius, radius, radius};
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setCornerRadii(radiuss);
//        drawable.setColor(isFill ? color : Color.TRANSPARENT);
//        drawable.setStroke(isFill ? 0 : strokeWidth, color);
//        return drawable;
//    }

    public int getColor(int colorResId){
        return context.getResources().getColor(colorResId);
    }

    /** dp转px */
    public static int dp2px(Context context, float dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal, context.getResources().getDisplayMetrics());
    }
    /** sp转px */
    public static int sp2px(Context context, float spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal, context.getResources().getDisplayMetrics());
    }

    public interface DialogOnClickListener{
        void onClick(View v, LDialog lDialog);
    }
}
