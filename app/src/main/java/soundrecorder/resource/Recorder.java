package soundrecorder.resource;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

public class Recorder {
    private final TargetDataLine line;
    private boolean isRecording = false;
    private byte[] audioByteStream;

    public Recorder(AudioFormat format) throws LineUnavailableException {
        line = AudioSystem.getTargetDataLine(format);
        line.open(format);
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
                audioByteStream = outputStream.toByteArray();
            }
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
}
