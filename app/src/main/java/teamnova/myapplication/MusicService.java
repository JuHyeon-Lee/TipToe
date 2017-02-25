package teamnova.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by gddjr on 2017-02-25.
 */

public class MusicService extends Service {

    public static final int CONFIG_DEFAULT = Context.BIND_AUTO_CREATE;
    private IBinder mBinder = new LocalBinder();
    boolean isPlaying = false;
    MediaPlayer mediaPlayer;
    int musicResource;
    int pos;

    public class LocalBinder extends Binder {

        MusicService getService() {

            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        try {

            musicResource = intent.getIntExtra("music_resource", 0);

            if(musicResource == 0) {

                throw new ResourceNotFoundException();
            }
        } catch (ResourceNotFoundException e) {

            e.getMessage();
        }
        return mBinder;
    }

    public void start() {

        if(!isPlaying) {

            mediaPlayer = MediaPlayer.create(
                    getApplicationContext(), musicResource);

            mediaPlayer.setLooping(false);
            mediaPlayer.start();

            isPlaying = true;
        }
    }

    public void pause() {

        if(isPlaying) {

            pos = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();

            isPlaying = false;
        }
    }

    public void stop() {

        if(isPlaying) {

            mediaPlayer.stop();
            mediaPlayer.release();

            isPlaying = false;
        }
    }

    public void restart() {

        if(!isPlaying) {

            mediaPlayer.seekTo(pos);
            mediaPlayer.start();

            isPlaying = true;
        }
    }

    class ResourceNotFoundException extends Exception {

        private String message;

        public ResourceNotFoundException() {

            message = "it's not found to receive resource.";
        }

        @Override
        public String getMessage() {

            System.out.println("ResourceNotFoundException : "+message);

            return message;
        }
    }
}
