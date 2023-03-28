package components;

import entity.GridStatus;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class BasicComponent extends JComponent implements java.io.Serializable {
    public BasicComponent() {
        initial();
    }
    public GridStatus status = GridStatus.Covered;
    boolean isTouch;

    private void initial() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == 1) {
                    onMouseLeftClicked();
                }
                if (e.getButton() == 3) {
                    onMouseRightClicked();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (status == GridStatus.Covered) {
                    isTouch = true;
                    inner();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if (isTouch) {
                    isTouch = false;
                    inner();
                }

            }

        });
    }

    /**
     * invoke this method when mouse left clicked
     *
     * @return
     */
    public abstract void onMouseLeftClicked();

    /**
     * invoke this method when mouse right clicked
     */
    public abstract void onMouseRightClicked();

    public abstract void inner();

}