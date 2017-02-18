package com.example.samuel.pitscoutingapp2017;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pit_Scouting_GUI extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;

    public void TakePhoto (View view) {

        EditText teamEdit = (EditText) findViewById(R.id.editTextNumber);
        String teamString = teamEdit.getText().toString();
        PitScout.botPitData.Number = teamString;

        Boolean allDataIn = true;
        if ((PitScout.botPitData.Number == null) || PitScout.botPitData.Number.equals("")) {
            Toast.makeText(getApplicationContext(), "Type Your Team Number In", Toast.LENGTH_LONG).show();
            allDataIn = false;
        }
        if (allDataIn== true){
            // create Intent to take a picture and return control to the calling application
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

            // start the image capture Intent
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("fileUri", fileUri);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri= savedInstanceState.getParcelable("fileUri");

    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HHmm").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "Bot_" + PitScout.botPitData.Number+"-"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "Bot_" + PitScout.botPitData.Number+"-"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    Toast.makeText(this, "Image saved to:\n" +
                            fileUri.toString(), Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
            }

            if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // Video captured and saved to fileUri specified in the Intent
                    Toast.makeText(this, "Video saved to:\n" +
                            data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the video capture
                } else {
                    // Video capture failed, advise user
                }
            }
        }
    }

    public void NextPage (View view) {
        EditText scoutEdit = (EditText) findViewById(R.id.editTextName);
        String nameString = scoutEdit.getText().toString();
        PitScout.botPitData.Name = nameString;

        EditText eventEdit = (EditText) findViewById(R.id.editTextEvent);
        String eventString = eventEdit.getText().toString();
        PitScout.botPitData.Event = eventString;

        EditText teamEdit = (EditText) findViewById(R.id.editTextNumber);
        String numberString = teamEdit.getText().toString();
        PitScout.botPitData.Number = numberString;

        // check that all data has been entered
        Boolean allDataIn = true;
        if (nameString.equals("")) {
            Toast.makeText(getApplicationContext(), "Type Your Name In", Toast.LENGTH_LONG).show();
            allDataIn = false;
        }
        if (eventString.equals("")) {
            Toast.makeText(getApplicationContext(), "Type Your Event In", Toast.LENGTH_LONG).show();
            allDataIn = false;
        }
        if (numberString.equals("")) {
            Toast.makeText(getApplicationContext(), "Type Your Team Number In", Toast.LENGTH_LONG).show();
            allDataIn = false;
        }

        if (allDataIn) {
                Intent intent = new Intent(this, Page2.class);
                startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
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

        if (!PitScout.botPitData.Name.equals("")){
            EditText scoutTxt = (EditText) findViewById(R.id.editTextName);
            scoutTxt.setHint(PitScout.botPitData.Name);
        }

    }
}
