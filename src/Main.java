import controller.GameController;
import minesweeper.*;
import music.Music1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            /*JOptionPane.showMessageDialog(null,"Welcome to CS102A MineSweeper. " +
                    "\nPlease enter players' names to continue.\nThis program was co-created by 12012906 and 12011507.","MineSweeper",JOptionPane.PLAIN_MESSAGE);*/
            Login player = new Login();
        });
        Music1 m = new Music1();
        m.start();

    }
}
