import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Vector;

/**
 * @author sowhile
 * @version 1.0
 * <p>
 * 2022/11/19 16:54
 */
public class test {
    @Test
    public void m1() throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("c:\\io\\tt.dat"));
        Vector<Integer> integers = new Vector<>();
        for (int i = 0; i < 10; i++) {
            integers.add(i);
        }
        Vector<String> v2 = new Vector<>();
        v2.add("dec");
        v2.add("dvfec");
        v2.add("devsdec");
        objectOutputStream.writeObject(integers);
        objectOutputStream.writeObject(v2);
        objectOutputStream.close();
    }

    @Test
    public void m2() throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("c:\\io\\tt.dat"));
        Vector<Integer> integers = (Vector<Integer>) objectInputStream.readObject();
        for (Integer integer : integers) {
            System.out.println(integer);
        }
        Vector<String> v2 = (Vector<String>) objectInputStream.readObject();
        for (String s : v2) {
            System.out.println(s);
        }
        objectInputStream.close();
    }
}
