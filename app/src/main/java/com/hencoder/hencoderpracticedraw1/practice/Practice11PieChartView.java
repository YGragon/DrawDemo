package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.model.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 参考这位大神：https://github.com/Jucongyuan/PracticeDraw1
 */
public class Practice11PieChartView extends View {

    private static final String TAG = "Practice11PieChartView";

    private float radius;
    private List<Data> datas;
    private Paint paint;
    private RectF rectF;
    private float total;
    private float max;

    float startAngle = 0f; // 开始的角度
    float sweepAngle;      // 扫过的角度
    float lineAngle;       // 当前扇形一半的角度

    float lineStartX = 0f; // 直线开始的X坐标
    float lineStartY = 0f; // 直线开始的Y坐标
    float lineEndX;        // 直线结束的X坐标
    float lineEndY;        // 直线结束的Y坐标

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        radius = 300;
        datas = new ArrayList<>();
        Data data = new Data("Gingerbread", 10.0f, Color.WHITE);
        datas.add(data);
        data = new Data("Ice Cream Sandwich", 18.0f, Color.MAGENTA);
        datas.add(data);
        data = new Data("Jelly Bean", 22.0f, Color.GRAY);
        datas.add(data);
        data = new Data("KitKat", 27.0f, Color.GREEN);
        datas.add(data);
        data = new Data("Lollipop", 40.0f, Color.BLUE);
        datas.add(data);
        data = new Data("Marshmallow", 60.0f, Color.RED);
        datas.add(data);
        data = new Data("Nougat", 33.5f, Color.YELLOW);
        datas.add(data);
        total = 0.0f;
        max = Float.MIN_VALUE;
        for (Data d : datas) {
            total += d.getNumber();
            max = Math.max(max, d.getNumber());
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);   // 用于绘制时抗锯齿
        paint.setStrokeWidth(2);
        paint.setTextSize(30);
        rectF = new RectF(-300, -300, 300, 300);    // 矩形内的图表范围
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);  // 将画布(0，0)坐标点移到画布的中心
        startAngle = 0f; //这句代码很重要，不然有bug
        for (Data data : datas) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(data.getColor());
            sweepAngle = data.getNumber() / total * 360f;   // 扫过的角度
            lineAngle = startAngle + sweepAngle / 2;        // 当前扇形一半的角度
            Log.e(TAG, "onDraw: 角度=="+lineAngle);
            lineStartX = radius * (float) Math.cos(lineAngle / 180 * Math.PI);  // 直线开始的X坐标
            lineStartY = radius * (float) Math.sin(lineAngle / 180 * Math.PI);  // 直线开始的Y坐标
            lineEndX = (radius + 50) * (float) Math.cos(lineAngle / 180 * Math.PI);
            lineEndY = (radius + 50) * (float) Math.sin(lineAngle / 180 * Math.PI);

            // 绘制最大的扇形突出
            if (data.getNumber() == max) {
                canvas.save();              // 保存画布当前的状态 和 canvas.restore() 同时存在，单独处理平移出来的扇形
                canvas.translate(lineStartX * 0.1f, lineStartY * 0.1f);
                canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            } else {
                canvas.drawArc(rectF, startAngle, sweepAngle - 1.0f, true, paint);
            }

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(lineStartX, lineStartY, lineEndX, lineEndY, paint);     // 画引出的线段
            // 计算处每个扇形所占的百分比，保留两位小数显示
            float   numberBefore  =   data.getNumber() / total *100;
            DecimalFormat   decimalFormat  =   new DecimalFormat("##0.00");
            String  numberAfter = decimalFormat.format(numberBefore);

            if (lineAngle > 90 && lineAngle <= 270) {
                Log.e(TAG, "onDraw: name=="+data.getName());
                canvas.drawLine(lineEndX, lineEndY, lineEndX - 50, lineEndY, paint);    // 往左画出直线
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(data.getName(), lineEndX - 50 - 10 - paint.measureText(data.getName()), lineEndY, paint);   // 往左画出文字
                canvas.drawText(numberAfter+"%", lineEndX - 50 - paint.measureText(numberAfter+"%"), lineEndY+30, paint);   // 往下画出百分比
            } else {
                canvas.drawLine(lineEndX, lineEndY, lineEndX + 50, lineEndY, paint);    // 往右画出直线
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(data.getName(), lineEndX + 50 + 10, lineEndY, paint);   // 往右画出文字
                canvas.drawText(numberAfter+"%", lineEndX + 50, lineEndY-30, paint);   // 往上画出百分比
            }

            // 取出之前保存的状态
            if (data.getNumber() == max) {
                canvas.restore();
            }
            startAngle += sweepAngle;
        }
    }

}
