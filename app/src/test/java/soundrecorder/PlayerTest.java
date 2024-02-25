package soundrecorder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import soundrecorder.resource.Player;

import javax.sound.sampled.LineUnavailableException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Nested
    class GetPlayer {
        @Test
        void shouldReturnTheSameAudioPlayerInstanceForMultipleGetPlayerCalls() {
            Player playerOne = Player.getPlayer();
            Player playerTwo = Player.getPlayer();

            assertEquals(playerOne, playerTwo);
        }
    }

    @Nested
    class Play {
        @Test
        void shouldPlayTheGivenByteArray() throws LineUnavailableException {
            Player player = Player.getPlayer();
            byte[] audioByteStream = {1, 2, 3, 1, 2, 3};
            player.play(audioByteStream);
        }
    }
}
