package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.GridStatus;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements java.io.Serializable {
    public static GridComponent[][] mineField;
    public GridComponent[][] mineFields;
    public static int[][] chessboard;
    public int[][] chessboards;
    private static final Random random = new Random();
    private static int mineCount;
    private static final long serialVersionUID = 42L;

    public GridComponent[][] getMineField() {
        return mineField;
    }

    /**
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
     * @param xCount    count of grid in column
     * @param yCount    count of grid in row
     * @param mineCount mine count
     */
    public GamePanel(int xCount, int yCount, int mineCount) {

        GamePanel.mineCount = mineCount;
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);
        initialGame(xCount, yCount, mineCount);
        repaint();
    }

    public void initialGame(int xCount, int yCount, int mineCount) {
        mineField = new GridComponent[xCount][yCount];

        //generateChessBoard(xCount, yCount, mineCount);

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                //gridComponent.setContent(chessboard[i][j]);
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
        prepareMines(-2, -2);
    }

    public GamePanel(int xCount, int yCount, GridComponent[][] mineField) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.status = mineField[i][j].status;
                gridComponent.setContent(mineField[i][j].getContent());
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
        repaint();
    }

    public static void prepareMines(int ignoreX, int ignoreY) {
        int xCount = mineField.length, yCount = mineField[0].length;
        int count1 = 1;
        chessboard = new int[xCount][yCount];

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                chessboard[i][j] = 0;
            }
        }

        for (int i = 0; i < mineCount; ) {
            int a = random.nextInt(xCount);
            int b = random.nextInt(yCount);
            if (a >= ignoreX - 1 && a <= ignoreX + 1 && b >= ignoreY - 1 && b <= ignoreY + 1) {
                continue;
            }
            if (chessboard[a][b] != -1) {
                chessboard[a][b] = -1;
                i++;
            }
        }

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (chessboard[i][j] == 0) {
                    int count = 0;
                    count = getCount(xCount, yCount, i, j, count);
                    chessboard[i][j] = count;
                }
                if (chessboard[i][j] == -1) {
                    count1 = getCount(xCount, yCount, i, j, count1);
                }
            }
        }

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                mineField[i][j].setContent(chessboard[i][j]);
            }
        }
        if (count1 == 9) {
            prepareMines(ignoreX, ignoreY);
        }

    }

    private static int getCount(int xCount, int yCount, int i, int j, int count) {
        if (i != 0 && j != 0 && chessboard[i - 1][j - 1] == -1) count++;        //左上
        if (i != 0 && chessboard[i - 1][j] == -1) count++;        //上
        if (i != 0 && j <= yCount - 2 && chessboard[i - 1][j + 1] == -1) count++;        //右上
        if (j != 0 && chessboard[i][j - 1] == -1) count++;        //左
        if (j <= yCount - 2 && chessboard[i][j + 1] == -1) count++;        //右
        if (i <= xCount - 2 && j != 0 && chessboard[i + 1][j - 1] == -1) count++;        //左下
        if (i <= xCount - 2 && chessboard[i + 1][j] == -1) count++;        //下
        if (i <= xCount - 2 && j <= yCount - 2 && chessboard[i + 1][j + 1] == -1) count++;        //右下
        return count;
    }

    public static void openGrid(int i, int j) {
        int xCount = Login.xCount, yCount = Login.yCount;
        if (mineField[i][j].status == GridStatus.Clicked || mineField[i][j].status == GridStatus.Flag) {
            return;
        }
        if (mineField[i][j].status == GridStatus.Covered) {
            mineField[i][j].status = GridStatus.Clicked;
            mineField[i][j].repaint();
        }
        if (chessboard[i][j] == 0) {
            if (MainFrame.isOpenZero != 1 || GameController.clickCount1 == 1) {
                if (i != 0 && j != 0 && chessboard[i - 1][j - 1] == 0) {
                    openGrid(i - 1, j - 1);
                }       //左上
                if (i != 0 /*&& chessboard[i - 1][j] == 0*/) {
                    openGrid(i - 1, j);
                }        //上
                if (i != 0 && j <= yCount - 2 /*&& chessboard[i - 1][j + 1] == 0*/) {
                    openGrid(i - 1, j + 1);
                }        //右上
                if (j != 0 /*&& chessboard[i][j - 1] == 0*/) {
                    openGrid(i, j - 1);
                }       //左
                if (j <= yCount - 2 /*&&chessboard[i][j + 1] == 0*/) {
                    openGrid(i, j + 1);
                }       //右
                if (i <= xCount - 2 && j != 0 /*&& chessboard[i + 1][j - 1] == 0*/) {
                    openGrid(i + 1, j - 1);
                }        //左下
                if (i <= xCount - 2 /*&& chessboard[i + 1][j] == 0*/) {
                    openGrid(i + 1, j);
                }        //下
                if (i <= xCount - 2 && j <= yCount - 2 /*&& chessboard[i + 1][j + 1] == 0*/) {
                    openGrid(i + 1, j + 1);
                }//右下}
            }
        }

    }


//   public void generateChessBoard(int xCount, int yCount, int mineCount) {
//
//        chessboard = new int[xCount][yCount];
//        if (GameController.clickCounts == 1) {
//            for (int i = 0; i < mineCount; ) {
//                int a = random.nextInt(xCount);
//                int b = random.nextInt(yCount);
//                if (chessboard[a][b] != -1) {
//                    chessboard[a][b] = -1;
//                    i++;
//                }
//            }
//        }
//        for (int i = 0; i < xCount; i++) {
//            for (int j = 0; j < yCount; j++) {
//                if (chessboard[i][j] == 0) {
//                    int count = 0;
//                    if (i != 0 && j != 0 && chessboard[i - 1][j - 1] == -1) count++;        //左上
//                    if (i != 0 && chessboard[i - 1][j] == -1) count++;        //上
//                    if (i != 0 && j <= yCount - 2 && chessboard[i - 1][j + 1] == -1) count++;        //右上
//                    if (j != 0 && chessboard[i][j - 1] == -1) count++;        //左
//                    if (j <= yCount - 2 && chessboard[i][j + 1] == -1) count++;        //右
//                    if (i <= xCount - 2 && j != 0 && chessboard[i + 1][j - 1] == -1) count++;        //左下
//                    if (i <= xCount - 2 && chessboard[i + 1][j] == -1) count++;        //下
//                    if (i <= xCount - 2 && j <= yCount - 2 && chessboard[i + 1][j + 1] == -1) count++;        //右下
//                    chessboard[i][j] = count;
//                }
//            }
//        }
//
//
//
//        /*for (int i = 0; i < xCount; i++) {
//            for (int j = 0; j < yCount; j++) {
//                // suppose -1 represents mine
//                chessboard[i][j] = random.nextInt(10) - 1;
//            }
//        }*/
//
//    }


    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public GridComponent getGrid(int x, int y) {
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setGridStatus(int x, int y, GridStatus st) {
        try {
            mineField[x][y].status = st;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static int getOpenedCount() {
        int res = 0;
        for (int i = 0; i < Login.xCount; i++) {
            for (int i1 = 0; i1 < Login.yCount; i1++) {
                if (mineField[i][i1].getStatus() == GridStatus.Clicked || mineField[i][i1].getStatus() == GridStatus.Flag || mineField[i][i1].getStatus() == GridStatus.Bombed) {
                    res++;
                }
            }
        }
        return res;
    }

    public static int getRemainMines() {
        int res = 0;
        for (int i = 0; i < Login.xCount; i++) {
            for (int i1 = 0; i1 < Login.yCount; i1++) {
                if (mineField[i][i1].getStatus() == GridStatus.Covered && mineField[i][i1].getContent() == -1) {
                    res++;
                }
            }
        }
        return res;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
