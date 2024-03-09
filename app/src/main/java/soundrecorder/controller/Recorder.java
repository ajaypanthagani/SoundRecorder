package soundrecorder.controller;

import soundrecorder.resource.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

public class Recorder {
    private static soundrecorder.resource.Recorder recorder;
    private static Player player;

    private static byte[] recording;

    private static Thread recorderThread;

    public static void init() throws LineUnavailableException {
        int SAMPLE_RATE = 44100;
        int SAMPLE_SIZE_IN_BITS = 24;
        int CHANNELS = 1;
        boolean SIGNED = true;
        boolean BIG_ENDIAN = true;

        AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        recorder = new soundrecorder.resource.Recorder(format);
        player = new Player(format);
    }

    public static void start() {
        recorderThread = new Thread(recorder::start);
        recorderThread.start();
        System.out.println("recording....");
    }

    public static void stop() {
        recorder.stop();
        try {
            recorderThread.join();
        } catch (InterruptedException e) {
            System.out.println("unable to join the thread: " + e.getMessage());
        }
        recording = recorder.getAudioByteStream();
        System.out.println("stopped recording!");
    }

    public static void play() {
        System.out.println("playing the audio....");
        try {
            player.play(recording);
        } catch (LineUnavailableException e) {
            System.out.println("unable to play: " + e.getMessage());
        }
    }
}
