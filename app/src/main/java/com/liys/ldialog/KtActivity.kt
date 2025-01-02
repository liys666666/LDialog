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

    override fun onClick(v: View) {
        var dBinding:DialogConfirm2Binding
        val dialog2 = LDialog2.Builder(this).apply {
            setBinding(DialogConfirm2Binding::class.java)

            when(v.id){
                R.id.btn1->{
                    setGravity(Gravity.BOTTOM, 0, 0)
                    setAnimations(LAnimationsType.BOTTOM)
                }
                R.id.btn2->{
                    setGravity(Gravity.LEFT, 0, 0)
                    setAnimations(LAnimationsType.LEFT)
                }
                R.id.btn3->{
                    setGravity(Gravity.CENTER, 0, 0)
                    setAnimations(LAnimationsType.SCALE)
                }
                R.id.btn4->{
                    setGravity(Gravity.RIGHT, 0, 0)
                    setAnimations(LAnimationsType.RIGHT)
                }
            }
            dBinding = binding as DialogConfirm2Binding
        }
            .build()
        dBinding.tvTitle.text = "66666"
        dialog2.show()
    }
}