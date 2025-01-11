package org.whitenoiseplayer;

import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("CallToPrintStackTrace")
@RestController
@RequestMapping("/api/noise") // Base path for the API
@CrossOrigin(origins = "*") // Allow all devices on the network
public class NoiseController {

    private Clip clip;
    private byte[] audioBytes;
    private AudioFormat audioFormat;
//    private static final String path = "C:/Users/David/Downloads/Brown.wav"; //Uncomment this line when debugging
    private static final String path = "/home/pi/BrownNoise/Brown.wav"; //Comment out when debugging

    public NoiseController() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Failed to initialize audio clip", e);
        }

        // Load the entire audio file into memory during initialization
        try {
            loadAudioData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load audio data", e);
        }

    }

    private void loadAudioData() throws UnsupportedAudioFileException, IOException {
        File audioFile = new File(path);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        audioFormat = audioInputStream.getFormat();
        audioBytes = audioInputStream.readAllBytes();
        audioInputStream.close(); // Close stream after reading
    }

    @PostMapping("/play")
    public String playBrownNoise() {
        if (clip.isRunning()) {
            System.out.println("/play: Noise is already playing.");
            return "Noise is already playing.";
        }
        try {
            InputStream byteStream = new ByteArrayInputStream(audioBytes);
            AudioInputStream audioInputStream = new AudioInputStream(byteStream, audioFormat, audioBytes.length / audioFormat.getFrameSize());
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("/play: Brown Noise started playing.");
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
            System.out.println("/stop: Brown Noise stopped.");
            return "Brown Noise stopped.";
        }
        System.out.println("/stop: Noise is not currently playing.");
        return "Noise is not currently playing.";
    }
    @GetMapping("/status")
    public Boolean getNoiseStatus() {
        if (clip.isRunning()) {
            System.out.println("/status playing? True.");
            return true;
        } else {
            System.out.println("/status playing? False.");
            return false;
        }
    }
}