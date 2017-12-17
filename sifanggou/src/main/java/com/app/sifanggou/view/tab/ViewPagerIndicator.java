package com.app.sifanggou.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sifanggou.R;
import com.app.sifanggou.listener.PageSelectListener;

import java.util.List;

/**
 * Created by master on 2016/3/17.
 *顶部tab标签控件
 */
public class ViewPagerIndicator extends LinearLayout{
    private Paint mPaint;//画笔
    private Path mPath;//点连接路径
    private float initTrangleX;//初始化三角形的X轴坐标
    private float mTrangleX;//移动中的三角形X轴坐标
    private int mTrangleWidth;//三角形底边宽度
    private int mTrangleHeight;//三角形的高
    private static final float RADIO_TRANGLE_WIDTH=1/8f;

    private int mTabVisibleCount;//可见的Tab标签个数
    private static final int Count_DeFAULT_TAB=2;//  默认可见的标签数量

    private List<String> mTabTitles;//初始化用的title集
    private Context mContext;
    private static final int COLOR_TEXT_NORMAL= R.color.black;
    private static final int COLOR_TEXT_ACTIVY= R.color.color_banner;
    private ViewPager mViewPager;
    public ViewPagerIndicator(Context context) {
        this(context, null);
    }
    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //获取可见tab的数量
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount=array.getInt(R.styleable.ViewPagerIndicator_visibal_tab_count,Count_DeFAULT_TAB);
        if(mTabVisibleCount<=0)
        {
            mTabVisibleCount=Count_DeFAULT_TAB;
        }
        array.recycle();
        //初始化画笔
        mPaint=new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.parseColor("#f1af3f"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));//设置连接线效果
    }

    /**
     *在控件的宽高发生变化的时候都会调用onSizeChanged方法
     *可以在改方法中得到控件的宽和高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTrangleWidth=(int)(w/mTabVisibleCount*RADIO_TRANGLE_WIDTH);
        initTrangleX=w/mTabVisibleCount/2-mTrangleWidth/2;
        initTrangle();//初始化三角形
    }
    //三角形绘制
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(initTrangleX + mTrangleX, getHeight() + 2);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 当XML加载完成之后会执行这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int cCount=getChildCount();//获取子元素个数
        if(cCount==0) return;
        for(int i=0;i<cCount;i++)
        {
            View view=getChildAt(i);
            //由于子view是在LinearLayout之下，所以必须使用LinearLayout.LayoutParams
            LayoutParams lp=(LayoutParams)view.getLayoutParams();
            lp.weight=0;
            lp.width=getScreenWidth()/mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();
    }

    /**
     * 获得屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 初始化三角形
     */
    private void initTrangle() {
        mTrangleHeight=mTrangleWidth/2;
        mPath=new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTrangleWidth, 0);
        mPath.lineTo(mTrangleWidth / 2, -mTrangleHeight);
        mPath.close();//线条闭合
    }

    /**
     * 三角形指示器跟随手指进行移动移动
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        int tabWidth=getWidth()/mTabVisibleCount;
        mTrangleX=(int)(tabWidth*(offset+position));
        //容器移动，当tab处于最后一个时
        if(position>=(mTabVisibleCount-2)&&offset>0&&getChildCount()>mTabVisibleCount)
        {
            if(mTabVisibleCount!=1)
            {
                this.scrollTo((position-(mTabVisibleCount-2))*tabWidth+(int)offset*tabWidth,0);
            }
            else
            {
                this.scrollTo((int)(position*tabWidth+tabWidth*offset),0);
            }

        }
        invalidate();
    }

    /**
     * 设置标签栏
     * @param titls
     */
    public void setTabTitles(List<String> titls)
    {
        if(titls!=null&&titls.size()>0)
        {
            //移除原来的textView
            this.removeAllViews();
            this.mTabTitles=titls;
            for(String title:mTabTitles)
            {
                addView(generateTextView(title));
            }
        }
        setItemClickEvent();
    }

    /**
     * 根据title生成textview
     * @param title
     * @return
     */
    private View generateTextView(String title) {
        TextView tv=new TextView(getContext());
        LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.width=getScreenWidth()/mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(mContext.getResources().getColor(COLOR_TEXT_NORMAL));
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 设置可见的tabs个数
     * @param count
     */
    public void setmTabVisibleCount(int count)
    {
        mTabVisibleCount=count;
    }

    /**
     * 设置与之关联的viewPager
     * @param viewPager
     * @param position
     */
    public void setViewPager(ViewPager viewPager, int position, final PageSelectListener listener)
    {
        mViewPager=viewPager;
        //监听面板移动变化
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //从第0页移动到第一页的时候移动量为 标签宽度*位置
                scroll(position,positionOffset);
                if(pageChangeListener!=null)
                {
                    pageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(pageChangeListener!=null)
                {
                    pageChangeListener.onPageSelected(position);
                }
                highLightTextView(position);
                if (listener != null) {
                    listener.pageSelect(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(pageChangeListener!=null)
                {
                    pageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(position);
        highLightTextView(position);
    }

    /**
     * 由于setOnPageChangeListener被我们使用，所以对外暴露一个借口供其他人使用
     */
    public interface PageChangeListener
    {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        public void onPageSelected(int position);
        public void onPageScrollStateChanged(int state);
    }
    private PageChangeListener pageChangeListener;
    public void setPageChangeListener(PageChangeListener listener)
    {
        pageChangeListener=listener;
    }
    /**
     * 设置高亮文本
     */
    private void highLightTextView(int pos)
    {
        resetTextViewColor();
        View view=getChildAt(pos);
        if(view instanceof TextView)
        {
            ((TextView) view).setTextColor(mContext.getResources().getColor(COLOR_TEXT_ACTIVY));
            System.out.println("设置高亮");
        }
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor()
    {
        for(int i=0;i<getChildCount();i++)
        {
            View view=getChildAt(i);
            if(view instanceof TextView)
            {
                ((TextView) view).setTextColor(mContext.getResources().getColor(COLOR_TEXT_NORMAL));
            }
        }
    }
    /**
     * 设置tab点击跳转
     */
    private void setItemClickEvent()
    {
        int cCount=getChildCount();
        for(int i=0;i<cCount;i++)
        {
            final int j=i;
            View view=getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }
}
