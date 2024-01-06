package tetris;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

import static tetris.Config.*;

/*****
 * Copyright (c) 2023 Renat Salimov
 **/

class Tetris {

    private void play() throws InterruptedException {
        setNewFigure();
        while (true) {
            if (!PAUSE) {
                if (figureIsLanded) {
                    checkoutAndBustRows();
                    if (!setNewFigure())
                        resetGame();
                    else {
                        pane.repaint();
                        Thread.sleep(50);
                        DELAY--;
                    }
                }
                if (System.currentTimeMillis() - timer > DELAY) {
                    gravitate();
                    timer = System.currentTimeMillis();
                }
                scanKeys();
            }
            pane.repaint();
            Thread.sleep(50);
        }
    }

    private void scanKeys() {
        switch (keyCode) {
            case 37:
                moveLeft();
                break;
            case 38: case 12:
                rotateFigure();
                break;
            case 39:
                moveRight();
                break;
            case 40:
                gravitate();
                keyCode = 0;
                break;
            case 32:
                forceLanding();
                keyCode = 0;
                break;
            case 65:
                showAboutDialog();
                PAUSE = true;
                break;
            case 66:
                playBeep = !playBeep;
                keyCode = 0;
                break;
            case 80:
                PAUSE = true;
                break;
            case 81:
                System.exit(0);
        }
    }

    private void moveLeft() {
        if (figureIsLanded) return;
        paintFigure(figure, subFigure, null);
        for (int[] currentCell : figures[figure][subFigure])
            if (figurePosX - 1 + currentCell[0] < 0
                    || figurePosY + currentCell[1] >= FIELD_HEIGHT
                    || glass[figurePosX - 1 + currentCell[0]][figurePosY + currentCell[1]] != null) {
                figurePosX++;
                break;
            }
        figurePosX--;
        paintFigure(figure, subFigure, figureColor);
        keyCode = 0;
    }

    private void moveRight() {
        if (figureIsLanded) return;
        paintFigure(figure, subFigure, null);
        for (int[] currentCell : figures[figure][subFigure])
            if (figurePosX + 1 + currentCell[0] >= FIELD_WIDTH
                    || figurePosY + currentCell[1] >= FIELD_HEIGHT
                    || glass[figurePosX + 1 + currentCell[0]][figurePosY + currentCell[1]] != null) {
                figurePosX--;
                break;
            }
        figurePosX++;
        paintFigure(figure, subFigure, figureColor);
        keyCode = 0;
    }

    private void rotateFigure() {
        if (figureIsLanded) return;
        paintFigure(figure, subFigure, null);
        int nextSubfigure = subFigure + 1 == figures[figure].length ? 0 : subFigure + 1;
        for (int[] currentCell : figures[figure][nextSubfigure])
            if (figurePosX + currentCell[0] >= FIELD_WIDTH
                    || figurePosY + currentCell[1] >= FIELD_HEIGHT
                    || glass[figurePosX + currentCell[0]][figurePosY + currentCell[1]] != null) {
                nextSubfigure = subFigure;
                break;
            }
        paintFigure(figure, subFigure = nextSubfigure, figureColor);
        keyCode = 0;
    }

    private void forceLanding() {
        paintFigure(figure, subFigure, null);
        int shortest = FIELD_HEIGHT;
        for (int cell = 0; cell < figures[figure][subFigure].length; cell++) {
            int count = 0;
               for (int y = figurePosY + figures[figure][subFigure][cell][1]; y <= FIELD_HEIGHT; y++) {
                   if (y == FIELD_HEIGHT || glass[figurePosX + figures[figure][subFigure][cell][0]][y] != null)
                       if (count <= shortest) {
                           shortest = count;
                           break;
                       }
                   count++;
               }
        }
        figurePosY = figurePosY + shortest - 1;
        paintFigure(figure, subFigure, figureColor);
        figureIsLanded = true;
    }

    private void checkoutAndBustRows() throws InterruptedException {
        int bustLine;
        while ((bustLine = glassScan()) != -1) {
            for (int x = 0; x < FIELD_WIDTH; x++)
                glass[x][bustLine] = Color.BLACK;
            pane.repaint();
            Thread.sleep(50);
            for (int x = 0; x < FIELD_WIDTH; x++)
                glass[x][bustLine] = null;
            pane.repaint();
            Thread.sleep(50);
            if (playBeep) playBeep();
            for (int y = bustLine - 1; y >= -1; y--)
                for (int x = 0; x < FIELD_WIDTH; x++)
                    glass[x][y + 1] = y >= 0 ? glass[x][y] : null;
        }
    }

    private boolean setNewFigure() {
        Random rnd = new Random();
        figure = rnd.nextInt(figures.length);
        subFigure = 0;
        figureColor = figureColors[rnd.nextInt(figureColors.length)];
        figurePosX = (FIELD_WIDTH - 1) / 2;
        figurePosY = 0;
        figureIsLanded = false;
        return paintFigure(figure, subFigure, figureColor);
    }

    private void gravitate() {
        if (figureIsLanded) return;
        paintFigure(figure, subFigure, null);
        for (int[] currentCell : figures[figure][subFigure])
            if (figurePosY + currentCell[1] + 1 >= FIELD_HEIGHT
                    || glass[figurePosX + currentCell[0]][figurePosY + currentCell[1] + 1] != null) {
                figureIsLanded = true;
                paintFigure(figure, subFigure, figureColor);
                return;
            }
        figurePosY++;
        paintFigure(figure, subFigure, figureColor);
    }

    private boolean paintFigure(int figure, int subFigure, Color color) {
        boolean result = true;
        for (int[] currentCell : figures[figure][subFigure]) {
            if (glass[figurePosX + currentCell[0]][figurePosY + currentCell[1]] != null)
                result = false;
            glass[figurePosX + currentCell[0]][figurePosY + currentCell[1]] = color;
        }
        return result;
    }

    private void resetGame() {
        glass = new Color[FIELD_WIDTH][FIELD_HEIGHT];
        setNewFigure();
        DELAY = 1000;
        keyCode = 0;
    }

    private int glassScan() {
        for (int y = FIELD_HEIGHT - 1; y >= 0; y--) {
            int counter = 0;
            for (int x = 0; x < FIELD_WIDTH; x++) {
                if (glass[x][y] != null)
                    counter++;
                if (counter == FIELD_WIDTH)
                    return y;
            }
        }
        return -1;
    }

    private void playBeep() {
        try {
            AudioInputStream inAudio = AudioSystem.getAudioInputStream
                    (Objects.requireNonNull(Tetris.class.getClassLoader().getResource("tetris.wav")));
            Clip clip = AudioSystem.getClip();
            clip.open(inAudio);
            clip.setFramePosition(0);
            clip.start();
        }
        catch(Exception ignored) {}
    }

    private void showAboutDialog() {
        new AboutDialog("About").showPanel();
    }

    public static void main(String[] args) throws InterruptedException {
        new Tetris().play();
    }
}
