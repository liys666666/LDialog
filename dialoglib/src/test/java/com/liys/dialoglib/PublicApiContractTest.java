package com.liys.dialoglib;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PublicApiContractTest {
    @Test
    public void sizeModesRemainStable() {
        assertArrayEquals(
                new int[]{1, 2, 3, 4},
                new int[]{ISizeModel.PX, ISizeModel.DP, ISizeModel.RATIO, ISizeModel.WRAP}
        );
    }

    @Test
    public void animationNamesRemainStable() {
        assertArrayEquals(
                new String[]{"default", "left", "right", "top", "bottom", "scale"},
                new String[]{
                        LAnimationsType.DEFAULT,
                        LAnimationsType.LEFT,
                        LAnimationsType.RIGHT,
                        LAnimationsType.TOP,
                        LAnimationsType.BOTTOM,
                        LAnimationsType.SCALE
                }
        );
    }
}
