package com.example.tmarton.sainsbury.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by Tamas Marton.
 */
public class ViewHelper {

    /**
     * @return DisplayImageOptions
     * setup ImageOptions
     */
    public static DisplayImageOptions setImageOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        return options;
    }
}
