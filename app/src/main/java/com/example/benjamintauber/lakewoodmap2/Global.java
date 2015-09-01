package com.example.benjamintauber.lakewoodmap2;

import android.app.Application;

/**
 * Created by benjamintauber on 9/11/14.
 */
public class Global extends Application {
    private String data;

    protected void setData(String data) {
        this.data = data;
    }

    protected String getData() {
        return data;
    }
}
