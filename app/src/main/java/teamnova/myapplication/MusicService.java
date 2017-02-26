package teamnova.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
    static MediaPlayer mediaPlayer;
    int musicResource;
    int pos;

    boolean isReleased;

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

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);


    }

    public int getMusicResource() {
        return musicResource;
    }

    public boolean isPlaying() {

        if(mediaPlayer != null && !isReleased)
            return mediaPlayer.isPlaying();
        else
            return false;
    }

    public int getCurrentPosition() {

        if(mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        else
            return 0;
    }

    public int getDuration() {

        if(mediaPlayer != null)
            return mediaPlayer.getDuration();
        else
            return 0;
    }

    public void seekTo(int seek) {

        if(mediaPlayer != null)
            mediaPlayer.seekTo(seek);
    }

    public void start() {

        mediaPlayer = MediaPlayer.create(
                getApplicationContext(), musicResource);

        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        isReleased = false;
    }

    public void pause() {

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {

            if(mediaPlayer != null) {

                pos = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
            }
        }
    }

    public void stop() {

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {

            if(mediaPlayer != null) {

                mediaPlayer.stop();
                mediaPlayer.reset();
                isReleased = true;
            }
            this.stopSelf();
        }
    }

    public void restart() {

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {

            mediaPlayer.seekTo(pos);
            mediaPlayer.start();
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
