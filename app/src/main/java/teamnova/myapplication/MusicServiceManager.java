package teamnova.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by gddjr on 2017-02-25.
 */

public class MusicServiceManager {

    private MusicService mp3Service;
    private Activity activity;
    private int musicResource;

    public MusicServiceManager(Activity activity, int musicResource) {

        this.activity = activity;
        this.musicResource = musicResource;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MusicService.LocalBinder localBinder = (MusicService.LocalBinder) service;
            mp3Service = localBinder.getService();
            mp3Service.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void start() {

        Intent intent = new Intent(activity, MusicService.class);
        intent.putExtra("music_resource", musicResource);

        activity.bindService(intent, serviceConnection, MusicService.CONFIG_DEFAULT);
    }

    public void pause() {

        mp3Service.pause();
    }

    public void stop() {

        mp3Service.stop();
    }

    public void restart() {

        mp3Service.restart();
    }
}
