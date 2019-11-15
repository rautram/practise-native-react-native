package com.rocketuber;

import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class FrameLayoutManager extends ViewGroupManager<FrameLayoutView> {
    private final static String REACT_CLASS = "FrameLayoutView";
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected FrameLayoutView createViewInstance(ThemedReactContext reactContext) {
        return new FrameLayoutView(reactContext);
    }
    @Override
    public void addView(FrameLayoutView parent, View child, int index)
    {
        super.addView(parent, child, index);
    }
}
