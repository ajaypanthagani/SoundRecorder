package soundrecorder.resource;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

public class Recorder {
    private static final int SAMPLE_RATE = 44100;
    private static final int SAMPLE_SIZE_IN_BITS = 24;
    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
    private final TargetDataLine line;
    private boolean isRecording = false;
    private byte[] audioByteStream;

    private Recorder() throws LineUnavailableException {
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        line = AudioSystem.getTargetDataLine(audioFormat);
        line.open(audioFormat);
    }

    public static Recorder getRecorder() {
        return SingletonHolder.INSTANCE;
    }

    public void start() {
        if (!isRecording) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[line.getBufferSize() / 5];

            line.start();
            isRecording = true;

            while (isRecording) {
                int bytesRead = line.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
            }

            audioByteStream = outputStream.toByteArray();
        }
    }

    public void stop() {
        isRecording = false;
        line.stop();
        line.close();
    }

    public byte[] getAudioByteStream() {
        return audioByteStream;
    }

    private static class SingletonHolder {
        private static final Recorder INSTANCE;

        static {
            try {
                INSTANCE = new Recorder();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
