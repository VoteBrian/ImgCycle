package net.votebrian.demos;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class ImgCycleActivity extends Activity {
    // globals
    Handler mHandler = new Handler();
    TypedArray backgrounds;
    ImageView iv;
    int currBG = 0;
    int totalBG;

    private final Runnable runDrawBG =  new Runnable() {
        public void run() {
            drawBG();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // load array of background image resources
        backgrounds = getResources().obtainTypedArray(R.array.bg_items);
        totalBG = backgrounds.length();

        // get a handle to the imageView
        iv = (ImageView) findViewById(R.id.bg_images);

        // loop the background image drawing
        drawBG();
    }

    private void drawBG() {
        iv.setImageDrawable(backgrounds.getDrawable(currBG % totalBG));
        currBG++;

        // first remove any runnables currently running
        mHandler.removeCallbacks(runDrawBG);
        mHandler.postDelayed(runDrawBG, 1000);
    }

    public void onDestroy() {
        mHandler.removeCallbacks(runDrawBG);
        super.onDestroy();
    }

    public void onPause() {
        mHandler.removeCallbacks(runDrawBG);
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        mHandler.post(runDrawBG);
    }
}