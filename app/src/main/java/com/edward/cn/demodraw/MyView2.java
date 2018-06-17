package com.edward.cn.demodraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by edward on 18-4-13.
 */

public class MyView2 extends View {

    protected Paint mPaint;
    protected Path mPath;
    protected Canvas mCanvas;
    protected Bitmap mBitmap;

    private int lastX,lastY;
    private int bitmapWidth,bitmapHeight;

    public MyView2(Context context) {
        super(context);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels;

        init();
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels;

        init();

    }

    protected void init() {

        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(16);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mBitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight,
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
//        Toast.makeText(getContext(), "Init done!", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
//                Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                mPath.moveTo(lastX,lastY);
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - lastX);
                int dy = Math.abs(y - lastY);
                if (dx>3 || dy>3)
                    mPath.lineTo(x,y);
                lastX = x;
                lastY = y;
                break;

        }

        invalidate();

        return true;
    }


}
