package com.example.dell.customcanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * 绘制的真正执行者画布
 * 所有的绘制操作都在这个SurfaceView对象中进行
 * Created by DELL on 2017/7/6.
 */

public class DrawCanvas extends SurfaceView implements SurfaceHolder.Callback {

    public boolean isDrawing , isRunning;//标识是否可以绘制、绘制线程是否可以运行

    private Bitmap mBitmap;//绘制到的位图对象
    private DrawInvoker mInvoker;//绘制命令请求对象
    private DrawThread mThread;//绘制线程

    public DrawCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInvoker = new DrawInvoker();
        mThread = new DrawThread();

        getHolder().addCallback(this);
    }

    //增加一条绘制命令
    public void add(DrawPath path) {
        mInvoker.add(path);
    }

    //重做上一步撤销的绘制
    public void redo() {
        isDrawing = true;
        mInvoker.redo();
    }

    //撤销上一步的绘制
    public void undo() {
        isDrawing = true;
        mInvoker.undo();
    }

    //是否可以重做
    public boolean canRedo() {
        return mInvoker.canRedo();
    }

    //是否可以撤销
    public boolean canUndo() {
        return mInvoker.canUndo();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        isRunning = false;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class DrawThread extends Thread{
        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning) {
                if (isDrawing) {
                    try {
                        canvas = getHolder().lockCanvas(null);
                        if (mBitmap == null) {
                            mBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        }
                        Canvas c = new Canvas(mBitmap);
                        c.drawColor(0, PorterDuff.Mode.CLEAR);
                        mInvoker.execute(c);
                        canvas.drawBitmap(mBitmap, 0, 0, null);
                    }finally {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                    isDrawing = false;
                }
            }
        }
    }
}
