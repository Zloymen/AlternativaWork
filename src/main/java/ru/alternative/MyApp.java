package ru.alternative;

/**
 * Created by Zloy on 21.06.2017.
 */
import java.io.*;

interface DataConnection {

    int loadDatas(int sum) throws Exception;

    void saveData(int year, int qq) throws IOException;
}

public class MyApp implements DataConnection {
    private String y;
    private int COUNT = 0;
    private int COUNT_WRITE = 0;
    protected final static int startYear = 1990;
    protected final static int endYear = 2020;


    public static void main(String[] args) {
        try {
            //todo переделать на логгер, выбрать логгер, касается всех System.out
            System.out.println("app v.1.13");
            for (int i = startYear; i < endYear; i++) {
                int sum = 0;
                MyApp app = new MyApp(String.valueOf(i));
                sum = app.loadDatas(sum);
                int count = app.getCOUNT();
                double qq = (double) sum / count == 0 ? 1 : count;
                if (qq > 0) {
                    System.out.println(i + " " + qq);
                }
                app.saveData(i, (int) qq);
            }
            System.out.println("gotovo");
        } catch (Exception e) {
            //todo вывести в нормальную ошибку
            e.printStackTrace();
            System.out.println("oshibka!");
        }
    }

    public int getCOUNT() {
        return COUNT;
    }

    public MyApp(String y) {
        this.y = y;
    }

    public int loadDatas(int sum) throws Exception {
        File file = new File("1.txt");

        StringBuilder s = new StringBuilder();
        try(FileInputStream fis = new FileInputStream(file); BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String strLine;
            while ((strLine = br.readLine()) != null){
                s.append(strLine);
            }
        }

        int begin = 0;
        while (true) {
            int e = s.indexOf("\n", begin + 1);
            if (e == -1) {
                break;
            }
            String ss = s.substring(begin, e);
            String[] sss = ss.split("	");
            if (sss.length > 2 && sss[2].contains(y)) {
                sum = sum + Integer.parseInt(sss[3]);
            }
            COUNT++;
            begin = e;
        }
        return sum;
    }

    public void saveData(int year, int qq) throws IOException {
        try(FileOutputStream fis = new FileOutputStream(new File("statistika.txt"), true); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fis))) {
            bw.write(COUNT_WRITE++ + "	" + year + "	" + qq + "\n");
        }
    }
}