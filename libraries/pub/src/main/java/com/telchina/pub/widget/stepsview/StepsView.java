package com.telchina.pub.widget.stepsview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.telchina.pub.R;

import java.util.List;

/***
 * 流程进度显示控件，可以自定义流程节点数、设置当前进度和控件样式
 * @author ZH
 *
 */
public class StepsView extends LinearLayout implements StepsViewIndicator.OnDrawListener {

	private static final int DEFAULT_PROGRESS_COLOR_INDICATOR = Color.BLUE;
	private static final int DEFAULT_LABEL_COLOR_INDICATOR =  Color.BLACK;
	private static final int DEFAULT_BAR_COLOR_INDICATOR = Color.BLACK;
	private static final int DEFAULT_LABLE_SIZE_INDICATOR =  14;
	private static final int DEFAULT_LINE_HEIGHT = 2;
	private static final int DEFAULT_THUMB_RADIUS = 20;
	private static final int DEFAULT_CIRCLE_RADIUS = 10;
	private static final int DEFAULT_PADDING = 50;
	
    private StepsViewIndicator mStepsViewIndicator;
    private FrameLayout mLabelsLayout;
    private String[] mLabels;
    
    /**进度颜色<br>默认：Color.YELLOW*/
    private int mProgressColorIndicator;
    
    /**标签颜色<br>默认：Color.BLACK*/
    private int mLabelColorIndicator;
    
    /**状态条颜色<br>默认：Color.BLACK*/
    private int mBarColorIndicator;
    
    /**完成个数<br>默认：0*/
    private int mCompletedPosition = 0;
    
    /** 未完成标签字体大小<br>默认:14**/
    private float mLableSizeIndicator;
    
    /** 当前进度标签字体大小<br>默认:14*/
    private float mProgressLableSizeIndicator;
    
    /** 进度线宽度<br>默认：2*/
    private float mLineHeight;
    
    /** 指示点半径<br>默认：20*/
    private float mThumbRadius;
    
    /** 阴影宽度<br>默认：10*/
    private float mCircleRadius;
    
    /** 左右边距<br> 默认：50*/
    private float mPadding;

    public StepsView(Context context) {
        this(context, null);
    }

    public StepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsView(Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs,int defStyleAttr) {
    	TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StepsViewIndicator, defStyleAttr, 0);
    	mProgressColorIndicator = a.getColor(R.styleable.StepsViewIndicator_sv_progress_color_indicator, DEFAULT_PROGRESS_COLOR_INDICATOR);
    	mLabelColorIndicator = a.getColor(R.styleable.StepsViewIndicator_sv_label_color_indicator, DEFAULT_LABEL_COLOR_INDICATOR);
    	mBarColorIndicator = a.getColor(R.styleable.StepsViewIndicator_sv_bar_color_indicator, DEFAULT_BAR_COLOR_INDICATOR);
    	mLableSizeIndicator = a.getDimensionPixelSize(R.styleable.StepsViewIndicator_sv_lable_size_indicator, DEFAULT_LABLE_SIZE_INDICATOR);
    	mProgressLableSizeIndicator = a.getDimensionPixelSize(R.styleable.StepsViewIndicator_sv_progress_lable_size_indicator,DEFAULT_LABLE_SIZE_INDICATOR);

    	mLineHeight = a.getDimensionPixelSize(R.styleable.StepsViewIndicator_sv_line_height,DEFAULT_LINE_HEIGHT);
    	mThumbRadius = a.getDimensionPixelSize(R.styleable.StepsViewIndicator_sv_thumb_radius, DEFAULT_THUMB_RADIUS);
    	mCircleRadius = a.getDimensionPixelSize(R.styleable.StepsViewIndicator_sv_circle_radius,DEFAULT_CIRCLE_RADIUS);
    	mPadding = a.getDimensionPixelSize(R.styleable.StepsViewIndicator_sv_padding, DEFAULT_PADDING);
    	
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_steps_view, this);
        mStepsViewIndicator = (StepsViewIndicator) rootView.findViewById(R.id.steps_indicator_view);
        mStepsViewIndicator.setLineHeight(mLineHeight)
					       .setThumbRadius(mThumbRadius)
					       .setCircleRadius(mCircleRadius)
					       .setLRPadding(mPadding)
					       .invalidate();
        mStepsViewIndicator.setDrawListener(this);
        mLabelsLayout = (FrameLayout) rootView.findViewById(R.id.labels_container);
        setProgressColorIndicator(mProgressColorIndicator);
        setLabelColorIndicator(mLabelColorIndicator);
        setBarColorIndicator(mBarColorIndicator);
        
    }

    public String[] getLabels() {
        return mLabels;
    }

    public StepsView setLabels(String[] labels) {
        mLabels = labels;
        mStepsViewIndicator.setStepSize(labels.length);
        return this;
    }

    public int getProgressColorIndicator() {
        return mProgressColorIndicator;
    }

    public StepsView setProgressColorIndicator(int progressColorIndicator) {
        mProgressColorIndicator = progressColorIndicator;
        mStepsViewIndicator.setProgressColor(mProgressColorIndicator);
        return this;
    }

    public int getLabelColorIndicator() {
        return mLabelColorIndicator;
    }

    public StepsView setLabelColorIndicator(int labelColorIndicator) {
        mLabelColorIndicator = labelColorIndicator;
        return this;
    }

    public int getBarColorIndicator() {
        return mBarColorIndicator;
    }

    public StepsView setBarColorIndicator(int barColorIndicator) {
        mBarColorIndicator = barColorIndicator;
        mStepsViewIndicator.setBarColor(mBarColorIndicator);
        return this;
    }

    public int getCompletedPosition() {
        return mCompletedPosition;
    }

    public StepsView setCompletedPosition(int completedPosition) {
        mCompletedPosition = completedPosition;
        mStepsViewIndicator.setCompletedPosition(mCompletedPosition);
        return this;
    }

    public void drawView() {
        if (mLabels == null) {
            throw new IllegalArgumentException("labels must not be null.");
        }

        if (mCompletedPosition < 0 || mCompletedPosition > mLabels.length - 1) {
            throw new IndexOutOfBoundsException(String.format("Index : %s, Size : %s", mCompletedPosition, mLabels.length));
        }

        mStepsViewIndicator.invalidate();
    }

    @Override
    public void onReady() {
        drawLabels();
    }

    private void drawLabels() {
        List<Float> indicatorPosition = mStepsViewIndicator.getThumbContainerXPosition();

        if (mLabels != null) {
            for (int i = 0; i < mLabels.length; i++) {
                TextView textView = new TextView(getContext());
                textView.setText(mLabels[i]);
                textView.setTextColor(mLabelColorIndicator);
                textView.setTextSize(mLableSizeIndicator);
                textView.setX(indicatorPosition.get(i));
                textView.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));

                if (i == mCompletedPosition) {
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextColor(mProgressColorIndicator);
                    textView.setTextSize(mProgressLableSizeIndicator);
                }

                mLabelsLayout.addView(textView);
            }
        }
    }
}
