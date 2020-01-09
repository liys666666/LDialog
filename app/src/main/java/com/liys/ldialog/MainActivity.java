package com.liys.ldialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.liys.dialoglib.LDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LDialog dialog = new LDialog(this, R.layout.dialog_confirm); //设置你的布局
        dialog.with()
                //设置布局控件的值
                .setText(R.id.tv_content, "确定要退出登录吗？")
                .setCancelBtn(R.id.tv_cancel)
                .setOnClickListener(new LDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(View v, LDialog lDialog) {
                        lDialog.dismiss();
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                }, R.id.tv_confirm)
                .show();
    }

//    private void test(){
//        //初始化布局
//        LDialog dialog = new LDialog(this, R.layout.dialog_confirm);
//        dialog.with()
//                //1.设置宽
//                .setWidth() //单位:dp
//                .setWidthPX() //单位:px
//                .setWidthRatio(0.8) //占屏幕宽比例
//
//                //2.设置高
//                .setHeight() //单位:dp
//                .setHeightPX() //单位:px
//                .setHeightRatio() //占屏幕高比例
//
//                //设置背景
//                .setBgColor(Color.WHITE) //背景颜色
//                .setBgColorRes(R.color.white) //res资源
//                .setBgRadius() //圆角, 单位：dp
//                .setBgRadiusPX() //圆角, 单位：px
//
//                //3.设置具体布局
//                //3.1 常见系统View属性
//                .setText(R.id.tv_title, "确定")
//                .setTextColor()
//                .setBackgroundColor()
//                .setBackgroundRes()
//                .setImageBitmap()
//                .setVisible()
//                .setGone()
//                //3.2 其它属性
//                .setCancelBtn(R.id.tv_cancel) //设置按钮，弹框消失的按钮
//                .setOnClickListener(new LDialog.DialogOnClickListener() { //设置按钮监听
//                    @Override
//                    public void onClick(View v, LDialog customDialog) {
//                        customDialog.dismiss();
//                    }
//                }, R.id.tv_confirm, R.id.tv_cancel)  //可以传多个
//                .show();
//    }
}
