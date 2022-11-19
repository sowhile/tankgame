import java.io.Serializable;

/**
 * @author WangDa
 * @version 1.3
 * <p>
 * 2022/11/15 22:02
 */
public class Shot implements Runnable, Serializable {

    private int x;
    private int y;

    //子弹飞行速度
    private static final int SPEED = 2;
    //子弹是否还存活
    private boolean isLive = true;
    private Direct direct;

    public Shot(int x, int y, Direct direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (isLive) {
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向改变子弹坐标
            switch (direct) {
                case UP:
                    y -= SPEED;
                    break;
                case DOWN:
                    y += SPEED;
                    break;
                case LEFT:
                    x -= SPEED;
                    break;
                case RIGHT:
                    x += SPEED;
                    break;
            }
            //System.out.println("子弹 = " + x + " " + y);
            //检查边界
            if (!(x >= SPEED && x <= 800 - SPEED && y >= SPEED && y <= 700 - SPEED)) {
                isLive = false;
                break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
