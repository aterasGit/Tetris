package tetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static tetris.Config.*;

/*****
 * Copyright (c) 2023 Renat Salimov
 **/

class AboutDialog extends JDialog {

    JPanel aboutPanel = new JPanel() {
        private Image image;
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                image = ImageIO.read(Objects.requireNonNull(
                        Tetris.class.getClassLoader().getResource("about.png"))
                );
            }
            catch (IOException ignored) {}
            g.drawImage(image, 0, 0,  441, 171,null);
        }
    };

    AboutDialog(String title) {
        setTitle(title);
        setLocation(frame.getX() - ((frame.getX() + 441 + frame.getInsets().left * 2) - (frame.getX() + frame.getWidth())) / 2,
                frame.getY() + (int) frame.getSize().getHeight() / 4);
        getContentPane().setPreferredSize(new Dimension(441, 171));
        setResizable(false);
        pack();
        aboutPanel.setBounds(0, 0, 353, 137);
        add(aboutPanel);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    void showPanel() {
        setVisible(true);
    }
}
