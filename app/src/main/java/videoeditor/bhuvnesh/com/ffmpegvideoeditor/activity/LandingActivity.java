package videoeditor.bhuvnesh.com.ffmpegvideoeditor.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import videoeditor.bhuvnesh.com.ffmpegvideoeditor.R;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GET_VIDEO_FROM_USER_KEY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_landing);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.record_video_btn) {
            dispatchTakeVideoIntent();
        } else if (view.getId() == R.id.upload_video_btn) {
            if (Build.VERSION.SDK_INT >= 23)
                getPermission();
            else
                uploadVideo();
        }
    }

    private void initView() {
        findViewById(R.id.record_video_btn).setOnClickListener(LandingActivity.this);
        findViewById(R.id.upload_video_btn).setOnClickListener(LandingActivity.this);
    }

    /**
     * Opening gallery for uploading video
     */
    private void uploadVideo() {
        try {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Video"), GET_VIDEO_FROM_USER_KEY);
        } catch (Exception e) {

        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, GET_VIDEO_FROM_USER_KEY);
        }
    }


    private void getPermission() {
        String[] params = null;
        String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;

        int hasWriteExternalStoragePermission = ActivityCompat.checkSelfPermission(this, writeExternalStorage);
        int hasReadExternalStoragePermission = ActivityCompat.checkSelfPermission(this, readExternalStorage);
        List<String> permissions = new ArrayList<String>();

        if (hasWriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(writeExternalStorage);
        if (hasReadExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
            permissions.add(readExternalStorage);

        if (!permissions.isEmpty()) {
            params = permissions.toArray(new String[permissions.size()]);
        }
        if (params != null && params.length > 0) {
            ActivityCompat.requestPermissions(this,
                    params,
                    200);
        } else
            uploadVideo();
    }


    /**
     * Handling response for permission request
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadVideo();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_VIDEO_FROM_USER_KEY) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constants.URI_PASS_KEY, data.getData().toString());
                startActivity(intent);
            }
        }
    }
}
