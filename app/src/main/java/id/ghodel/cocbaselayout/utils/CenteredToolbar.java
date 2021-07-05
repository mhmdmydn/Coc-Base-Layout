package id.ghodel.cocbaselayout.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import id.ghodel.cocbaselayout.R;

public class CenteredToolbar extends Toolbar {

    private TextView tvTitle;
    private TextView tvSubtitle;

    private LinearLayout linear;

    public CenteredToolbar(Context context) {
        super(context);
        setupTextViews();
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupTextViews();
    }

    public CenteredToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupTextViews();
    }

    @Override
    public CharSequence getTitle() {
        return tvTitle.getText().toString();
    }

    @Override
    public void setTitle(@StringRes int resId) {
        String s = getResources().getString(resId);
        setTitle(s);
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvTitle.setTypeface(getResources().getFont(R.font.roboto_light));
        }
    }

    @Override
    public CharSequence getSubtitle() {
        return tvSubtitle.getText().toString();
    }

    @Override
    public void setSubtitle(int resId) {
        String s = getResources().getString(resId);
        setSubtitle(s);
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        tvSubtitle.setVisibility(VISIBLE);
        tvSubtitle.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvSubtitle.setTypeface(getResources().getFont(R.font.roboto_light));
        }
        tvSubtitle.setText(subtitle);
    }

    private void setupTextViews() {
        tvSubtitle = new TextView(getContext());
        tvTitle = new TextView(getContext());

        tvTitle.setEllipsize(TextUtils.TruncateAt.END);
        tvTitle.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        tvTitle.setGravity(Gravity.CENTER);

        linear = new LinearLayout(getContext());
        linear.setGravity(Gravity.CENTER);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.addView(tvTitle);
        linear.addView(tvSubtitle);

        tvSubtitle.setSingleLine();
        tvSubtitle.setEllipsize(TextUtils.TruncateAt.END);
        tvSubtitle.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Widget_ActionBar_Subtitle);

        tvSubtitle.setVisibility(GONE);

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        linear.setLayoutParams(lp);

        addView(linear);
    }

}
