package id.ghodel.cocbaselayout.activity;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;


import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.tfb.fbtoast.FBToast;

import id.ghodel.cocbaselayout.BuildConfig;
import id.ghodel.cocbaselayout.R;
import id.ghodel.cocbaselayout.services.FloatingService;
import id.ghodel.cocbaselayout.utils.MainUtils;

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

public class PreviewActivity extends BaseActivity {

    private static final String TAG = PreviewActivity.class.getSimpleName();

    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;

    private AppCompatTextView tvDownload, tvLike, tvView, tvCcTroops, tvCcTitle;
    private PhotoView imgThumb;
    private MaterialButton btnOpenCoc;
    private AdView adView;

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initView();
        initLogic();
        initListener();
    }

    @Override
    public void initView() {
        imgThumb = findViewById(R.id.image_thumb);
        tvDownload = findViewById(R.id.tv_download);
        tvLike = findViewById(R.id.tv_like);
        tvView = findViewById(R.id.tv_view);
        tvCcTroops =  findViewById(R.id.tv_cc_troops);
        btnOpenCoc = findViewById(R.id.button_open_coc);
        tvCcTitle = findViewById(R.id.tv_cc_title);
        adView = findViewById(R.id.adView);
    }

    @Override
    public void initLogic() {
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        prepareAd();

        getSupportActionBar().setTitle(getResources().getString(R.string.town_halll)+" "+checkEmpty(HALL));
        getSupportActionBar().setSubtitle(checkEmpty(TYPE));
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        switch (checkEmpty("town_hall")){
            case "TH":
                Picasso.get()
                        .load(BuildConfig.BASE_IMAGE_URL + checkEmpty(IMAGE))
                        .placeholder(circularProgressDrawable)
                        .error(getResources().getDrawable(R.drawable.ic_placeholder_image_error))
                        .into(imgThumb);
                Log.d(TAG, "Image : " + BuildConfig.BASE_IMAGE_URL + checkEmpty(IMAGE));
                tvCcTitle.setText("* CC Troops");
                tvCcTroops.setText(checkEmpty(CC_TROOPS));
                break;
            case "BH":
                Picasso.get()
                        .load(BuildConfig.BUILDER_BASE_IMAGE_URL + checkEmpty(IMAGE))
                        .placeholder(circularProgressDrawable)
                        .error(getResources().getDrawable(R.drawable.ic_placeholder_image_error))
                        .into(imgThumb);
                Log.d(TAG, "Image : " + BuildConfig.BUILDER_BASE_IMAGE_URL + checkEmpty(IMAGE));
                tvCcTitle.setText("Description");
                tvCcTroops.setText(checkEmpty("description"));
                break;

        }
        tvDownload.setText(checkEmpty(DOWNLOAD));
        tvLike.setText(checkEmpty(LIKE));
        tvView.setText(checkEmpty(VIEW));

    }

    @Override
    public void initListener() {

        btnOpenCoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainUtils.isPackageInstalled(getApplicationContext(), "com.supercell.clashofclans")){
                    if(checkEmpty(LAYOUT_LINK) != "-"){
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(checkEmpty(LAYOUT_LINK))));
                        } catch (ActivityNotFoundException e){
                            e.printStackTrace();
                        }

                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Log.d("TAG"," Interstitial not loaded");
                        }
                        prepareAd();
                    } else {
                        FBToast.errorToast(getApplicationContext(), "Maaf link tidak tersedia", FBToast.LENGTH_SHORT, Gravity.BOTTOM);
                    }

                } else {
                    FBToast.errorToast(getApplicationContext(), "Clash Of Clans tidak terinstal", FBToast.LENGTH_SHORT, Gravity.BOTTOM);
                }
            }
        });

        imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(PreviewActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE);
                    } else {
                       startFloatingWidgetService();
                    }
                } else {
                    startFloatingWidgetService();
                }
            }
        });
    }

    private String checkEmpty(String type){
        return TextUtils.isEmpty(getIntent().getStringExtra(type)) ? "-" : getIntent().getStringExtra(type);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void prepareAd(){

        interstitialAd = new InterstitialAd(PreviewActivity.this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.inter_ads));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d(TAG, "Ad Closed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d(TAG, "Ad Failed To Load : " + i);

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "Ad loaded");
            }
        });
    }

    /*  Start Floating widget service and finish current activity */
    private void startFloatingWidgetService() {
        Intent iService = new Intent(PreviewActivity.this, FloatingService.class);
        switch (checkEmpty("town_hall")){
            case "TH":
                iService.putExtra("data", "TH");
                iService.putExtra("id", checkEmpty(ID));
                iService.putExtra("image", checkEmpty(IMAGE));
                iService.putExtra("layout_link", checkEmpty(LAYOUT_LINK));
                iService.putExtra("type",checkEmpty(TYPE));
                iService.putExtra("hall", checkEmpty(HALL));
                iService.putExtra("download", checkEmpty(DOWNLOAD));
                iService.putExtra("view", checkEmpty(VIEW));
                iService.putExtra("like", checkEmpty(LIKE));
                iService.putExtra("base_strength", checkEmpty(BASE_STRENGTH));
                iService.putExtra("cc_troops", checkEmpty(CC_TROOPS));
                break;
            case "BH" :
                iService.putExtra("data", "BH");
                iService.putExtra("id", checkEmpty(ID));
                iService.putExtra("image", checkEmpty(IMAGE));
                iService.putExtra("layout_link", checkEmpty(LAYOUT_LINK));
                iService.putExtra("hall", checkEmpty(HALL));
                iService.putExtra("title", checkEmpty("title"));
                iService.putExtra("description", checkEmpty("description"));
                iService.putExtra("download", checkEmpty(DOWNLOAD));
                iService.putExtra("view", checkEmpty(VIEW));
                iService.putExtra("like", checkEmpty(LIKE));
                iService.putExtra("created", checkEmpty("created"));
                iService.putExtra("modified", checkEmpty("modified"));
                break;
        }

        startService(iService);
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE: {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (Settings.canDrawOverlays(PreviewActivity.this)) {
                        startFloatingWidgetService();
                    }
                } else {
                    startFloatingWidgetService();
                }
                break;
            }
        }


    }
}