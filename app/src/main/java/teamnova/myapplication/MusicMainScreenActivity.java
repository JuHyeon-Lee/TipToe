package teamnova.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import teamnova.myapplication.woongbi.activity_main;

import static teamnova.myapplication.woongbi.activity_main.serviceManager;

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

    MediaPlayer mediaPlayer;

    SeekBar seekBar;

    int musicIdx;
    int maxIdx;

    boolean isPlaying;
    int resource;

    private final int LIKE = 1;

    final Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1) {

                imgViewLike.setVisibility(View.INVISIBLE);
                imgViewNotLike.setVisibility(View.VISIBLE);

                tvNumOfLike.post(new Runnable() {
                    @Override
                    public void run() {
                        tvNumOfLike.setText(""+MusicListUtil.내가등록한음악리스트.get(musicIdx).hartcount);
                        tvMusicTitle.setText(MusicListUtil.내가등록한음악리스트.get(musicIdx).title);
                        tvMusicSinger.setText(MusicListUtil.내가등록한음악리스트.get(musicIdx).artist);
                        imgViewAlbumArt.setImageResource(MusicListUtil.내가등록한음악리스트.get(musicIdx).image);
                    }
                });
            }

            if(msg.what == 2) {

                imgViewLike.setVisibility(View.VISIBLE);
                imgViewNotLike.setVisibility(View.INVISIBLE);

                tvNumOfLike.post(new Runnable() {
                    @Override
                    public void run() {
                        tvNumOfLike.setText(""+MusicListUtil.내가등록한음악리스트.get(musicIdx).hartcount);
                        tvMusicTitle.setText(MusicListUtil.내가등록한음악리스트.get(musicIdx).title);
                        tvMusicSinger.setText(MusicListUtil.내가등록한음악리스트.get(musicIdx).artist);
                        imgViewAlbumArt.setImageResource(MusicListUtil.내가등록한음악리스트.get(musicIdx).image);
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

    class WatchChangingSeekbarThread extends Thread {

        @Override
        public void run() {
            super.run();
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

                seekBarMainMusic.setProgress(mediaPlayer.getCurrentPosition());

                th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tvCurMusicPos.post(new Runnable() {

                            int sec;

                            @Override
                            public void run() {

                                sec = mediaPlayer.getCurrentPosition() / 1000;

                                String minute = String.valueOf(sec / 60);
                                String second = String.valueOf(sec % 60);

                                if (second.length() == 1) {
                                    second = "0" + second;
                                }

                                tvCurMusicPos.setText("" + minute + ":" + second);
                            }
                        });



                        try {

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                th.start();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main_screen);

        seekBar = (SeekBar) findViewById(R.id.seekbar_main_music);

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

        resource = activity_main.data_list.get(musicIdx).sound;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), resource);

        tvMusicTitle.setText(activity_main.data_list.get(musicIdx).title);
        tvMusicSinger.setText(activity_main.data_list.get(musicIdx).artist);

        tvNumOfLike.setText(""+activity_main.data_list.get(musicIdx).hartcount);

        if(MusicListUtil.내가등록한음악리스트.get(musicIdx).hart) {

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

                MusicListUtil.내가등록한음악리스트.get(musicIdx).hartcount--;
                MusicListUtil.내가등록한음악리스트.get(musicIdx).hart = false;

                imgViewLike.setVisibility(View.INVISIBLE);
                imgViewNotLike.setVisibility(View.VISIBLE);

                new Thread(task1).start();
            }
        });

        imgViewNotLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicListUtil.내가등록한음악리스트.get(musicIdx).hartcount++;
                MusicListUtil.내가등록한음악리스트.get(musicIdx).hart = true;

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

                    mediaPlayer.reset();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(mediaPlayer == null)
                    return;

                int touchedPosition = seekBar.getProgress();
                mediaPlayer.seekTo(touchedPosition);
                mediaPlayer.start();
            }
        });

    }

    private int pos;

    ImageButton.OnClickListener btnPauseListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            btnPlay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.INVISIBLE);

            isPlaying = false;

            mediaPlayer.pause();
            pos = mediaPlayer.getCurrentPosition();

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

            btnPlay.setVisibility(View.INVISIBLE);
            btnPause.setVisibility(View.VISIBLE);

            if(mediaPlayer != null) {

                mediaPlayer.seekTo(pos);
                mediaPlayer.start();

                isPlaying = true;

                return;
            }

            if(musicIdx == -1) {
                Log.e(TAG, "btnGoHomeListener: value of musicIdx is crazy.");
                return;
            }

            btnPlay.setVisibility(View.INVISIBLE);
            btnPause.setVisibility(View.VISIBLE);

            isPlaying = true;

            mediaPlayer.start();
            new WatchChangingSeekbarThread().start();
        }
    };

    ImageButton.OnClickListener btnGoBeforeListener = new ImageButton.OnClickListener() {

        private long pressedTime = 0;

        @Override
        public void onClick(View v) {

            if(mediaPlayer == null) {

                return;
            }

            if (System.currentTimeMillis() > pressedTime + 2000) {

                pressedTime = System.currentTimeMillis();
                mediaPlayer.seekTo(0);
                return;
            }

            if(System.currentTimeMillis() <= pressedTime + 2000) {

                if(musicIdx <= 0) {

                    Toast.makeText(getApplicationContext(), "첫번째 음악입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                musicIdx--;
                resource = MusicListUtil.내가등록한음악리스트.get(musicIdx).sound;

                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), resource);

                if(MusicListUtil.내가등록한음악리스트.get(musicIdx).hart) {

                    new Thread(task2).start();
                }
                else {

                    new Thread(task1).start();
                }

                mediaPlayer.start();
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

            musicIdx++;
            resource = MusicListUtil.내가등록한음악리스트.get(musicIdx).sound;

            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), resource);

            if(MusicListUtil.내가등록한음악리스트.get(musicIdx).hart) {

                new Thread(task2).start();
            }
            else {
                new Thread(task1).start();
            }

            mediaPlayer.start();
        }
    };

    protected void onStart() {
        super.onStart();
        seekBar.bringToFront();
    }
}