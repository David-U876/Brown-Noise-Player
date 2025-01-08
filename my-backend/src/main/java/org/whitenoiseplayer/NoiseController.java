package org.whitenoiseplayer;

import org.springframework.web.bind.annotation.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;

@SuppressWarnings("CallToPrintStackTrace")
@RestController
@RequestMapping("/api/noise") // Base path for the API
public class NoiseController {

    private Clip clip;
//    private static final String path = "C:/Users/David/Downloads/Brown.wav"; //Uncomment this line when debugging
    private static final String path = "/home/pi/BrownNoise/Brown.wav"; //Comment out when debugging

    public NoiseController() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Failed to initialize audio clip", e);
        }
    }

    @PostMapping("/play")
    public String playBrownNoise() {
        if (clip.isRunning()) {
            return "Noise is already playing.";
        }
        try {
            File audioFile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            return "Brown Noise started playing.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to play noise: " + e.getMessage();
        }
    }

    @PostMapping("/stop")
    public String stopNoise() {
        if (clip.isRunning()) {
            clip.stop();
            clip.close();
            return "Brown Noise stopped.";
        }
        return "Noise is not currently playing.";
    }
}