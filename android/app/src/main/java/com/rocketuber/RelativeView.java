package com.rocketuber;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class RelativeView extends RecyclerView {
    public RelativeView(Context context) {
        super(context);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width, height);
        this.setLayoutParams(params);
        this.setFitsSystemWindows(false);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }
}
