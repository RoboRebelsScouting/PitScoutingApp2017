package com.example.samuel.pitscoutingapp2017;

public class PitData {
    public String Name = "";
    public String Event;
    public String Number;
    /*
    String replaced = sentence.replace("and", "");
    System.out.printIn(replaced);
    */
    public boolean[] BallCapacityFIX = new boolean[6];
    public boolean[] LayoutFIX = new boolean[5];
    public boolean[] Shooting = new boolean[2];
    public boolean[] Collecting = new boolean[4];
    public boolean[] Rope = new boolean[2];
    public boolean[] Frame = new boolean[3];

    public void clear() {
        this.Name = "";
        this.Event = "";
        this.Number = "";
        this.BallCapacity = "";
        this.Layout = "";
        this.Shooting = new boolean[2];
        this.Collecting = new boolean[4];
        this.Rope = new boolean[2];
        this.Frame = new boolean[3];
    }
}
