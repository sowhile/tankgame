import java.io.Serializable;
import java.util.Vector;

/**
 * @author WangDa
 * @version 1.3
 * <p>
 * 2022/11/15 11:13
 */
public class EnemyTank extends Tank implements Runnable, Serializable {
    public Vector<Shot> shots;
    private Vector<EnemyTank> enemyTanks;
    private static final int ENEMY_STEP = 3;

    public EnemyTank() {
    }

    public EnemyTank(int x, int y, Direct direct) {
        super(x, y, direct);
    }

    public EnemyTank(int x, int y, Direct direct, Vector<EnemyTank> enemyTanks) {
        super(x, y, direct);
        this.enemyTanks = enemyTanks;
        shots = new Vector<>();
    }

    @Override
    public void moveUp() {
        setDirect(Direct.UP);
        if (super.getY() >= ENEMY_STEP && !isOverlap()) super.setY(super.getY() - ENEMY_STEP);
    }

    @Override
    public void moveDown() {
        setDirect(Direct.DOWN);
        if (super.getY() <= 700 - ENEMY_STEP - 60 && !isOverlap()) super.setY(super.getY() + ENEMY_STEP);
    }

    @Override
    public void moveLeft() {
        setDirect(Direct.LEFT);
        if (super.getX() >= ENEMY_STEP && !isOverlap()) super.setX(super.getX() - ENEMY_STEP);
    }

    @Override
    public void moveRight() {
        setDirect(Direct.RIGHT);
        if (super.getX() <= 800 - ENEMY_STEP - 60 && !isOverlap()) super.setX(super.getX() + ENEMY_STEP);
    }

    @Override
    public void run() {
        int speed = 20;
        while (isLive()) {
            //每次移动的距离
            int mile = (int) (Math.random() * 41) + 40;
            //是否改变方向
            int directorNot = (int) (Math.random() * 2);
            if (directorNot == 0) {
                int direct = (int) (Math.random() * 4);
                switch (direct) {
                    case 0:
                        setDirect(Direct.UP);
                        break;
                    case 1:
                        setDirect(Direct.DOWN);
                        break;
                    case 2:
                        setDirect(Direct.LEFT);
                        break;
                    case 3:
                        setDirect(Direct.RIGHT);
                        break;
                }
            }
            switch (getDirect()) {
                case UP:
                    for (int i = mile; i > 0; i--) {
                        moveUp();
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case DOWN:
                    for (int i = mile; i > 0; i--) {
                        moveDown();
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case LEFT:
                    for (int i = mile; i > 0; i--) {
                        moveLeft();
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case RIGHT:
                    for (int i = mile; i > 0; i--) {
                        moveRight();
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            shot();
            try {
                Thread.sleep(1350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //判断敌人坦克是否重叠
    public boolean isOverlap() {
        switch (this.getDirect()) {
            case UP:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank == this) break;
                    if (enemyTank.getDirect() == Direct.UP || enemyTank.getDirect() == Direct.DOWN) {
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60) return true;
                        if (this.getX() + 40 >= enemyTank.getX()
                                && this.getX() + 40 <= enemyTank.getX() + 40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60) return true;
                    }
                    if (enemyTank.getDirect() == Direct.LEFT || enemyTank.getDirect() == Direct.RIGHT) {
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40) return true;
                        if (this.getX() + 40 >= enemyTank.getX()
                                && this.getX() + 40 <= enemyTank.getX() + 60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40) return true;
                    }
                }
                break;
            case DOWN:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank == this) break;
                    if (enemyTank.getDirect() == Direct.UP || enemyTank.getDirect() == Direct.DOWN) {
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 40
                                && this.getY() + 60 >= enemyTank.getY()
                                && this.getY() + 60 <= enemyTank.getY() + 60) return true;
                        if (this.getX() + 40 >= enemyTank.getX()
                                && this.getX() + 40 <= enemyTank.getX() + 40
                                && this.getY() + 60 >= enemyTank.getY()
                                && this.getY() + 60 <= enemyTank.getY() + 60) return true;
                    }
                    if (enemyTank.getDirect() == Direct.LEFT || enemyTank.getDirect() == Direct.RIGHT) {
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 60
                                && this.getY() + 60 >= enemyTank.getY()
                                && this.getY() + 60 <= enemyTank.getY() + 40) return true;
                        if (this.getX() + 40 >= enemyTank.getX()
                                && this.getX() + 40 <= enemyTank.getX() + 60
                                && this.getY() + 60 >= enemyTank.getY()
                                && this.getY() + 60 <= enemyTank.getY() + 40) return true;
                    }
                }
                break;
            case LEFT:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank == this) break;
                    if (enemyTank.getDirect() == Direct.UP || enemyTank.getDirect() == Direct.DOWN) {
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60) return true;
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 40
                                && this.getY() + 40 >= enemyTank.getY()
                                && this.getY() + 40 <= enemyTank.getY() + 60) return true;
                    }
                    if (enemyTank.getDirect() == Direct.LEFT || enemyTank.getDirect() == Direct.RIGHT) {
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40) return true;
                        if (this.getX() >= enemyTank.getX()
                                && this.getX() <= enemyTank.getX() + 60
                                && this.getY() + 40 >= enemyTank.getY()
                                && this.getY() + 40 <= enemyTank.getY() + 40) return true;
                    }
                }
                break;
            case RIGHT:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank == this) break;
                    if (enemyTank.getDirect() == Direct.UP || enemyTank.getDirect() == Direct.DOWN) {
                        if (this.getX() + 60 >= enemyTank.getX()
                                && this.getX() + 60 <= enemyTank.getX() + 40
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 60) return true;
                        if (this.getX() + 60 >= enemyTank.getX()
                                && this.getX() + 60 <= enemyTank.getX() + 40
                                && this.getY() + 40 >= enemyTank.getY()
                                && this.getY() + 40 <= enemyTank.getY() + 60) return true;
                    }
                    if (enemyTank.getDirect() == Direct.LEFT || enemyTank.getDirect() == Direct.RIGHT) {
                        if (this.getX() + 60 >= enemyTank.getX()
                                && this.getX() + 60 <= enemyTank.getX() + 60
                                && this.getY() >= enemyTank.getY()
                                && this.getY() <= enemyTank.getY() + 40) return true;
                        if (this.getX() + 60 >= enemyTank.getX()
                                && this.getX() + 60 <= enemyTank.getX() + 60
                                && this.getY() + 40 >= enemyTank.getY()
                                && this.getY() + 40 <= enemyTank.getY() + 40) return true;
                    }
                }
                break;
        }
        return false;
    }

    //敌方坦克发射行为
    private void shot() {
        Shot shot = getShot();
        this.shots.add(shot);
        //启动一个enemyTankShotThread线程
        Thread enemyTankShotThread = new Thread(shot);
        enemyTankShotThread.setName("enemyTankShotThread");
        enemyTankShotThread.start();
    }

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
}
