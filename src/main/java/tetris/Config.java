package tetris;

import java.awt.*;

/*****
 * Copyright (c) 2023 Renat Salimov
 **/

class Config {

    static final int FIELD_WIDTH = 10, FIELD_HEIGHT = 20, CELLS_GAP = 0;
    static final Color[] figureColors = { Color.BLUE, Color.MAGENTA, Color.CYAN, Color.RED, Color.GREEN, Color.YELLOW };
    static final int CELL_SIZE = (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.89) / FIELD_HEIGHT);

    static Color[][] glass = new Color[FIELD_WIDTH][FIELD_HEIGHT];
    static int figurePosX, figurePosY, keyCode, DELAY = 1000;
    static boolean figureIsLanded, PAUSE, playBeep = true;
    static long timer = System.currentTimeMillis();

    static KeyListener keyListener = new KeyListener();
    static GamePane pane = new GamePane();
    static MainFrame frame = new MainFrame("Tetris Classic 2023");

    static Color glassColor = frame.getBackground();

    static int figure, subFigure;
    static Color figureColor;

    static int[][][][] figures = {
            {
                {   { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 }  },
                {   { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }  }
            },
            {
                {   { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }  }
            },
            {
                {   { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 }  },
                {   { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 }  },
                {   { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 }  },
                {   { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 }  }
            },
            {
                {   { 1, 0 }, { 1, 1 }, { 1, 2 }, { 0, 2 }  },
                {   { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 }  },
                {   { 0, 0 }, { 1, 0 }, { 0, 1 }, { 0, 2 }  },
                {   { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 }  }
            },
            {
                {   { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 }  },
                {   { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }  },
            },
            {
                {   { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 }  },
                {   { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 }  },
            },
            {
                {   { 1, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 }  },
                {   { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 }  },
                {   { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 }  },
                {   { 1, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 }  }
            },
    };

}
