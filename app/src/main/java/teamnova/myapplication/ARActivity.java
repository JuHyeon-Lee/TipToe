package teamnova.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ARActivity extends Activity implements SensorEventListener {
    private static final String TAG = "ARActivity";
    Preview preview;
    Camera camera;
    Activity act;
    Context ctx;
    ProgressDialog dialog;
    ArrayList<Data> image_list = new ArrayList<Data>();

    ImageView elbum_ex_iamge_001;
    ImageView elbum_ex_iamge_002;
    ImageView elbum_ex_iamge_003;

    TextView music_title;
    TextView artist_name;
    TextView like;
    Button left;
    Button right;
    ImageView like_switch;
    Switch ar_switch;
    LinearLayout layout;

    boolean 진입체크 = true;

    Handler handler = new Handler();
    Animation animFadein;
    Animation animFadeout;

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;


    int address = 0x7f020053;

    int page = 1;


    // Request code for camera
    private final int CAMERA_REQUEST_CODE = 100;

    // Request code for runtime permissions
    private final int REQUEST_CODE_STORAGE_PERMS = 321;

    private boolean hasPermissions() {
        int res = 0;
        // list all permissions which you want to check are granted or not.
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                // it return false because your app dosen't have permissions.
                return false;
            }

        }
        // it return true, your app has permissions.
        return true;
    }

    private void requestNecessaryPermissions() {
        // make array of permissions which you want to ask from user.
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // have arry for permissions to requestPermissions method.
            // and also send unique Request code.
            requestPermissions(permissions, REQUEST_CODE_STORAGE_PERMS);
        }
    }

    /* when user grant or deny permission then your app will check in
      onRequestPermissionsReqult about user's response. */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grandResults) {
        // this boolean will tell us that user granted permission or not.
        boolean allowed = true;
        switch (requestCode) {
            case REQUEST_CODE_STORAGE_PERMS:
                for (int res : grandResults) {
                    // if user granted all required permissions then 'allowed' will return true.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
//                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                // if user denied then 'allowed' return false
//                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                allowed = false;
                break;
        }
        if (allowed) {
            // if user granted permissions then do your work.
            //startCamera();
            doRestart(this);
        } else {
            // else give any custom waring message.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                    Toast.makeText(ARActivity.this, "Camera Permissions denied", Toast.LENGTH_SHORT).show();
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    Toast.makeText(ARActivity.this, "Storage Permissions denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    public static void doRestart(Context c) {
        //http://stackoverflow.com/a/22345538
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
//                        Log.e(TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
//                    Log.e(TAG, "Was not able to restart application, PM null");
                }
            } else {
//                Log.e(TAG, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
//            Log.e(TAG, "Was not able to restart application");
        }
    }

    public void startCamera() {

        if (preview == null) {
            preview = new Preview(this, (SurfaceView) findViewById(R.id.surfaceView));
            preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            ((FrameLayout) findViewById(R.id.layout)).addView(preview);
            preview.setKeepScreenOn(true);

//            preview.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
//                }
//            });
//

        }

        preview.setCamera(null);
        if (camera != null) {
            camera.release();
            camera = null;
        }

        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {

                // Camera.CameraInfo.CAMERA_FACING_FRONT or Camera.CameraInfo.CAMERA_FACING_BACK
                int CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK;
                camera = Camera.open(CAMERA_FACING);
                // camera orientation
                camera.setDisplayOrientation(setCameraDisplayOrientation(this, CAMERA_FACING, camera));
                // get Camera parameters
                Camera.Parameters params = camera.getParameters();
                // picture image orientation
                params.setRotation(setCameraDisplayOrientation(this, CAMERA_FACING, camera));
                camera.startPreview();
//                Toast.makeText(this, "camera start", Toast.LENGTH_LONG).show();

            } catch (RuntimeException ex) {
//                Toast.makeText(ctx, "camera_not_found " + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
//                Log.d(TAG, "camera_not_found " + ex.getMessage().toString());
            }
        }

        preview.setCamera(camera);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null) {
            sensorManager.registerListener(this, accelerormeterSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager !=null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

















        ctx = this;
        act = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ar);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            if (!hasPermissions()) {
                // your app doesn't have permissions, ask for them.
                requestNecessaryPermissions();
            } else {
                // your app already have permissions allowed.
                // do what you want.
                //startCamera();
            }


        } else {
//            Toast.makeText(ARActivity.this, "Camera not supported", Toast.LENGTH_LONG).show();
        }


        /*
         * 데이터 삽입
         */


        switch(getIntent().getIntExtra("kind", -1)){
            case 2://위워크
                image_list = MusicListUtil.위워크에등록된음악리스트;
                break;
            case 3://교회
                image_list = MusicListUtil.평린교회에등록된음악리스트;
                break;
            case 4://건설
                image_list = MusicListUtil.SK건설에등록된음악리스트;
                break;
            default:
                break;
        }
//
//        image_list.add(new ArItem("눈의 꽃", address, "박효신", 52));
//        image_list.add(new ArItem("좋은 사람", address + 1, "박효신", 38));
//        image_list.add(new ArItem("널 바라기", address + 2, "박효신", 25));
//        image_list.add(new ArItem("바보", address + 3, "토이", 12));
//        image_list.add(new ArItem("Happy Together", address + 4, "박효신", 2));


        elbum_ex_iamge_001 = (ImageView) findViewById(R.id.elbum_view_left);
        elbum_ex_iamge_002 = (ImageView) findViewById(R.id.elbum_view_center);
        elbum_ex_iamge_003 = (ImageView) findViewById(R.id.elbum_view_right);

        music_title = (TextView) findViewById(R.id.ar_music_title);
        artist_name = (TextView) findViewById(R.id.ar_artist_name);
        like = (TextView) findViewById(R.id.ar_like);
        ar_switch = (Switch) findViewById(R.id.ar_switch);
        like_switch = (ImageView) findViewById(R.id.ar_like_button);
        layout = (LinearLayout) findViewById(R.id.wrap_layout);


        Glide.with(this).load(image_list.get(page - 1).image).bitmapTransform(new BlurTransformation(ARActivity.this)).into(elbum_ex_iamge_001);
        Glide.with(this).load(image_list.get(page).image).into(elbum_ex_iamge_002);
        Glide.with(this).load(image_list.get(page + 1).image).bitmapTransform(new BlurTransformation(ARActivity.this)).into(elbum_ex_iamge_003);
        artist_name.setText(image_list.get(page).artist);
        like.setText(String.valueOf(image_list.get(page).hartcount));
        music_title.setText(image_list.get(page).title);


        left = (Button) findViewById(R.id.ar_left_button);
        right = (Button) findViewById(R.id.ar_right_button);


        elbum_ex_iamge_002.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(ARActivity.this, "", "재생 목록에 추가 중입니다.", true);
                dialog.show();

                MusicListUtil.내가등록한음악리스트.add(image_list.get(page));

                EndDialog endDialog = new EndDialog();
                endDialog.start();
            }
        });



        ar_switch.setChecked(true);
        ar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ar_switch.setChecked(false);
                            }
                        });

                        finish();
                    }
                }).start();



            }

        });



        left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (page <= 0) {
                                            Toast.makeText(ARActivity.this, "더 이상 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                                        } else {
                                            page--;
                                            elbum_ex_iamge_001.setVisibility(View.VISIBLE);
                                            elbum_ex_iamge_003.setVisibility(View.VISIBLE);
                                            left.setVisibility(View.VISIBLE);
                                            right.setVisibility(View.VISIBLE);

                                            if (page - 1 < 0) {
                                                elbum_ex_iamge_001.setVisibility(View.INVISIBLE);
                                                left.setVisibility(View.INVISIBLE);
                                            } else {
                                                Glide.with(ARActivity.this).load(image_list.get(page - 1).image).bitmapTransform(new BlurTransformation(ARActivity.this)).into(elbum_ex_iamge_001);
                                            }

                                            Glide.with(ARActivity.this).load(image_list.get(page).image).into(elbum_ex_iamge_002);
                                            artist_name.setText(image_list.get(page).artist);
                                            like.setText(String.valueOf(image_list.get(page).hartcount));
                                            music_title.setText(image_list.get(page).title);


                                            Glide.with(ARActivity.this).load(image_list.get(page + 1).image).bitmapTransform(new BlurTransformation(ARActivity.this)).into(elbum_ex_iamge_003);

                                        }


                                    }
                                }
        );

        right.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         if (page >= image_list.size() - 1) {
                                             Toast.makeText(ARActivity.this, "더 이상 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                                         } else {
                                             page++;
                                             elbum_ex_iamge_001.setVisibility(View.VISIBLE);
                                             elbum_ex_iamge_003.setVisibility(View.VISIBLE);
                                             right.setVisibility(View.VISIBLE);
                                             left.setVisibility(View.VISIBLE);
                                             Glide.with(ARActivity.this).load(image_list.get(page - 1).image).bitmapTransform(new BlurTransformation(ARActivity.this)).into(elbum_ex_iamge_001);
                                             Glide.with(ARActivity.this).load(image_list.get(page).image).into(elbum_ex_iamge_002);
                                             artist_name.setText(image_list.get(page).artist);
                                             like.setText(String.valueOf(image_list.get(page).hartcount));
                                             music_title.setText(image_list.get(page).title);


                                             if (page + 1 >= image_list.size()) {
                                                 elbum_ex_iamge_003.setVisibility(View.INVISIBLE);
                                                 right.setVisibility(View.INVISIBLE);
                                             } else {
                                                 Glide.with(ARActivity.this).load(image_list.get(page + 1).image).bitmapTransform(new BlurTransformation(ARActivity.this)).into(elbum_ex_iamge_003);
                                             }


                                         }


                                     }
                                 }
        );


    }


    @Override
    protected void onResume() {
        super.onResume();

        startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Surface will be destroyed when we return, so stop the preview.
        if (camera != null) {
            // Call stopPreview() to stop updating the preview surface
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }

        ((FrameLayout) findViewById(R.id.layout)).removeView(preview);
        preview = null;

    }

    private void resetCam() {
        startCamera();
    }

//    private void refreshGallery(File file) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        mediaScanIntent.setData(Uri.fromFile(file));
//        sendBroadcast(mediaScanIntent);
//    }
//
//    ShutterCallback shutterCallback = new ShutterCallback() {
//        public void onShutter() {
//            //			 Log.d(TAG, "onShutter'd");
//        }
//    };
//
//    PictureCallback rawCallback = new PictureCallback() {
//        public void onPictureTaken(byte[] data, Camera camera) {
//            //			 Log.d(TAG, "onPictureTaken - raw");
//        }
//    };
//
//    PictureCallback jpegCallback = new PictureCallback() {
//        public void onPictureTaken(byte[] data, Camera camera) {
//            new SaveImageTask().execute(data);
//            resetCam();
////            Log.d(TAG, "onPictureTaken - jpeg");
//        }
//    };
//


//
//    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {
//
//        @Override
//        protected Void doInBackground(byte[]... data) {
//            FileOutputStream outStream = null;
//
//            // Write to SD Card
//            try {
//                File sdCard = Environment.getExternalStorageDirectory();
//                File dir = new File(sdCard.getAbsolutePath() + "/camtest");
//                dir.mkdirs();
//
//                String fileName = String.format("%d.jpg", System.currentTimeMillis());
//                File outFile = new File(dir, fileName);
//
//                outStream = new FileOutputStream(outFile);
//                outStream.write(data[0]);
//                outStream.flush();
//                outStream.close();
//
////                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length + " to " + outFile.getAbsolutePath());
//
//                refreshGallery(outFile);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//            }
//            return null;
//        }
//
//    }
//

    /**
     * @param activity
     * @param cameraId Camera.CameraInfo.CAMERA_FACING_FRONT, Camera.CameraInfo.CAMERA_FACING_BACK
     * @param camera   Camera Orientation
     *                 reference by https://developer.android.com/reference/android/hardware/Camera.html
     */
    public static int setCameraDisplayOrientation(Activity activity,
                                                  int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation){
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

//                Log.e("SENSOR", "X : " + x);
//                Log.e("SENSOR", "Y : " + y);
//                Log.e("SENSOR", "Z : " + z);


                if (진입체크) {
//                    Log.e("TAG", "진입");
                    if (-1 < z && z < 1) {
                        //정면
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setAllViewVisible();
                                Log.e("TAG", "정면입니다");
                            }
                        });
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                진입체크 = false;

                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                진입체크 = true;
                            }
                        }).start();

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setAllViewInvisible();
                            }
                        });

                    }

                    if (speed > SHAKE_THRESHOLD) { // 이벤트발생!!

                    }

                    lastX = event.values[DATA_X];
                    lastY = event.values[DATA_Y];
                    lastZ = event.values[DATA_Z];
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    void setAllViewInvisible(){


        Log.e("TAG", "사라지기");
//        animFadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
//        elbum_ex_iamge_001.startAnimation(animFadeout);
//        elbum_ex_iamge_002.startAnimation(animFadeout);
//        elbum_ex_iamge_003.startAnimation(animFadeout);
//        left.startAnimation(animFadeout);
//        right.startAnimation(animFadeout);
//        music_title.startAnimation(animFadeout);
//        artist_name.startAnimation(animFadeout);
//        like_switch.startAnimation(animFadeout);
//        like.startAnimation(animFadeout);


        elbum_ex_iamge_001.setVisibility(View.INVISIBLE);
        elbum_ex_iamge_002.setVisibility(View.INVISIBLE);
        elbum_ex_iamge_003.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
        music_title.setVisibility(View.INVISIBLE);
        artist_name.setVisibility(View.INVISIBLE);
        like.setVisibility(View.INVISIBLE);
//        ar_switch.setVisibility(View.INVISIBLE);
        like_switch.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);



        elbum_ex_iamge_001.invalidate();
        elbum_ex_iamge_002.invalidate();
        elbum_ex_iamge_003.invalidate();
        left.invalidate();
        right.invalidate();
        music_title.invalidate();
        artist_name.invalidate();
        like.invalidate();
//        ar_switch.invalidate();
        like_switch.invalidate();
        layout.invalidate();
    }



    void setAllViewVisible(){
        elbum_ex_iamge_001.setVisibility(View.VISIBLE);
        elbum_ex_iamge_002.setVisibility(View.VISIBLE);
        elbum_ex_iamge_003.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        music_title.setVisibility(View.VISIBLE);
        artist_name.setVisibility(View.VISIBLE);
        like.setVisibility(View.VISIBLE);
//        ar_switch.setVisibility(View.VISIBLE);
        like_switch.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);


//        // load the animation
//        animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
//        elbum_ex_iamge_001.startAnimation(animFadein);
//        elbum_ex_iamge_002.startAnimation(animFadein);
//        elbum_ex_iamge_003.startAnimation(animFadein);
//        left.startAnimation(animFadein);
//        right.startAnimation(animFadein);
//        music_title.startAnimation(animFadein);
//        artist_name.startAnimation(animFadein);
//        like_switch.startAnimation(animFadein);
//        like.startAnimation(animFadein);



        /*
         *  뷰 새로고침
         */

        elbum_ex_iamge_001.invalidate();
        elbum_ex_iamge_002.invalidate();
        elbum_ex_iamge_003.invalidate();
        left.invalidate();
        right.invalidate();
        music_title.invalidate();
        artist_name.invalidate();
        like.invalidate();
//        ar_switch.invalidate();
        like_switch.invalidate();
        layout.invalidate();

    }
    private Handler DialogHandler = new Handler (){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            dialog.dismiss();

//            finish();
//            startActivity(new Intent(getApplicationContext(), activity_main.class));
//            startActivity(intent);
        }
    };

    private class EndDialog extends Thread {
        public void run(){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DialogHandler.sendEmptyMessage(0);
        }
    }
}