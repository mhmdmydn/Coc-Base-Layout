package id.ghodel.cocbaselayout.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatTextView;

import id.ghodel.cocbaselayout.R;

public class SplashActivity extends BaseActivity {

    private static final int LOADING = 3500;
    private AppCompatTextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Untuk Menghilangkan Status Bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initView();
        initLogic();
        initListener();

    }

    @Override
    public void initView() {
        tvTitle = findViewById(R.id.tv_title);
    }

    @Override
    public void initLogic() {
        //tvTitle.setText("\u00A9"+ getResources().getString(R.string.ghodel_dev));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent();
                in.setAction(Intent.ACTION_VIEW);
                in.setClass(SplashActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        }, LOADING);
    }

    @Override
    public void initListener() {

    }
}