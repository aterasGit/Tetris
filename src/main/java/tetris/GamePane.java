package tetris;

import javax.swing.*;
import java.awt.*;

import static tetris.Config.*;

/*****
 * Copyright (c) 2023 Renat Salimov
 **/

class GamePane extends JPanel {

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(glassColor);
        Graphics2D g2 = (Graphics2D) g;
        for(int x = 0; x < FIELD_WIDTH; x++)
            for(int y = 0; y < FIELD_HEIGHT; y++)
            {
                if(glass[x][y] == null) continue;
                g2.setColor(glass[x][y]);
                g2.fillRect(x * CELL_SIZE + CELLS_GAP, y * CELL_SIZE + CELLS_GAP,
                        CELL_SIZE - CELLS_GAP, CELL_SIZE - CELLS_GAP);
                g2.setColor(Color.BLACK);
                g2.drawRect(x * CELL_SIZE + CELLS_GAP, y * CELL_SIZE + CELLS_GAP,
                        CELL_SIZE - CELLS_GAP, CELL_SIZE - CELLS_GAP);
            }
    }

}
