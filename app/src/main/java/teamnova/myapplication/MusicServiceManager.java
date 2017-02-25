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

    private MusicService musicService;
    private Activity activity;
    private int musicResource;

    public static boolean OK_READY = false;

    public MusicServiceManager(Activity activity, int musicResource) {

        this.activity = activity;
        this.musicResource = musicResource;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MusicService.LocalBinder localBinder = (MusicService.LocalBinder) service;
            musicService = localBinder.getService();
            musicService.start();

            OK_READY = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private final ServiceConnection serviceConnection2 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MusicService.LocalBinder localBinder = (MusicService.LocalBinder) service;
            musicService = localBinder.getService();
            musicService.start();
            musicService.pause();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public int getMusicResource() {
        return musicResource;
    }

    public boolean isPlaying() {

        return musicService.isPlaying();
    }

    public void seekTo(int seek) {

        if(musicService != null)
            musicService.seekTo(seek);
    }

    public int getCurrentPosition() {

        if(musicService != null)
            return musicService.getCurrentPosition();
        else
            return 0;
    }

    public int getDuration() {

        if(musicService != null)
            return musicService.getDuration();
        else
            return 0;
    }

    public void start() {

        Intent intent = new Intent(activity, MusicService.class);
        intent.putExtra("music_resource", musicResource);

        if(musicService != null && musicService.isPlaying()) {

            musicService.stop();
        }

        activity.bindService(intent, serviceConnection, MusicService.CONFIG_DEFAULT);
    }

    public void startOnPause() {

        Intent intent = new Intent(activity, MusicService.class);
        intent.putExtra("music_resource", musicResource);

        if(musicService != null && musicService.isPlaying()) {

            musicService.stop();
        }

        activity.bindService(intent, serviceConnection2, MusicService.CONFIG_DEFAULT);
    }

    public void pause() {

        musicService.pause();
    }

    public void stop() {

        if(musicService != null && musicService.isPlaying())
            musicService.stop();

        OK_READY = false;
    }

    public void restart() {

        if(musicService != null)
            musicService.restart();
    }
}
