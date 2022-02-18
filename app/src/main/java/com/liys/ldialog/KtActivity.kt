package com.liys.ldialog

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.liys.dialoglib.LAnimationsType
import com.liys.dialoglib.LDialog2
import com.liys.ldialog.databinding.DialogConfirm2Binding

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2022/2/18 17:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/2/18 17:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class KtActivity:AppCompatActivity() , View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn1).setOnClickListener(this)
        findViewById<View>(R.id.btn2).setOnClickListener(this)
        findViewById<View>(R.id.btn3).setOnClickListener(this)
        findViewById<View>(R.id.btn4).setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        val dialog2 = LDialog2.Builder(this).apply {
            setBinding(DialogConfirm2Binding::class.java)
            setGravity(Gravity.BOTTOM, 0, 0)
            setAnimations(LAnimationsType.BOTTOM)

        }
            .build()
        val binding = dialog2.binding as DialogConfirm2Binding
        binding.tvTitle.text = "66666"
        dialog2.show()
    }
}