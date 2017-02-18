package com.example.samuel.pitscoutingapp2017;

import android.app.Application;

public class PitScout extends Application {
    public static PitData botPitData = new PitData();

    public static PitScout singleton;

    public static PitScout getInstance() {return singleton;}

    @Override
    public void onCreate(){
        super.onCreate();
        singleton= this;
    }
}

