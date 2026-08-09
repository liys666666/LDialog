package com.liys.dialoglib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.SparseArray
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.Size
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import com.liys.dialoglib.LAnimationsType.AAnimationsType

open class LDialog2(var builder: Builder) : AppCompatDialog(builder.context, builder.themeResId) {

    var binding: Any? = builder.binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userView = requireNotNull(builder.userView) {
            "请使用以下方法设置您的布局 setView() setLayout() setBinding()"
        }
        builder.controlView.addView(userView)
        setContentView(builder.controlView)

        val xmlLp = userView.layoutParams
            ?: ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        if (builder.width != 0) {
            xmlLp.width = builder.width
        }
        if (builder.height != 0) {
            xmlLp.height = builder.height
        }
        userView.layoutParams = xmlLp

        if (builder.minWidth > 0) {
            builder.controlView.minimumWidth = builder.minWidth
        }
        if (builder.maxWidth > 0) {
            builder.controlView.setMaxWidth(builder.maxWidth)
        }
        if (builder.minHeight > 0) {
            builder.controlView.minimumHeight = builder.minHeight
        }
        if (builder.maxHeight > 0) {
            builder.controlView.setMaxHeight(builder.maxHeight)
        }

        val dialogWindow = requireNotNull(window)
        val windowLP = dialogWindow.attributes
        windowLP.width = xmlLp.width
        windowLP.height = xmlLp.height
        windowLP.x = builder.offX
        windowLP.y = builder.offY
        dialogWindow.attributes = windowLP

        val controlLP = builder.controlView.layoutParams
        controlLP.width = xmlLp.width
        controlLP.height = xmlLp.height
        builder.controlView.layoutParams = controlLP

        dialogWindow.setGravity(builder.gravity)
        dialogWindow.setWindowAnimations(builder.animationsStyle)
        dialogWindow.setDimAmount(builder.maskValue)
        setCancelable(builder.cancelable)
        setCanceledOnTouchOutside(builder.cancelable)

        val transparentBackground = GradientDrawable()
        transparentBackground.setColor(Color.TRANSPARENT)
        dialogWindow.setBackgroundDrawable(transparentBackground)
        builder.refreshBg()

        builder.viewActions.forEach { (viewId, action) ->
            action(requireView(viewId))
        }

        (builder.clickListeners.keys + builder.cancelIds).forEach { viewId ->
            requireView<View>(viewId).setOnClickListener { view ->
                builder.clickListeners[viewId]?.onClick(view, this)
                if (viewId in builder.cancelIds) {
                    dismiss()
                }
            }
        }
    }

    private fun <T : View> requireView(@IdRes viewId: Int): T {
        return requireNotNull(findViewById(viewId)) { "找不到View，viewId=$viewId" }
    }

    fun interface DialogOnClickListener {
        fun onClick(view: View, dialog: LDialog2)
    }

    open class Builder(var context: Context) {

        var themeResId = R.style.LDialog
        var controlView: LDialogRootView = LDialogRootView(context)
        var userView: View? = null
        var binding: Any? = null

        @JvmField
        var width = 0

        @JvmField
        var height = 0

        @JvmField
        var minWidth = 0

        @JvmField
        var maxWidth = 0

        @JvmField
        var minHeight = 0

        @JvmField
        var maxHeight = 0

        @JvmField
        var gravity = Gravity.CENTER

        var offX = 0
        var offY = 0

        @JvmField
        var maskValue = 0.5f

        @JvmField
        var animationsStyle = R.style.li_dialog_default

        var cancelable = true
        var bgBean = BgBean()

        internal val viewActions = mutableListOf<Pair<Int, (View) -> Unit>>()
        internal val clickListeners = linkedMapOf<Int, DialogOnClickListener>()
        internal val cancelIds = linkedSetOf<Int>()
        private val views = SparseArray<View>()

        open fun setView(view: View): Builder {
            userView = view
            binding = null
            views.clear()
            return this
        }

        open fun setLayout(@LayoutRes layoutId: Int): Builder {
            userView = LayoutInflater.from(context).inflate(layoutId, controlView, false)
            binding = null
            views.clear()
            return this
        }

        open fun setBinding(bindingClazz: Class<*>): Builder {
            val inflate = bindingClazz.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            binding = inflate.invoke(null, LayoutInflater.from(context), controlView, false)
            userView = binding?.javaClass?.getMethod("getRoot")?.invoke(binding) as View
            views.clear()
            return this
        }

        open fun setOnTouchOutside(cancel: Boolean): Builder {
            cancelable = cancel
            return this
        }

        open fun setAnimationsStyle(style: Int): Builder {
            animationsStyle = style
            return this
        }

        open fun setAnimations(@AAnimationsType styleType: String?): Builder {
            when (styleType) {
                LAnimationsType.DEFAULT -> animationsStyle = R.style.li_dialog_default
                LAnimationsType.SCALE -> animationsStyle = R.style.li_dialog_scale
                LAnimationsType.LEFT -> animationsStyle = R.style.li_dialog_translate_left
                LAnimationsType.TOP -> animationsStyle = R.style.li_dialog_translate_top
                LAnimationsType.RIGHT -> animationsStyle = R.style.li_dialog_translate_right
                LAnimationsType.BOTTOM -> animationsStyle = R.style.li_dialog_translate_bottom
            }
            return this
        }

        open fun setGravity(gravity: Int): Builder {
            return setGravity(gravity, 0, 0)
        }

        open fun setGravity(gravity: Int, offX: Int, offY: Int): Builder {
            this.gravity = gravity
            this.offX = offX
            this.offY = offY
            return this
        }

        open fun setMaskValue(value: Float): Builder {
            maskValue = value
            return this
        }

        open fun refreshBg(): Builder {
            controlView.background = bgBean.getRoundRectDrawable()
            controlView.setBgRadius(
                bgBean.left_top_radius,
                bgBean.right_top_radius,
                bgBean.right_bottom_radius,
                bgBean.left_bottom_radius
            )
            return this
        }

        open fun setBgColor(@ColorInt color: Int): Builder {
            bgBean.color = color
            bgBean.gradientsColors = null
            return refreshBg()
        }

        open fun setBgColor(@Size(min = 1) colorString: String): Builder {
            return setBgColor(Color.parseColor(colorString))
        }

        open fun setBgColor(
            orientation: GradientDrawable.Orientation,
            @ColorInt vararg colors: Int
        ): Builder {
            bgBean.gradientsOrientation = orientation
            bgBean.gradientsColors = colors
            return refreshBg()
        }

        open fun setBgColor(
            orientation: GradientDrawable.Orientation,
            @Size(min = 1) vararg colorStrings: String
        ): Builder {
            return setBgColor(
                orientation,
                *colorStrings.map(Color::parseColor).toIntArray()
            )
        }

        open fun setBgColorRes(@ColorRes colorRes: Int): Builder {
            return setBgColor(getColor(colorRes))
        }

        open fun setBgColorRes(
            orientation: GradientDrawable.Orientation,
            @Size(min = 1) vararg colorStrings: String
        ): Builder {
            return setBgColor(orientation, *colorStrings)
        }

        open fun setBgColorRes(
            orientation: GradientDrawable.Orientation,
            @ColorRes vararg colorRes: Int
        ): Builder {
            return setBgColor(
                orientation,
                *colorRes.map(::getColor).toIntArray()
            )
        }

        open fun setBgRadius(bgRadius: Float): Builder {
            return setBgRadius(bgRadius, bgRadius, bgRadius, bgRadius)
        }

        open fun setBgRadius(
            leftTopRadius: Float,
            rightTopRadius: Float,
            rightBottomRadius: Float,
            leftBottomRadius: Float
        ): Builder {
            bgBean.left_top_radius = dp2px(context, leftTopRadius).toFloat()
            bgBean.right_top_radius = dp2px(context, rightTopRadius).toFloat()
            bgBean.right_bottom_radius = dp2px(context, rightBottomRadius).toFloat()
            bgBean.left_bottom_radius = dp2px(context, leftBottomRadius).toFloat()
            return refreshBg()
        }

        open fun setBgRadiusPX(bgRadius: Int): Builder {
            return setBgRadiusPX(
                bgRadius.toFloat(),
                bgRadius.toFloat(),
                bgRadius.toFloat(),
                bgRadius.toFloat()
            )
        }

        open fun setBgRadiusPX(
            leftTopRadius: Float,
            rightTopRadius: Float,
            rightBottomRadius: Float,
            leftBottomRadius: Float
        ): Builder {
            bgBean.left_top_radius = leftTopRadius
            bgBean.right_top_radius = rightTopRadius
            bgBean.right_bottom_radius = rightBottomRadius
            bgBean.left_bottom_radius = leftBottomRadius
            return refreshBg()
        }

        open fun setWidth(
            value: Float,
            @ISizeModel.ASizeModel model: Int = ISizeModel.DP
        ): Builder {
            width = resolveSize(value, model, getWidthPixels(context), true)
            return this
        }

        open fun setWidth(width: Int): Builder {
            return setWidth(width.toFloat(), ISizeModel.DP)
        }

        open fun setWidthPX(width: Int): Builder {
            return setWidth(width.toFloat(), ISizeModel.PX)
        }

        open fun setWidthRatio(widthRatio: Double): Builder {
            return setWidth(widthRatio.toFloat(), ISizeModel.RATIO)
        }

        open fun setHeight(
            value: Float,
            @ISizeModel.ASizeModel model: Int = ISizeModel.DP
        ): Builder {
            height = resolveSize(value, model, getHeightPixels(context), true)
            return this
        }

        open fun setHeight(height: Int): Builder {
            return setHeight(height.toFloat(), ISizeModel.DP)
        }

        open fun setHeightPX(height: Int): Builder {
            return setHeight(height.toFloat(), ISizeModel.PX)
        }

        open fun setHeightRatio(heightRatio: Double): Builder {
            return setHeight(heightRatio.toFloat(), ISizeModel.RATIO)
        }

        open fun setMinWidth(
            value: Float,
            @ISizeModel.ASizeModel model: Int = ISizeModel.DP
        ): Builder {
            minWidth = resolveSize(value, model, getWidthPixels(context), false)
            return this
        }

        open fun setMinWidth(width: Int): Builder {
            return setMinWidth(width.toFloat(), ISizeModel.DP)
        }

        open fun setMinWidthPX(width: Int): Builder {
            return setMinWidth(width.toFloat(), ISizeModel.PX)
        }

        open fun setMinWidthRatio(widthRatio: Double): Builder {
            return setMinWidth(widthRatio.toFloat(), ISizeModel.RATIO)
        }

        open fun setMaxWidth(
            value: Float,
            @ISizeModel.ASizeModel model: Int = ISizeModel.DP
        ): Builder {
            maxWidth = resolveSize(value, model, getWidthPixels(context), false)
            return this
        }

        open fun setMaxWidth(width: Int): Builder {
            return setMaxWidth(width.toFloat(), ISizeModel.DP)
        }

        open fun setMaxWidthPX(width: Int): Builder {
            return setMaxWidth(width.toFloat(), ISizeModel.PX)
        }

        open fun setMaxWidthRatio(widthRatio: Double): Builder {
            return setMaxWidth(widthRatio.toFloat(), ISizeModel.RATIO)
        }

        open fun setMinHeight(
            value: Float,
            @ISizeModel.ASizeModel model: Int = ISizeModel.DP
        ): Builder {
            minHeight = resolveSize(value, model, getHeightPixels(context), false)
            return this
        }

        open fun setMinHeight(height: Int): Builder {
            return setMinHeight(height.toFloat(), ISizeModel.DP)
        }

        open fun setMinHeightPX(height: Int): Builder {
            return setMinHeight(height.toFloat(), ISizeModel.PX)
        }

        open fun setMinHeightRatio(heightRatio: Double): Builder {
            return setMinHeight(heightRatio.toFloat(), ISizeModel.RATIO)
        }

        open fun setMaxHeight(
            value: Float,
            @ISizeModel.ASizeModel model: Int = ISizeModel.DP
        ): Builder {
            maxHeight = resolveSize(value, model, getHeightPixels(context), false)
            return this
        }

        open fun setMaxHeight(height: Int): Builder {
            return setMaxHeight(height.toFloat(), ISizeModel.DP)
        }

        open fun setMaxHeightPX(height: Int): Builder {
            return setMaxHeight(height.toFloat(), ISizeModel.PX)
        }

        open fun setMaxHeightRatio(heightRatio: Double): Builder {
            return setMaxHeight(heightRatio.toFloat(), ISizeModel.RATIO)
        }

        protected open fun resolveSize(
            value: Float,
            @ISizeModel.ASizeModel model: Int,
            totalPixels: Int,
            allowWrapContent: Boolean
        ): Int {
            return when (model) {
                ISizeModel.PX -> value.toInt()
                ISizeModel.DP -> dp2px(context, value)
                ISizeModel.RATIO -> (totalPixels * value).toInt()
                ISizeModel.WRAP -> if (allowWrapContent) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    0
                }
                else -> 0
            }
        }

        open fun setOnClickListener(
            onClickListener: DialogOnClickListener,
            @IdRes vararg viewIds: Int
        ): Builder {
            viewIds.forEach { clickListeners[it] = onClickListener }
            return this
        }

        open fun setCancelBtn(@IdRes vararg viewIds: Int): Builder {
            cancelIds.addAll(viewIds.toList())
            return this
        }

        @Suppress("UNCHECKED_CAST")
        open fun <T : View> getView(@IdRes viewId: Int): T {
            var view = views[viewId]
            if (view == null) {
                view = requireNotNull(userView?.findViewById(viewId)) {
                    "找不到View，viewId=$viewId"
                }
                views.put(viewId, view)
            }
            return view as T
        }

        protected open fun addViewAction(
            @IdRes viewId: Int,
            action: (View) -> Unit
        ): Builder {
            viewActions += viewId to action
            return this
        }

        open fun setText(@IdRes viewId: Int, value: CharSequence): Builder {
            return addViewAction(viewId) { (it as TextView).text = value }
        }

        open fun setText(@IdRes viewId: Int, @StringRes strId: Int): Builder {
            return addViewAction(viewId) { (it as TextView).setText(strId) }
        }

        open fun setTextSize(@IdRes viewId: Int, size: Float): Builder {
            return addViewAction(viewId) {
                (it as TextView).setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
            }
        }

        open fun setTextSizePX(@IdRes viewId: Int, size: Float): Builder {
            return addViewAction(viewId) {
                (it as TextView).setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            }
        }

        open fun setImageResource(
            @IdRes viewId: Int,
            @DrawableRes imageResId: Int
        ): Builder {
            return addViewAction(viewId) { (it as ImageView).setImageResource(imageResId) }
        }

        open fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): Builder {
            return addViewAction(viewId) { it.setBackgroundColor(color) }
        }

        open fun setBackgroundRes(
            @IdRes viewId: Int,
            @DrawableRes backgroundRes: Int
        ): Builder {
            return addViewAction(viewId) { it.setBackgroundResource(backgroundRes) }
        }

        open fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): Builder {
            return addViewAction(viewId) { (it as TextView).setTextColor(textColor) }
        }

        open fun setImageDrawable(
            @IdRes viewId: Int,
            drawable: Drawable?
        ): Builder {
            return addViewAction(viewId) { (it as ImageView).setImageDrawable(drawable) }
        }

        open fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): Builder {
            return addViewAction(viewId) { (it as ImageView).setImageBitmap(bitmap) }
        }

        open fun setAlpha(@IdRes viewId: Int, value: Float): Builder {
            return addViewAction(viewId) { it.alpha = value }
        }

        open fun setGone(@IdRes viewId: Int, visible: Boolean): Builder {
            return addViewAction(viewId) {
                it.visibility = if (visible) View.VISIBLE else View.GONE
            }
        }

        open fun setVisible(@IdRes viewId: Int, visible: Boolean): Builder {
            return addViewAction(viewId) {
                it.visibility = if (visible) View.VISIBLE else View.INVISIBLE
            }
        }

        open fun build(): LDialog2 {
            requireNotNull(userView) {
                "请使用以下方法设置您的布局 setView() setLayout() setBinding()"
            }
            return LDialog2(this)
        }

        open fun getWidthPixels(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        open fun getHeightPixels(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        open fun getColor(@ColorRes colorResId: Int): Int {
            return ContextCompat.getColor(context, colorResId)
        }

        open fun getRoundRectDrawable(radius: Int, @ColorInt color: Int): GradientDrawable {
            return GradientDrawable().apply {
                cornerRadii = FloatArray(8) { radius.toFloat() }
                setColor(if (color != 0) color else Color.TRANSPARENT)
            }
        }

        open fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                context.resources.displayMetrics
            ).toInt()
        }

        open fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spVal,
                context.resources.displayMetrics
            ).toInt()
        }
    }
}
