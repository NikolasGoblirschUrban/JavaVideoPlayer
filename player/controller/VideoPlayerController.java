package player.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
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
    @FXML
    private Button btnPlay;
    @FXML
    private Slider sldTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileChooser fileChooser = new FileChooser();
        mbMenu.getMenus().get(0).getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser.setTitle("Chose Video");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4"));
                selectedFile = fileChooser.showOpenDialog(null);

                Media m = new Media(Paths.get(selectedFile.getAbsolutePath()).toUri().toString());
                player = new MediaPlayer(m);
                mvPlayer.setMediaPlayer(player);
                player.setOnEndOfMedia(() -> {
                    player.seek(new javafx.util.Duration(0));
                    setIsPlaying();
                });

                sldTime.maxProperty().bind(Bindings.createDoubleBinding(
                        () -> player.getTotalDuration().toSeconds(),
                        player.totalDurationProperty()));
                player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                    sldTime.setValue(newValue.toSeconds());
                });
            }
        });

        mbMenu.getMenus().get(0).getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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

    public void handleSliderChanged() {
        if (player != null) {
            player.seek(new javafx.util.Duration(sldTime.getValue() * 1000));
        }
    }

    public void handlePlay() {
        setIsPlaying();
    }

    public void handleState() {
        setIsPlaying();
    }

    public void setIsPlaying() {
        if (player != null) {
            if (isPlaying) {
                this.isPlaying = false;
                player.pause();
                btnPlay.setText("Play");
            } else {
                this.isPlaying = true;
                player.play();
                btnPlay.setText("Pause");
            }
        }
    }

}
