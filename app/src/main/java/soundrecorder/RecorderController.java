package soundrecorder;

import javax.sound.sampled.LineUnavailableException;

public class RecorderController {
    private static AudioRecorder audioRecorder;
    private static AudioPlayer audioPlayer;

    private static byte[] recording;

    private static Thread recorderThread;

    public static void init() {
        audioRecorder = AudioRecorder.getRecorder();
        audioPlayer = AudioPlayer.getPlayer();
    }

    public static void start() {
        recorderThread = new Thread(audioRecorder::start);
        recorderThread.start();
        System.out.println("recording....");
    }

    public static void stop() {
        audioRecorder.stop();
        try {
            recorderThread.join();
        } catch (InterruptedException e) {
            System.out.println("unable to join the thread: " + e.getMessage());
        }
        recording = audioRecorder.getAudioByteStream();
        System.out.println("stopped recording!");
    }

    public static void play() {
        System.out.println("playing the audio....");
        try {
            audioPlayer.play(recording);
        } catch (LineUnavailableException e) {
            System.out.println("unable to play: " + e.getMessage());
        }
    }

    public static void exit() {
        System.out.println("exiting the sound recorder... thanks!");
        audioPlayer = null;
        audioRecorder = null;
        System.exit(0);
    }
}
