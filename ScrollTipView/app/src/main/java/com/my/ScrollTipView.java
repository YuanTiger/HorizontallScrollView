package com.my;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  2016/11/25/上午11:31
 * DESC:
 */

public class ScrollTipView<T extends TextView> extends View {

    private Paint linePaint;//线的画笔
    private Paint textPaint;//文字画笔


    private int viewWidth;//整个控件宽度
    private int ImageHeight = 60;//游标的高度
    private int LINE_HEIGHT = 5;//线的宽度
    private int textSziePx = 44;//字体大小
    private int VIEW_PADDING_LEFT_RIGHT = 50;//控件左右边距
    private int TEXT_MARGIN_TOP = 10;//文字与线之间的间隔
    private Bitmap bitmap;

    private int currentProgress = 0;


    private T textView;


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
        textPaint = new Paint();
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(textSziePx);

        //压缩图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.icon_arrow, options);
        //宽度 = 高度的0.775倍，根据宽度计算出合适的压缩比例
        options.inSampleSize = calculateInSampleSize(options, (int) (ImageHeight * 0.775));
        options.inJustDecodeBounds = false;
        ImageHeight = options.outHeight;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_arrow, options);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float lastI = 0;
        //画线条-渐变
        for (float i = 0.1f; i <= 1.02f; i += 0.05f) {
            //改变画笔颜色
            if (i <= 0.7f) {
                linePaint.setColor(Color.argb(255, (int) (255 * (1f - i + 0.1f)), (int) (255 * i + 0.1f), 0));
            } else {
                linePaint.setColor(Color.argb(255, 32, 233, 22));
            }


            /**
             * 参数1：左上坐标点x轴
             * 参数2：左上坐标点的y轴
             * 参数3：右下坐标点x轴
             * 参数4：右下坐标点y轴
             * 参数5：画笔
             */
            canvas.drawRect(viewWidth * lastI + VIEW_PADDING_LEFT_RIGHT, ImageHeight, viewWidth * i + VIEW_PADDING_LEFT_RIGHT, ImageHeight + LINE_HEIGHT, linePaint);
            lastI = i;
        }
        /**
         * 画游标
         * 参数1：圆心x轴坐标
         * 参数2：圆心y轴坐标
         * 参数3：半径
         * 参数4：画笔
         */
        canvas.drawBitmap(bitmap, viewWidth * ((float) currentProgress / 100) + VIEW_PADDING_LEFT_RIGHT / 2, ImageHeight / 2, new Paint());
        if (textView != null) {
            textView.setText(currentProgress + "");
        }
        /**
         * 画指示文字
         * 参数1：文字内容
         * 参数2：左上x轴坐标
         * 参数3：左上y轴坐标
         * 参数4：画笔
         */
        canvas.drawText("0%", VIEW_PADDING_LEFT_RIGHT, ImageHeight + LINE_HEIGHT + TEXT_MARGIN_TOP * 2 + textSziePx, textPaint);
        canvas.drawText("70%", viewWidth * 7 / 10, ImageHeight + LINE_HEIGHT + TEXT_MARGIN_TOP * 2 + textSziePx, textPaint);
        canvas.drawText("100%", viewWidth - VIEW_PADDING_LEFT_RIGHT, ImageHeight + LINE_HEIGHT + TEXT_MARGIN_TOP * 2 + textSziePx, textPaint);
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
            case MotionEvent.ACTION_UP:
                Toast.makeText(this.getContext(), "TODO：" + currentProgress, Toast.LENGTH_SHORT).show();
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    public void bindView(T view) {
        this.textView = view;
    }


    public void setCurrentProgress(int currentProgress) {
        if (currentProgress < 0) {
            currentProgress = 0;
        }
        if (currentProgress > 100) {
            currentProgress = 100;
        }
        this.currentProgress = currentProgress;
        Toast.makeText(this.getContext(), "TODO：" + currentProgress, Toast.LENGTH_SHORT).show();
        invalidate();
    }


    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * 根据预期宽度计算出图片的压缩比例
     * @param options
     * @param reqWidth  预期宽度
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth) {
        // 源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            // 计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;

    }
}
