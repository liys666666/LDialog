
#使用详情:[https://www.jianshu.com/p/4ab3462b3056](https://www.jianshu.com/p/4ab3462b3056)
```aidl
//项目根目录下 build.gradle
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' } //添加
        }
    }
```
```aidl
dependencies {
    //如果项目是support包，可以使用V2.0.1版本
     implementation 'com.github.liys666666:LDialog:V2.1.1'  //添加
}
```

```
LDialog dialog = LDialog.newInstance(this, R.layout.dialog_confirm);
        dialog
                .setMaskValue(0.5f) //遮罩--透明度(0-1)
                //1.设置宽
                //精确宽度
                .setWidth(100) //单位:dp
                .setWidthPX(100) //单位:px
                .setWidthRatio(0.8) //占屏幕宽比例
                //最小宽度
                .setMinWidth(100) //单位:dp
                .setMinWidth(100) //单位:px
                .setMinWidthRatio(0.5) //占屏幕宽比例
                //最大宽度
                .setMaxWidth(100) //单位:dp
                .setMaxWidthPX(100) //单位:px
                .setMaxWidthRatio(0.8) //占屏幕宽比例

                //2.设置高
                //精确高度
                .setHeight(100) //单位:dp
                .setHeightPX(100) //单位:px
                .setHeightRatio(0.3) //占屏幕高比例
                //最小高度
                .setMinHeight(100) //单位:dp
                .setMinHeightPX(100) //单位:px
                .setMinHeightRatio(0.3) //占屏幕高比例
                //最大高度
                .setMaxHeight(100) //单位:dp
                .setMaxHeightPX(100) //单位:px
                .setMaxHeightRatio(0.3) //占屏幕高比例

                //3.设置背景
                //颜色
                .setBgColor(Color.WHITE) //一种颜色
                .setBgColor("#FFFFFF") //一种颜色
                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, Color.BLUE, Color.YELLOW) //颜色渐变(可传多个) 参数1：渐变的方向
                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, "#00FEE9", "#008EB4") //颜色渐变(可传多个)
                .setBgColorRes(R.color.white) //一种颜色(res资源)
                .setBgColorRes(GradientDrawable.Orientation.BOTTOM_TOP, R.color.colorAccent, R.color.colorPrimary) //颜色渐变(可传多个)
                //圆角
                .setBgRadius(5) //圆角, 单位：dp
                .setBgRadius(5, 5, 0, 0) //圆角, 单位：dp
                .setBgRadiusPX(10) //圆角, 单位：px
                .setBgRadiusPX(10, 10, 10, 10) //圆角, 单位：px

                //4.设置弹框位置
                .setGravity(Gravity.LEFT | Gravity.BOTTOM) //弹框位置
                .setGravity(Gravity.LEFT, 0, 0) //弹框位置(偏移量)

                //5.设置动画
                //5.1 内置动画(平移，从各个方向弹出)
                // 对应的值：DEFAULT(渐变) (LEFT TOP RIGHT BOTTOM 平移)  SCALE(缩放)
                .setAnimations(LAnimationsType.LEFT)
                //5.2 自定义动画
                .setAnimationsStyle(R.style.li_dialog_default) //设置动画

                //6.设置具体布局
                //6.1 常见系统View属性
                .setText(R.id.tv_title, "确定")
                .setTextColor()
                .setTextSize()
                .setTextSizePX()
                .setBackgroundColor()
                .setBackgroundRes()
                .setImageBitmap()
                .setVisible()
                .setGone()
                //6.2 其它属性
                .setCancelBtn(R.id.tv_cancel, R.id.tv_confirm) //设置按钮，点击弹框消失(可以传多个)
                .setOnClickListener(new LDialog.DialogOnClickListener() { //设置按钮监听
                    @Override
                    public void onClick(View v, LDialog customDialog) {
                        customDialog.dismiss();
                    }
                }, R.id.tv_confirm, R.id.tv_cancel)  //可以传多个
                .show();
```