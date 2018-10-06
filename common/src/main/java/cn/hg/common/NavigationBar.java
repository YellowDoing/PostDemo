package cn.hg.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 黄干 on 2017/11/15.
 */

public class NavigationBar extends RelativeLayout {

    private TextView mTitle, mRightTitle;
    private ImageView mBackImg;
    private ImageView mMoreImg;
    private View mBottomLine;

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_navigation, this, true);

        mTitle = findViewById(R.id.title);
        mRightTitle = findViewById(R.id.right_title);
        mBackImg = findViewById(R.id.back);
        mMoreImg = findViewById(R.id.more);
        mBottomLine = findViewById(R.id.bottom_line);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NavigationBar, 0, 0);
        //获取控件属性的值
        try {
            String title = a.getString(R.styleable.NavigationBar_title);
            String rightTitle = a.getString(R.styleable.NavigationBar_rightTitle);
            int resId = a.getResourceId(R.styleable.NavigationBar_backImg, R.mipmap.back);
            int rightImgId = a.getResourceId(R.styleable.NavigationBar_rightImg, -1);
            int titleColor = a.getColor(R.styleable.NavigationBar_titleColor, Color.BLACK);
            int rightColor = a.getColor(R.styleable.NavigationBar_rightColor, 0);
            boolean isBottomLine = a.getBoolean(R.styleable.NavigationBar_bottomLine, true);

            setRightTitle(rightTitle);
            if (rightColor != 0) mRightTitle.setTextColor(rightColor);
            mTitle.setTextColor(titleColor);
            setTitle(title);
            mBackImg.setBackgroundResource(resId);
            mBottomLine.setVisibility(isBottomLine ? VISIBLE : GONE);
            if (rightImgId != -1) {
                mRightTitle.setVisibility(GONE);
                mMoreImg.setVisibility(VISIBLE);
                mMoreImg.setImageResource(rightImgId);
            }

        } finally {
            a.recycle();
        }
        findViewById(R.id.back_container).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }


    public void setRightTitle(String title) {
        mRightTitle.setText(title);
    }

    public void setRightTitleClick(OnClickListener listener) {
        mRightTitle.setOnClickListener(listener);
        mMoreImg.setOnClickListener(listener);
    }

    public TextView getRightTitle() {
        return mRightTitle;
    }

    public String getTitle() {
        return mTitle.getText().toString();
    }
}