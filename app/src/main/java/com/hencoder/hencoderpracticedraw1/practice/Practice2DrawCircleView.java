package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice2DrawCircleView extends View {

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawCircle() 方法画圆
//        一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆
        Paint paint0 = new Paint();
        canvas.drawCircle(100,100,50,paint0);  // 默认实心圆

        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE); // Paint.Style.STROKE 修改为画线模式饥渴实现空心圆
        canvas.drawCircle(200,200,50,paint1);

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        canvas.drawCircle(300,300,50,paint2);  // 蓝色实心圆

        Paint paint3 = new Paint() ;
        paint3.setStrokeWidth(20f);
        paint3.setStyle(Paint.Style.STROKE);    // 空心圆
        canvas.drawCircle(400,400,50,paint3);   // 线宽为20的实心圆

    }
}
