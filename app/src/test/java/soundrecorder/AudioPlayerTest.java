package soundrecorder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioPlayerTest {

    @Nested
    class GetPlayer {
        @Test
        void shouldReturnTheSameAudioPlayerInstanceForMultipleGetPlayerCalls() {
            AudioPlayer audioPlayerOne = AudioPlayer.getPlayer();
            AudioPlayer audioPlayerTwo = AudioPlayer.getPlayer();

            assertEquals(audioPlayerOne, audioPlayerTwo);
        }
    }

    @Nested
    class Play {
        @Test
        void shouldPlayTheGivenByteArray() throws LineUnavailableException {
            AudioPlayer audioPlayer = AudioPlayer.getPlayer();
            byte[] audioByteStream = {1, 2, 3, 1, 2, 3};
            audioPlayer.play(audioByteStream);
        }
    }
}
