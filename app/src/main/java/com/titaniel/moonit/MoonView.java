package com.titaniel.moonit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class MoonView extends View {

    private float mBorderWidth = 0;

    private float mWidth, mHeight, mDiameter;
    private Paint mLight, mShadow, mBackground, mBorder;
    private double mValue = 0.75;
    private float mScale = 0.7f;

    public MoonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);



        init();
    }

    private void init() {
        int light = Color.WHITE;
//        int light = ContextCompat.getColor(context, R.color.four);
        int shadow = ContextCompat.getColor(getContext(), R.color.one);

        mLight = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLight.setColor(light);
        mLight.setStyle(Paint.Style.FILL);
        mLight.setStrokeWidth(2);

        mBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackground.setColor(shadow);
        mBackground.setStyle(Paint.Style.FILL);
        mBackground.setStrokeWidth(2);

        mBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorder.setColor(Color.WHITE);
        mBorder.setStyle(Paint.Style.STROKE);
        mBorder.setStrokeWidth(mBorderWidth);

        //mBorderWidth -= 4;

        mShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadow.setColor(shadow);
        mShadow.setStyle(Paint.Style.FILL);
        mShadow.setStrokeWidth(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mBorderWidth = w*mScale*0.1333333333f;
        mDiameter = w- mBorderWidth;

        init();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScale, mScale, mWidth/2, mHeight/2);

        canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, mBackground);

        canvas.save();

        canvas.scale(1.01f, 1.01f, mWidth/2, mHeight/2);

        canvas.translate(mBorderWidth/2, mBorderWidth/2);
        //canvas.drawRect(0, 0, mDiameter, mDiameter, mBackground);

        double v;
        if(mValue <= 0.5) {
            //Schwärzung von rechts
            v = mValue/0.5d;
            float a = Math.abs((float)(mDiameter * v - mDiameter/2));
            if(v >= 0.5f) {
                //right
                //black
                canvas.drawArc(0, 0, mDiameter, mDiameter, -90, 180, true, mShadow);

                //left
                //white
                canvas.drawArc(0, 0, mDiameter, mDiameter, 90, 180, true, mLight);
                //black
                canvas.drawOval(mDiameter/2-a, 0, mDiameter/2+a, mDiameter, mShadow);
            } else {
                //left
                //white
                canvas.drawArc(0, 0, mDiameter, mDiameter, 90, 180, true, mLight);

                //right
                //white
                canvas.drawOval(mDiameter/2-a, 0, mDiameter/2+a, mDiameter, mLight);
            }
        } else {
            //Schwärzung von links
            v = (1d-mValue)/0.5d;
            float a = Math.abs((float)(mDiameter * v - mDiameter/2));
            if(v >= 0.5f) {
                //left
                //black
                canvas.drawArc(0, 0, mDiameter, mDiameter, 90, 180, true, mShadow);

                //right
                //white
                canvas.drawArc(0, 0, mDiameter, mDiameter, -90, 180, true, mLight);
                //black
                canvas.drawOval(mDiameter/2-a, 0, mDiameter/2+a, mDiameter, mShadow);
            } else {
                //right
                //white
                canvas.drawArc(0, 0, mDiameter, mDiameter, -90, 180, true, mLight);

                //left
                //white
                canvas.drawOval(mDiameter/2-a, 0, mDiameter/2+a, mDiameter, mLight);
            }
        }
        canvas.restore();

        canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2, mBorder);

        canvas.restore();
    }

    public void setValue(double value) {
        mValue = value;
        invalidate();
    }
}
