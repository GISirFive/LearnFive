package com.telchina.image.imagecliper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

public class ClipImageBorderView extends View {
	/**
	 * 边框的宽度 单位dp
	 */
	private int mBorderWidth = 2;

	private Paint mPaint;
	// 圆所占屏幕宽度的比例
	private static final float SCALE = 0.618f;
	// 半径
	public static int mRadius = 0;
	// 圆心坐标
	public static int mCircleCenterX, mCircleCenterY;

	public ClipImageBorderView(Context context) {
		this(context, null);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(localDisplayMetrics);
		int mScreenWidth = localDisplayMetrics.widthPixels;
		int mScreenHeight = localDisplayMetrics.heightPixels;

		mRadius = (int) (mScreenWidth * SCALE) / 2;
		mCircleCenterX = mScreenWidth / 2;
		mCircleCenterY = mScreenHeight / 2;

		mBorderWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
						.getDisplayMetrics());
		mPaint = new Paint();
		mPaint.setAntiAlias(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.save();
		Path path = new Path();
		Paint borderPaint = new Paint();
		borderPaint.setAntiAlias(true);
		borderPaint.setColor(Color.argb(255, 255, 255, 255));
		borderPaint.setStrokeWidth(mBorderWidth);
		borderPaint.setStyle(Style.STROKE);
		Rect viewDrawingRect = new Rect(0, 0, getWidth(), getHeight());
		// 添加一个圆形
		path.addCircle(mCircleCenterX, mCircleCenterY, mRadius, Direction.CW);

		Paint outsidePaint = new Paint();
		outsidePaint.setAntiAlias(true);
		outsidePaint.setARGB(128, 50, 50, 50);
		// 裁剪画布，path之外的区域，以outsidePaint填充
		canvas.clipPath(path, Region.Op.DIFFERENCE);
		canvas.drawRect(viewDrawingRect, outsidePaint);
		canvas.restore();
		// 绘制圆上高亮线，这里outlinePaint定义的Paint.Style.STROKE：表示只绘制几何图形的轮廓。
		canvas.drawPath(path, borderPaint);
	}

}
