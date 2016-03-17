package com.canislupus.mediarecodersample;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    private VideoPreview mVideoPreview = null;
    private Button buttonOperation = null;
    private Button buttonSwitchCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        mVideoPreview = (VideoPreview) findViewById(R.id.videorecordview);
        buttonOperation = (Button) findViewById(R.id.operation);
        buttonOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoPreview.isRecording()) {
                    buttonOperation.setText("Start");

                    mVideoPreview.setFilePath(getOutputMediaFile().toString());
                    mVideoPreview.stopRecord();
                } else {
                    buttonOperation.setText("Stop");
                    mVideoPreview.setFilePath(getOutputMediaFile().toString());
                    mVideoPreview.startRecord();
                }
            }
        });

        buttonSwitchCamera = (Button)findViewById(R.id.switch_camera);
        buttonSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOperation.setText("Start");
                if(!mVideoPreview.switchCamera()){
                    Toast.makeText(MainActivity.this, "switch failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private File getOutputMediaFile() {


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), "SampleAPP");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "VID_" + timeStamp + ".mp4");


        return mediaFile;
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVideoPreview.releaseMediaRecorder();
        mVideoPreview.releaseCamera();
    }
}
