package ru.alternative;

/**
 * Created by Zloy on 21.06.2017.
 */
import java.io.File;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

interface DataConnection {

    int loadDatas(int sum) throws Exception;

    void saveData(int year, int qq) throws IOException;
}

public class MyApp implements DataConnection {


    public static void main(String[] args) {
        try {
            //todo переделать на логгер, выбрать логгер, касается всех System.out
            System.out.println("app v.1.13");
            for (int i = startYear; i < endYear; i++) {
                int sum = 0;
                COUNT = 0;
                COUNT1 = 0;
                String y = i + "";
                DataConnection dc = new MyApp(y);
                sum = dc.loadDatas(sum);
                double qq = sum > 0 ? (double) sum / (double) COUNT : 0;
                if (qq > 0) {
                    System.out.println(i + " " + qq);
                }
                dc.saveData(i, (int) qq);
            }
            System.out.println("gotovo");
        } catch (Exception e) {
            //todo вывести в нормальную ошибку
            e.printStackTrace();
            System.out.println("oshibka!");
        }
    }


    public MyApp(String y) {
        this.y = y;
    }
    private String y;
    private static int COUNT = 0;
    private static int COUNT1 = 0;
    protected final static int startYear = 1990;
    protected final static int endYear = 2020;



    public int loadDatas(int sum) throws Exception {
        File file = new File("1.txt");

        StringBuilder s = new StringBuilder();
        try(FileInputStream fis = new FileInputStream(file)) {
            int i = fis.read();
            do {
                s.append(new String(new byte[]{(byte) i}));
                i = fis.read();
            } while (i != -1);
        }

        int begin = 0;
        while (true) {
            int e = s.indexOf("\n", begin + 1);
            if (e == -1) {
                break;
            }
            String ss = s.substring(begin, e);
            String[] sss = ss.split("	");
            if (sss[2].contains(this.y) || sss[2].contains(y)) {
                sum = sum + Integer.parseInt(sss[3]);
            }
            COUNT++;
            begin = e;
        }
        return sum;
    }

    public void saveData(int year, int qq) throws IOException {
        try(FileOutputStream fis = new FileOutputStream(new File("statistika.txt"), true)) {
            String s = COUNT1++ + "	" + year + "	" + qq + "\n";
            fis.write(s.getBytes());
        }
    }
}