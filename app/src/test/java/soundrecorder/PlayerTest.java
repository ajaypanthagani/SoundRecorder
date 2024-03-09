package soundrecorder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import soundrecorder.resource.Player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

class PlayerTest {
    static AudioFormat format;

    @BeforeAll
    static void setup() {
        int SAMPLE_RATE = 44100;
        int SAMPLE_SIZE_IN_BITS = 24;
        int CHANNELS = 1;
        boolean SIGNED = true;
        boolean BIG_ENDIAN = true;

        format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    }

    @Nested
    class Play {
        @Test
        void shouldPlayTheGivenByteArray() throws LineUnavailableException {
            Player player = new Player(format);
            byte[] audioByteStream = {1, 2, 3, 1, 2, 3};
            player.play(audioByteStream);
        }
    }
}
