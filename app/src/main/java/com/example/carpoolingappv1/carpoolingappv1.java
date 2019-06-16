package com.example.carpoolingappv1;

import android.app.Application;
import android.content.Context;

public class carpoolingappv1 extends Application {
    private static Context context;
    private static Application sApplication;

    public static Application getApplication(){
        return sApplication;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        sApplication=this;

    }
    public static Context getAppContext(){
        return getApplication().getApplicationContext();
    }
}
