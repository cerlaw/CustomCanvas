package com.example.dell.customcanvas;

import android.graphics.Path;

/**
 * 普通笔触
 * Created by DELL on 2017/7/6.
 */

public class NormalBrush implements IBrush {
    @Override
    public void down(Path path, float x, float y) {
        path.moveTo(x, y);
    }

    @Override
    public void move(Path path, float x, float y) {
        path.lineTo(x, y);
    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
