package com.example.dell.customcanvas;

import android.graphics.Canvas;

/**
 * 绘制命令接口
 * Created by DELL on 2017/7/6.
 */

public interface IDraw {

    //绘制命令
    void draw(Canvas canvas);

    //撤销命令
    void undo();
}
