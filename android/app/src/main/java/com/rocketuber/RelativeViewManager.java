package com.rocketuber;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;


public class RelativeViewManager extends ViewGroupManager<RecyclerView> {
    private final static String REACT_CLASS = "RelativeView";
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RecyclerView createViewInstance(ThemedReactContext reactContext) {
        return new RecyclerView(reactContext);
    }
    @Override
    public void addView(RecyclerView parent, View child, int index)
    {
        super.addView(parent, child, index);
    }
}
