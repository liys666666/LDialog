
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
     implementation 'com.github.liys666666:LDialog:V1.0.3'  //添加
}
```

```
LDialog dialog = new LDialog(this, R.layout.dialog_confirm); //你的布局
        dialog.with()  //必须
                //1.设置宽
                .setWidth() //单位:dp
                .setWidthPX() //单位:px
                .setWidthRatio(0.8) //占屏幕宽比例

                //2.设置高
                .setHeight() //单位:dp
                .setHeightPX() //单位:px
                .setHeightRatio() //占屏幕高比例

                //3.设置背景
                .setBgColor(Color.WHITE) //背景颜色
                .setBgColorRes(R.color.white) //res资源
                .setBgRadius() //圆角, 单位：dp
                .setBgRadiusPX() //圆角, 单位：px

                //4.设置弹框位置 和 动画(显示和隐藏动画)
                .setGravity(Gravity.TOP | Gravity.BOTTOM) //设置弹框位置
                .setGravity(Gravity.LEFT, 0, 0) //设置弹框位置(偏移量)
                .setAnimationsStyle(R.style.dialog_translate) //设置动画

                //5.设置具体布局
                //5.1 常见系统View属性
                .setText(R.id.tv_title, "确定")
                .setTextColor()
                .setBackgroundColor()
                .setBackgroundRes()
                .setImageBitmap()
                .setVisible()
                .setGone()
                //5.2 其它属性
                .setCancelBtn(R.id.tv_cancel) //设置按钮，弹框消失的按钮
                .setOnClickListener(new LDialog.DialogOnClickListener() { //设置按钮监听
                    @Override
                    public void onClick(View v, LDialog customDialog) {
                        customDialog.dismiss();
                    }
                }, R.id.tv_confirm, R.id.tv_cancel)  //可以传多个
                .show(); //显示
```