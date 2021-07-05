package id.ghodel.cocbaselayout;

import android.app.Activity;
import android.app.Application;
import android.view.Window;
import android.view.WindowManager;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.MobileAds;

import id.ghodel.cocbaselayout.utils.CrashHandler;

/**
 * Created by Muhammad Mayudin on 03-Jul-21.
 */
public class App extends MultiDexApplication {

    private static App singleton = null;

    public static App getInstance(){
        if(singleton == null){
            singleton = new App();
        }

        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        MobileAds.initialize(singleton, getResources().getString(R.string.id_ads));
        CrashHandler.init(singleton);
    }
}
