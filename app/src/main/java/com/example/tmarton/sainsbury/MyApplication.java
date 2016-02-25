package com.example.tmarton.sainsbury;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Tamas Marton.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    /**
     * @param context
     * init universalimageloader to cache the images
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.denyCacheImageMultipleSizesInMemory();
        config.writeDebugLogs();
        config.memoryCacheSize(20 * 1024 * 1024);
        ImageLoader.getInstance().init(config.build());
    }
}
