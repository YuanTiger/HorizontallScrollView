package com.my;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  2016/11/25/上午11:31
 * DESC:
 */

public class ScrollTipView extends View {

    private Paint linePaint;//线的画笔
    private Paint textPaint;//文字画笔


    private int viewWidth;//整个控件宽度
    private int ImageHeight = 30;//游标的直径
    private int LINE_HEIGHT = 10;//线的宽度
    private int textSziePx = 44;//字体大小
    private int VIEW_PADDING_LEFT_RIGHT = 50;//控件左右边距
    private int TEXT_MARGIN_TOP = 10;//文字与线之间的间隔


    private int currentProgress = 0;


    private TextView textView;


    public ScrollTipView(Context context) {
        super(context);
        initView(context);
    }


    public ScrollTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ScrollTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        viewWidth = screenWidth - VIEW_PADDING_LEFT_RIGHT * 2;

        //画笔
        linePaint = new Paint();
        linePaint.setColor(context.getResources().getColor(R.color.colorAccent));
        textPaint = new Paint();
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(textSziePx);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float lastI = 0;
        //画线条-渐变
        for (float i = 0.1f; i <= 1.05f; i = i + 0.1f) {
            //改变画笔颜色
            if (i <=  0.6f) {
                linePaint.setColor(Color.argb(255, (int) (255 * (1f - i+0.1f)), (int) (255 * i+0.1f), 0));
            } else {
                linePaint.setColor(Color.argb(255, 22, 255, 0));
            }


            /**
             * 参数1：左上坐标点x轴
             * 参数2：左上坐标点的y轴
             * 参数3：右下坐标点x轴
             * 参数4：右下坐标点y轴
             * 参数5：画笔
             */
            canvas.drawRect(viewWidth * lastI + VIEW_PADDING_LEFT_RIGHT, ImageHeight + TEXT_MARGIN_TOP, viewWidth * i + VIEW_PADDING_LEFT_RIGHT, ImageHeight + TEXT_MARGIN_TOP + LINE_HEIGHT, linePaint);
            lastI = i;
        }
        /**
         * 画游标
         * 参数1：圆心x轴坐标
         * 参数2：圆心y轴坐标
         * 参数3：半径
         * 参数4：画笔
         */
        canvas.drawCircle(viewWidth * ((float) currentProgress / 100) + VIEW_PADDING_LEFT_RIGHT / 2 + ImageHeight / 2, ImageHeight / 2, ImageHeight / 2, linePaint);
        if (textView != null) {
            textView.setText(currentProgress + "M");
        }
        /**
         * 画指示文字
         * 参数1：文字内容
         * 参数2：左上x轴坐标
         * 参数3：左上y轴坐标
         * 参数4：画笔
         */
        canvas.drawText("0M", VIEW_PADDING_LEFT_RIGHT, ImageHeight + LINE_HEIGHT + TEXT_MARGIN_TOP * 2 + textSziePx, textPaint);
        canvas.drawText("70M", viewWidth * 7 / 10, ImageHeight + LINE_HEIGHT + TEXT_MARGIN_TOP * 2 + textSziePx, textPaint);
        canvas.drawText("100M", viewWidth - VIEW_PADDING_LEFT_RIGHT, ImageHeight + LINE_HEIGHT + TEXT_MARGIN_TOP * 2 + textSziePx, textPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                double currentX = event.getX();
                if (currentX < VIEW_PADDING_LEFT_RIGHT) {
                    currentX = VIEW_PADDING_LEFT_RIGHT;
                }
                if (currentX > viewWidth + VIEW_PADDING_LEFT_RIGHT) {
                    currentX = viewWidth + VIEW_PADDING_LEFT_RIGHT;
                }
                currentProgress = (int) (((currentX - VIEW_PADDING_LEFT_RIGHT) / viewWidth) * 100);
                invalidate();
                return true;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    public void bindView(TextView textView) {
        this.textView = textView;
    }


    public void setCurrentProgress(int currentProgress) {
        if (currentProgress < 0) {
            currentProgress = 0;
        }
        if (currentProgress > 100) {
            currentProgress = 100;
        }
        this.currentProgress = currentProgress;
        invalidate();
    }

}
