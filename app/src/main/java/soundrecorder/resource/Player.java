package soundrecorder.resource;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Player {

    private final SourceDataLine line;
    private final AudioFormat audioFormat;

    public Player(AudioFormat format) throws LineUnavailableException {
        this.audioFormat = format;
        this.line = AudioSystem.getSourceDataLine(format);
    }

    public void play(byte[] audioByteStream) throws LineUnavailableException {
        if (audioByteStream == null || audioByteStream.length == 0) {
            return;
        }
        this.line.open(this.audioFormat);
        this.line.start();
        this.line.write(audioByteStream, 0, audioByteStream.length);
        this.line.drain();
        this.line.stop();
        this.line.close();
    }
}
