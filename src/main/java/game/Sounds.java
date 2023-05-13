package game;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.LineEvent.Type;
public class Sounds {
    BufferedInputStream temp = (BufferedInputStream) Sounds.class.getResourceAsStream("/sounds/chomp.wav");
    static final int PACSOUND = 1;
    static final int MENUSOUND = 2;
    static final int ENDSOUND = 3;
    static final int KEYSOUND = 4;
    static final int DEATHSOUND = 5;

    /**
     * Plays preloaded .Wav clips
     * @param clipFile - File of clip to play
     */
    private static void playClip(InputStream clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        class AudioListener implements LineListener {
            private boolean done = false;
            @Override public synchronized void update(LineEvent event) {
                Type eventType = event.getType();
                if (eventType == Type.STOP || eventType == Type.CLOSE) {
                    done = true;
                    notifyAll();
                }
            }

            /**
             * Wait to finish a track
             */
            public synchronized void waitUntilDone() throws InterruptedException {
                while (!done) { wait(); }
            }
        }
        AudioListener listener = new AudioListener();
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(clipFile))) {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(listener);
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            try {
                clip.start();
                listener.waitUntilDone();
            } finally {
                clip.close();
            }
        }
    }

    /**
     * Play sound effects in a separate thread
     * @param sound - sound effect to play
     */
    public static void playSound(int sound){
        switch (sound) {
            case PACSOUND -> new Thread(() -> {
                try {
                    playClip(Sounds.class.getResourceAsStream("/sounds/chomp.wav"));
                } catch (Exception e) {
                    System.out.println("Sound playing error !");
                }
            }).start();
            case MENUSOUND -> new Thread(() -> {
                try {
                    playClip(Sounds.class.getResourceAsStream("/sounds/beginning.wav"));
                } catch (Exception e) {
                    System.out.println("Sound playing error !");
                }
            }).start();
            case ENDSOUND -> new Thread(() -> {
                try {
                    playClip(Sounds.class.getResourceAsStream("/sounds/intermission.wav"));
                } catch (Exception e) {
                    System.out.println("Sound playing error !");
                }
            }).start();
            case KEYSOUND -> new Thread(() -> {
                try {
                    playClip(Sounds.class.getResourceAsStream("/sounds/eatfruit.wav"));
                } catch (Exception e) {
                    System.out.println("Sound playing error !");
                }
            }).start();
            case DEATHSOUND -> new Thread(() -> {
                try {
                    playClip(Sounds.class.getResourceAsStream("/sounds/death.wav"));
                } catch (Exception e) {
                    System.out.println("Sound playing error !");
                }
            }).start();
        }
    }

}
