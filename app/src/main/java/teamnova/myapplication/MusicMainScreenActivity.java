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
    ImageButton btnPause;

    ImageButton btnGoBeforeMusic;
    ImageButton btnGoNextMusic;


    TextView tvCurMusicPos;
    TextView tvMusicSize;

    TextView tvNumOfLike;

    TextView tvMusicTitle;
    TextView tvMusicSinger;

    ImageView imgViewLike;
    ImageView imgViewNotLike;

    public static MusicServiceManager serviceManager;

    int musicIdx;
    int maxIdx;

    boolean threadAlive;
    boolean isPlaying = true;

    boolean deadFlag = false;

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

                    while(isPlaying) {

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
                    deadFlag = true;
                    break;
                }
            }
        }
    }

    final Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1) {

                imgViewLike.setVisibility(View.INVISIBLE);
                imgViewNotLike.setVisibility(View.VISIBLE);

                tvNumOfLike.post(new Runnable() {
                    @Override
                    public void run() {
                        tvNumOfLike.setText(""+activity_main.data_list.get(musicIdx).hartcount);
                        tvMusicTitle.setText(activity_main.data_list.get(musicIdx).title);
                        tvMusicSinger.setText(activity_main.data_list.get(musicIdx).artist);
                    }
                });
            }

            if(msg.what == 2) {

                imgViewLike.setVisibility(View.VISIBLE);
                imgViewNotLike.setVisibility(View.INVISIBLE);

                tvNumOfLike.post(new Runnable() {
                    @Override
                    public void run() {
                        tvNumOfLike.setText(""+activity_main.data_list.get(musicIdx).hartcount);
                        tvMusicTitle.setText(activity_main.data_list.get(musicIdx).title);
                        tvMusicSinger.setText(activity_main.data_list.get(musicIdx).artist);
                    }
                });
            }

        }
    };

    final Runnable task1 = new Thread(new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);

        }
    });

    final Runnable task2 = new Thread(new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(2);

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main_screen);

        imgViewAlbumArt = (ImageView) findViewById(R.id.iv_album_art);
        imgViewLike = (ImageView) findViewById(R.id.iv_like);
        imgViewNotLike = (ImageView) findViewById(R.id.iv_not_like);

        btnGoHome = (ImageButton) findViewById(R.id.img_btn_go_home);
        btnPlay = (ImageButton) findViewById(R.id.img_btn_play_music);
        btnPause = (ImageButton) findViewById(R.id.img_btn_pause_music);
        btnPlay.setVisibility(View.INVISIBLE);

        btnGoBeforeMusic = (ImageButton) findViewById(R.id.img_btn_go_before_music);
        btnGoNextMusic = (ImageButton) findViewById(R.id.img_btn_go_next_music);

        tvCurMusicPos = (TextView) findViewById(R.id.tv_cur_music_pos);
        tvMusicSize = (TextView) findViewById(R.id.tv_music_size);
        tvMusicSinger = (TextView) findViewById(R.id.tv_music_singer);
        tvNumOfLike = (TextView) findViewById(R.id.tv_num_of_like);
        tvMusicTitle = (TextView) findViewById(R.id.tv_music_title);


        musicIdx = getIntent().getIntExtra("position", -1);
        maxIdx = activity_main.data_list.size()-1;
//                        tvNumOfLike.setText(""+activity_main.data_list.get(musicIdx).hartcount);

        tvMusicTitle.setText(activity_main.data_list.get(musicIdx).title);
        tvMusicSinger.setText(activity_main.data_list.get(musicIdx).artist);

        tvNumOfLike.setText(""+activity_main.data_list.get(musicIdx).hartcount);

        if(activity_main.data_list.get(musicIdx).hart) {

            imgViewLike.setVisibility(View.VISIBLE);
            imgViewNotLike.setVisibility(View.INVISIBLE);
        }
        else {

            imgViewLike.setVisibility(View.INVISIBLE);
            imgViewNotLike.setVisibility(View.VISIBLE);
        }

        imgViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_main.data_list.get(musicIdx).hartcount--;
                activity_main.data_list.get(musicIdx).hart = false;

                imgViewLike.setVisibility(View.INVISIBLE);
                imgViewNotLike.setVisibility(View.VISIBLE);

                new Thread(task1).start();
            }
        });

        imgViewNotLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_main.data_list.get(musicIdx).hartcount++;
                activity_main.data_list.get(musicIdx).hart = true;

                imgViewLike.setVisibility(View.VISIBLE);
                imgViewNotLike.setVisibility(View.INVISIBLE);

                new Thread(task2).start();
            }
        });


        btnGoHome.setOnClickListener(btnGoHomeListener);
        btnPlay.setOnClickListener(btnPlayListener);
        btnGoNextMusic.setOnClickListener(btnGoNextListener);
        btnGoBeforeMusic.setOnClickListener(btnGoBeforeListener);
        btnPause.setOnClickListener(btnPauseListener);

        seekBarMainMusic = (SeekBar) findViewById(R.id.seekbar_main_music);

        seekBarMainMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(seekBar.getMax() == progress) {

                    serviceManager.stop();

                    seekBarMainMusic.setProgress(0);

                    int beforeMusicIdx = musicIdx;
                    musicIdx++;

                    while(activity_main.data_list.get(musicIdx).alpha) {

                        if(musicIdx >= maxIdx-1) {
                            break;
                        }
                        else {

                            musicIdx++;
                        }
                    }


                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(musicIdx));
                    msg.setData(bundle);

                    handler.sendMessage(msg);

                    serviceManager = new MusicServiceManager(MusicMainScreenActivity.this,
                            activity_main.data_list.get(musicIdx).sound);

                    serviceManager.start();

                    threadAlive = false;
                    threadAlive = true;
                    new WatchCurSeekbarPosThread().start();
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

    boolean isPaused = false;

    ImageButton.OnClickListener btnPauseListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(serviceManager == null) {

                return;
            }

            serviceManager.pause();

            btnPlay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.INVISIBLE);

            isPaused = true;
        }
    };

    ImageButton.OnClickListener btnGoHomeListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };

    ImageButton.OnClickListener btnPlayListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(isPaused) {

                serviceManager.restart();
                isPaused = false;

                btnPlay.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);

                return;
            }

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

            btnPlay.setVisibility(View.INVISIBLE);
            btnPause.setVisibility(View.VISIBLE);
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

                while(activity_main.data_list.get(musicIdx).alpha) {

                    if(musicIdx <= 0)
                        break;

                    musicIdx--;
                }

                serviceManager = new MusicServiceManager(MusicMainScreenActivity.this,
                        activity_main.data_list.get(musicIdx).sound);

                if(activity_main.data_list.get(musicIdx).hart) {

                    imgViewLike.setVisibility(View.VISIBLE);
                    imgViewNotLike.setVisibility(View.INVISIBLE);

                    new Thread(task1).start();
                }
                else {

                    imgViewLike.setVisibility(View.INVISIBLE);
                    imgViewNotLike.setVisibility(View.VISIBLE);

                    new Thread(task2).start();
                }

                threadAlive = false;
                threadAlive = true;

                serviceManager.start();

                new WatchCurSeekbarPosThread().start();
            }
        }
    };

    ImageButton.OnClickListener btnGoNextListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(musicIdx >= maxIdx-1) {

                Toast.makeText(getApplicationContext(), "마지막 음악입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(serviceManager != null)
                serviceManager.stop();

            musicIdx++;

            while(activity_main.data_list.get(musicIdx).alpha) {

                if(musicIdx == maxIdx) {
                    break;
                }
                musicIdx++;
            }

            serviceManager = new MusicServiceManager(MusicMainScreenActivity.this,
                    activity_main.data_list.get(musicIdx).sound);

            if(activity_main.data_list.get(musicIdx).hart) {

                imgViewLike.setVisibility(View.VISIBLE);
                imgViewNotLike.setVisibility(View.INVISIBLE);

                new Thread(task1).start();
            }
            else {

                imgViewLike.setVisibility(View.INVISIBLE);
                imgViewNotLike.setVisibility(View.VISIBLE);

                new Thread(task2).start();
            }

            threadAlive = false;
            threadAlive = true;

            serviceManager.start();

            new WatchCurSeekbarPosThread().start();
        }
    };

    protected void onStart() {
        super.onStart();
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar_main_music);

        seekBar.bringToFront();
    }
}