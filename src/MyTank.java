import java.util.Vector;

/**
 * @author WangDa
 * @version 1.3
 * <p>
 * 2022/11/15 0:14
 */
public class MyTank extends Tank {
    public Vector<Shot> shots;

    public MyTank(int x, int y, Direct direct) {
        super(x, y, direct);
        shots = new Vector<>();
    }

    public void shotEnemy() {
        Shot shot = getShot();
        synchronized (this) {
            this.shots.add(shot);
        }
        //启动一个shot线程
        Thread shotThread = new Thread(shot);
        shotThread.setName("shotThread");
        shotThread.start();
    }
}
