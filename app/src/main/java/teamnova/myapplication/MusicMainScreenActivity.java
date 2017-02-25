package teamnova.myapplication;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import teamnova.myapplication.woongbi.activity_main;

public class MusicMainScreenActivity extends AppCompatActivity {

    public static final String TAG = "MusicMainScreenActivity";

    SeekBar seekBarMainMusic;

    ImageView imgViewAlbumArt;

    ImageButton btnGoHome;
    ImageButton btnPlay;

    ImageButton btnGoBeforeMusic;
    ImageButton btnGoNextMusic;

    TextView tvCurMusicPos;
    TextView tvMusicSize;

    TextView tvNumOfLike;

    TextView tvMusicTitle;
    TextView tvMusicSinger;

    public static MusicServiceManager serviceManager;

    int musicIdx;
    int maxIdx;

    boolean threadAlive;

    class WatchCurSeekbarPosThread extends Thread {

        @Override
        public void run() {
            super.run();

            boolean escapeFlag = false;

            while(threadAlive) {

                while(MusicServiceManager.OK_READY) {

                    seekBarMainMusic.setMax(serviceManager.getDuration());

                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            tvMusicSize.post(new Runnable() {

                                int sec;

                                @Override
                                public void run() {

                                    sec = serviceManager.getDuration() / 1000;

                                    String minute = String.valueOf(sec / 60);
                                    String second = String.valueOf(sec % 60);

                                    if (second.length() == 1) {
                                        second = "0" + second;
                                    }

                                    tvMusicSize.setText("" + minute + ":" + second);
                                }
                            });
                        }
                    });

                    th.start();

                    while(serviceManager.isPlaying()) {

                        seekBarMainMusic.setProgress(serviceManager.getCurrentPosition());

                        th = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                tvCurMusicPos.post(new Runnable() {

                                    int sec;

                                    @Override
                                    public void run() {

                                        sec = serviceManager.getCurrentPosition() / 1000;

                                        String minute = String.valueOf(sec / 60);
                                        String second = String.valueOf(sec % 60);

                                        if (second.length() == 1) {
                                            second = "0" + second;
                                        }

                                        tvCurMusicPos.setText("" + minute + ":" + second);
                                    }
                                });

//                                mHandler.sendEmptyMessage(0);

                                try {

                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        th.start();
                    }

                    escapeFlag = true;
                    break;
                }

                if(escapeFlag) {

                    MusicServiceManager.OK_READY = false;
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main_screen);

        imgViewAlbumArt = (ImageView) findViewById(R.id.iv_album_art);

        btnGoHome = (ImageButton) findViewById(R.id.img_btn_go_home);
        btnPlay = (ImageButton) findViewById(R.id.img_btn_play_music);

        btnGoBeforeMusic = (ImageButton) findViewById(R.id.img_btn_go_before_music);
        btnGoNextMusic = (ImageButton) findViewById(R.id.img_btn_go_next_music);

        tvCurMusicPos = (TextView) findViewById(R.id.tv_cur_music_pos);
        tvMusicSize = (TextView) findViewById(R.id.tv_music_size);

        tvMusicSinger = (TextView) findViewById(R.id.tv_music_singer);
        tvNumOfLike = (TextView) findViewById(R.id.tv_num_of_like);

        tvMusicTitle = (TextView) findViewById(R.id.tv_music_title);

        musicIdx = getIntent().getIntExtra("position", -1);
        maxIdx = activity_main.data_list.size()-1;

        btnGoHome.setOnClickListener(btnGoHomeListener);
        btnPlay.setOnClickListener(btnPlayListener);
        btnGoNextMusic.setOnClickListener(btnGoNextListener);
        btnGoBeforeMusic.setOnClickListener(btnGoBeforeListener);

        seekBarMainMusic = (SeekBar) findViewById(R.id.seekbar_main_music);

        seekBarMainMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(seekBar.getMax() == progress) {

                    serviceManager.stop();

                    seekBarMainMusic.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(serviceManager == null)
                    return;

                int touchedPosition = seekBar.getProgress();
                serviceManager.seekTo(touchedPosition);
                serviceManager.start();
            }
        });

        if(serviceManager != null && serviceManager.isPlaying()) {

            if(serviceManager.getMusicResource() == activity_main.data_list.get(musicIdx).sound) {

                seekBarMainMusic.setProgress(serviceManager.getCurrentPosition());

                if(serviceManager.isPlaying()) {
                    new WatchCurSeekbarPosThread().start();
                }
            }
        }
        else {

            seekBarMainMusic.setProgress(0);
        }
    }

    @Override
    protected void onPause() {
        threadAlive = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
//        threadAlive = true;
        super.onResume();
    }

    ImageButton.OnClickListener btnGoHomeListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };

    ImageButton.OnClickListener btnPlayListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(musicIdx == -1) {
                Log.e(TAG, "btnGoHomeListener: value of musicIdx is crazy.");
                return;
            }

            serviceManager = new MusicServiceManager(MusicMainScreenActivity.this,
                    activity_main.data_list.get(musicIdx).sound);

            serviceManager.start();

            threadAlive = false;
            threadAlive = true;
            new WatchCurSeekbarPosThread().start();
        }
    };

    ImageButton.OnClickListener btnGoBeforeListener = new ImageButton.OnClickListener() {

        private long pressedTime = 0;

        @Override
        public void onClick(View v) {

            if(serviceManager == null) {

                return;
            }

            if (System.currentTimeMillis() > pressedTime + 2000) {

                pressedTime = System.currentTimeMillis();
                serviceManager.seekTo(0);
                return;
            }

            if(System.currentTimeMillis() <= pressedTime + 2000) {

                if(musicIdx <= 0) {

                    Toast.makeText(getApplicationContext(), "첫번째 음악입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(serviceManager != null)
                    serviceManager.stop();

                musicIdx--;

                serviceManager = new MusicServiceManager(MusicMainScreenActivity.this,
                        activity_main.data_list.get(musicIdx).sound);

                serviceManager.start();

                threadAlive = false;
                threadAlive = true;
                new WatchCurSeekbarPosThread().start();
            }
        }
    };

    ImageButton.OnClickListener btnGoNextListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(musicIdx >= maxIdx) {

                Toast.makeText(getApplicationContext(), "마지막 음악입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(serviceManager != null)
                serviceManager.stop();

            musicIdx++;

            serviceManager = new MusicServiceManager(MusicMainScreenActivity.this,
                    activity_main.data_list.get(musicIdx).sound);

            serviceManager.start();

            threadAlive = false;
            threadAlive = true;
            new WatchCurSeekbarPosThread().start();
        }
    };

    protected void onStart() {
        super.onStart();
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_main_music);

        seekBar.bringToFront();
    }
}