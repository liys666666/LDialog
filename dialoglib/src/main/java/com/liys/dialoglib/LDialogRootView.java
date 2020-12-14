package com.liys.dialoglib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/11/30 16:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/30 16:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LDialogRootView extends FrameLayout {

    private int width;  //
    private int height; //
    private int maxWidth;  //最大宽度
    private int maxHeight; //最大高度

    private float left_top_radius; //
    private float right_top_radius; //
    private float right_bottom_radius; //
    private float left_bottom_radius; //

    private Path path = new Path(); //

    public LDialogRootView(@NonNull Context context) {
        this(context, null);
    }

    public LDialogRootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LDialogRootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(width==w && height==h){
            return;
        }
        width = w;
        height = h;
        setBgRadius();
    }

    /**
     *  设置背景圆角
     * @param bgRadius
     */
    public LDialogRootView setBgRadius(float bgRadius){
        return setBgRadius(bgRadius, bgRadius, bgRadius, bgRadius);
    }
    public LDialogRootView setBgRadius(float left_top_radius, float right_top_radius, float right_bottom_radius, float left_bottom_radius){
        this.left_top_radius = left_top_radius;
        this.right_top_radius = right_top_radius;
        this.right_bottom_radius = right_bottom_radius;
        this.left_bottom_radius = left_bottom_radius;
        setBgRadius();
        return this;
    }

    private LDialogRootView setBgRadius() {
        path.reset();
        path.addRoundRect(new RectF(0, 0, width, height), new float[]{
                        left_top_radius, left_top_radius,
                        right_top_radius, right_top_radius,
                        right_bottom_radius, right_bottom_radius,
                        left_bottom_radius, left_bottom_radius},
                Path.Direction.CW);
        invalidate();
        return this;
    }


    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        invalidate();
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(getWidthMeasureSpec(widthMeasureSpec), getHeightMeasureSpec(heightMeasureSpec));
    }

    //最大宽高处理
    public int getWidthMeasureSpec(int widthMeasureSpec) {
        if (maxWidth > 0) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.AT_MOST);
        }
        return widthMeasureSpec;
    }

    public int getHeightMeasureSpec(int heightMeasureSpec) {
        if (maxHeight > 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST);
        }
        return heightMeasureSpec;
    }

//    // 绘制圆角
//    @Override
//    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        canvas.clipPath(path);
//        return super.drawChild(canvas, child, drawingTime);
//    }

    @Override
    public void draw(Canvas canvas) {
        canvas.clipPath(path);
        super.draw(canvas);
    }


    /** dp转px */
    private int dp2px(float dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal, getResources().getDisplayMetrics());
    }
}
