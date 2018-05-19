package player.controller;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VideoPlayerController {
    private MediaPlayer player;
    public MediaView mvPlayer;
    public Button btnPlay;
    private boolean isPlaying = false;

    public void handlePlay(){
        if(!isPlaying) {
            if(player == null) {
                Media m = new Media(getClass().getResource("/player/fit.mp4").toExternalForm());
                player = new MediaPlayer(m);
                mvPlayer.setMediaPlayer(player);
            }
            player.play();
            isPlaying = true;
        }
    }

    public void  handlePause() {
        if(isPlaying) {
            isPlaying = false;
            player.pause();
        }

    }
}
