package com.example.dell.customcanvas;

import android.graphics.Path;

/**
 * 圆形笔触
 * Created by DELL on 2017/7/6.
 */

public class CircleBrush implements IBrush {
    @Override
    public void down(Path path, float x, float y) {

    }

    //Path.Direction.CW stands for Clockwise
    @Override
    public void move(Path path, float x, float y) {
        path.addCircle(x, y, 10, Path.Direction.CW);
    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
