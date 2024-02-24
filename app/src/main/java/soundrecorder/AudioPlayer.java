package soundrecorder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {
    private static final int SAMPLE_RATE = 44100;
    private static final int SAMPLE_SIZE_IN_BITS = 24;
    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;

    private final SourceDataLine line;
    private final AudioFormat audioFormat;

    private AudioPlayer() throws LineUnavailableException {
        this.audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        this.line = AudioSystem.getSourceDataLine(audioFormat);
    }

    public static AudioPlayer getPlayer() {
        return SingletonHolder.INSTANCE;
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

    private static class SingletonHolder {
        private static final AudioPlayer INSTANCE;

        static {
            try {
                INSTANCE = new AudioPlayer();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
