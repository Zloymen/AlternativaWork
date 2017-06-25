package ru.alternative.structure;

import java.util.List;

/**
 * Игровая сессия
 * Created by Zloy on 24.06.2017.
 */
public class GameSession {
    private List<QueryGamerSession> gamers;
    private long createTime;

    public GameSession(List<QueryGamerSession> gamers, long createTime) {
        this.gamers = gamers;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "createTime=" + createTime +
                ", gamers= [" + gamers + "]" ;
    }
}
