package teamnova.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MusicMainScreenActivity extends AppCompatActivity {

    ImageView imgViewAlbumArt;
    ImageButton btnNumOfLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main_screen);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.bringToFront();
    }
}