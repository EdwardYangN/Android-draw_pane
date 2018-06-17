package com.edward.cn.demodraw;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by edward on 18-4-13.
 */

public class MyView3 extends View {

    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    private Paint mPaint;
    private Bitmap mBitmap;

    private int lastX,lastY;
    private int bitmapWidth,bitmapHeight;
    private ArrayList<DrawPath> savePath;
    private ArrayList<DrawPath> deletePath;
    private DrawPath dp;

    public Map<Integer,ArrayList<Point>> pointsList;
    private ArrayList<Point> points;
    private int obj=0;

    class DrawPath {
        Path path;
        Paint paint;
    }

    public MyView3(Context context) {
        super(context);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels;
        init();
        savePath = new ArrayList<DrawPath>();
        deletePath = new ArrayList<DrawPath>();
        pointsList = new HashMap<Integer, ArrayList<Point>>();
        points = new ArrayList<Point>();
    }

    public MyView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels;
        init();

        savePath = new ArrayList<DrawPath>();
        deletePath = new ArrayList<DrawPath>();
        pointsList = new HashMap<Integer, ArrayList<Point>>();
        points = new ArrayList<Point>();
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

        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        mBitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight,
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
//        Toast.makeText(getContext(), "Init done!", Toast.LENGTH_SHORT).show();

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

                mPath = new Path();
                dp = new DrawPath();
                dp.paint = mPaint;
                dp.path = mPath;

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
                points.add(new Point(x,y));
                break;

            case MotionEvent.ACTION_UP:
                savePath.add(dp);
                obj++;
                pointsList.put(obj, points);
                AlertDialog.Builder dia = new AlertDialog.Builder(getContext());
                dia.setTitle("Map");
                dia.setMessage(pointsList.get(obj).toString()+"\n"
                        +"Size: "+pointsList.size());
                dia.show();

                points.clear();
                break;

        }

        invalidate();

        return true;
    }

    protected String saveBitmap(String s){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date(System.currentTimeMillis());
        String dateStr = formatter.format(now);
        if(s==null)
            dateStr = dateStr + "_paint.png";
        else
            dateStr = s+"_paint.png";
        String fullPath = "";

        File dir = new File("/sdcard/drawNotes/");
        File file = new File("/sdcard/drawNotes/"+dateStr);
        if (!dir.exists())
            dir.mkdir();
        else
            if (file.exists())
                file.delete();

        try {

            FileOutputStream out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
            fullPath = "/sdcard/drawNotes/"+dateStr;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullPath;
    }

    protected void clearAll(){
        init();
        invalidate();
        savePath.clear();
        deletePath.clear();
        obj=0;
        pointsList.clear();
    }

    protected void undo(){

        System.out.println(savePath.size()+"------");
        if (savePath != null && savePath.size()>0){
            init();
            DrawPath drawPath = savePath.get(savePath.size()-1);
            deletePath.add(drawPath);
            savePath.remove(savePath.size()-1);
            for (DrawPath d:savePath){
                System.out.println(savePath.size()+"------");
                mCanvas.drawPath(d.path,d.paint);
            }
            invalidate();
        }else
            Toast.makeText(getContext(),"No more path to remove ~",Toast.LENGTH_SHORT).show();
    }

    protected void redo(){

        if (deletePath!=null && deletePath.size()>0){
            DrawPath d = deletePath.get(deletePath.size()-1);
            savePath.add(d);
            mCanvas.drawPath(d.path,d.paint);
            deletePath.remove(deletePath.size()-1);
            invalidate();
        } else
            Toast.makeText(getContext(),"No more path to recovered ~",Toast.LENGTH_SHORT).show();

    }

}
