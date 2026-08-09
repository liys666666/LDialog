# LDialog

轻量、可扩展的 Android Dialog 库。V3 以 `LDialog2` 和 Builder 模式作为主要 API，支持 ViewBinding、尺寸约束、背景与圆角、动画、位置、控件属性和点击事件的链式配置。

- 主仓库：[Gitee](https://gitee.com/liys666666/LDialog)
- GitHub 镜像：[GitHub](https://github.com/liys666666/LDialog)
- 使用说明：[简书](https://www.jianshu.com/p/4ab3462b3056)

## 版本要求

| 项目 | V3 要求 |
| --- | --- |
| 依赖版本 | `V3.0.0` |
| minSdk | 21 |
| 推荐 compileSdk | 35 或更高 |
| Java 字节码 | Java 8 |
| 构建工具 | 库使用 AGP 8.10.1、Gradle 8.11.1、JDK 17 构建 |

使用方不需要与库采用完全相同的 Gradle 或 AGP 版本，但需要能够解析 Kotlin 2.1.20 和 AndroidX AppCompat 1.7.1。

仍需支持 API 19 的旧项目请继续使用 V2：

```groovy
implementation 'com.github.liys666666:LDialog:V2.1.2'
```

## 引入 V3

在项目的 `settings.gradle` 中添加 JitPack：

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri('https://jitpack.io') }
    }
}
```

添加依赖：

```groovy
dependencies {
    implementation 'com.gitee.liys666666:LDialog:V3.0.0'
}
```

## Kotlin 使用

使用布局资源创建：

```kotlin
val dialog = LDialog2.Builder(this)
    .setLayout(R.layout.dialog_confirm)
    .setWidthRatio(0.88)
    .setGravity(Gravity.CENTER)
    .setMaskValue(0.5f)
    .setBgColor(Color.WHITE)
    .setBgRadius(8f)
    .setAnimations(LAnimationsType.SCALE)
    .setText(R.id.tv_title, "提示")
    .setCancelBtn(R.id.tv_cancel)
    .setOnClickListener({ _, currentDialog ->
        currentDialog.dismiss()
    }, R.id.tv_confirm)
    .build()

dialog.show()
```

## ViewBinding 使用

```kotlin
val dialog = LDialog2.Builder(this)
    .setBinding(DialogConfirm2Binding::class.java)
    .setGravity(Gravity.BOTTOM)
    .setWidthRatio(1.0)
    .setBgRadius(12f, 12f, 0f, 0f)
    .setAnimations(LAnimationsType.BOTTOM)
    .build()

val binding = dialog.binding as DialogConfirm2Binding
binding.tvTitle.text = "确认操作"
binding.tvConfirm.setOnClickListener {
    dialog.dismiss()
}
dialog.show()
```

`build()` 前必须调用 `setView()`、`setLayout()` 或 `setBinding()` 中的一个方法设置内容视图。

## Java 使用

```java
LDialog2 dialog = new LDialog2.Builder(this)
        .setLayout(R.layout.dialog_confirm)
        .setWidthRatio(0.88)
        .setGravity(Gravity.CENTER)
        .setBgColor(Color.WHITE)
        .setBgRadius(8f)
        .setAnimations(LAnimationsType.DEFAULT)
        .setText(R.id.tv_title, "提示")
        .setCancelBtn(R.id.tv_cancel)
        .setOnClickListener((view, currentDialog) -> {
            currentDialog.dismiss();
        }, R.id.tv_confirm)
        .build();

dialog.show();
```

## 布局

```kotlin
builder.setView(customView)
builder.setLayout(R.layout.dialog_confirm)
builder.setBinding(DialogConfirm2Binding::class.java)
```

## 宽高

```kotlin
builder
    // 精确尺寸，默认单位为 dp
    .setWidth(280)
    .setHeight(160)
    // px
    .setWidthPX(720)
    .setHeightPX(480)
    // 屏幕比例
    .setWidthRatio(0.9)
    .setHeightRatio(0.5)
    // 最小/最大尺寸
    .setMinWidth(240)
    .setMaxWidthRatio(0.95)
    .setMinHeight(100)
    .setMaxHeightRatio(0.8)
    // 通用尺寸模式：PX、DP、RATIO、WRAP
    .setWidth(0f, ISizeModel.WRAP)
    .setHeight(0f, ISizeModel.WRAP)
```

## 背景与圆角

```kotlin
builder
    .setBgColor(Color.WHITE)
    .setBgColor("#FFFFFF")
    .setBgColorRes(R.color.white)
    .setBgColor(
        GradientDrawable.Orientation.LEFT_RIGHT,
        Color.BLUE,
        Color.CYAN
    )
    // dp
    .setBgRadius(10f)
    .setBgRadius(10f, 10f, 0f, 0f)
    // px
    .setBgRadiusPX(20)
    .setBgRadiusPX(20f, 20f, 0f, 0f)
```

## 位置、动画和遮罩

```kotlin
builder
    .setGravity(Gravity.BOTTOM, 0, 24)
    .setMaskValue(0.45f)
    .setOnTouchOutside(true)
    .setAnimations(LAnimationsType.BOTTOM)
    .setAnimationsStyle(R.style.li_dialog_default)
```

内置动画类型：

```text
DEFAULT、SCALE、LEFT、TOP、RIGHT、BOTTOM
```

## 控件属性

控件操作会在 Dialog 内容视图创建后统一执行：

```kotlin
builder
    .setText(R.id.tv_title, "提示")
    .setText(R.id.tv_confirm, R.string.confirm)
    .setTextSize(R.id.tv_title, 16f)
    .setTextSizePX(R.id.tv_title, 32f)
    .setTextColor(R.id.tv_title, Color.BLACK)
    .setImageResource(R.id.iv_icon, R.drawable.ic_launcher_foreground)
    .setImageDrawable(R.id.iv_icon, drawable)
    .setImageBitmap(R.id.iv_icon, bitmap)
    .setBackgroundColor(R.id.tv_confirm, Color.BLUE)
    .setBackgroundRes(R.id.tv_confirm, R.drawable.button_background)
    .setAlpha(R.id.iv_icon, 0.8f)
    .setGone(R.id.tv_message, false)
    .setVisible(R.id.loading, false)
```

也可以在设置布局后直接获取控件：

```kotlin
val title = builder.getView<TextView>(R.id.tv_title)
```

## 点击与关闭

```kotlin
builder
    // 点击这些控件时自动关闭 Dialog
    .setCancelBtn(R.id.tv_cancel)
    // 回调中可以获得当前 LDialog2
    .setOnClickListener({ view, dialog ->
        when (view.id) {
            R.id.tv_confirm -> dialog.dismiss()
        }
    }, R.id.tv_confirm, R.id.tv_cancel)
```

同一个控件同时通过 `setCancelBtn()` 和 `setOnClickListener()` 配置时，会先执行点击回调，再关闭 Dialog。

## 扩展 Builder

`LDialog2.Builder` 及其方法均允许继承和重写：

```kotlin
class AppDialogBuilder(context: Context) : LDialog2.Builder(context) {

    override fun setMaskValue(value: Float): AppDialogBuilder {
        super.setMaskValue(value.coerceIn(0f, 1f))
        return this
    }

    override fun build(): LDialog2 {
        if (userView == null) {
            setLayout(R.layout.dialog_confirm)
        }
        return super.build()
    }
}
```

## 从 LDialog 迁移

V2：

```java
LDialog dialog = LDialog.newInstance(context, R.layout.dialog_confirm)
        .setWidthRatio(0.9)
        .setBgRadius(8);
dialog.show();
```

V3：

```java
LDialog2 dialog = new LDialog2.Builder(context)
        .setLayout(R.layout.dialog_confirm)
        .setWidthRatio(0.9)
        .setBgRadius(8f)
        .build();
dialog.show();
```

主要变化：

- 使用 `LDialog2.Builder` 收集配置。
- 使用 `build()` 创建 Dialog，最后调用 `show()`。
- 点击监听类型改为 `LDialog2.DialogOnClickListener`。
- V3 最低支持 API 21；API 19 项目继续使用 V2。

## 发布源

V3 以 Gitee 为 JitPack 构建源，依赖坐标固定使用：

```text
com.gitee.liys666666:LDialog:V3.0.0
```

GitHub 仓库作为相同提交和 Tag 的镜像，不作为 V3 的推荐依赖坐标。
