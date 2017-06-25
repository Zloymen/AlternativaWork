package ru.alternative;

import ru.alternative.structure.QueryGamerSession;


/**
 * генератор запросов игровой сессии
 * Created by Zloy on 24.06.2017.
 */
public class GenerateUserQuery {
    public static final int MAX = 30;

    public static int rnd( ){
        return rnd(MAX);
    }

    public static int rnd(int value){
        return (int) (Math.random() * value) + 1;
    }

    public static QueryGamerSession createUserSession(long id){
        return new QueryGamerSession(String.valueOf(id), rnd(), System.currentTimeMillis());
    }
}
