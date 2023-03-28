package controller;

import components.GridComponent;
import entity.GridStatus;
import minesweeper.*;
import entity.Player;
import music.Music;
import music.Music2;

import javax.swing.*;
import java.util.Random;


public class GameController implements java.io.Serializable {


    private Player p1;
    private Player p2;
    public static int clickCount = 0;
    public int clickCounts = 0;
    public static Player onTurn;
    public Player onTurns;
    public GamePanel gamePanel;
    /*    private ScoreBoard scoreBoard;*/
    public static GridComponent ini;
    private static final long serialVersionUID = 42L;
    public static int clickCount1 = 0;

    public int getClickCount() {
        return clickCount;
    }

    public GameController(Player p1, Player p2) {
        this.init(p1, p2);
        onTurn = p2;
//        onTurns=p2;
    }

    /**
     * 初始化游戏。在开始游戏前，应先调用此方法，给予游戏必要的参数。
     *
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public void init(Player p1, Player p2) {
        this.gamePanel = new GamePanel(MainFrame.xCount, MainFrame.yCount, MainFrame.mineCount);
        this.p1 = p1;
        this.p2 = p2;
        onTurn = p2;
//        this.onTurns = p2;
        if(Login.player1.getRobot()!=3) {
            MainFrame.scoreBoard1.checkTurn.setText("还剩" + (Login.n - GameController.clickCount1) + "次点击");
            MainFrame.scoreBoard2.checkTurn.setText("");
        }
        //TODO: 在初始化游戏的时候，还需要做什么？
    }

    /**
     * 进行下一个回合时应调用本方法。
     * 在这里执行每个回合结束时需要进行的操作。
     * <p>
     * (目前这里没有每个玩家进行n回合的计数机制的，请自行修改完成哦~）
     */
    /*public void checkFirst(GridComponent gr) {
        if (gr.getContent() == -1) {
            MainFrame.controller=new GameController(Login.player1,Login.player2);
            new MainFrame();
        }
    }*/
    public void addclickCount() {
        clickCount++;
        MainFrame.scoreBoard1.update();
        MainFrame.scoreBoard2.update();
    }

    public void addclickCount1() {
        clickCount1++;
    }

    public void nextTurn() {

        if (onTurn == p2) {
            onTurn = p1;
//            onTurns = p1;
            MainFrame.scoreBoard1.checkTurn.setText("");
            MainFrame.scoreBoard2.checkTurn.setText("还剩"+(Login.n-GameController.clickCount1)+"次点击");
            System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
            clickCount1 = 0;
            MainFrame.seconds = 0;
            //普通人机
            if (Login.player1.getRobot() == 1) {
//                System.out.println("666");
                for (int i = 0; i < Login.n; ) {
                    Random random = new Random();
                    int x = random.nextInt(GamePanel.mineField.length);
                    int y = random.nextInt(GamePanel.mineField[0].length);
//                    if (GamePanel.mineField[x][y].status == GridStatus.Covered) {
//                        GamePanel.mineField[x][y].status = GridStatus.Clicked;
//                        System.out.printf("Gird (%d,%d) is left-clicked.\n", x, y);
//                        if (GamePanel.chessboard[x][y] == -1) {
//                            GamePanel.mineField[x][y].status = GridStatus.Bombed;
//                            if (GameController.onTurn.picture == 1) {
//                                if (i == 0) {
//                                    JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能、\"开路先锋\"！"));
//                                }
//                                else {
//                                    MainFrame.controller.getOnTurnPlayer().costScore();
//                                }
//                            }
//                            if (GameController.onTurn.picture == 2) {
//                                JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能\"拉扯大师\"！"));
//                                MainFrame.controller.getOnTurnPlayer().setScore(MainFrame.controller.getOnTurnPlayer().getScore() * 5 / 6);
//                            }
//                            /*                            Login.costrest();*/
////                            MainFrame.controller.getOnTurnPlayer().addError();
//                            new Music().start();
//                            System.out.println("BOMB~~");
//                        }
//                        addclickCount();
//                        i++;
//                        gamePanel.repaint();
//                        MainFrame.scoreBoard1.update();
//                        MainFrame.scoreBoard2.update();
//                    }
                    if (GamePanel.mineField[x][y].status == GridStatus.Covered && GamePanel.chessboard[x][y] != -1) {
                        GamePanel.mineField[x][y].status = GridStatus.Clicked;
                        System.out.printf("Gird (%d,%d) is left-clicked.\n", x, y);
                        addclickCount();
                        i++;

                        gamePanel.repaint();
                        MainFrame.scoreBoard1.update();
                        MainFrame.scoreBoard2.update();
                    }
                    if (GamePanel.mineField[x][y].status == GridStatus.Covered && GamePanel.chessboard[x][y] == -1) {
                        GamePanel.mineField[x][y].status = GridStatus.Flag;
                        System.out.printf("Gird (%d,%d) is right-clicked.\n", x, y);
                        new Music2().start();
                        if (GameController.onTurn.picture == 0 && i == 0) {
                            JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能\"开门大吉\"！"));
                            MainFrame.controller.getOnTurnPlayer().addScore();
                        }
                        MainFrame.controller.getOnTurnPlayer().addScore();
                        addclickCount();
                        i++;
                        /*                      Login.costrest();*/
                        gamePanel.repaint();
                        MainFrame.scoreBoard1.update();
                        MainFrame.scoreBoard2.update();
                    }
                    MainFrame.controller.isWin();
                    GridComponent.upperUpdate();
                }
                onTurn = p2;
                MainFrame.seconds = 0;
                MainFrame.scoreBoard2.checkTurn.setText("");
                MainFrame.scoreBoard1.checkTurn.setText("还剩"+(Login.n-GameController.clickCount1)+"次点击");
                System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
            }
            //高级人机
            if (Login.player1.getRobot() == 2) {
//                System.out.println("666");
                for (int i = 0; i < Login.n; ) {
                    Random random = new Random();
                    int x = random.nextInt(GamePanel.mineField.length);
                    int y = random.nextInt(GamePanel.mineField[0].length);
                    if (GamePanel.mineField[x][y].status == GridStatus.Covered && GamePanel.chessboard[x][y] != -1&& GamePanel.getRemainMines()==0) {
                        GamePanel.mineField[x][y].status = GridStatus.Clicked;
                        System.out.printf("Gird (%d,%d) is left-clicked.\n", x, y);
                        addclickCount();
                        i++;

                        gamePanel.repaint();
                        MainFrame.scoreBoard1.update();
                        MainFrame.scoreBoard2.update();
                    }
                    if (GamePanel.mineField[x][y].status == GridStatus.Covered && GamePanel.chessboard[x][y] == -1) {
                        GamePanel.mineField[x][y].status = GridStatus.Flag;
                        System.out.printf("Gird (%d,%d) is right-clicked.\n", x, y);
                        new Music2().start();
                        if (GameController.onTurn.picture == 0 && i == 0) {
                            JOptionPane.showMessageDialog(null, (GameController.onTurn.userName + "发动了技能\"开门大吉\"！"));
                            MainFrame.controller.getOnTurnPlayer().addScore();
                        }
                        MainFrame.controller.getOnTurnPlayer().addScore();
                        addclickCount();
                        i++;
                        /*                      Login.costrest();*/
                        gamePanel.repaint();
                        MainFrame.scoreBoard1.update();
                        MainFrame.scoreBoard2.update();
                    }
                    MainFrame.controller.isWin();
                    GridComponent.upperUpdate();
                }
                onTurn = p2;
                MainFrame.seconds = 0;
                MainFrame.scoreBoard2.checkTurn.setText("");
                MainFrame.scoreBoard1.checkTurn.setText("还剩"+(Login.n-GameController.clickCount1)+"次点击");
                System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
            }
        } else if (onTurn == p1) {
            clickCount1 = 0;
            onTurn = p2;
            onTurns = p2;
            MainFrame.seconds = 0;
            MainFrame.scoreBoard2.checkTurn.setText("");
            MainFrame.scoreBoard1.checkTurn.setText("还剩"+(Login.n-GameController.clickCount1)+"次点击");
            System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
        }
        MainFrame.turnCounter.setText("This is " + String.valueOf(GameController.clickCount) + " Turn");


        //TODO: 在每个回合结束的时候，还需要做什么 (例如...检查游戏是否结束？)

    }


    /**
     * 获取正在进行当前回合的玩家。
     *
     * @return 正在进行当前回合的玩家
     */
    public Player getOnTurnPlayer() {
        return onTurn;
    }


    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

/*    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }*/

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

/*    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }*/


    public void readFileData(String fileName) {
        //todo: read date from file

    }

    public void writeDataToFile(String fileName) {
        //todo: write data into file
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


    public void isWin() {
        if (Login.player1.getRobot() != 3) {
            if (Login.player1.getScore() > Login.player2.getScore() &&MainFrame.controller.gamePanel.getRemainMines()==0) {
                JOptionPane.showMessageDialog(null, "Game is over! The winner is " + p2.getUserName(), "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            else if (Login.player2.getScore() > Login.player1.getScore() &&MainFrame.controller.gamePanel.getRemainMines()==0) {
                JOptionPane.showMessageDialog(null, "Game is over! The winner is " + p1.getUserName(), "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            else if(Login.player1.getScore() - Login.player2.getScore()>MainFrame.controller.gamePanel.getRemainMines()){
                JOptionPane.showMessageDialog(null, "Game is over! The winner is " + p2.getUserName(), "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            else if(Login.player2.getScore() - Login.player1.getScore()>MainFrame.controller.gamePanel.getRemainMines()){
                JOptionPane.showMessageDialog(null, "Game is over! The winner is " + p1.getUserName(), "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            else if (Login.player1.getScore() == Login.player2.getScore() &&MainFrame.controller.gamePanel.getRemainMines()==0) {
                if (Login.player1.getMistake() > Login.player2.getMistake()) {
                    JOptionPane.showMessageDialog(null, "Game is over! The winner is " + p1.getUserName(), "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
                else if (Login.player2.getMistake() > Login.player1.getMistake()) {
                    JOptionPane.showMessageDialog(null, "Game is over! The winner is " + p2.getUserName(), "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
                else if (Login.player1.getMistake() == Login.player2.getMistake()) {
                    JOptionPane.showMessageDialog(null, "Game is over! You are all winners!  ", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            }
        }
        if (Login.player1.getRobot() == 3) {
            if (Login.player1.getScore() < -Login.mineCount / 3 || Login.player1.getMistake() > 6) {
                JOptionPane.showMessageDialog(null, "Very regret! You failed in the game.", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            if (Login.player1.getScore() > Login.mineCount / 2) {
                JOptionPane.showMessageDialog(null, "Congratulations! You win the game.", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
            if (GamePanel.getRemainMines() == 0) {
                if (Login.player1.getScore() >= Login.mineCount / 3 && Login.player1.getScore() <= Login.mineCount / 2) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You win the game.", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Very regret! You failed in the game.", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            }

        }

    }
}
