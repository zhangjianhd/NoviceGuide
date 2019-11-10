package com.zj.lib.noviceguide;

import android.app.Activity;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zj on 2019-07-22.
 */
public class NoviceGuide extends FrameLayout {

    private final String TAG = "NoviceGuide";

    private View focusView;    //高亮View
    private Activity relyActivity;    //所处的Activity
    private int layout;
    private int clickId;
    private float radius;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int bgColor = Color.parseColor("#B3000000");

    private DecorateInflate decorateInflate;

    public void setRelyActivity(Activity relyActivity) {
        this.relyActivity = relyActivity;
        contentParent = relyActivity.findViewById(android.R.id.content);
    }

    private NoviceGuide(Context context) {
        super(context);
        setWillNotDraw(false);
        viewRect = new Rect();
        contentRect = new Rect();
        rectF = new RectF();

        initPint();
    }


    private void initPint() {
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;
        porterDuffXfermode = new PorterDuffXfermode(mode);
        paint.setXfermode(porterDuffXfermode);
        //设置画笔遮罩滤镜,可以传入BlurMaskFilter或EmbossMaskFilter，前者为模糊遮罩滤镜而后者为浮雕遮罩滤镜
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private Rect viewRect;
    private Rect contentRect;
    private Paint paint;
    private RectF rectF;
    private PorterDuffXfermode porterDuffXfermode;
    private FrameLayout contentParent;

    private View inflaterView;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw");

        canvas.drawColor(bgColor);

        contentParent.getGlobalVisibleRect(contentRect);
        int topMargin = contentRect.top;

        Log.d(TAG, "topMargin:" + topMargin);

        //画出高亮区域
        focusView.getGlobalVisibleRect(viewRect);

        int left = viewRect.left - paddingLeft;
        rectF.left = left;
        Log.d(TAG, "left:" + left);
        int right = viewRect.right + paddingRight;
        rectF.right = right;
        Log.d(TAG, "right:" + right);
        int top = viewRect.top - paddingTop - topMargin;
        rectF.top = top;
        Log.d(TAG, "top:" + top);
        int bottom = viewRect.bottom + paddingBottom - topMargin;
        rectF.bottom = bottom;
        Log.d(TAG, "bottom:" + bottom);

        canvas.drawRoundRect(rectF, radius, radius, paint);

    }

    public void show() {

        inflaterView = LayoutInflater.from(relyActivity).inflate(layout, this, true);
        if (clickId != 0) {
            inflaterView.findViewById(clickId).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (dismissCallBack != null) {
                        dismissCallBack.onDismiss();
                    }
                }
            });
        }
        if (decorateInflate != null) {
            decorateInflate.onInflate(this,inflaterView);
        }
        this.setClickable(true);
        contentParent.post(new Runnable() {
            @Override
            public void run() {
                contentParent.addView(NoviceGuide.this,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

    }

    private DismissCallBack dismissCallBack;

    void setDismissCallBack(DismissCallBack dismissCallBack) {
        this.dismissCallBack = dismissCallBack;
    }

    public void dismiss() {
        contentParent.post(new Runnable() {
            @Override
            public void run() {
                contentParent.removeView(NoviceGuide.this);
            }
        });
    }

    public static class Builder {

        private NoviceGuide noviceGuide;

        public Builder(Context context) {
            noviceGuide = new NoviceGuide(context);
        }

        public Builder focusView(View view) {
            noviceGuide.focusView = view;
            return this;
        }

        public Builder setRelyActivity(Activity activity) {
            noviceGuide.setRelyActivity(activity);
            return this;
        }

        public Builder setLayout(@LayoutRes int layout,@Nullable DecorateInflate decorateInflate) {
            noviceGuide.layout = layout;
            noviceGuide.decorateInflate = decorateInflate;
            return this;
        }

        public Builder setPassId(@IdRes int clickId) {
            noviceGuide.clickId = clickId;
            return this;
        }

        public Builder setRadius(float radius) {
            noviceGuide.radius = radius;
            return this;
        }

        public Builder setPadding(int paddingLeft, int paddingRight, int paddingTop, int paddingBottom) {
            noviceGuide.paddingTop = paddingTop;
            noviceGuide.paddingBottom = paddingBottom;
            noviceGuide.paddingLeft = paddingLeft;
            noviceGuide.paddingRight = paddingRight;
            return this;
        }

        public Builder setBgColor(int bgColor) {
            noviceGuide.bgColor = bgColor;
            return this;
        }

        public NoviceGuide build() {
            return noviceGuide;
        }
    }
}
