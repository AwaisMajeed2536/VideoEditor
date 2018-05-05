package videoeditor.bhuvnesh.com.ffmpegvideoeditor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import videoeditor.bhuvnesh.com.ffmpegvideoeditor.R;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_landing);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.record_video_btn) {
            startActivity(new Intent(this, VideoCaptureActivity.class));
        } else if (view.getId() == R.id.upload_video_btn) {

        }
    }

    private void initView() {
        findViewById(R.id.record_video_btn).setOnClickListener(LandingActivity.this);
        findViewById(R.id.upload_video_btn).setOnClickListener(LandingActivity.this);
    }
}
