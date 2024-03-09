package soundrecorder.component;

import soundrecorder.resource.Player;
import soundrecorder.resource.Recorder;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Objects;

public class RecorderComponent extends JFrame {
    JButton start;
    JButton stop;
    JButton play;

    public RecorderComponent(Recorder recorder, Player player) throws LineUnavailableException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(System.getProperty("APP_NAME"));
        setTaskBarIcon();

        JPanel buttonPanel = new JPanel(new GridLayout());

        this.start = createIconButton(System.getProperty("RECORD_IMG"));
        this.stop = createIconButton(System.getProperty("STOP_IMG"));
        this.play = createIconButton(System.getProperty("PLAY_IMG"));

        this.start.addActionListener(e -> new Thread(recorder::start).start());
        this.stop.addActionListener(e -> recorder.stop());
        this.play.addActionListener(e -> {
            try {
                player.play(recorder.getAudioByteStream());
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(play);

        SineWaveAudioVisualizer soundWaveVisualizer = new SineWaveAudioVisualizer(300, 175);
        soundWaveVisualizer.setBackground(Color.black);
        recorder.subscribe(soundWaveVisualizer);

        getContentPane().setLayout(new GridLayout(2, 1));
        getContentPane().add(buttonPanel);
        getContentPane().add(soundWaveVisualizer, BorderLayout.CENTER);

        this.setSize(300, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private ImageIcon createScaledIcon(String path) {
        URL imageURL = ClassLoader.getSystemClassLoader().getResource(path);
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            int iconWidth = Math.min(icon.getIconWidth(), 30);
            int iconHeight = Math.min(icon.getIconHeight(), 30);

            return new ImageIcon(icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_FAST));
        } else {
            System.err.println("Resource not found: " + path);
            return null;
        }
    }

    private JButton createIconButton(String path) {
        ImageIcon icon = createScaledIcon(path);
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }

    private void setTaskBarIcon() {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            String voiceImgPath = System.getProperty("VOICE_IMG");
            Objects.requireNonNull(voiceImgPath, "VOICE_IMG property is not set");

            URL img = Objects.requireNonNull(classLoader.getResource(voiceImgPath), "Image resource not found");
            BufferedImage image = javax.imageio.ImageIO.read(img);

            Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        } catch (Exception e) {
            System.out.println("Failed to set application icon: " + e.getMessage());
        }
    }
}
