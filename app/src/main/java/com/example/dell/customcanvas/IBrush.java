package com.example.dell.customcanvas;

import android.graphics.Path;

/**
 * 抽象笔触
 * Created by DELL on 2017/7/6.
 */

public interface IBrush {

    //触点接触时
    void down(Path path, float x, float y);

    //触点移动时
    void move(Path path, float x, float y);

    //触点离开时
    void up(Path path, float x, float y);
}
