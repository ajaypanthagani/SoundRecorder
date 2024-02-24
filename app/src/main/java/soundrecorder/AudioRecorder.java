package soundrecorder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

public class AudioRecorder {
    private static final int SAMPLE_RATE = 44100;
    private static final int SAMPLE_SIZE_IN_BITS = 16;
    private static final int CHANNELS = 2;
    private static final int FRAME_SIZE = 4;
    private static final int FRAME_RATE = 44100;
    private static final boolean BIG_ENDIAN = true;

    private static final AudioFormat AUDIO_FORMAT = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, FRAME_SIZE, FRAME_RATE, BIG_ENDIAN);

    private boolean isRecording = false;
    private final TargetDataLine targetDataLine;

    private AudioRecorder() throws LineUnavailableException {
        targetDataLine = AudioSystem.getTargetDataLine(AUDIO_FORMAT);
        targetDataLine.open(AUDIO_FORMAT);
    }

    public static AudioRecorder getRecorder() {
        return SingletonHolder.INSTANCE;
    }

    public byte[] record() {
        if(!isRecording){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[targetDataLine.getBufferSize() / 5];

            targetDataLine.start();
            isRecording = true;

            while (isRecording) {
                int bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        } else {
            throw new RuntimeException("recording already in progress");
        }
    }

    public void stopRecording() {
        isRecording = false;
        targetDataLine.stop();
        targetDataLine.close();
    }

    private static class SingletonHolder {
        private static final AudioRecorder INSTANCE;

        static {
            try {
                INSTANCE = new AudioRecorder();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
