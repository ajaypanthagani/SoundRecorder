/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package soundrecorder;


import soundrecorder.component.RecorderComponent;
import soundrecorder.resource.Player;
import soundrecorder.resource.Recorder;
import soundrecorder.util.Config;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

public class App {

    public static void main(String[] args) throws LineUnavailableException {
        int SAMPLE_RATE = 44100;
        int SAMPLE_SIZE_IN_BITS = 24;
        int CHANNELS = 1;
        boolean SIGNED = true;
        boolean BIG_ENDIAN = true;

        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        Recorder recorder = new Recorder(format);
        Player player = new Player(format);
        Config.init();
        SwingUtilities.invokeLater(() -> {
            try {
                new RecorderComponent(recorder, player);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
