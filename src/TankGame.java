import javax.swing.*;
import java.awt.*;

/**
 * @author WangDa
 * @version 1.3
 * <p>
 * 坦克大战1.3版
 * 单人对战
 * 2022/11/15 0:16
 */
public class TankGame extends JFrame {
    MyPanel myPanel;

    public static void main(String[] args) {
        new TankGame("TankGame");
    }

    public TankGame(String title) throws HeadlessException {
        super(title);
        myPanel = new MyPanel();
        Thread myPanelThread = new Thread(this.myPanel);
        myPanelThread.setName("myPanelThread");
        myPanelThread.start();
        this.add(this.myPanel);
        this.addKeyListener(this.myPanel);
        //窗口大小
        this.setSize(816, 739);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
