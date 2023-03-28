package minesweeper;

import entity.Player;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DualLogin extends JFrame implements ActionListener {

    public static JPanel imgPanel;
    public static int rest = 10;
    JButton login = new JButton("确认");
    JButton exit = new JButton("退出");
    JLabel pn1 = new JLabel("玩家1");
    JLabel pn2 = new JLabel("玩家2");
    JTextField p1 = new JTextField(5);
    JTextField p2 = new JTextField(5);
    CardLayout cl = new CardLayout(5, 5);
    CardLayout cl2 = new CardLayout(5, 5);
    static int[] plist1 = {1, 2, 3};
    static int[] plist2 = {4, 5, 6};
    static int playerPhotoNumber1 = 0;
    static int playerPhotoNumber2 = 0;
    public static int xCount = 9;
    public static int yCount = 9;
    public static int mineCount = 10;
    public static int n = 3;
    public static entity.Player player1 = new Player();
    public static entity.Player player2 = new Player();
    public static Save save = null;

/*    public static void costrest() {
        rest--;
        player1.label4.setText("        剩余雷数：" + Login.rest);
    }*/

    public static int getRest() {
        return rest;
    }

    public DualLogin() {
        JPanel plp1 = new JPanel();
        JPanel plp2 = new JPanel();

        plp1.setLayout(cl);
        plp2.setLayout(cl2);
        JButton load = new JButton("读取");
        JButton diff = new JButton("模式选择");


        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2, 2));  //3行2列的面板jp（网格布局）
        jp.add(load);
        jp.add(diff);


        imgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/mine.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 220, 220, icon.getImageObserver());
                this.setSize(220, 220);
            }
        };
        JPanel pho1 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/1.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 84*2, 220, icon.getImageObserver());
                this.setSize(84*2, 220);
            }
        };
        JPanel pho2 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/2.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 84*2, 220, icon.getImageObserver());
                this.setSize(84*2, 220);
            }
        };
        JPanel pho3 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/3.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 84*2, 220, icon.getImageObserver());
                this.setSize(84*2, 220);
            }
        };
        JPanel pho4 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/4.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 84*2, 220, icon.getImageObserver());
                this.setSize(84*2, 220);
            }
        };
        JPanel pho5 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/5.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 84*2, 220, icon.getImageObserver());
                this.setSize(84*2, 220);
            }
        };
        JPanel pho6 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/art/6.jpg"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, 84*2, 220, icon.getImageObserver());
                this.setSize(84*2, 220);
            }
        };


        JButton con1 = new JButton("就这个了");
        JButton con2 = new JButton("就这个了");
        JButton nex1 = new JButton("不要这个");
        JButton nex2 = new JButton("不要这个");

        plp1.add(pho1);
        plp1.add(pho2);
        plp1.add(pho3);
        plp2.add(pho4);
        plp2.add(pho5);
        plp2.add(pho6);

        pn1.setHorizontalAlignment(SwingConstants.CENTER);  //设置该组件的对齐方式为向右对齐
        pn2.setHorizontalAlignment(SwingConstants.CENTER);

        pn1.setSize(10, 10);
        pn2.setSize(10, 10);


        jp.add(login);
        jp.add(exit);

        login.addActionListener(this); //登录增加事件监听
        exit.addActionListener(this);    //退出增加事件监听
        Box center = Box.createVerticalBox();
        Box west = Box.createVerticalBox();
        Box east = Box.createVerticalBox();
        Box bn1 = Box.createHorizontalBox();
        Box bn2 = Box.createHorizontalBox();
        bn1.add(con1);
        bn1.add(nex1);
        bn2.add(con2);
        bn2.add(nex2);

        center.add(imgPanel);
        center.add(jp);
        west.add(pn1);
        west.add(plp1);
        west.add(bn1);
        west.add(p1);

        east.add(pn2);
        east.add(plp2);
        east.add(bn2);
        east.add(p2);

        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                switch (actionCommand) {
                    case "不要这个":
                        cl.next(plp1);

                        playerPhotoNumber1++;
                        if (playerPhotoNumber1 > 2) {
                            playerPhotoNumber1 = 0;
                        }
                        player1.setPic(playerPhotoNumber1);
                        break;
                }

            }
        };
        nex1.addActionListener(listener1);

        nex2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl2.next(plp2);
                playerPhotoNumber2++;
                if (playerPhotoNumber2 > 2) {
                    playerPhotoNumber2 = 0;
                }
                player2.setPic(playerPhotoNumber2);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    jfc.showDialog(new JLabel(), "读取");
                    File saving = jfc.getSelectedFile();
                    saving.createNewFile();
                    FileInputStream fileIn = new FileInputStream(saving.getAbsoluteFile());
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    save = (Save) in.readObject();
                    in.close();
                    fileIn.close();
                    DualMainFrame dualMainFrame = new DualMainFrame(player1, player2, save.xCount, save.yCount, save.mineCount, save);
                    DualMainFrame.playerPhoto2 = save.player2.picture;
                    DualMainFrame.playerPhoto1 = save.player1.picture;
                    DualMainFrame.seconds = save.time;
                } catch (IOException i) {
                    i.printStackTrace();
                    return;
                } catch (ClassNotFoundException c) {
                    System.out.println("Employee class not found");
                    c.printStackTrace();
                    return;
                }
            }
        });


        diff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"简单-双人", "中等-双人", "困难-双人", "自定义-双人", "普通-人机", "困难-人机", "单机"};
                String info = (String) JOptionPane.showInputDialog(null, "Which difficulty?", "Difficulty", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                switch (info) {
                    case "简单-双人":
                        DualLogin.xCount = 9;
                        DualLogin.yCount = 9;
                        DualLogin.mineCount = 10;
                        n = 3;
                        break;
                    case "中等-双人":
                        DualLogin.xCount = 16;
                        DualLogin.yCount = 16;
                        DualLogin.mineCount = 40;
                        n = 4;
                        break;
                    case "困难-双人":
                        DualLogin.xCount = 16;
                        DualLogin.yCount = 30;
                        DualLogin.mineCount = 99;
                        n = 5;
                        break;
                    case "自定义-双人":
                        mineCustomize();
                        break;
                    case "普通-人机":
                        mineCustomize();
                        player1.setRobot(1);
                        break;
                    case "困难-人机":
                        mineCustomize();
                        player1.setRobot(2);
                        break;
                    case "单机":
                        mineCustomize();
                        player1.setRobot(3);
                        break;

                }
            }

            private void mineCustomize() {
                JOptionPane.showMessageDialog(null, "请注意限制条件！", "Input", JOptionPane.PLAIN_MESSAGE);
                DualLogin.xCount = Integer.parseInt(JOptionPane.showInputDialog(null, "输入雷区的高度", "Input", JOptionPane.PLAIN_MESSAGE));        //输入对话框
                DualLogin.yCount = Integer.parseInt(JOptionPane.showInputDialog(null, "输入雷区的宽度", "Input", JOptionPane.PLAIN_MESSAGE));        //输入对话框
                DualLogin.mineCount = Integer.parseInt(JOptionPane.showInputDialog(null, "输入雷数，不能大于棋子数的一半", "Input", JOptionPane.PLAIN_MESSAGE));//输入对话框
                DualLogin.n = Integer.parseInt(JOptionPane.showInputDialog(null, "每回合可以点击的次数，不能大于5", "Input", JOptionPane.PLAIN_MESSAGE));//输入对话框
                if (n > 5 || DualLogin.xCount > 24 || DualLogin.yCount > 30 || DualLogin.mineCount > (DualLogin.xCount * DualLogin.yCount) / 2) {
                    JOptionPane.showMessageDialog(null, "棋盘不能大于24*30，并且雷数不能超过格子数的一半！已经重置为简单-人机！次数不能大于五次！", "MineSweeper", JOptionPane.PLAIN_MESSAGE);
                    DualLogin.xCount = 9;
                    DualLogin.yCount = 9;
                    DualLogin.mineCount = 10;
                    DualLogin.rest = 10;
                    n = 3;
                }
            }
        });


        this.add(center, BorderLayout.CENTER);    //将整块面板定义在中间
        this.add(east, BorderLayout.EAST);
        this.add(west, BorderLayout.WEST);
        imgPanel.setPreferredSize(new Dimension(220, 220));
        plp1.setPreferredSize(new Dimension(133, 200));
        plp2.setPreferredSize(new Dimension(133, 200));
        this.getContentPane().setBackground(new Color(255,255,255));

        this.setTitle("MineSweeper");
        this.setLocation(500, 300);    //设置初始位置
        this.pack();        //表示随着面板自动调整大小
        this.setResizable(false);
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        p1.addFocusListener(new JTextFieldHintListener(p1,"在这里输入玩家1的名称"));
        p2.addFocusListener(new JTextFieldHintListener(p2,"在这里输入玩家2的名称"));
        this.setVisible(true);
        System.out.println(con1.getWidth());
        System.out.println(pn1.getHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e)  // 对时间进行处理
    {
        if (e.getSource() == exit) {
            int i = JOptionPane.showConfirmDialog(null, "Are you sure to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
            // 显示选择对话框
            if (i == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            //人人、人机对战部分根据按钮的设定来稍作修改。
            //人人对战
//            if (true) {
//            }
            //人机对战(普通）,强制定义p1为机器，可弹出对话框提醒玩家
//            if (true) {
//                player1 = new entity.Player(p1.getText());
//                player2 = new entity.Player(p2.getText());
//                player1.setRobot(1);
//
//            }
//            人机对战（困难）
//            if (true) {
//                player1 = new entity.Player(p1.getText());
//                player2 = new entity.Player(p2.getText());
//                player1.setRobot(2);
//                System.out.println(player1.getRobot());
//            }
//            单机模式
//            if (true){
//                int a=3;
//                player1 = new entity.Player(p1.getText());
//                player2 = new entity.Player(p2.getText());
//                Login.player1.setRobot(a);
//            }
            if (!p1.getText().equals("在这里输入玩家1的名称")) {
                player1.userName = p1.getText();
            }
            if (!p2.getText().equals("在这里输入玩家2的名称")) {
                player2.userName = p2.getText();
            }
            DualMainFrame.playerPhoto1 = playerPhotoNumber1;
            DualMainFrame.playerPhoto2 = playerPhotoNumber2;
            this.setVisible(false);
            DualMainFrame dualMainFrame = new DualMainFrame(player1, player2, xCount, yCount, mineCount, save);
            dualMainFrame.setVisible(true);
            this.setVisible(false);

        }
    }
}


