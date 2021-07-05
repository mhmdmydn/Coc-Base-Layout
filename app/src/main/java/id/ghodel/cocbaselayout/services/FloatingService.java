package id.ghodel.cocbaselayout.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import id.ghodel.cocbaselayout.BuildConfig;
import id.ghodel.cocbaselayout.R;
import id.ghodel.cocbaselayout.activity.PreviewActivity;
import id.ghodel.cocbaselayout.utils.TouchImageView;

import static id.ghodel.cocbaselayout.activity.MainActivity.ID;
import static id.ghodel.cocbaselayout.activity.MainActivity.IMAGE;
import static id.ghodel.cocbaselayout.activity.MainActivity.LAYOUT_LINK;
import static id.ghodel.cocbaselayout.activity.MainActivity.BASE_STRENGTH;
import static id.ghodel.cocbaselayout.activity.MainActivity.HALL;
import static id.ghodel.cocbaselayout.activity.MainActivity.TYPE;
import static id.ghodel.cocbaselayout.activity.MainActivity.DOWNLOAD;
import static id.ghodel.cocbaselayout.activity.MainActivity.LIKE;
import static id.ghodel.cocbaselayout.activity.MainActivity.VIEW;
import static id.ghodel.cocbaselayout.activity.MainActivity.CC_TROOPS;

/**
 * Created by Muhammad Mayudin on 05-Jul-21.
 */
public class FloatingService extends Service {
    private Context mContext;
    private WindowManager windowManager;
    private View mView;
    private WindowManager.LayoutParams mWindowsParams;

    private AppCompatImageView imgClose, imgOpenApp;
    private PhotoView imgThumb;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        configureFloatingLayout(intent);
        moveView();

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {

        if (mView != null) {
            windowManager.removeView(mView);
        }
        super.onDestroy();
    }

    private void moveView() {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.5f);
        int height = (int) (metrics.heightPixels * 0.3f);

        mWindowsParams = new WindowManager.LayoutParams(
                width,
                height,
                (Build.VERSION.SDK_INT <= 25) ? WindowManager.LayoutParams.TYPE_PHONE : WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                ,WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );


        mWindowsParams.gravity = Gravity.TOP | Gravity.LEFT;
        //params.x = 0;
        mWindowsParams.y = 100;
        windowManager.addView(mView, mWindowsParams);

        mView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            long startTime = System.currentTimeMillis();
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = mWindowsParams.x;
                        initialY = mWindowsParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mWindowsParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        mWindowsParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(mView, mWindowsParams);
                        break;
                }
                return false;
            }
        });
    }


    private void configureFloatingLayout(Intent intent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.floating_layout, null);

        imgThumb = (PhotoView) mView.findViewById(R.id.img_float_thumb);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mContext);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        switch (getDataIntent(intent, "data")){
            case "TH":

                Picasso.get()
                        .load(BuildConfig.BASE_IMAGE_URL + getDataIntent(intent, IMAGE))
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.ic_placeholder_image_error)
                        .into(imgThumb);
                Log.d(FloatingService.class.getSimpleName(), "Load Image In Service " + BuildConfig.BASE_IMAGE_URL +getDataIntent(intent, IMAGE));

                break;
            case "BH":
                Picasso.get()
                        .load(BuildConfig.BUILDER_BASE_IMAGE_URL + getDataIntent(intent, IMAGE))
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.ic_placeholder_image_error)
                        .into(imgThumb);
                Log.d(FloatingService.class.getSimpleName(), "Load Image In Service " + BuildConfig.BUILDER_BASE_IMAGE_URL +getDataIntent(intent, IMAGE));

                break;
        }


        imgOpenApp = (AppCompatImageView) mView.findViewById(R.id.img_float_open_app);
        imgClose = (AppCompatImageView) mView.findViewById(R.id.img_float_close);


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        imgOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iService = new Intent(FloatingService.this, PreviewActivity.class);

                switch (getDataIntent(intent, "data")){
                    case "TH":
                        iService.putExtra("town_hall", "TH");
                        iService.putExtra("id", getDataIntent(intent, ID));
                        iService.putExtra("image", getDataIntent(intent, IMAGE));
                        iService.putExtra("layout_link", getDataIntent(intent, LAYOUT_LINK));
                        iService.putExtra("type",getDataIntent(intent, TYPE));
                        iService.putExtra("hall", getDataIntent(intent, HALL));
                        iService.putExtra("download", getDataIntent(intent, DOWNLOAD));
                        iService.putExtra("view", getDataIntent(intent, VIEW));
                        iService.putExtra("like", getDataIntent(intent, LIKE));
                        iService.putExtra("base_strength", getDataIntent(intent, BASE_STRENGTH));
                        iService.putExtra("cc_troops", getDataIntent(intent, CC_TROOPS));

                        break;
                    case "BH":

                        iService.putExtra("town_hall", "BH");
                        iService.putExtra("id", getDataIntent(intent,ID));
                        iService.putExtra("image", getDataIntent(intent,IMAGE));
                        iService.putExtra("layout_link", getDataIntent(intent,LAYOUT_LINK));
                        iService.putExtra("hall", getDataIntent(intent,HALL));
                        iService.putExtra("title", getDataIntent(intent,"title"));
                        iService.putExtra("description", getDataIntent(intent,"description"));
                        iService.putExtra("download", getDataIntent(intent,DOWNLOAD));
                        iService.putExtra("view", getDataIntent(intent,VIEW));
                        iService.putExtra("like", getDataIntent(intent,LIKE));
                        iService.putExtra("created", getDataIntent(intent,"created"));
                        iService.putExtra("modified", getDataIntent(intent,"modified"));

                        break;
                }

                iService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iService);

                stopSelf();
            }
        });

    }

    private String getDataIntent(Intent intent, String key){
        return intent.getStringExtra(key);
    }


}
