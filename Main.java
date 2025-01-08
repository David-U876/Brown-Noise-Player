package org.whitenoiseplayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.util.Scanner;

@SuppressWarnings("CallToPrintStackTrace")

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            System.out.println("Type 'Brown' to play Brown Noise");
            String input = scanner.nextLine();
            if (input.equals("Brown")) {
                playBrownNoise(clip);
                break;
            }
        }

        while (true) {
            System.out.println("Type 'Stop' to stop");
            String input = scanner.nextLine();
            if (input.equals("Stop")) {
                assert clip != null;
                clip.stop();
                clip.close();
                break;
            }
        }
    }

    private static void playBrownNoise(Clip clip) {
        try {
            File audioFile = new File("C:/Users/David/Downloads/Brown.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}