import java.io.*;
import java.util.Vector;

/**
 * @author sowhile
 * @version 1.0
 * <p>
 * 一个记录器，用于记录相关信息
 * 2022/11/19 14:25
 */
public class Recorder {
    public static MyPanel mypanel;
    private static int defeatNum;
    private static final File file;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static boolean loadGame = false;
    private static Vector<EnemyTank> enemyTanks;
    private static MyTank myTank;
    private static Vector<Shot> myTankShots;
    private static Vector<Shot> enemyTankShots;

    static {
        try {
            file = new File("src/resource/info.dat");
            if (!file.exists()) {
                file.createNewFile();
            } else loadGame = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            System.out.println((objectInputStream.readObject()));
//            mypanel.enemyTanks = ((Vector<EnemyTank>) (objectInputStream.readObject()));
            mypanel.myTank = (MyTank) objectInputStream.readObject();
            mypanel.myTank.shots = (Vector<Shot>) objectInputStream.readObject();
            defeatNum = objectInputStream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void save() {
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(enemyTanks);
            objectOutputStream.writeObject(myTank);
            objectOutputStream.writeObject(myTankShots);
            objectOutputStream.writeInt(defeatNum);
            System.out.println(enemyTanks);
            System.out.println(myTank);
            System.out.println(myTankShots);
            System.out.println(defeatNum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void addDefeatNum() {
        defeatNum++;
    }

    public static int getDefeatNum() {
        return defeatNum;
    }

    public static Vector<EnemyTank> getTanks() {
        return enemyTanks;
    }

    public static void setEnemyTank(Vector<EnemyTank> tanks) {
        Recorder.enemyTanks = tanks;
    }

    public static void setMyTank(MyTank myTank) {
        Recorder.myTank = myTank;
    }

    public static void setMyTankShots(Vector<Shot> myTankShots) {
        Recorder.myTankShots = myTankShots;
    }

    public static void setEnemyTankShots(Vector<Shot> enemyTankShots) {
        Recorder.enemyTankShots = enemyTankShots;
    }

    public static boolean isLoadGame() {
        return loadGame;
    }

    public static void setLoadGame(boolean loadGame) {
        Recorder.loadGame = loadGame;
    }
}
