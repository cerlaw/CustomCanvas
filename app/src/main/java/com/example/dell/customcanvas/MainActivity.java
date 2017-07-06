package com.example.dell.customcanvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 在Activity中整合各个功能模块
 */
public class MainActivity extends AppCompatActivity {
    private DrawCanvas mCanvas;
    private DrawPath mPath;
    private Paint mPaint;
    private IBrush mBrush;

    private Button btnRedo, btnUndo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPaint = new Paint();
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        mBrush = new NormalBrush();

        mCanvas = (DrawCanvas) findViewById(R.id.canvas);
        mCanvas.setOnTouchListener(new DrawOnTouchListener());

        btnRedo = (Button) findViewById(R.id.redo_btn);
        btnRedo.setEnabled(false);
        btnUndo = (Button) findViewById(R.id.undo_btn);
        btnUndo.setEnabled(false);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.red_btn:
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
                mPaint.setColor(Color.RED);
                Toast.makeText(this, "red", Toast.LENGTH_SHORT).show();
                break;
            case R.id.green_btn:
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
                mPaint.setColor(Color.GREEN);
                Toast.makeText(this, "green", Toast.LENGTH_SHORT).show();
                break;
            case R.id.blue_btn:
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3);
                mPaint.setColor(Color.BLUE);
                Toast.makeText(this, "blue", Toast.LENGTH_SHORT).show();
                break;
            case R.id.undo_btn:
                mCanvas.undo();
                if (!mCanvas.canUndo()) {
                    btnUndo.setEnabled(false);
                }
                btnRedo.setEnabled(true);
                break;
            case R.id.redo_btn:
                mCanvas.redo();
                if (!mCanvas.canRedo()) {
                    btnRedo.setEnabled(false);
                }
                btnUndo.setEnabled(true);
                break;
            case R.id.normal_btn:
                mBrush = new NormalBrush();
                break;
            case R.id.circle_btn:
                mBrush = new CircleBrush();
                break;
        }
    }

    private class DrawOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mPath = new DrawPath();
                mPath.mPaint = mPaint;
                mPath.mPath = new Path();
                mBrush.down(mPath.mPath, event.getX(), event.getY());
            }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mBrush.move(mPath.mPath, event.getX(), event.getY());
            }else if (event.getAction() == MotionEvent.ACTION_UP) {
                mBrush.up(mPath.mPath, event.getX(), event.getY());
                mCanvas.add(mPath);
                mCanvas.isDrawing = true;
                btnUndo.setEnabled(true);
                btnRedo.setEnabled(false);
            }
            return true;
        }
    }
}
