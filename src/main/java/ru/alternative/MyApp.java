package ru.alternative;

/**
 * загружаем файл
 * ишем строки с годом с 1990 по 2020 в третием слове и получаем сумму с 4 словом.
 * пишем результат в файлик год, полученная сумма/количество записей
 * Created by Zloy on 21.06.2017.
 * todo - название файлов в аргументы?
 */


import java.io.*;

public class MyApp {

    private StringBuilder s = new StringBuilder();
    private int COUNT = 0;
    private static int COUNT_WRITE = 0;
    public final static int startYear = 1990;
    public final static int endYear = 2020;


    public static void main(String[] args) {
        try {
            //todo переделать на логгер, выбрать логгер, касается всех System.out
            System.out.println("app v.1.13");
            MyApp app = new MyApp();
            app.loadDatas();
            for (int i = startYear; i < endYear; i++) {
                int sum = app.getCountY(String.valueOf(i));
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

    public void loadDatas() throws Exception {
        s = null;
        s = new StringBuilder();
        File file = new File("1.txt");
        try(FileInputStream fis = new FileInputStream(file); BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String strLine;
            while ((strLine = br.readLine()) != null){
                s.append(strLine);
            }
        }
    }

    public int getCountY(String y){
        COUNT = 0;
        COUNT_WRITE = 0;
        int sum = 0, begin = 0, e;
        while ((e = s.indexOf("\n", begin + 1)) != -1) {
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