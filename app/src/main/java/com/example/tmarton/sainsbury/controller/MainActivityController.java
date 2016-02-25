package com.example.tmarton.sainsbury.controller;

import android.app.Activity;

import com.example.tmarton.sainsbury.controller.task.ParseTask;

/**
 * Created by Tamas Marton.
 */
public class MainActivityController {

    private final Activity activity;

    public MainActivityController(Activity activity) {
        this.activity = activity;
    }

    /**
     * @param html
     * call the asyncTask
     */
    public void createView(String html) {
        new ParseTask(activity).execute(html);
    }
}
