package ru.alternative;

import ru.alternative.structure.QueryGamerSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Zloy on 21.06.2017.
 */
public class Matchmaking {

    private static long currTime;

    public static void createMatch(List<QueryGamerSession> gamerSessions){
        currTime = System.currentTimeMillis();

        gamerSessions.sort( Matchmaking::comparable);
        List<QueryGamerSession> resultList = gamerSessions.subList(0,7);

        System.out.print(System.currentTimeMillis() - currTime);
        System.out.print(resultList.toString());
    }

    private static long waitingTime(long timeBegin, long timeEnd){
        return timeEnd - timeBegin;
    }

    private static int comparable(QueryGamerSession A, QueryGamerSession B){
        if ((abs(A.getRank() - B.getRank()) > (waitingTime(currTime, A.getCreateTime()) / 5000)) &&
                (abs(A.getRank() - B.getRank()) > (waitingTime(currTime, B.getCreateTime()) / 5000))) return 1;
        else if((abs(A.getRank() - B.getRank()) == (waitingTime(currTime, A.getCreateTime()) / 5000)) &&
                (abs(A.getRank() - B.getRank()) == (waitingTime(currTime, B.getCreateTime()) / 5000))) return 0;

        return -1;
    }

}
