package components;

import controller.GameController;
import entity.GridStatus;
import minesweeper.GamePanel;
import minesweeper.Login;
import minesweeper.MainFrame;
import minesweeper.Save;
import music.Music;
import music.Music2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GridComponent extends BasicComponent implements java.io.Serializable {
    public static int gridSize = 30;
    public static Color color = Color.CYAN;
    public int row;
    public int col;
    private int content = 0;
    int seconds = 0;
    public static String gridStyle = "/art/maskgrid.png";

    public GridComponent(int x, int y) {
        this.setSize(gridSize, gridSize);
        this.row = x;
        this.col = y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void inner() {
        repaint();
    }


    /*public void reveal(int x, int y) {
        for (int m = Math.max(0, x - 1); m < Math.min(chessboard.length, x + 2); m++) {
            for (int n = Math.max(0, y - 1); n < Math.min(chessboard[m].length, y + 2); n++) {

            }
        }
    }*/

    @Override
    public void onMouseLeftClicked() {

        System.out.printf("Gird (%d,%d) is left-clicked.\n", row, col);
        saveTmp();
        GameController.ini = this;

//        for (GridComponent[] gridComponents : proxy.getMineField()) {
//            for (GridComponent gridComponent : gridComponents) {
//                System.out.println(gridComponent);
//            }
//        }
        if (GameController.clickCount == 0) {
            GamePanel.prepareMines(this.row, this.col);
        }

        if (status == GridStatus.Covered) {
            MainFrame.controller.addclickCount();
            MainFrame.controller.addclickCount1();

//            status = GridStatus.Clicked;
            GamePanel.openGrid(this.row, this.col);
            if (this.content == -1) {
                if (GameController.onTurn.picture == 0) {
                    MainFrame.controller.getOnTurnPlayer().costScore();
                } else if (GameController.onTurn.picture == 2) {
                    JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能\"拉扯大师\"！"));
                    MainFrame.controller.getOnTurnPlayer().setScore(MainFrame.controller.getOnTurnPlayer().getScore() * 5 / 6 + 1);
                } else if (GameController.onTurn.picture == 1) {
                    if (GameController.clickCount1 == 1) {
                        JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能\"开路先锋\"！"));
                    }
                    if (GameController.clickCount1 != 1) {
                        GameController.onTurn.costScore();
                    }
                }
                /*                Login.costrest();*/
                this.status = GridStatus.Bombed;
                new Music().start();
                System.out.println("BOMB~~");

            }

            if (GameController.clickCount1 >= Login.n && Login.player1.getRobot() != 3) {
                MainFrame.controller.nextTurn();
//                GameController.clickCount1=0;
            }
            repaint();
            scoreBoardUpdate();
            MainFrame.scoreBoard1.update();
            MainFrame.scoreBoard2.update();
            upperUpdate();
            MainFrame.controller.isWin();
        }


        //TODO: 在左键点击一个格子的时候，还需要做什么？
        //return new GridComponent(row, col);
    }

    @Override
    public void onMouseRightClicked() {

        System.out.printf("Gird (%d,%d) is right-clicked.\n", row, col);
        saveTmp();
        if (status == GridStatus.Covered) {
            MainFrame.controller.addclickCount();
            MainFrame.controller.addclickCount1();
            if (this.content == -1) {
                MainFrame.controller.getOnTurnPlayer().addScore();
                if (GameController.onTurn.picture == 0 && GameController.clickCount1 == 1) {
                    JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能\"开门大吉\"！"));
                    MainFrame.controller.getOnTurnPlayer().addScore();
                }
                new Music2().start();
                this.status = GridStatus.Flag;
                /*                Login.costrest();*/
            }
            if (this.content != -1) {
                MainFrame.controller.getOnTurnPlayer().addMistake();
//                if (MainFrame.controller.getOnTurnPlayer().picture == 1) {
//                    MainFrame.controller.getOnTurnPlayer().setMistake(MainFrame.controller.getOnTurnPlayer().getMistake() / 2);
//                }
//                MainFrame.controller.getOnTurnPlayer().addError();
                JOptionPane.showMessageDialog(null, "判断错啦~", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                status = GridStatus.Clicked;
                repaint();
            }
            if (GameController.clickCount1 >= Login.n && Login.player1.getRobot() != 3) {
                MainFrame.controller.nextTurn();
            }
            scoreBoardUpdate();
            MainFrame.scoreBoard1.update();
            MainFrame.scoreBoard2.update();
            upperUpdate();
            MainFrame.controller.isWin();
        }

        //TODO: 在右键点击一个格子的时候，还需要做什么？
    }

    private void saveTmp() {
        try {
            Save newSave = new Save(Login.save);
            File saving = new File(System.getProperty("java.io.tmpdir") + "/save" + GameController.clickCount);
            saving.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(saving.getAbsoluteFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newSave);
               /* out.writeObject(p1);
                out.writeObject(p2);
                out.writeObject(turnCounter);*/
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + saving.getAbsoluteFile());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void upperUpdate() {
        MainFrame.label1.setText("待开：" + (Login.xCount * Login.yCount - MainFrame.controller.getGamePanel().getOpenedCount()));
        MainFrame.label2.setText("  已开：" + MainFrame.controller.getGamePanel().getOpenedCount());
        MainFrame.label4.setText("  剩余雷数：" + MainFrame.controller.getGamePanel().getRemainMines());
        MainFrame.label5.setText("  点击数：" + GameController.clickCount);
    }

    public void draw(Graphics g) {
        if (status == GridStatus.Covered && isTouch) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            ImageIcon icon = new ImageIcon(getClass().getResource("/art/touchedgrid.jpg"));
            Image img = icon.getImage();
            g.drawImage(img, 1, 1, icon.getImageObserver());
        }
        if (status == GridStatus.Covered && !isTouch) {

            g.setColor(color);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            ImageIcon icon = new ImageIcon(getClass().getResource(gridStyle));
            Image img = icon.getImage();
            g.drawImage(img, 1, 1, icon.getImageObserver());
        }
        if (status == GridStatus.Clicked) {
            if (GameController.clickCount != 0) {
                g.setColor(color);
                g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
                g.setColor(Color.GRAY);
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
//            ActionListener taskPerformer = new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Timer timer = (Timer) e.getSource();
//                    seconds++;
//                }
//
//            };
//            Timer timer = new Timer(300, taskPerformer);
//            timer.start();
            /*if(seconds==1){
                g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/maskgrid2.png"));
                Image img = icon.getImage();
                g.drawImage(img, 1, 1, icon.getImageObserver());
            }*/
            switch (this.content) {
                case 1:
                    g.setColor(new Color(178, 34, 34));
                    break;
                case 2:
                    g.setColor(new Color(100, 149, 237));
                    break;
                case 3:
                    g.setColor(new Color(240, 230, 140));
                    break;
                case 4:
                    g.setColor(new Color(210, 105, 30));
                    break;
                case 5:
                    g.setColor(new Color(50, 205, 50));
                    break;
                case 6:
                    g.setColor(new Color(128, 0, 0));
                    break;
                case 7:
                    g.setColor(new Color(95, 158, 160));
                    break;
                case 8:
                    g.setColor(new Color(0, 0, 0));
                    break;
            }
            Font f5 = new Font("Purisa", Font.BOLD + Font.ITALIC, 22);
            g.setFont(f5);
            if (this.content != 0) {
                g.drawString(Integer.toString(content), getWidth() / 2 - 7, getHeight() / 2 + 7);
            } else {
//                ImageIcon icon = new ImageIcon(getClass().getResource("/art/flower.jpg"));
//                Image img = icon.getImage();
//                g.drawImage(img, 1, 1, icon.getImageObserver());
            }
        }
        if (status == GridStatus.Flag) {

            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            // g.drawString("F", getWidth() / 2 - 5, getHeight() / 2 + 5);
            ImageIcon icon = new ImageIcon(getClass().getResource("/art/flag.png"));
            Image img = icon.getImage();
            g.drawImage(img, 1, 1, icon.getImageObserver());
            this.setSize(gridSize, gridSize);
            repaint();
        }
        if (status == GridStatus.Bombed) {
            int ntime = MainFrame.seconds - 1 + 1;
//            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            // g.drawString("F", getWidth() / 2 - 5, getHeight() / 2 + 5);

            ImageIcon icon = new ImageIcon(getClass().getResource("/art/bomb.gif"));
            Image img = icon.getImage();
            g.drawImage(img, 1, 1, icon.getImageObserver());
            if (MainFrame.seconds - ntime >= 1) ;
            {
                ImageIcon icon1 = new ImageIcon(getClass().getResource("/art/mine12.jpg"));
                Image img1 = icon1.getImage();
                g.drawImage(img1, 1, 1, icon.getImageObserver());
            }
            this.setSize(gridSize, gridSize);
            repaint();
        }
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }

    public GridStatus getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "GridComponent{" +
                "row=" + row +
                ", col=" + col +
                ", status=" + status +
                ", content=" + content +
                '}';
    }

    public int getContent() {
        return this.content;
    }

    public static void scoreBoardUpdate() {
        if (!MainFrame.scoreBoard1.checkTurn.getText().equals(""))
            MainFrame.scoreBoard1.checkTurn.setText("还剩" + (Login.n - GameController.clickCount1) + "次点击");
        if (!MainFrame.scoreBoard2.checkTurn.getText().equals(""))
            MainFrame.scoreBoard2.checkTurn.setText("还剩" + (Login.n - GameController.clickCount1) + "次点击");
    }

    public boolean isOpenAll() {
        int remainCount = 0;
        for (int i = 0; i < Login.xCount; i++) {
            for (int i1 = 0; i1 < Login.yCount; i1++) {
                if (GamePanel.mineField[i][i1].getStatus() == GridStatus.Covered) {
                    remainCount++;
                }
            }
        }
        return remainCount == Login.xCount * Login.yCount;
    }
}
