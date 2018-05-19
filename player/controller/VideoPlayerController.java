package player.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class VideoPlayerController implements Initializable {
    private boolean isPlaying = false;
    private MediaPlayer player;
    private File selectedFile;

    @FXML
    private MediaView mvPlayer;
    @FXML
    private MenuBar mbMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mbMenu.getMenus().get(0).getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Chose Video");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4"));
                selectedFile = fileChooser.showOpenDialog(null);
                Media m = new Media(Paths.get(selectedFile.getAbsolutePath()).toUri().toString());
                player = new MediaPlayer(m);
                mvPlayer.setMediaPlayer(player);
            }
        });

        mbMenu.getMenus().get(0).getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Video");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4"));
                try {
                    Files.copy(selectedFile.toPath(), fileChooser.showSaveDialog(null).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void handlePlay() {
        if (!isPlaying) {
            if (player != null) {
                player.play();
                isPlaying = true;
            }
        }
    }

    public void handlePause() {
        if (isPlaying) {
            isPlaying = false;
            player.pause();
        }

    }

}
