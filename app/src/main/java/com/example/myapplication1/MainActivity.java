package com.example.myapplication1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    Button btn1 , btn2 , btn3 , btn4 , btn5 , btn6 ;
    Bitmap rgbFrameBitmap;
    ImageView mImageView , mFairy;
    TextView mLable , mtext4 , mtext5 , mtext2 , mtext6 ;
    FrameLayout mA0 , mA1 , mA10 , mA2 , mA3 , mA4;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    Classifier.Recognition r;
    String TAG ="MainActivityResult";
    String mResult;
    String[] comment = {"你的內心柔軟,卻有原則，不喜歡討好別人,很看重面子。\n你討厭欺騙,容易耳根軟,面對熟悉的朋友會任性。"
            , "你是個不會耍心機,講義氣的人,對待感情很認真。\n要面子,心地善良,害怕孤單,不兇又很好相處。"
            , "你是個真性情,愛恨分明,總是口是心非。\n你喜歡簡簡單單就好,不喜歡世俗。"
            , "你有個善辯的口才，喜歡為他人著想。\n你喜歡帶給別人歡笑，不喜歡孤獨，喜歡受到他人的肯定。"};
    int count=0;
    private MSCognitiveServicesClassifier classifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        mtext4 = findViewById(R.id.textView4);
        mtext5 = findViewById(R.id.textView5);
        mtext2 = findViewById(R.id.textView2);
        mtext6 = findViewById(R.id.textView6);
        mA0 = findViewById(R.id.A0);
        mA1 = findViewById(R.id.A1);
        mA10 = findViewById(R.id.A10);
        mA2 = findViewById(R.id.A2);
        mA3 = findViewById(R.id.A3);
        mA4 = findViewById(R.id.A4);
        mImageView = findViewById(R.id.imageView);
        mFairy = findViewById(R.id.imageView2);
        mLable = findViewById(R.id.textView);
        classifier = new MSCognitiveServicesClassifier(MainActivity.this);
        verifyPermissions();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                mA1.setVisibility(View.VISIBLE);
                mA2.setVisibility(View.INVISIBLE);
            }
        },1000);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                mA10.setVisibility(View.VISIBLE);
                mtext4.setVisibility(View.INVISIBLE);
            }
        },3000);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA1.setVisibility(View.VISIBLE);
                mA2.setVisibility(View.INVISIBLE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mA2.setVisibility(View.INVISIBLE);
                        mA3.setVisibility(View.VISIBLE);
                    }
                },3000);

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA2.setVisibility(View.VISIBLE);
                mA3.setVisibility(View.INVISIBLE);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA3.setVisibility(View.INVISIBLE);
                mA4.setVisibility(View.VISIBLE);
                FrameLayout.LayoutParams mFairyLoc = new FrameLayout.LayoutParams((int)171.2,(int)190.4);
                Random r=new Random();
                switch (r.nextInt(4)){
                    case 0:
                        mFairyLoc.setMargins(720,590,0,0);
                        break;
                    case 1:
                        mFairyLoc.setMargins(220,390,0,0);
                        break;
                    case 2:
                        mFairyLoc.setMargins(45,990,0,0);
                        break;
                    case 3:
                        mFairyLoc.setMargins(400,790,0,0);
                        break;
                }
                mFairy.setLayoutParams(mFairyLoc);

                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mtext2.setVisibility(View.INVISIBLE);
                        mFairy.setVisibility(View.INVISIBLE);
                        mtext6.setVisibility(View.VISIBLE);
                    }
                },1000);
            }
        });


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mA1.setVisibility(View.INVISIBLE);
                mA2.setVisibility(View.VISIBLE);

            }
        });

        //跳轉至map
        final Intent intent = new Intent(this, MapsActivity.class);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
//        bp = (Bitmap) data.getExtras().get("data");
        rgbFrameBitmap = (Bitmap) data.getExtras().get("data");
        mImageView.setImageBitmap(rgbFrameBitmap);

        processImageRGBbytes();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

    //給相機權限
    private void verifyPermissions() {
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 0);
        }
    }

    protected void processImageRGBbytes() {
        startHandlerThread();
//        rgbFrameBitmap.setPixels(rgbBytes, 0, previewWidth, 0, 0, previewWidth, previewHeight);

        new Thread(new Runnable(){
            @Override
            public void run() {
                r = classifier.classifyImage(rgbFrameBitmap, 0);
                Log.d(TAG,"Detect: " + r.getTitle());
                mResult = r.getTitle();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (mResult) {
                            case "Water":
                                mLable.setText(comment[0]);
                                break;
                            case "Fire":
                                mLable.setText(comment[1]);
                                break;
                            case "Earth":
                                mLable.setText(comment[2]);
                                break;
                            case "Wind":
                                mLable.setText(comment[3]);
                                break;
                        }
//                        mLable.setText(mResult);
                    }
                });
            }

        }).start();

    }


    public void startHandlerThread(){
        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
//        startHandlerThread();
//        processImageRGBbytes();
    }


    @Override
    public synchronized void onStop() {
        super.onStop();
//        processImageRGBbytes();
    }

    public void resetA1(){
        mtext4.setVisibility(View.VISIBLE);
        mtext5.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);
        count=0;
    }



}
