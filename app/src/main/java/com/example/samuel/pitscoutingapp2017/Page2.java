package com.example.samuel.pitscoutingapp2017;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Page2 extends AppCompatActivity {

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void getShooting () {
        CheckBox HighS = (CheckBox) findViewById(R.id.checkBoxHigh);
        CheckBox LowS = (CheckBox) findViewById(R.id.checkBoxLow);

        PitScout.botPitData.Shooting[0] = HighS.isChecked();
        PitScout.botPitData.Shooting[1] = LowS.isChecked();
    }

    public void getCollecting () {
        CheckBox Groundg = (CheckBox) findViewById(R.id.checkBoxGroundG);
        CheckBox Player = (CheckBox) findViewById(R.id.checkBoxPlayer);
        CheckBox Groundf = (CheckBox) findViewById(R.id.checkBoxGroundF);
        CheckBox Hopper = (CheckBox) findViewById(R.id.checkBoxHopper);

        PitScout.botPitData.Collecting[0] = Groundg.isChecked();
        PitScout.botPitData.Collecting[1] = Player.isChecked();
        PitScout.botPitData.Collecting[2] = Groundf.isChecked();
        PitScout.botPitData.Collecting[3] = Hopper.isChecked();
    }

     public void getRope () {
        CheckBox Custom = (CheckBox) findViewById(R.id.checkBoxCustom);
        CheckBox Default = (CheckBox) findViewById(R.id.checkBoxDefault);

        PitScout.botPitData.Rope[0] = Custom.isChecked();
        PitScout.botPitData.Rope[1] = Default.isChecked();
    }

    public void getFrame () {
        CheckBox Wood = (CheckBox) findViewById(R.id.checkBoxWood);
        CheckBox Aluminium = (CheckBox) findViewById(R.id.checkBoxAluminium);
        CheckBox Steel = (CheckBox) findViewById(R.id.checkBoxSteel);

        PitScout.botPitData.Frame[0] = Wood.isChecked();
        PitScout.botPitData.Frame[1] = Aluminium.isChecked();
        PitScout.botPitData.Frame[2] = Steel.isChecked();
    }

    public static File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e("ERROR", "Directory NOT Created");
        }
        return file;
    }

    public File CreateCSV (View view) {
        getShooting();
        getCollecting();
        getRope();
        getFrame();
        String fileName = PitScout.botPitData.Event + "-" + PitScout.botPitData.Number + "-"+ System.currentTimeMillis() + "-" + "pit.csv";
        File directory = getAlbumStorageDir("/FRC2017");
        File file = new File(directory, fileName);
        try {
            FileWriter writer = new FileWriter(file);

            String lineOne = PitScout.botPitData.Event + "," +
                    PitScout.botPitData.Number + ","+
                    PitScout.botPitData.Name + ","+
                    PitScout.botPitData.BallCapacity + ","+
                    PitScout.botPitData.Layout + ","
                    + PitScout.botPitData.Shooting[0] + ","
                    + PitScout.botPitData.Shooting[1] + ","
                    + PitScout.botPitData.Collecting[0] + ","
                    + PitScout.botPitData.Collecting[1] + ","
                    + PitScout.botPitData.Collecting[2] + ","
                    + PitScout.botPitData.Collecting[3] + ","
                    + PitScout.botPitData.Rope[0] + ","
                    + PitScout.botPitData.Rope[1] + ","
                    + PitScout.botPitData.Frame[0] + ","
                    + PitScout.botPitData.Frame[1] + ","
                    + PitScout.botPitData.Frame[2];

            writer.write(lineOne + "\n");
            writer.close();

            return file;

        } catch (IOException e) {
            Log.e("ERROR", "File NOT Created", e);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent(this, Page1.class);
        String Name = PitScout.botPitData.Name;
        PitScout.botPitData.clear();
        PitScout.botPitData.Name = Name;
        startActivity(intent);
    }

    public void SendCSV (View view) {
        File file = CreateCSV (view);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        this.startActivityForResult(intent, 0);
    }

}