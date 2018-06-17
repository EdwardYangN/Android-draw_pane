package com.edward.cn.demodraw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by edward on 18-4-12.
 */

public class MyView extends View {

    private Paint mPaint;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //create object
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorAccent, null));
        mPaint.setTextSize(36);
        mPaint.setStrokeWidth(5);

    }



    @SuppressLint("ResourceType")
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(getResources().getColor(R.color.yellow, null));
//        canvas.drawCircle(200, 200, 100, mPaint);
//        canvas.drawRect(new Rect(100, 350, 500, 750), mPaint);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round),
//                100, 800, mPaint);
//        canvas.drawArc(new RectF(350, 100, 550, 300),
//                0, 90, true, mPaint);
//        canvas.drawRoundRect(new RectF(550,400,750,500),
//                50,50,mPaint);
//        canvas.drawText("I'm Edward",100,1000,mPaint);
//
//        Path path = new Path();
//        path.moveTo(100,1200);
//        path.lineTo(200,1300);
//        path.lineTo(300,1400);
//        path.close();
//        canvas.drawTextOnPath("Edward Sang ~",path,100,1200,mPaint);

        int WIDTH = canvas.getWidth();
        int HEIGHT = canvas.getHeight();
        canvas.translate(WIDTH/2,HEIGHT/4);
        canvas.drawCircle(0,0,WIDTH/4,mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0,0,WIDTH/4-10,mPaint);
        mPaint.setColor(R.color.colorAccent);

        canvas.save();
        canvas.translate(-WIDTH/5,-WIDTH/5);

        Path path = new Path();
        path.addArc(new RectF(0,0,WIDTH/5*2,WIDTH/5*2),-120,240);
        Paint citePaint = new Paint(mPaint);
        citePaint.setColor(R.color.green);
        citePaint.setTextSize(36);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("Edward Yang ~",path,0,0,citePaint);
        canvas.restore();

        Paint tmpPaint = new Paint(mPaint);
        tmpPaint.setTextSize(36);
        tmpPaint.setStrokeWidth(1);
        float y = WIDTH/4;
        int count = 60;
        for (int i=0; i<count; i++) {
            if (i%5 == 0){
                canvas.drawLine(0,y,0,y+20,mPaint);
                canvas.drawText(String.valueOf((i/5+6)%12),-10f,y+WIDTH/12,tmpPaint);
            } else
                canvas.drawLine(0,y,0,y+10,tmpPaint);
            canvas.rotate(360/count,0,0);
        }


        tmpPaint.setColor(Color.CYAN);
        tmpPaint.setStrokeWidth(8);
        canvas.drawCircle(0,0,WIDTH/40,tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0,0,WIDTH/40-10,tmpPaint);

        tmpPaint.setColor(Color.GREEN);
        canvas.drawLine(0,WIDTH/16-10,0,-WIDTH/6,tmpPaint);

    }
}
