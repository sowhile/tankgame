import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author WangDa
 * @version 1.3
 * <p>
 * 2022/11/15 0:11
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank;
    Vector<EnemyTank> enemyTanks;

    Vector<Bomb> bombs;
    //爆炸图片
    Image image1;
    Image image2;
    Image image3;

    public MyPanel() {
        //设置画布的尺寸
        this.setSize(800, 700);
        //初始化玩家坦克位置
        myTank = new MyTank(360, 600, Direct.UP);
        //初始化敌方坦克
        enemyTanks = new Vector<>();
        for (int i = 1; i <= 4; i++) {
            EnemyTank enemyTank = new EnemyTank(100 + 100 * i, 200, Direct.DOWN);
            synchronized (this) {
                enemyTanks.add(enemyTank);
            }
            //启动一个敌方坦克线程
            Thread enemyTankThread = new Thread(enemyTank);
            enemyTankThread.setName("enemyTankThread");
            enemyTankThread.start();
        }
        //初始化爆炸图片
        bombs = new Vector<>();
        image1 = Toolkit.getDefaultToolkit().getImage("src/resource/bomb_1.gif");
        image2 = Toolkit.getDefaultToolkit().getImage("src/resource/bomb_2.gif");
        image3 = Toolkit.getDefaultToolkit().getImage("src/resource/bomb_3.gif");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //背景
        g.fillRect(0, 0, 800, 700);
        //画玩家坦克
        if (myTank != null && myTank.isLive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), myTank.getClass().getSimpleName());
        }

        for (int i = 0; myTank != null && i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive()) g.fill3DRect(shot.getX() - 2, shot.getY() - 2, 4, 4, false);
            else synchronized (this) {
                myTank.shots.remove(shot);
            }
        }
        //画敌方坦克
        for (EnemyTank enemyTank : enemyTanks) {
            if (!enemyTank.isLive()) {
                synchronized (this) {
                    enemyTanks.remove(enemyTank);
                }
                break;
            }
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), enemyTank.getClass().getSimpleName());
            for (int i = 0; i < enemyTank.shots.size(); i++) {
                Shot shot = enemyTank.shots.get(i);
                if (shot.isLive()) {
                    g.fill3DRect(shot.getX() - 2, shot.getY() - 2, 4, 4, false);
                } else {
                    enemyTank.shots.remove(shot);
                }
            }
        }
        //画爆炸
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.getLife() > 40) g.drawImage(image1, bomb.getX(), bomb.getY(), 60, 60, this);
            else if (bomb.getLife() > 20) g.drawImage(image2, bomb.getX(), bomb.getY(), 60, 60, this);
            else g.drawImage(image3, bomb.getX(), bomb.getY(), 60, 60, this);
            bomb.lifeDown();
            if (!bomb.isLive()) bombs.remove(bomb);
        }
    }

    /**
     * @param x      坦克左上角的x坐标
     * @param y      坦克左上角的y坐标
     * @param g      画笔
     * @param direct 坦克炮管方向
     * @param type   类型
     */
    public void drawTank(int x, int y, Graphics g, Direct direct, String type) {
        switch (type) {
            case "MyTank":
                g.setColor(Color.YELLOW);
                break;
            case "EnemyTank":
                g.setColor(Color.CYAN);
                break;
        }
        switch (direct) {
            case UP:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 9, y + 20, 20, 20);
                g.drawLine(x + 20, y, x + 20, y + 30);
                break;
            case DOWN:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 9, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case LEFT:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 9, 20, 20);
                g.drawLine(x, y + 20, x + 30, y + 20);
                break;
            case RIGHT:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 9, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
        }
    }

    @Deprecated
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (myTank == null) return;
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) myTank.moveUp();
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) myTank.moveDown();
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) myTank.moveLeft();
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) myTank.moveRight();
        //System.out.println("x=" + myTank.getX() + " y=" + myTank.getY());
        //按J射击
        if (e.getKeyCode() == KeyEvent.VK_J) {
            myTank.shotEnemy();
        }
        //K测试敌方子弹
        if (e.getKeyCode() == KeyEvent.VK_K) {
            //初始化敌方坦克子弹
            for (EnemyTank enemyTank : enemyTanks) {
                Shot shot = enemyTank.getShot();
                enemyTank.shots.add(shot);
                //启动一个enemyTankShotTestThread线程
                Thread enemyShotTestThread = new Thread(shot);
                enemyShotTestThread.setName("enemyTankShotTestThread");
                enemyShotTestThread.start();
            }
        }
        //T测试
        if (e.getKeyCode() == KeyEvent.VK_T) {
            System.out.println("当前敌方坦克数量：" + enemyTanks.size());
            System.out.println("当前我的子弹数量：" + myTank.shots.size());
        }
        //Esc退出
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
    }

    @Deprecated
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            try {
                //刷新率
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //刷新
            this.repaint();

            //判断子弹否击中敌方
            synchronized (this) {
                if (myTank != null && myTank.shots.size() != 0) for (int i = 0; i < myTank.shots.size(); i++) {
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        hitTank(myTank.shots.get(i), enemyTanks.get(j));
                    }
                }
            }
            //判断敌方子弹是否击中我
            for (int i = 0; i < enemyTanks.size(); i++) {
                for (int j = 0; j < enemyTanks.get(i).shots.size(); j++) {
                    if (hitTank(enemyTanks.get(i).shots.get(j), myTank)) {
                        JOptionPane.showMessageDialog(this, "游戏结束！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                }
            }
        }
    }

    //判断子弹是否击中坦克
    public boolean hitTank(Shot shot, Tank tank) {
        if (tank == null) return false;
        switch (tank.getDirect()) {
            case UP:
            case DOWN:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 40 && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60) {
                    shot.setLive(false);
                    tank.setLive(false);
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                    return true;
                }
                break;
            case LEFT:
            case RIGHT:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60 && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 40) {
                    shot.setLive(false);
                    tank.setLive(false);
                    bombs.add(new Bomb(tank.getX(), tank.getY()));
                    return true;
                }
                break;
        }
        return false;
    }
}
