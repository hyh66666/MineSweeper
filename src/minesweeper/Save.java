package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.Player;

public class Save implements java.io.Serializable {
    public Player player1 = Login.player1;
    public Player player2 = Login.player2;
    public ScoreBoard scoreBoard1 = MainFrame.scoreBoard1;
    public ScoreBoard scoreBoard2 = MainFrame.scoreBoard2;
    public GameController gameController = MainFrame.controller;
    public GamePanel gamePanel = gameController.getGamePanel();
    private static final long serialVersionUID = 42L;
    public int Click = GameController.clickCount;
    public int Click1= GameController.clickCount1;
    public Player onTurn = GameController.onTurn;
    public GridComponent[][] mineFields = GamePanel.mineField;
    public int[][] chessBoard = GamePanel.chessboard;
    public int time = MainFrame.seconds;
    public int robot=Login.player1.getRobot();
    public int xCount=Login.xCount;
    public int yCount=Login.yCount;
    public int mineCount=Login.mineCount;
    public int n=Login.n;
    public int playerPhoto1=MainFrame.playerPhoto1;
    public int playerPhoto2=MainFrame.playerPhoto2;


    public Save() {
    }

    public Save(Save savings) {
        if (player1 == null) {
            player1 = savings.player1;
        }
        if (player2 == null) {
            player2 = savings.player2;
        }
        if (mineFields == null) {
            mineFields = savings.mineFields;
        }
    }
}
