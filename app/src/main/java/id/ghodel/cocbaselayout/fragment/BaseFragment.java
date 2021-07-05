package id.ghodel.cocbaselayout.fragment;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initView(View view);
    public abstract void initLogic(View view);
    public abstract void initListener(View view);
}
