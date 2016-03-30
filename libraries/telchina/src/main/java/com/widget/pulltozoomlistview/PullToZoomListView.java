package com.widget.pulltozoomlistview;

import com.telchina.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 下拉放大Header
 * @author GISirFive
 *
 */
public class PullToZoomListView extends ListView implements
		AbsListView.OnScrollListener {
	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float paramAnonymousFloat) {
			float f = paramAnonymousFloat - 1.0F;
			return 1.0F + f * (f * (f * (f * f)));
		}
	};
	int mActivePointerId = -1;
	private FrameLayout mHeaderContainer;
	private int mHeaderHeight;
	private ImageView mHeaderImage;
	float mLastMotionY = -1.0F;
	float mLastScale = -1.0F;
	float mMaxScale = -1.0F;
	private AbsListView.OnScrollListener mOnScrollListener;
	private ScalingRunnalable mScalingRunnalable;
	private int mScreenHeight;
	private ImageView mShadow;
	private IDropHeaderListener dropListener;

	public PullToZoomListView(Context paramContext) {
		super(paramContext);
		init(paramContext, null);
	}

	public PullToZoomListView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext, paramAttributeSet);
	}

	public PullToZoomListView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init(paramContext, paramAttributeSet);
	}

	private void endScraling() {
		if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight)
		this.mScalingRunnalable.startAnimation(200L);
	}
	
	/**
	 * 初始化控件
	 * @param context
	 * @param attrs
	 */
	private void init(Context context, AttributeSet attrs) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		this.mScreenHeight = localDisplayMetrics.heightPixels;
		this.mHeaderContainer = new FrameLayout(context);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToZoomListView);
		int resId = a.getResourceId(R.styleable.PullToZoomListView_headerlayout, 0);
		if(resId != 0)
			initHeader(context, attrs, resId);
		else
			initDefaultHeader(context, attrs);
	
		this.mScalingRunnalable = new ScalingRunnalable();
		super.setOnScrollListener(this);
	}

	/**
	 * 初始化默认的Header
	 * @param context
	 * @param attrs
	 */
	private void initDefaultHeader(Context context, AttributeSet attrs){
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToZoomListView);

		//宽度
		int widthPixels = localDisplayMetrics.widthPixels;
		//高度
		int heightPixels = a.getDimensionPixelSize
				(R.styleable.PullToZoomListView_headerheight, 0);
		//未获取到Header高度，使用16：9的屏幕尺寸自动计算
		if(heightPixels == 0){
			heightPixels = (int) (9.0F * (localDisplayMetrics.widthPixels / 16.0F));
		}
		
		//设置Header宽高
		setHeaderViewSize(widthPixels, heightPixels);
		
		mHeaderImage = new ImageView(context);
		
		//初始化阴影
		this.mShadow = new ImageView(context);
		FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -2);
		localLayoutParams.gravity = 80;
		this.mShadow.setLayoutParams(localLayoutParams);
		
		//添加Header和阴影
		this.mHeaderContainer.addView(mHeaderImage);
		this.mHeaderContainer.addView(this.mShadow);
		addHeaderView(this.mHeaderContainer);
	}
	
	/**
	 * 初始化Header
	 * @param paramContext
	 * @param paramAttributeSet
	 */
	private void initHeader(Context context, AttributeSet attrs, int layoutId) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		
		View header = inflate(context, layoutId, null);
		if(header == null)//非布局资源
			return;
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToZoomListView);
		
		//宽度
		int widthPixels = localDisplayMetrics.widthPixels;
		//高度
		int heightPixels = a.getDimensionPixelSize
				(R.styleable.PullToZoomListView_headerheight, 0);
		//未获取到Header高度，使用16：9的屏幕尺寸自动计算
		if(heightPixels == 0){
			heightPixels = (int) (9.0F * (localDisplayMetrics.widthPixels / 16.0F));
		}
		
		//设置Header宽高
		setHeaderViewSize(widthPixels, heightPixels);

		//设置可缩放的ImageView
		mHeaderImage = getHeaderImageView(header.getRootView());
		//布局文件中无可缩放的ImageView，则自定义布局
		if(mHeaderImage == null){
			mHeaderImage = new ImageView(context);
		}
		//初始化阴影
		this.mShadow = new ImageView(context);
		FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -2);
		localLayoutParams.gravity = 80;
		this.mShadow.setLayoutParams(localLayoutParams);
		
		//添加Header和阴影
		this.mHeaderContainer.addView(header);
		this.mHeaderContainer.addView(this.mShadow);
		addHeaderView(this.mHeaderContainer);
	}
	
	/**
	 * 获取Header中可缩放的第一个ImageView
	 * @param v
	 */
	private ImageView getHeaderImageView(View v){
		if(v instanceof ImageView)
			return (ImageView)v;
		if(!(v instanceof ViewGroup))
			return null;
		
		ViewGroup group = (ViewGroup)v;

		for (int i = 0; i < group.getChildCount(); i++) {
			View child = group.getChildAt(i);
			ImageView result = getHeaderImageView(child);
			if(result == null)
				continue;
			else
				return result;
		}
		return null;
	}
	
	private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
		int i = (paramMotionEvent.getAction()) >> 8;
		if (paramMotionEvent.getPointerId(i) == this.mActivePointerId){
			if (i != 0) {
				int j = 1;
				this.mLastMotionY = paramMotionEvent.getY(0);
				this.mActivePointerId = paramMotionEvent.getPointerId(0);
				return;
			}
		}
	}

	private void reset() {
		this.mActivePointerId = -1;
		this.mLastMotionY = -1.0F;
		this.mMaxScale = -1.0F;
		this.mLastScale = -1.0F;
	}

	/** 下拉幅度是否可以出发下拉刷新事件 */
	private void checkDropToRefresh(){
		if(dropListener == null)
			return;
		if(mLastScale > 1.28f)
			dropListener.onRefresh();
	}
	
	/**
	 * 获取Header */
	public View getHeaderView() {
		return this.mHeaderContainer;
	}
	
	/** 获取header中的ImageView */
	public ImageView getHeaderImageView(){
		return this.mHeaderImage;
	}

	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
			int paramInt3, int paramInt4) {
		super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
		if (this.mHeaderHeight == 0)
			this.mHeaderHeight = this.mHeaderContainer.getHeight();
	}

	@Override
	public void onScroll(AbsListView paramAbsListView, int paramInt1,
			int paramInt2, int paramInt3) {
		float f = this.mHeaderHeight - this.mHeaderContainer.getBottom();
		if ((f > 0.0F) && (f < this.mHeaderHeight)) {
			int i = (int) (0.65D * f);
			this.mHeaderImage.scrollTo(0, -i);
		} else if (this.mHeaderImage.getScrollY() != 0) {
			this.mHeaderImage.scrollTo(0, 0);
		}
		if (this.mOnScrollListener != null) {
			this.mOnScrollListener.onScroll(paramAbsListView, paramInt1,
					paramInt2, paramInt3);
		}
		if(this.dropListener != null){
			this.dropListener.onHeaderHeightChanged(f, this.mHeaderHeight);
		}
	}

	public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
		if (this.mOnScrollListener != null)
			this.mOnScrollListener.onScrollStateChanged(paramAbsListView,
					paramInt);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {  
        switch (paramMotionEvent.getAction() & MotionEvent.ACTION_MASK) {  
        case MotionEvent.ACTION_DOWN:  
  
            this.mActivePointerId = paramMotionEvent.getPointerId(0);  
            this.mMaxScale = (this.mScreenHeight / this.mHeaderHeight);  
            break;  
  
        case MotionEvent.ACTION_UP:  
            reset();  
            break;  
  
        case MotionEvent.ACTION_POINTER_DOWN:  
            this.mActivePointerId = paramMotionEvent  
                    .getPointerId(paramMotionEvent.getActionIndex());  
            break;  
  
        case MotionEvent.ACTION_POINTER_UP:  
            onSecondaryPointerUp(paramMotionEvent);  
            break;  
        }  
        return super.onInterceptTouchEvent(paramMotionEvent);  
    }  
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (0xFF & event.getAction()) {
			case MotionEvent.ACTION_OUTSIDE:
			case MotionEvent.ACTION_DOWN:
				if (!this.mScalingRunnalable.mIsFinished) {
					this.mScalingRunnalable.abortAnimation();
				}
				this.mLastMotionY = event.getY();
				this.mActivePointerId = event.getPointerId(0);
				this.mMaxScale = (this.mScreenHeight / this.mHeaderHeight);
				this.mLastScale = (this.mHeaderContainer.getBottom() / this.mHeaderHeight);
				break;
			case MotionEvent.ACTION_MOVE:
				int j = event.findPointerIndex(this.mActivePointerId);
				if (j == -1) 
					break;
				if (this.mLastMotionY == -1.0F)
					this.mLastMotionY = event.getY(j);
				if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight) {
					ViewGroup.LayoutParams localLayoutParams = this.mHeaderContainer
							.getLayoutParams();
					float f = ((event.getY(j) - this.mLastMotionY + this.mHeaderContainer
							.getBottom()) / this.mHeaderHeight - this.mLastScale)
							/ 2.0F + this.mLastScale;
					if ((this.mLastScale <= 1.0D) && (f < this.mLastScale)) {
						localLayoutParams.height = this.mHeaderHeight;
						this.mHeaderContainer
						.setLayoutParams(localLayoutParams);
					}
					this.mLastScale = Math.min(Math.max(f, 1.0F),
							this.mMaxScale);
					localLayoutParams.height = ((int) (this.mHeaderHeight * this.mLastScale));
					if (localLayoutParams.height < this.mScreenHeight)
						this.mHeaderContainer.setLayoutParams(localLayoutParams);
					this.mLastMotionY = event.getY(j);
				}
				break;
			case MotionEvent.ACTION_UP:
				checkDropToRefresh();
				reset();
				endScraling();
				break;
			case MotionEvent.ACTION_CANCEL:  
				
	            break;  
	        case MotionEvent.ACTION_POINTER_DOWN:  
	            int i = event.getActionIndex();  
	            this.mLastMotionY = event.getY(i);  
	            this.mActivePointerId = event.getPointerId(i);  
	            break;  
	        case MotionEvent.ACTION_POINTER_UP:  
	            onSecondaryPointerUp(event);  
	            this.mLastMotionY = event.getY(event.findPointerIndex(this.mActivePointerId));  
	            break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 设置Header大小
	 * @param width
	 * @param height
	 */
	public void setHeaderViewSize(int width, int height) {
		Object localObject = this.mHeaderContainer.getLayoutParams();
		if (localObject == null)
			localObject = new AbsListView.LayoutParams(width, height);
		((ViewGroup.LayoutParams) localObject).width = width;
		((ViewGroup.LayoutParams) localObject).height = height;
		this.mHeaderContainer
				.setLayoutParams((ViewGroup.LayoutParams) localObject);
		this.mHeaderHeight = height;
	}

	public void setOnScrollListener(
			AbsListView.OnScrollListener paramOnScrollListener) {
		this.mOnScrollListener = paramOnScrollListener;
	}

	public void setShadow(int paramInt) {
		this.mShadow.setBackgroundResource(paramInt);
	}

	/**
	 * 设置下拉刷新监听
	 * @param listener
	 */
	public void setOnDropHeaderListener(IDropHeaderListener listener){
		this.dropListener = listener;
	}
	
	class ScalingRunnalable implements Runnable {
		long mDuration;
		boolean mIsFinished = true;
		float mScale;
		long mStartTime;

		ScalingRunnalable() {
		}

		public void abortAnimation() {
			this.mIsFinished = true;
		}

		public boolean isFinished() {
			return this.mIsFinished;
		}

		public void run() {
			float f2;
			ViewGroup.LayoutParams localLayoutParams;
			if ((!this.mIsFinished) && (this.mScale > 1.0D)) {
				float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) this.mStartTime)
						/ (float) this.mDuration;
				f2 = this.mScale - (this.mScale - 1.0F)
						* PullToZoomListView.sInterpolator.getInterpolation(f1);
				localLayoutParams = PullToZoomListView.this.mHeaderContainer
						.getLayoutParams();
				if (f2 > 1.0F) {
					localLayoutParams.height = PullToZoomListView.this.mHeaderHeight;
					;
					localLayoutParams.height = ((int) (f2 * PullToZoomListView.this.mHeaderHeight));
					PullToZoomListView.this.mHeaderContainer
							.setLayoutParams(localLayoutParams);
					PullToZoomListView.this.post(this);
					return;
				}
				this.mIsFinished = true;
			}
		}

		public void startAnimation(long paramLong) {
			this.mStartTime = SystemClock.currentThreadTimeMillis();
			this.mDuration = paramLong;
			this.mScale = ((float) (PullToZoomListView.this.mHeaderContainer
					.getBottom()) / PullToZoomListView.this.mHeaderHeight);
			this.mIsFinished = false;
			PullToZoomListView.this.post(this);
		}
	}

	/** 下拉头部监听 */
	public interface IDropHeaderListener{
		/** 下拉刷新 */
		void onRefresh();
		
		/**
		 * 头部大小发生变化
		 * @param currentHeight 头部当前高度
		 * @param totalHeight 头部原始高度
		 */
		void onHeaderHeightChanged(float currentHeight, int totalHeight);
	}
	
}
