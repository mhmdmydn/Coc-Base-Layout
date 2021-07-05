package id.ghodel.cocbaselayout.activity;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public abstract void initView();
    public abstract void initLogic();
    public abstract void initListener();
}
