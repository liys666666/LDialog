package com.liys.ldialog;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.liys.dialoglib.LAnimationsType;
import com.liys.dialoglib.LDialog;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LDialog dialog = LDialog.newInstance(this, R.layout.dialog_confirm2) //设置你的布局
                .setGravity(Gravity.BOTTOM)
                .setAnimations(LAnimationsType.BOTTOM)
                .setWidthRatio(1)
                .setBgColor(Color.WHITE)
                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, "#00FEE9", "#008EB4")
                .setBgRadius(10, 10, 0, 0)
                .setWidth(200)
//                .setMaxHeight(400)
                //.setAnimationsStyle(R.style.dialog_translate)
                .setMaskValue(0.5f)
                //设置布局控件的值
                .setText(R.id.tv_content, "确定要退出登录吗？>>>>>>>>>>>")
                .setCancelBtn(R.id.tv_cancel, R.id.tv_confirm)
                .setOnClickListener(new LDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(View v, LDialog lDialog) { //可以根据viewId判断
                        Log.d("66", "点击");
                    }
                }, R.id.tv_confirm, R.id.tv_content); //可以设多控件
        switch (v.getId()){
            case R.id.btn1:
                dialog.setGravity(Gravity.BOTTOM)
                        .setAnimations(LAnimationsType.BOTTOM);
                break;
            case R.id.btn2:
                dialog.setGravity(Gravity.CENTER)
                        .setAnimations(LAnimationsType.LEFT);
                break;
            case R.id.btn3:
                dialog.setGravity(Gravity.CENTER)
                        .setAnimations(LAnimationsType.SCALE);
                break;
            case R.id.btn4:
                dialog.setGravity(Gravity.CENTER)
                        .setAnimations(LAnimationsType.BOTTOM);
                break;
        }

        dialog.show();
//        test();
    }

//    private void test(){
//        //初始化布局
//        LDialog dialog = LDialog.newInstance(this, R.layout.dialog_confirm);
//        dialog
//                .setMaskValue(0.5f) //遮罩--透明度(0-1)
//                //1.设置宽
//                //精确宽度
//                .setWidth(100) //单位:dp
//                .setWidthPX(100) //单位:px
//                .setWidthRatio(0.8) //占屏幕宽比例
//                //最小宽度
//                .setMinWidth(100) //单位:dp
//                .setMinWidth(100) //单位:px
//                .setMinWidthRatio(0.5) //占屏幕宽比例
//                //最大宽度
//                .setMaxWidth(100) //单位:dp
//                .setMaxWidthPX(100) //单位:px
//                .setMaxWidthRatio(0.8) //占屏幕宽比例
//
//                //2.设置高
//                //精确高度
//                .setHeight(100) //单位:dp
//                .setHeightPX(100) //单位:px
//                .setHeightRatio(0.3) //占屏幕高比例
//                //最小高度
//                .setMinHeight(100) //单位:dp
//                .setMinHeightPX(100) //单位:px
//                .setMinHeightRatio(0.3) //占屏幕高比例
//                //最大高度
//                .setMaxHeight(100) //单位:dp
//                .setMaxHeightPX(100) //单位:px
//                .setMaxHeightRatio(0.3) //占屏幕高比例
//
//                //3.设置背景
//                //颜色
//                .setBgColor(Color.WHITE) //一种颜色
//                .setBgColor("#FFFFFF") //一种颜色
//                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, Color.BLUE, Color.YELLOW) //颜色渐变(可传多个) 参数1：渐变的方向
//                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, "#00FEE9", "#008EB4") //颜色渐变(可传多个)
//                .setBgColorRes(R.color.white) //一种颜色(res资源)
//                .setBgColorRes(GradientDrawable.Orientation.BOTTOM_TOP, R.color.colorAccent, R.color.colorPrimary) //颜色渐变(可传多个)
//                //圆角
//                .setBgRadius(5) //圆角, 单位：dp
//                .setBgRadius(5, 5, 0, 0) //圆角, 单位：dp
//                .setBgRadiusPX(10) //圆角, 单位：px
//                .setBgRadiusPX(10, 10, 10, 10) //圆角, 单位：px
//
//                //4.设置弹框位置
//                .setGravity(Gravity.LEFT | Gravity.BOTTOM) //弹框位置
//                .setGravity(Gravity.LEFT, 0, 0) //弹框位置(偏移量)
//
//                //5.设置动画
//                //5.1 内置动画(平移，从各个方向弹出)
//                // 对应的值：DEFAULT(渐变) (LEFT TOP RIGHT BOTTOM 平移)  SCALE(缩放)
//                .setAnimations(LAnimationsType.LEFT)
//                //5.2 自定义动画
//                .setAnimationsStyle(R.style.li_dialog_default) //设置动画
//
//                //6.设置具体布局
//                //6.1 常见系统View属性
//                .setText(R.id.tv_title, "确定")
//                .setTextColor()
//                .setTextSize()
//                .setTextSizePX()
//                .setBackgroundColor()
//                .setBackgroundRes()
//                .setImageBitmap()
//                .setVisible()
//                .setGone()
//                //6.2 其它属性
//                .setCancelBtn(R.id.tv_cancel, R.id.tv_confirm) //设置按钮，点击弹框消失(可以传多个)
//                .setOnClickListener(new LDialog.DialogOnClickListener() { //设置按钮监听
//                    @Override
//                    public void onClick(View v, LDialog customDialog) {
//                        customDialog.dismiss();
//                    }
//                }, R.id.tv_confirm, R.id.tv_cancel)  //可以传多个
//                .show();
//    }
}
