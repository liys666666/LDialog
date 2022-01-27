package com.liys.dialoglib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @Description: 大小模式
 * @Author: liys
 * @CreateDate: 2022/1/27 15:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/1/27 15:31
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ISizeModel {

    int PX = 1;
    int DP = 2;
    int RATIO = 3; //比例
    int WRAP = 4; //自适应

    @IntDef({PX, DP, RATIO, WRAP})
    @Retention(SOURCE)
    @Target({ElementType.PARAMETER})
    public @interface ASizeModel { }
}
