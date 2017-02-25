package teamnova.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MusicMainScreenActivity extends AppCompatActivity {

    ImageView imgViewAlbumArt;
    ImageButton btnNumOfLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_main_screen);

    }
}