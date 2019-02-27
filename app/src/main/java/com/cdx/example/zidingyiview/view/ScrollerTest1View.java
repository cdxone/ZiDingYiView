package com.cdx.example.zidingyiview.view;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 手指按下-滑动-抬起，手指继续滑动效果
 */
public class ScrollerTest1View extends ViewGroup {

    private static final String TAG = "MyView2";

    private RectF mChildeRectF;
    private VelocityTracker velocityTracker;//速度追踪
    private Scroller mScroller;
    private int mMaxFlintVelocity, mMinFlintVelocity;//获得允许fling的最大速度和最小速度。
    private int mChildMeasuredWidth,mChildMeasuredHeight;
    private View chileView;

    public ScrollerTest1View(Context context) {
        this(context, null);
    }

    public ScrollerTest1View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerTest1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        //构造一个矩形
        mChildeRectF = new RectF();
        //弹性滑动的类
        mScroller = new Scroller(context, null, true);
        //View配置的类
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMaxFlintVelocity = viewConfiguration.getScaledMaximumFlingVelocity();//24000
        mMinFlintVelocity = viewConfiguration.getScaledMinimumFlingVelocity();//150
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量所有的子View尺寸，将measure过程交到子View的内部。
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //必须在onLayout中去测量宽度和高度,因为onMeasure会执行多次。
        Log.e(TAG,"onLayout");
        chileView = getChildAt(0);
        //测量控件的宽高。
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //测量子孩子的高度。
        mChildMeasuredWidth = chileView.getMeasuredWidth();
        mChildMeasuredHeight = chileView.getMeasuredHeight();
        //限定了矩阵的位置，在中心点的地方。
        mChildeRectF.set(measuredWidth / 2 - mChildMeasuredWidth / 2, measuredHeight / 2 - mChildMeasuredHeight / 2, measuredWidth / 2 + mChildMeasuredWidth / 2, measuredHeight / 2 + mChildMeasuredHeight / 2);
        //给View设置位置。
        chileView.layout(measuredWidth / 2 - mChildMeasuredWidth / 2, measuredHeight / 2 - mChildMeasuredHeight / 2, measuredWidth / 2 + mChildMeasuredWidth / 2, measuredHeight / 2 + mChildMeasuredHeight / 2);
        Log.e(TAG,"mScroller.getCurrX():"+mScroller.getCurrX());
        Log.e(TAG,"mScroller.getCurrY():"+mScroller.getCurrY());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获得速度追踪
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        //将移动的事件加入到速度获取器中。
        velocityTracker.addMovement(event);
        //判断事件
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"手指抬起");
                //计算当前速度,计算水平的速度和竖直的速度。
                velocityTracker.computeCurrentVelocity(1000, mMaxFlintVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();
                Log.e(TAG,"xVelocity:"+xVelocity);
                Log.e(TAG,"yVelocity:"+yVelocity);
                //获得水平的偏移量
                int scrollX = getScrollX();
                //获得竖直的偏移量
                int scrollY = getScrollY();
                Log.e(TAG,"scrollX:"+scrollX+" scrollY:"+scrollY);
                Log.e(TAG,"mMinFlintVelocity=" + mMinFlintVelocity + ",xVelocity=" + xVelocity + ",yVelocity=" + yVelocity);
                if (Math.abs(xVelocity) > mMinFlintVelocity && Math.abs(yVelocity) > mMinFlintVelocity) {
                    /**
                     * 根据滑动的手势，开始滑动，滑动的距离根据初始的速度。
                     * 参数1：滑动开始点的x坐标。
                     * 参数2：滑动开始点的y坐标。
                     * 参数3：当滑动屏幕时X方向初速度，以每秒像素数计算。
                     * 参数4：当滑动屏幕时Y方向初速度，以每秒像素数计算。
                     * 参数5：X方向的最小值，Scroller在水平方向滑动的最小值。
                     * 参数6：X方向的最大值，Scroller在水平方向滑动的最大值。
                     * 参数7：Y方向的最小值，Scroller在y方法滑动的最小值。
                     * 参数8：Y方向的最大值，Scroller在y方法滑动的最大值。
                     */
                    Log.e("Fling", "手指抬起");
                    mScroller.fling(scrollX, scrollY, -xVelocity, -yVelocity,
                            -500,
                            500,
                            -500,
                            500);

                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.e("Fling","computeScroll");
        if (mScroller.computeScrollOffset()) {
            Log.e(TAG,"computeScroll:"+mScroller.getCurrX()+","+mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
