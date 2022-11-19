import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author WangDa
 * @version 1.4
 * <p>
 * 坦克大战1.4版
 * 单人对战
 * 2022/11/15 0:16
 */
public class TankGame extends JFrame {
    MyPanel myPanel;

    public static void main(String[] args) {
        new TankGame("TankGame");
    }

    public TankGame(String title) {
        super(title);
        myPanel = new MyPanel();
        Recorder.mypanel = myPanel;
        Thread myPanelThread = new Thread(this.myPanel);
        myPanelThread.setName("myPanelThread");
        myPanelThread.start();
        this.add(this.myPanel);
        this.addKeyListener(this.myPanel);
        //窗口大小
        //width=816, height=739
        this.setSize(1000, 739);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.save();
                System.exit(0);
            }
        });
    }
}
