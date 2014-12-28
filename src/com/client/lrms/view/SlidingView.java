
package com.client.lrms.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlidingView extends ViewGroup {
    /**
     * FrameLayout对象
     */
    private FrameLayout mContainer;

    private Scroller mScroller;

    /**
     * 最小滑动距离
     */
    private int mTouchSlop;

    /**
     * 最后滑动的位置
     */
    private float mLastMotionX;

    /**
     * 左边的菜单
     */
    private View mMenuView;

    /**
     * 右边的菜单
     */
    private View mDetailView;

    /**
     * 如果超过滑动的偏移量了才开始滑动如果想 滑动一丁点就开始移动可以将其设为true
     */
    private boolean start = false;

    public SlidingView(Context context) {
        super(context);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContainer.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = r - l;
        final int height = b - t;
        mContainer.layout(0, 0, width, height);
    }

    private void init() {
        mContainer = new FrameLayout(getContext());
        mContainer.setBackgroundColor(0xff000000);
        mScroller = new Scroller(getContext());
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        super.addView(mContainer);
    }

    public void setView(View v) {
        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
        }
        mContainer.addView(v);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        postInvalidate();
    }

    /**
     * 在父控件调用dispatchDraw的时候dispatchDraw里面有一个drawChild方法，在drawChild方法里面有child.
     * computescroll方法
     */
    @Override
    public void computeScroll() {
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();
                if (oldX != x || oldY != y) {
                    scrollTo(x, y);
                }
                // Keep on drawing until the animation has finished.
                invalidate();
            } else {
                clearChildrenCache();
            }
        } else {
            clearChildrenCache();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        final float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                // protected int mScrollX;该视图内容相对于视图起始坐标的偏移量 ， X轴 方向

                // getScrollX获得mscrollX,往右为负
                if (getScrollX() == -getMenuViewWidth() && mLastMotionX < getMenuViewWidth()) {
                    return false;
                }
                if (getScrollX() == getDetailViewWidth()
                        && mLastMotionX > getWidth() - getDetailViewWidth()) {
                    return false;
                }
                start = true;
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动时x轴距离按下点的偏移量
                if (start) {
                    final float xDiff = Math.abs(x - mLastMotionX);
                    if (xDiff > mTouchSlop) {
                        start = false;
                    }
                    if (start) {
                        break;
                    }
                }
                enableChildrenCache();
                // 每次滑动的距离---->实时变化的
                final float deltaX = mLastMotionX - x;
                mLastMotionX = x;
                float oldScrollXf = getScrollX();
                float scrollX = oldScrollXf + deltaX;

                if (deltaX < 0 && oldScrollXf < 0) { // left view
                    final float leftBound = 0;
                    final float rightBound = -getMenuViewWidth();
                    if (scrollX > leftBound) {
                        scrollX = leftBound;
                    } else if (scrollX < rightBound) {
                        scrollX = rightBound;
                    }
                } else if (deltaX > 0 && oldScrollXf > 0) { // right view
                    final float rightBound = getDetailViewWidth();
                    final float leftBound = 0;
                    if (scrollX < leftBound) {
                        scrollX = leftBound;
                    } else if (scrollX > rightBound) {
                        scrollX = rightBound;
                    }
                }

                scrollTo((int) scrollX, getScrollY());

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (start) {
                    break;
                }
                int oldScrollX = getScrollX();
                int dx = 0;
                if (oldScrollX < 0) {
                    if (oldScrollX < -getMenuViewWidth() / 2) {
                        dx = -getMenuViewWidth() - oldScrollX;
                    } else if (oldScrollX >= -getMenuViewWidth() / 2) {
                        dx = -oldScrollX;
                    }
                } else {
                    if (oldScrollX > getDetailViewWidth() / 2) {
                        dx = getDetailViewWidth() - oldScrollX;
                    } else if (oldScrollX <= getDetailViewWidth() / 2) {
                        dx = -oldScrollX;
                    }
                }

                smoothScrollTo(dx);
                clearChildrenCache();
                break;
        }
        return true;
    }

    /**
     * 获得左菜单宽度
     * 
     * @return
     */
    private int getMenuViewWidth() {
        if (mMenuView == null) {
            return 0;
        }
        return mMenuView.getWidth();
    }

    /**
     * 获得右菜单的宽度
     * 
     * @return
     */
    private int getDetailViewWidth() {
        if (mDetailView == null) {
            return 0;
        }
        return mDetailView.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public View getDetailView() {
        return mDetailView;
    }

    public void setDetailView(View mDetailView) {
        this.mDetailView = mDetailView;
    }

    public View getMenuView() {
        return mMenuView;
    }

    public void setMenuView(View mMenuView) {
        this.mMenuView = mMenuView;
    }

    public void showLeftView() {
        int menuWidth = mMenuView.getWidth();
        int oldScrollX = getScrollX();
        if (oldScrollX == 0) {
            smoothScrollTo(-menuWidth);
        } else if (oldScrollX == -menuWidth) {
            smoothScrollTo(menuWidth);
        }
    }

    public void showRightView() {
        int menuWidth = mDetailView.getWidth();
        int oldScrollX = getScrollX();
        if (oldScrollX == 0) {
            smoothScrollTo(menuWidth);
        } else if (oldScrollX == menuWidth) {
            smoothScrollTo(-menuWidth);
        }
    }

    void smoothScrollTo(int dx) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, getScrollY(), 500);
        invalidate();
    }

    void enableChildrenCache() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View layout = (View) getChildAt(i);
            layout.setDrawingCacheEnabled(true);
        }
    }

    void clearChildrenCache() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View layout = (View) getChildAt(i);
            layout.setDrawingCacheEnabled(false);
        }
    }

}
