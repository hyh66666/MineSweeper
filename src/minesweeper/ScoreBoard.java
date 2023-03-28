package minesweeper;

import components.GridComponent;
import controller.GameController;
import entity.Player;

import javax.swing.*;
import java.awt.*;

/**
 * 此类的对象是一个计分板容器，通过传入玩家对象，
 * 可以用update()方法实时更新玩家的分数以及失误数。
 */

public class ScoreBoard extends JPanel implements java.io.Serializable{
    Player player;
    JLabel score = new JLabel();
    JLabel name = new JLabel();
    JLabel mistake = new JLabel();
    public JLabel checkTurn = new JLabel();


    /**
     * 通过进行游戏的玩家来初始化计分板。这里只考虑了两个玩家的情况。
     * 如果想要2-4人游戏甚至更多，请自行修改(建议把所有玩家存在ArrayList)~
     *
     * @param player 玩家1
     */

    public ScoreBoard(Player player, int xCount, int yCount) {

        mistake.setFont(new Font("幼圆",Font.BOLD,15));
        score.setFont(new Font("幼圆",Font.BOLD,15));
        checkTurn.setFont(new Font("幼圆",Font.BOLD,15));
        checkTurn.setOpaque(true);
        checkTurn.setBackground(Color.CYAN);
        checkTurn.setForeground(Color.red);
        this.setPreferredSize(new Dimension(133, 50));
        this.setLocation(0, xCount * GridComponent.gridSize);
        this.setLayout(new BorderLayout());
        this.player = player;
        Box center = Box.createVerticalBox();
        center.add(name);
        center.add(score);
        center.add(mistake);
        this.add(center, BorderLayout.CENTER);
        this.add(checkTurn, BorderLayout.NORTH);
        update();
    }

    /**
     * 刷新计分板的数据。
     * 计分板会自动重新获取玩家的分数，并更新显示。
     */
    public void update() {
        this.score.setText(String.format("分数: " + player.getScore()));
        this.mistake.setText(String.format("错误数: " + player.getMistake()));

    }

}
