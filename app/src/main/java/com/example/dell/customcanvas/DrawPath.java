package com.example.dell.customcanvas;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 具体绘制路径命令
 * Created by DELL on 2017/7/6.
 */

public class DrawPath implements IDraw {
    public Paint mPaint;
    public Path mPath;

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void undo() {

    }
}
