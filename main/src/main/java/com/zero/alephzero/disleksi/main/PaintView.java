package com.zero.alephzero.disleksi.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {


    public static int BRUSH_SIZE  = 20;
    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLORANCE = 4;
    private float mX,mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int current_color;
    private int background_color = DEFAULT_BG_COLOR;

    private int strokeWidth;
    private boolean blur;
    private boolean emboss;

    private MaskFilter mEmboss;
    private MaskFilter mBlur;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public PaintView(Context context) {
        this(context,null);
    }

    public PaintView(Context context,  AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        mEmboss = new EmbossMaskFilter(new float[] {1,1,1}, 0.4f,6,3.5f );
        mBlur = new BlurMaskFilter(5,BlurMaskFilter.Blur.NORMAL);
    }
    public void init(DisplayMetrics displayMetrics){

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);


        current_color = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }

    public void normal(){

        emboss = false;
        blur = false;
    }

    public void emboss(){

        emboss = true;
        blur = false;
    }
    public void blur(){

        emboss = false;
        blur = true;
    }

    public void  clear(){

        background_color = DEFAULT_BG_COLOR;
        paths.clear();
        normal();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        mCanvas.drawColor(background_color);

        // set color, thickness and maskfilter
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStrokeWidth(40);
        mPaint.setMaskFilter(null);
        // draw circle for background
        mCanvas.drawCircle(50, 50, 20, mPaint);
        // draw ring for background
        mCanvas.drawCircle(200, 200, 30, mPaint);
        mPaint.setColor(background_color);
        mCanvas.drawCircle(200, 200, 29, mPaint);

        //draw lines for background

        mCanvas.drawLine(0, mCanvas.getHeight()/2 + 100, mCanvas.getWidth(), mCanvas.getHeight()/2 + 100, mPaint);
        mCanvas.drawLine(0, mCanvas.getHeight()/2 - 300, mCanvas.getWidth(), mCanvas.getHeight()/2 - 300, mPaint);


        for (FingerPath fg : paths){

            mPaint.setColor(fg.color);
            mPaint.setStrokeWidth(fg.strokeWidth);
            mPaint.setMaskFilter(null);

            if (fg.emboss)
                mPaint.setMaskFilter(mEmboss);
            else if (fg.blur)
                mPaint.setMaskFilter(mBlur);

            mCanvas.drawPath(fg.path ,mPaint);

        }
        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);
        canvas.restore();
    }


    private void touchStart(float x, float y){

        mPath = new Path();
        FingerPath fp = new FingerPath(current_color,emboss, blur,strokeWidth, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x ,float y){

        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);

        if (dx >= TOUCH_TOLORANCE || dy >= TOUCH_TOLORANCE){

            mPath.quadTo(mX,mY,(x + mX) / 2 ,(y + mY)/2 );

            mX = x;
            mY = y;
        }
    }

    private void touchUp(){

        mPath.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (motionEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;

        }
        return true;
    }

}
