package com.liys.dialoglib;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import androidx.annotation.StringDef;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @Description:
 * @Author: liys
 * @CreateDate: 2020/11/30 12:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/30 12:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface LAnimationsType {

    String DEFAULT = "default";
    String LEFT = "left";
    String RIGHT = "right";
    String TOP = "top";
    String BOTTOM = "bottom";
    String SCALE = "scale";

    @StringDef({DEFAULT, LEFT, RIGHT, TOP, BOTTOM, SCALE})
    @Retention(SOURCE)
    @Target({ElementType.PARAMETER})
    @interface AAnimationsType{}
}
