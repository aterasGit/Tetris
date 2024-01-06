package tetris;

import java.awt.event.KeyEvent;
import static tetris.Config.*;

/*****
 * Copyright (c) 2023 Renat Salimov
 **/

 class KeyListener implements java.awt.event.KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 37 & e.getKeyCode() <= 40 || e.getKeyCode() == 12 || e.getKeyCode() == 32 ||
                e.getKeyCode() == 81 || e.getKeyCode() == 65 || e.getKeyCode() == 66 || e.getKeyCode() == 80) {
            keyCode = e.getKeyCode();
            PAUSE = false;
        }
    }
    public void keyTyped(KeyEvent e) {} public void keyReleased(KeyEvent e) {}

}
