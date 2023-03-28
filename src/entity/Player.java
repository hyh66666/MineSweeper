package entity;

import minesweeper.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;

public class Player implements java.io.Serializable {
    private static Random ran = new Random();
    public String userName;

    private int score = 0;


    private int mistake = 0;
    public int error = 0;
    private int robot;
    public int unopened = Login.xCount * Login.yCount;
    public int opened = 0;
    public int picture;


    /**
     * 通过特定名字初始化一个玩家对象。
     *
     * @param userName 玩家的名字
     */
    public Player(String userName) {

        if (userName.equals("")) {
            this.userName = "User#" + (ran.nextInt(9000) + 1000);
        } else {
            this.userName = userName;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 通过默认名字初始化一个玩家对象。
     */
    public Player() {
        userName = "User#" + (ran.nextInt(9000) + 1000);
    }

    /**
     * 为玩家加一分。
     */
    public void addScore() {
        score++;
    }

    public void setRobot(int robot) {
        this.robot = robot;
    }
    public void setMistake(int mistake) {
        this.mistake = mistake;
    }

    /**
     * 为玩家扣一分。
     */
    public void costScore() {
        score--;
    }

    /**
     * 为玩家增加一次失误数。
     */
    public void addMistake() {
        mistake++;
    }

    public void addError() {
        error++;
    }

    public int getError() {
        return error;
    }

    public int getUnopened() {
        return unopened;
    }

    public int getOpened() {
        return opened;
    }

    public int getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }

    public int getMistake() {
        return mistake;
    }

    public int getRobot() {
        return robot;
    }

    public void setPic(int a) {
        this.picture = a;
    }
}
