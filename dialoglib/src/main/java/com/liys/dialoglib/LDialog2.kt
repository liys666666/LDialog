package com.liys.dialoglib

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import com.liys.dialoglib.LAnimationsType.AAnimationsType

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2022/1/27 14:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/1/27 14:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class LDialog2(var builder: Builder):AppCompatDialog(builder.context, builder.themeResId){

    var binding:Any? = null

    init {
        binding = builder.binding
    }

    //开始构建
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //添加布局
        builder.controlView.addView(builder.userView)
        setContentView(builder.controlView)


//---------------------------设置宽高--------------------------------------------
        //判断xml根布局, 宽高
        val xmlLp: ViewGroup.LayoutParams = builder.userView!!.layoutParams
        //是否使用了setWidth等方法设置宽高
        if(builder.width!=0){
            xmlLp.width = builder.width
        }
        if(builder.height!=0){
            xmlLp.height = builder.height
        }
        builder.userView!!.layoutParams = xmlLp
        //window窗口
        val windowLP = window!!.attributes
        windowLP.width = xmlLp.width
        windowLP.height = xmlLp.height
        window!!.attributes = windowLP
        //controlView宽高
        val controlLP: ViewGroup.LayoutParams = builder.controlView.layoutParams
        controlLP.width = xmlLp.width
        controlLP.height = xmlLp.height
        builder.controlView.layoutParams = controlLP

        //显示位置
        window.setGravity(builder.gravity)
        val layoutParams = window.attributes
        layoutParams.x = builder.offX
        layoutParams.y = builder.offY
        window.attributes = layoutParams

        //其它
        window.setWindowAnimations(builder.animationsStyle) //动画
        window.setDimAmount(builder.maskValue)  //遮罩透明度
        setCanceledOnTouchOutside(builder.cancelable)  //点击取消

        //设置透明背景
        val drawable = GradientDrawable()
        drawable.setColor(Color.TRANSPARENT)
        window.setBackgroundDrawable(drawable)
    }


//------------------------建造者模式------------------------------
    open class Builder(var context: Context) {
        //背景、圆角 ---> 暂不处理

        //主题
        var themeResId = R.style.LDialog

        //View布局
        var controlView:LDialogRootView = LDialogRootView(context)
        var userView:View?=null //用户定义的View
        var binding:Any?=null //binding

        //宽高---精确值
        var width = 0 //
        var height = 0; //

        //宽高范围
        var minWidth = 0 // 宽
        var maxWidth = 0 //
        var minHeight = 0 // 高
        var maxHeight = 0 //

        //显示位置
        var gravity = Gravity.CENTER
        var offX = 0  //位置偏移
        var offY = 0

        //其它属性
        var maskValue = 0.5f  //遮罩透明度
        var animationsStyle = R.style.li_dialog_default //动画
        var cancelable = true //点击取消


        init {
            controlView = LDialogRootView(context)
        }

    // --------------------------------设置你的布局----------------------------------------
        fun setView(view: View):Builder{
            userView = view
            return this
        }
        fun setLayout(@LayoutRes layoutId: Int):Builder{
            userView = LayoutInflater.from(context).inflate(layoutId, controlView, false)
            return this
        }
        fun setBinding(bindingClazz: Class<*>):Builder{
            var inflate = bindingClazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            binding = inflate.invoke(null, LayoutInflater.from(context), controlView, false)
            userView = binding?.javaClass?.getMethod("getRoot")?.invoke(binding) as View
            return this
        }

// --------------------------------设置宽高(精确值)---------------------------------------
        //------------宽--------------
        fun setWidth(value: Float, @ISizeModel.ASizeModel model: Int = ISizeModel.DP):Builder{
            when(model){
                ISizeModel.PX -> {
                    width = value.toInt()
                }
                ISizeModel.DP -> {
                    width = dp2px(context, value)
                }
                ISizeModel.RATIO -> {
                    width = (getWidthPixels(context) * value).toInt()
                }
                ISizeModel.WRAP -> {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }
            return this
        }

        //------------高--------------
        fun setHeight(value: Float, @ISizeModel.ASizeModel model: Int = ISizeModel.DP):Builder{
            when(model){
                ISizeModel.PX -> {
                    height = value.toInt()
                }
                ISizeModel.DP -> {
                    height = dp2px(context, value)
                }
                ISizeModel.RATIO -> {
                    height = (getWidthPixels(context) * value).toInt()
                }
                ISizeModel.WRAP -> {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }
            return this
        }

// --------------------------------设置宽高(范围)---------------------------------------
        //宽---最小值
        fun setMinWidth(value: Float, @ISizeModel.ASizeModel model: Int = ISizeModel.DP):Builder{
            var minWidth = 0
            when(model){
                ISizeModel.PX -> {
                    minWidth = value.toInt()
                }
                ISizeModel.DP -> {
                    minWidth = dp2px(context, value)
                }
                ISizeModel.RATIO -> {
                    minWidth = (getWidthPixels(context) * value).toInt()
                }
            }
            if(minWidth>0){
                controlView.minimumWidth = minWidth
            }
            return this
        }
        //宽---最大值
        fun setMaxWidth(value: Float, @ISizeModel.ASizeModel model: Int = ISizeModel.DP):Builder{
            var maxWidth = 0
            when(model){
                ISizeModel.PX -> {
                    maxWidth = value.toInt()
                }
                ISizeModel.DP -> {
                    maxWidth = dp2px(context, value)
                }
                ISizeModel.RATIO -> {
                    maxWidth = (getWidthPixels(context) * value).toInt()
                }
            }
            if(maxWidth>0){
                controlView.setMaxHeight(maxWidth)
            }
            return this
        }
        //高---最小值
        fun setMinHeight(value: Float, @ISizeModel.ASizeModel model: Int = ISizeModel.DP):Builder{
            var minHeight = 0;
            when(model){
                ISizeModel.PX -> {
                    minHeight = value.toInt()
                }
                ISizeModel.DP -> {
                    minHeight = dp2px(context, value)
                }
                ISizeModel.RATIO -> {
                    minHeight = (getWidthPixels(context) * value).toInt()
                }
            }
            if(minHeight>0){
                controlView.minimumHeight = minHeight
            }
            return this
        }
        //高---最大值
        fun setMaxHeight(value: Float, @ISizeModel.ASizeModel model: Int = ISizeModel.DP):Builder{
            var maxHeight = 0
            when(model){
                ISizeModel.PX -> {
                    maxHeight = value.toInt()
                }
                ISizeModel.DP -> {
                    maxHeight = dp2px(context, value)
                }
                ISizeModel.RATIO -> {
                    maxHeight = (getWidthPixels(context) * value).toInt()
                }
            }

            if(maxHeight>0){
                controlView.setMaxHeight(maxHeight)
            }
            return this
        }

// --------------------------------设置位置---------------------------------------
        fun setGravity(gravity: Int, offX: Int = 0, offY: Int = 0):Builder{
            this.gravity = gravity
            this.offX = offY
            this.offY = offY
            return this
        }

// --------------------------------遮罩透明度---------------------------------------
        /**
         * 遮罩透明度
         * @param value 0-1f
         * @return
         */
        fun setMaskValue(value: Float):Builder{
            this.maskValue = value
            return this
        }

// --------------------------------动画-------------------------------------------
        open fun setAnimations(@AAnimationsType styleType: String?):Builder{
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


// --------------------------------开始构建-------------------------------------------
        fun build():LDialog2{
            if(userView==null){
                throw RuntimeException("请使用以下方法设置您的布局 setView() setLayout() setBinding()")
            }
            return LDialog2(this)
        }

// --------------------------------工具方法-------------------------------------------
        /** 1.获取屏幕宽度(单位px)  */
        open fun getWidthPixels(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        /** 2.获取屏幕高度(单位px)  */
        fun getHeightPixels(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        /** dp转px  */
        open fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
        }
    }

}