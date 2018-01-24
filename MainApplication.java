package com.vasu.nameart;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

// guidance link //
https://developers.facebook.com/docs/audience-network/android

// placement id banava mate //
https://developers.facebook.com/apps/<your_app_id>/audience-network/.

/**
compile 'com.facebook.android:audience-network-sdk:4.+'
 */
public class MainApplication extends MultiDexApplication {

   
    //facebook
    public com.facebook.ads.InterstitialAd mInterstitialAdfb;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

       LoadAdsFb();

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    
    public static MainApplication getInstance() {
        return singleton;
    }

    public void LoadAdsFb() {
        try {

            mInterstitialAdfb = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_test_ad_img) + getString(R.string.fb_inter_placement));
//            mInterstitialAdfb = new com.facebook.ads.InterstitialAd(this,  getString(R.string.fb_inter_placement));
            AdSettings.addTestDevice("5de1d7f9ad822be9013726e7ee8c0578"); //Lenovo
            AdSettings.addTestDevice("f5d25a7560deadafd3cef18dac35d0a4"); //Coolpad
            AdSettings.addTestDevice("8e859dd13860d27e83bd58c5da3d87ea"); //Ziox
            AdSettings.addTestDevice("dbc7437e652b49bc83729a92183636a5"); //samsung tab

            mInterstitialAdfb.loadAd();
            mInterstitialAdfb.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial displayed callback
                    Log.e("onInterstitialDisplayed", "--> onInterstitialDisplayed");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                    Log.e("onInterstitialDismissed", "--> onInterstitialDismissed");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
                    Log.e("FB interstitialAd", "onError --> " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Show the ad when it's done loading.
                    Log.e("onAdLoaded", "--> onAdLoaded");

                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                    Log.e("onAdClicked", "--> onAdClicked");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                    Log.e("onLoggingImpression", "--> onLoggingImpression");
                }
            });


        } catch (Exception e) {
        }
    }

    public boolean requestNewInterstitialfb() {

        try {
            if (mInterstitialAdfb.isAdLoaded()) {
                mInterstitialAdfb.show();
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isLoadedfnad() {

        try {
            if (mInterstitialAdfb.isAdLoaded() && mInterstitialAdfb != null) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}

// onresume code //

 if (!MainApplication.getInstance().isLoadedfnad())
            MainApplication.getInstance().LoadAdsFb();

// call karva mate no code //

if (!MainApplication.getInstance().requestNewInterstitialfb()) {
                        redirectActivity();

                    } else {
                        MainApplication.getInstance().mInterstitialAdfb.setAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                MainApplication.getInstance().mInterstitialAdfb.setAdListener(null);
                                MainApplication.getInstance().mInterstitialAdfb = null;
                                MainApplication.getInstance().LoadAdsFb();

                                redirectActivity();
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                redirectActivity();
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        });
                    }