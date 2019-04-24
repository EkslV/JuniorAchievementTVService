package com.android.test.juniorachievementtvservice;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.media.tv.TvInputService;
import android.media.tv.TvView;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());

        registerReceiver(receiver, new IntentFilter("finish"));

        Session session = new Session(this);
        TvView tvView = new TvView(this);
        tvView.setStreamVolume(0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(getApplicationContext(), DBService.class);
                startService(startIntent);
            }
        }, 2000);

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    class Session extends TvInputService.Session {

        Context mContext;

        public Session(Context context) {
            super(context);
            mContext = context;

            TvInputManager manager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);
            if (manager != null) {
                Log.d("TAG", "onCreate: " + manager.getTvInputList().size());
                for (TvInputInfo info : manager.getTvInputList()) {
                    Log.d("TAG", "onCreate: " + info.toString());
                }
            }
        }

        @Override
        public void onRelease() {

        }

        @Override
        public boolean onSetSurface(@Nullable Surface surface) {
            return false;
        }

        @Override
        public void onSetStreamVolume(float v) {

        }

        @Override
        public boolean onTune(Uri uri) {
            return false;
        }

        @Override
        public void onSetCaptionEnabled(boolean b) {

        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
}
