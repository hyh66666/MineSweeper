package minesweeper;


import components.GridComponent;
import controller.GameController;
import entity.GridStatus;
import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SingleMainFrame extends JFrame {
    public static GameController controller;
    public static int xCount = SingleLogin.xCount;
    public static int yCount = SingleLogin.yCount;
    public static int mineCount = SingleLogin.mineCount;
    public static int playerPhoto1;
    public static int playerPhoto2;
    private Player p1 = SingleLogin.player1;
    private Player p2 = SingleLogin.player2;
    public static ScoreBoard scoreBoard1;
    public static ScoreBoard scoreBoard2;
    public static JLabel turnCounter = new JLabel();
    public static int seconds = 0;
    public JLabel label3 = new JLabel("       回合时间：" + seconds + "s");
    public static JLabel label1;
    public static JLabel label2;
    public static JLabel label4;
    public static JLabel label5;
    public static boolean isValid = false;


    public SingleMainFrame(Player pl1, Player pl2, int xCount, int yCount, int mineCount, Save save) {
        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor

        this.setTitle("MineSweeper");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        SingleMainFrame.xCount = xCount;
        SingleMainFrame.yCount = yCount;
        SingleMainFrame.mineCount = mineCount;
        JPanel main = new JPanel();


        JPanel imgPanel1 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/" + SingleLogin.plist1[playerPhoto1] + ".jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
                this.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };
        JPanel imgPanel2 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/" + SingleLogin.plist2[playerPhoto2] + ".jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
                this.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };

        if (save != null) {
            SingleLogin.player1 = save.player1;
            p1 = save.player1;
            SingleLogin.player2 = save.player2;
            p2 = save.player2;
            scoreBoard1 = save.scoreBoard1;
            scoreBoard2 = save.scoreBoard2;
            controller = save.gameController;
            GameController.onTurn = save.onTurn;
            GameController.clickCount = save.Click;
            GameController.clickCount1 = save.Click1;
            save.player1.setRobot(save.robot);
            SingleLogin.n = save.n;
            /*for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    GridComponent gridComponent = new GridComponent(i,j);
                    gridComponent.status=save.mineFields[i][j].status;
                    gridComponent.setContent(save.mineFields[i][j].getContent());
                    gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                    GamePanel.mineField[i][j] = gridComponent;
                    *//*controller.gamePanel= new GamePanel(Login.xCount,Login.yCount,save.mineFields);*//*
             *//*controller.gamePanel.add(GamePanel.mineField[i][j]);*//*
                }
            }*/
            SingleLogin.xCount = save.xCount;
            SingleLogin.yCount = save.yCount;
            SingleLogin.mineCount = save.mineCount;
            controller.setGamePanel(new GamePanel(save.xCount, save.yCount, save.mineFields));
            controller.gamePanel.setEnabled(true);
            GamePanel.mineField = save.mineFields;
            GamePanel.chessboard = save.chessBoard;
            /*controller.setGamePanel(save.gamePanel);*/
        } else {
            scoreBoard1 = new ScoreBoard(SingleLogin.player1, xCount, yCount);
            scoreBoard2 = new ScoreBoard(SingleLogin.player2, xCount, yCount);
            controller = new GameController(p2, p1);

        }

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = (Timer) e.getSource();
                seconds++;
                label3.setText("    回合时间：" + seconds + "s");
                if (seconds == 50 && SingleLogin.player1.getRobot() != 3) {
                    SingleMainFrame.controller.nextTurn();
                }
                timer.start();
            }

        };
        Timer timer = new Timer(1000, taskPerformer);
        timer.start();


        /* controller.setScoreBoard(scoreBoard1);*/
        JLabel playerName1 = new JLabel();
        playerName1.setText(SingleLogin.player1.getUserName());
        playerName1.setFont(new Font("幼圆", Font.PLAIN, 15));
        playerName1.setHorizontalAlignment(SwingConstants.LEFT);
        playerName1.setVerticalAlignment(SwingConstants.TOP);
        JLabel playerName2 = new JLabel();
        playerName2.setFont(new Font("幼圆", Font.PLAIN, 15));
        playerName2.setHorizontalAlignment(SwingConstants.LEFT);
        playerName2.setVerticalAlignment(SwingConstants.TOP);
        turnCounter.setText("现在是 " + String.valueOf(GameController.clickCount) + " 回合");
        playerName2.setText(SingleLogin.player2.getUserName());
        imgPanel1.setPreferredSize(new Dimension(133, 200));
        imgPanel2.setPreferredSize(new Dimension(133, 200));
        Box center = Box.createVerticalBox();
        Box west = Box.createVerticalBox();
        Box east = Box.createVerticalBox();
        west.setPreferredSize(new Dimension(133, Math.max(xCount * GridComponent.gridSize, 280)));
        east.setPreferredSize(new Dimension(133, Math.max(xCount * GridComponent.gridSize, 280)));
        center.setPreferredSize(new Dimension(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize));
        center.add(controller.getGamePanel());
        west.add(playerName1);
        west.add(imgPanel1);
        west.add(scoreBoard1);
        east.add(playerName2);
        east.add(imgPanel2);
        east.add(scoreBoard2);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu savingsMenu = new JMenu("存档操作");
        JMenu appearanceMenu = new JMenu("外观");
        JMenu gameMenu = new JMenu("游戏");
        JMenu otherMenu = new JMenu("其他");
        menuBar.add(savingsMenu);
        menuBar.add(appearanceMenu);
        menuBar.add(gameMenu);
        menuBar.add(otherMenu);
        JMenuItem loadItem = new JMenuItem("读取");
        JMenuItem saveItem = new JMenuItem("保存");
        JMenuItem regretItem = new JMenuItem("悔棋");
        JMenuItem cheatingModeItem = new JMenuItem("揭开所有棋子");
        JMenuItem coverGridsItem = new JMenuItem("覆盖所有棋子");
        JMenuItem restartGameItem = new JMenuItem("重新开始游戏");
        JMenuItem changeGridColorItem = new JMenuItem("更改棋子颜色");
        JMenuItem changeGridStyleItem = new JMenuItem("更改棋子风格");
        JMenuItem changeBackgroundColorItem = new JMenuItem("更改背景颜色");
        JMenuItem aboutItem = new JMenuItem("关于");
        JMenuItem reGameItem = new JMenuItem("重新进入游戏");
        JMenuItem skinItem = new JMenuItem("更改界面风格");
        JMenuItem settingsItem = new JMenuItem("设置自动连开");
        otherMenu.add(aboutItem);
        otherMenu.add(reGameItem);
        otherMenu.add(settingsItem);
        savingsMenu.add(loadItem);
        savingsMenu.add(saveItem);
        gameMenu.add(cheatingModeItem);
        gameMenu.add(regretItem);
        appearanceMenu.add(changeGridColorItem);
        appearanceMenu.add(changeBackgroundColorItem);
        appearanceMenu.add(changeGridStyleItem);
        appearanceMenu.add(skinItem);
//        savingsMenu.addSeparator();
        gameMenu.add(restartGameItem);
        gameMenu.add(coverGridsItem);
        this.add(west, BorderLayout.WEST);
        this.add(east, BorderLayout.EAST);
        this.add(center);
        repaint();
        Box centerDown = Box.createHorizontalBox();
        Box centerUp = Box.createHorizontalBox();
        JButton clickBtn = new JButton("Click");
        clickBtn.setSize(80, 20);
        clickBtn.setLocation(5, controller.getGamePanel().getHeight() + scoreBoard1.getHeight());
        centerDown.add(clickBtn);
        centerDown.add(turnCounter);
        label1 = new JLabel("待开：" + (SingleLogin.xCount * SingleLogin.yCount - controller.getGamePanel().getOpenedCount()));
        label2 = new JLabel("  已开：" + controller.getGamePanel().getOpenedCount());
        label5 = new JLabel("  点击数：" + GameController.clickCount);
        label4 = new JLabel("  剩余雷数：" + controller.getGamePanel().getRemainMines());
        label1.setFont(new Font("幼圆", Font.PLAIN, 15));
        label2.setFont(new Font("幼圆", Font.PLAIN, 15));
        label3.setFont(new Font("幼圆", Font.PLAIN, 15));
        label4.setFont(new Font("幼圆", Font.PLAIN, 15));
        label5.setFont(new Font("幼圆", Font.PLAIN, 15));
        centerUp.add(label1);
        centerUp.add(label2);
        centerUp.add(label3);
        centerUp.add(label4);
        centerUp.add(label5);
//        label1.setOpaque(true);
//        label2.setOpaque(true);
//        label3.setOpaque(true);
//        label4.setOpaque(true);
//        label5.setOpaque(true);
//        controller.getGamePanel().setOpaque(true);
        this.add(centerUp, BorderLayout.NORTH);
//        center.add(centerDown);
//        clickBtn.addActionListener(e -> {
//
//            String fileName = JOptionPane.showInputDialog(this, "input here");
//            System.out.println("fileName :" + fileName);
//            if (fileName.equals("reveal")) {
//                for (int i = 0; i < xCount; i++) {
//                    for (int i1 = 0; i1 < yCount; i1++) {
//                        GamePanel.mineField[i][i1].status = GridStatus.Clicked;
//                        repaint();
//                    }
//                }
////            controller.readFileData(fileName);
////            controller.writeDataToFile(fileName);
//            }
//            if (fileName.equals("cover")) {
//                for (int i = 0; i < xCount; i++) {
//                    for (int i1 = 0; i1 < yCount; i1++) {
//                        GamePanel.mineField[i][i1].status = GridStatus.Covered;
//                        repaint();
//                    }
//                }
//            }
//            if (fileName.equals("restart")) {
//                seconds = 0;
//                MainFrame.controller = new GameController(p1, p2);
//                MainFrame.scoreBoard1 = new ScoreBoard(p1, Login.xCount, Login.yCount);
//                MainFrame.scoreBoard2 = new ScoreBoard(p2, Login.xCount, Login.yCount);
//                MainFrame mainFrame = new MainFrame(Login.player1, Login.player2, Login.xCount, Login.yCount, Login.mineCount, Login.save);
//                mainFrame.setLocation(this.getLocation());
//                this.removeAll();
//            }
//        });

        JButton saveBtn = new JButton("Save");
        clickBtn.setSize(80, 20);
        clickBtn.setLocation(25, controller.getGamePanel().getHeight() + scoreBoard1.getHeight());
        centerDown.add(saveBtn);
//        center.add(centerDown);

        saveItem.addActionListener(e -> {
            try {
                Save newSave = new Save(SingleLogin.save);
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "Save");
                File saving = jfc.getSelectedFile();
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
        });
        restartGameItem.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int i1 = 0; i1 < yCount; i1++) {
                    GamePanel.mineField[i][i1].status = GridStatus.Covered;
                    repaint();
                }
            }
            seconds = 0;
            if (controller.getOnTurnPlayer() != pl1) {
                controller.nextTurn();
            }
            GridComponent.scoreBoardUpdate();
            GameController.clickCount = 0;
            GameController.clickCount1 = 0;

            pl1.setScore(0);
            pl2.setScore(0);
            pl1.setMistake(0);
            pl2.setMistake(0);
            scoreBoard1.update();
            scoreBoard2.update();
            GridComponent.upperUpdate();
        });
        loadItem.addActionListener(e -> {
            try {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "读取");
                File saving = jfc.getSelectedFile();
                saving.createNewFile();
                FileInputStream fileIn = new FileInputStream(saving.getAbsoluteFile());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                Save load = (Save) in.readObject();
                in.close();
                fileIn.close();
                timer.stop();
                SingleMainFrame SingleMainFrame = new SingleMainFrame(load.player1, load.player2, load.xCount, load.yCount, mineCount, load);
                SingleMainFrame.setLocation(this.getLocation());
//                controller.gamePanel.setEnabled(true);
//                controller.setGamePanel(new GamePanel(load.xCount, load.yCount, load.mineFields));
//                repaint();
//                controller.gamePanel.setEnabled(true);
//                GamePanel.mineField = load.mineFields;
//                controller.gamePanel.repaint();
                GamePanel.chessboard = load.chessBoard;
                SingleMainFrame.playerPhoto2 = load.player2.picture;
                SingleMainFrame.playerPhoto1 = load.player1.picture;
                this.dispose();
                SingleMainFrame.seconds = load.time;
                GameController.clickCount = load.Click;
                GameController.clickCount1 = load.Click;
                SingleLogin.player1.setScore(load.player1.getScore());
                SingleLogin.player2.setScore(load.player2.getScore());
                SingleLogin.player1.setMistake(load.player1.getMistake());
                SingleLogin.player2.setMistake(load.player2.getMistake());
//                scoreBoard1 = load.scoreBoard1;
//                scoreBoard2 = load.scoreBoard2;
//                repaint();
            } catch (IOException i) {
                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                System.out.println("Employee class not found");
                c.printStackTrace();
                return;
            }
        });
        cheatingModeItem.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int i1 = 0; i1 < yCount; i1++) {
                    if (GamePanel.mineField[i][i1].getContent() == -1) {
                        GamePanel.mineField[i][i1].status = GridStatus.Bombed;
                    } else {
                        GamePanel.mineField[i][i1].status = GridStatus.Clicked;
                    }
                    repaint();
                }
            }
//            controller.readFileData(fileName);
//            controller.writeDataToFile(fileName);
        });
        coverGridsItem.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int i1 = 0; i1 < yCount; i1++) {
                    GamePanel.mineField[i][i1].status = GridStatus.Covered;
                    repaint();
                }
            }
        });
        changeGridColorItem.addActionListener(e -> {
            String[] options = {"粉色", "白色", "红色", "黄色"};
            String info = (String) JOptionPane.showInputDialog(null, "啥子颜色", "Color", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (info) {
                case "粉色":
                    GridComponent.color = Color.PINK;
                    repaint();
                    break;
                case "白色":
                    GridComponent.color = Color.white;
                    repaint();
                    break;
                case "红色":
                    GridComponent.color = Color.RED;
                    repaint();
                    break;
                case "黄色":
                    GridComponent.color = Color.YELLOW;
                    repaint();
                    break;
            }
        });
        changeBackgroundColorItem.addActionListener(e -> {
            String[] options = {"白色", "金属灰", "灰色", "黄色"};
            String info = (String) JOptionPane.showInputDialog(null, "啥子颜色", "Color", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (info) {
                case "白色":
                    this.getContentPane().setBackground(Color.white);
                    scoreBoard1.setBackground(Color.white);
                    scoreBoard2.setBackground(Color.white);
                    controller.getGamePanel().setBackground(Color.white);
                    repaint();
                    break;
                case "金属灰":
                    this.getContentPane().setBackground(Color.lightGray);
                    scoreBoard1.setBackground(Color.lightGray);
                    scoreBoard2.setBackground(Color.lightGray);
                    controller.getGamePanel().setBackground(Color.lightGray);
                    repaint();
                    break;
                case "灰色":
                    this.getContentPane().setBackground(Color.GRAY);
                    scoreBoard1.setBackground(Color.GRAY);
                    scoreBoard2.setBackground(Color.GRAY);
                    controller.getGamePanel().setBackground(Color.GRAY);
                    repaint();
                    break;
                case "黄色":
                    this.getContentPane().setBackground(Color.yellow);
                    scoreBoard1.setBackground(Color.yellow);
                    scoreBoard2.setBackground(Color.yellow);
                    controller.getGamePanel().setBackground(Color.yellow);
                    repaint();
                    break;
            }
        });
        regretItem.addActionListener(e -> {
            int n = 1;
            do {
                n = Integer.parseInt(JOptionPane.showInputDialog(null, "输入想要退回的点击数", "Input", JOptionPane.PLAIN_MESSAGE));
                if (n >= GameController.clickCount) {
                    JOptionPane.showMessageDialog(null, "不能退回到第一回合以前~", "Input", JOptionPane.PLAIN_MESSAGE);
                }
            } while (n >= GameController.clickCount);
            try {
                File saving = new File(System.getProperty("java.io.tmpdir") + "/save" + (GameController.clickCount - n));
                saving.createNewFile();
                FileInputStream fileIn = new FileInputStream(saving.getAbsoluteFile());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                Save load = (Save) in.readObject();
                in.close();
                fileIn.close();
                SingleMainFrame SingleMainFrame = new SingleMainFrame(load.player1, load.player2, load.xCount, load.yCount, mineCount, load);
                SingleMainFrame.setLocation(this.getLocation());
//                controller.gamePanel.setEnabled(true);
//                controller.setGamePanel(new GamePanel(load.xCount, load.yCount, load.mineFields));
//                repaint();
//                controller.gamePanel.setEnabled(true);
//                GamePanel.mineField = load.mineFields;
//                controller.gamePanel.repaint();
                GamePanel.chessboard = load.chessBoard;
                SingleMainFrame.playerPhoto2 = load.player2.picture;
                SingleMainFrame.playerPhoto1 = load.player1.picture;
                SingleMainFrame.seconds = load.time;
                GameController.clickCount = load.Click;
                GameController.clickCount1 = load.Click;
                SingleLogin.player1.setScore(load.player1.getScore());
                SingleLogin.player2.setScore(load.player2.getScore());
                SingleLogin.player1.setMistake(load.player1.getMistake());
                SingleLogin.player2.setMistake(load.player2.getMistake());
                this.dispose();
//                scoreBoard1 = load.scoreBoard1;
//                scoreBoard2 = load.scoreBoard2;
//                repaint();
            } catch (IOException i) {
                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                System.out.println("Employee class not found");
                c.printStackTrace();
                return;
            }
        });
        reGameItem.addActionListener(e -> {
            this.dispose();
            SingleLogin SingleLogin = new SingleLogin();
        });
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "扫雷，由姚刘嘉和黄宇海共同开发。开发版本，v0.9.6", "About", JOptionPane.INFORMATION_MESSAGE);
        });
        skinItem.addActionListener(e -> {
            String[] options = {"Windows风格", "Windows Classic风格", "Motif风格"};
            String look = null;
            String info = (String) JOptionPane.showInputDialog(null, "Which difficulty?", "Difficulty", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (info) {
                case "Windows风格":
                    look = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                    break;
                case "Windows Classic风格":
                    look = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
                    break;
                case "Motif风格":
                    look = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                    break;

            }
            try {
                UIManager.setLookAndFeel(look);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (InstantiationException instantiationException) {
                instantiationException.printStackTrace();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                unsupportedLookAndFeelException.printStackTrace();
            }
        });
        changeGridStyleItem.addActionListener(e -> {
            String[] options = {"简约时尚", "尊贵儒雅", "神秘端庄"};
            String look = null;
            String info = (String) JOptionPane.showInputDialog(null, "Which style?", "Difficulty", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch (info) {
                case "简约时尚":
                    GridComponent.gridStyle = "/art/maskgrid.png";
                    repaint();
                    break;
                case "尊贵儒雅":
                    GridComponent.gridStyle = "/art/maskgrid2.jpg";
                    repaint();
                    break;
                case "神秘端庄":
                    GridComponent.gridStyle = "/art/maskgrid3.jpeg";
                    repaint();
                    break;
            }
        });
        // 设置背景
//        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
//        File resource = this.getClass().getResource("src/art/ming.jpg"); // 获取背景图片路径
//        ImageIcon icon = new ImageIcon(this.getClass().getResource("/art/mine.jpg")); // 创建背景图片对象
//        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
//        lblBackground.setBounds(0, 0, Login.xCount*GridComponent.gridSize, Login.yCount*GridComponent.gridSize); // 设置组件的显示位置及大小
//        lblBackground.setPreferredSize(new Dimension(Login.xCount*GridComponent.gridSize, Login.yCount*GridComponent.gridSize));
//        this.getContentPane().add(lblBackground); // 将组件添加到面板中

        this.setResizable(false);
        this.pack();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


}



