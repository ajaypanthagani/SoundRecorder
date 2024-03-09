package soundrecorder.component;

import soundrecorder.interfaces.Subscriber;

import javax.swing.*;
import java.awt.*;

public class SineWaveAudioVisualizer extends JPanel implements Subscriber<byte[]> {
    private final int width;
    private final int height;
    private byte[] audioData;

    public SineWaveAudioVisualizer(int width, int height) {
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (audioData != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);

            int dataSize = audioData.length;
            int sampleRate = 44100;
            int bytesInSample = 3;
            int samplesPerSecond = sampleRate / bytesInSample;
            int numOfSecondsToBeDrawn = 5;
            int startIndex = Math.max(0, dataSize - samplesPerSecond * numOfSecondsToBeDrawn);

            double dx = (double) width / (samplesPerSecond * numOfSecondsToBeDrawn);
            int amplitude = 50;
            double dy = (double) height / (amplitude * 2);
            int centerY = height / 2;

            for (int i = startIndex; i < dataSize - bytesInSample; i += bytesInSample) {
                int x1 = (int) ((i - startIndex) * dx);
                int y1 = centerY + (int) (audioData[i] * dy);
                int x2 = (int) (((i + bytesInSample) - startIndex) * dx);
                int y2 = centerY + (int) (audioData[i + bytesInSample] * dy);

                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    @Override
    public void onNotify(byte[] data) {
        audioData = data;
        repaint();
    }
}
