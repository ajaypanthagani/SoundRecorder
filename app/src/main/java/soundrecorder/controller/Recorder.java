package soundrecorder.controller;

import soundrecorder.resource.Player;

import javax.sound.sampled.LineUnavailableException;

public class Recorder {
    private static soundrecorder.resource.Recorder recorder;
    private static Player player;

    private static byte[] recording;

    private static Thread recorderThread;

    public static void init() {
        recorder = soundrecorder.resource.Recorder.getRecorder();
        player = Player.getPlayer();
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

    public static void exit() {
        System.out.println("exiting the sound recorder... thanks!");
        player = null;
        recorder = null;
        System.exit(0);
    }
}
