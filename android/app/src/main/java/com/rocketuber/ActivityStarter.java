package com.rocketuber;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class ActivityStarter extends ReactContextBaseJavaModule {

    ActivityStarter(ReactApplicationContext reactApplicationContext)
    {
        super(reactApplicationContext);
    }
    @Override
    public String getName() {
        return "ActivityStarter";
    }

    @ReactMethod
    void navigateTo()
    {
        ReactApplicationContext context = getReactApplicationContext();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
