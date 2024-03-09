package soundrecorder.resource;

import soundrecorder.interfaces.Provider;
import soundrecorder.interfaces.Subscriber;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Recorder implements Provider<byte[]> {
    private final TargetDataLine line;
    List<Subscriber<byte[]>> subscribers;
    private boolean isRecording = false;
    private byte[] audioByteStream;

    public Recorder(AudioFormat format) throws LineUnavailableException {
        subscribers = new ArrayList<>();
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
                notifyAllSubscribers(audioByteStream);
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

    public void subscribe(Subscriber<byte[]> s) {
        subscribers.add(s);
    }

    public void unsubscribe(Subscriber<byte[]> s) {
        subscribers.remove(s);
    }

    public void notifyAllSubscribers(byte[] data) {
        for (var subscriber : subscribers) {
            subscriber.onNotify(data);
        }
    }
}
