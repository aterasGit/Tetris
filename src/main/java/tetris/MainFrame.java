package tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static tetris.Config.*;

/*****
 * Copyright (c) 2023 Renat Salimov
 **/

class MainFrame extends JFrame {

    MainFrame(String title) {
        super(title);
        setIconImage(
                new ImageIcon(Objects.requireNonNull(Tetris.class.getClassLoader()
                .getResource("tetris.png"))).getImage()
        );
        getContentPane().setPreferredSize(
                new Dimension(FIELD_WIDTH * CELL_SIZE + 1, FIELD_HEIGHT * CELL_SIZE + 1)
        );
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(pane);
        pack();
        setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (getWidth()) / 2, 10);
        addKeyListener(keyListener);
        setVisible(true);
    }

}
