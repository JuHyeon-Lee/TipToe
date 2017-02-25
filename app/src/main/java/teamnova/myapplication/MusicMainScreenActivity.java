package teamnova.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicMainScreenActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main_screen);

        imgViewAlbumArt = (ImageView) findViewById(R.id.iv_album_art);

        btnGoHome = (ImageButton) findViewById(R.id.img_btn_go_home);
        btnPlay = (ImageButton) findViewById(R.id.img_btn_play_music);

        btnGoBeforeMusic = (ImageButton) findViewById(R.id.img_btn_go_before_music);
        btnGoNextMusic = (ImageButton) findViewById(R.id.img_btn_go_before_music);

        tvCurMusicPos = (TextView) findViewById(R.id.tv_cur_music_pos);
        tvMusicSize = (TextView) findViewById(R.id.tv_music_singer);

        tvMusicSinger = (TextView) findViewById(R.id.tv_music_singer);
        tvNumOfLike = (TextView) findViewById(R.id.tv_num_of_like);

        tvMusicTitle = (TextView) findViewById(R.id.tv_music_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.bringToFront();
    }
}