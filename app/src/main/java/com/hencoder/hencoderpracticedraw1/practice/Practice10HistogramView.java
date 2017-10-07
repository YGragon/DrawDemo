package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.model.Data;

import java.util.ArrayList;
import java.util.List;


public class Practice10HistogramView extends View {

    private static final String TAG = "Practice10HistogramView";
    private final static String NAME = "直方图";

    private List<Data> datas;   // 数据
    private Paint paint;        // 画笔
    private float startX = 0f;       // 直方图的矩形和文字开始位置
    private float startY = 0f;       // 直方图的矩形和文字开始位置
    private float space;        // 直方图的间距
    private float width;        //
    private float max;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        datas = new ArrayList<>();
        Data data = new Data("Froyo", 10.0f, Color.GREEN);
        datas.add(data);
        data = new Data("ICS", 18.0f, Color.GREEN);
        datas.add(data);
        data = new Data("JB", 22.0f, Color.GREEN);
        datas.add(data);
        data = new Data("KK", 27.0f, Color.GREEN);
        datas.add(data);
        data = new Data("L", 40.0f, Color.GREEN);
        datas.add(data);
        data = new Data("M", 60.0f, Color.GREEN);
        datas.add(data);
        data = new Data("N", 33.5f, Color.GREEN);
        datas.add(data);
        max = Float.MIN_VALUE;
        for (Data d : datas) {
            max = Math.max(max, d.getNumber()); // 获得直方图的数值
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        paint.setColor(Color.WHITE);
        paint.setTextSize(72);
        canvas.drawText(NAME, (canvas.getWidth() - paint.measureText(NAME)) / 2, canvas.getHeight() * 0.9f, paint); // 绘制直方图的文字

        canvas.translate(canvas.getWidth() * 0.1f, canvas.getHeight() * 0.7f); // 将画图原点移动直方图的原点位置

        width = (canvas.getWidth() * 0.8f - 100) / datas.size() * 0.8f; // 每个矩形的宽度
        space = (canvas.getWidth() * 0.8f - 100) / datas.size() * 0.2f; // 矩形间的间距

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(0, 0, canvas.getWidth() * 0.8f, 0, paint);   // 画x轴

        canvas.drawLine(0, 0, 0, -canvas.getHeight() * 0.6f, paint); // 画y轴
        float spaceY = canvas.getHeight()*0.6f / 6 ;
        for(int i = 0; i <= 6; ++i){
            paint.setTextSize(24);
            canvas.drawText(i*10+"", -40 , -startY , paint);
            Paint paint1 = new Paint();
            paint1.setStrokeWidth(10);
            paint1.setStrokeCap(Paint.Cap.BUTT);
            canvas.drawPoint(-startX,-startY, paint1);
            startY = (i+1)*spaceY ;
        }



        /**
         *
         * 实现直方图中矩形和底部的文字
         */
        paint.setTextSize(36);
        paint.setStyle(Paint.Style.FILL);
        for (Data data : datas) {
            paint.setColor(data.getColor());
            /**
             * 上边为矩形的高度 : -(data.getNumber() / max * canvas.getHeight() * 0.6f) 得到数值在整体宽度的比例
             */
            canvas.drawRect(startX + space, -(data.getNumber() / max * canvas.getHeight() * 0.6f), startX + space + width, 0, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText(data.getNumber()+"",startX + space + (width - paint.measureText(data.getNumber()+"")) / 2,-(data.getNumber() / max * canvas.getHeight() * 0.6f)-10,paint);

            // startX + space + (width - paint.measureText(data.getName())) / 2 : 起始点坐标+矩形宽度的一半
            canvas.drawText(data.getName(), startX + space + (width - paint.measureText(data.getName())) / 2, 40, paint);

            startX += width + space;    // 改变起始位置
        }
    }
}
