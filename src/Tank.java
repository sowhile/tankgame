/**
 * @author WangDa
 * @version 1.3
 * <p>
 * 2022/11/15 0:12
 */
public class Tank {
    private int x;
    private int y;
    private Direct direct;
    private boolean isLive = true;
    //坦克移动速度
    public final int STEP = 10;


    public Tank(int x, int y, Direct direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public void moveUp() {
        setDirect(Direct.UP);
        if (y >= STEP) y -= STEP;
    }

    public void moveDown() {
        setDirect(Direct.DOWN);
        if (y <= 700 - STEP - 60) y += STEP;
    }

    public void moveLeft() {
        setDirect(Direct.LEFT);
        if (x >= STEP) x -= STEP;
    }

    public void moveRight() {
        setDirect(Direct.RIGHT);
        if (x <= 800 - STEP - 60) x += STEP;
    }

    protected Shot getShot() {
        Shot shot = null;
        //创建shot对象,获得子弹的初始坐标
        switch (this.getDirect()) {
            case UP:
                shot = new Shot(getX() + 20, getY(), getDirect());
                break;
            case DOWN:
                shot = new Shot(getX() + 20, getY() + 60, getDirect());
                break;
            case LEFT:
                shot = new Shot(getX(), getY() + 20, getDirect());
                break;
            case RIGHT:
                shot = new Shot(getX() + 60, getY() + 20, getDirect());
                break;
        }
        return shot;
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

enum Direct {
    UP, DOWN, LEFT, RIGHT
}