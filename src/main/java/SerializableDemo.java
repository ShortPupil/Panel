import java.awt.Shape;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import graph.MyShape;

public class SerializableDemo {

    public static void main(String[] args) {
        //Initializes The Object
       // Shape myShape_1 = new Line2D.Double(0, 0, 0, 0);
        MyShape myShape_2 = new MyShape(new ArrayList<Shape>());
        myShape_2.setOver(false);
        //System.out.println(myShape);

        //Write Obj to File
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("./saveFile.txt"));
           // oos.writeObject(myShape_1);
            oos.writeObject(myShape_2);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read Obj from File
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
        fis = new FileInputStream("./saveFile.txt");
        ois = new ObjectInputStream(fis);

        while (true) {
        	System.out.println(ois.readObject().getClass().getName());
        	MyShape s = (MyShape)ois.readObject();
        	System.out.println(s.isOver());
        }

        } catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (IOException e) {
        System.out.println("文件终止");
        e.printStackTrace(); //此处解决序列化完成后的异常
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("输出结束");
        } finally {
        try {
        if (fis != null)
        fis.close();
        if (ois != null)
        ois.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        }

        }
        
    
}