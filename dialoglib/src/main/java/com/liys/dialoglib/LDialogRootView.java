package com.liys.dialoglib;

import android.content.Context;
import android.util.AttributeSet;
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

    private int maxWidth;  //最大宽度
    private int maxHeight; //最大高度

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
}
