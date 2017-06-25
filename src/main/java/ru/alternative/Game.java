package ru.alternative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alternative.structure.GameSession;
import ru.alternative.structure.QueryGamerSession;


/**
 * Игра
 * Created by Zloy on 24.06.2017.
 */
public class Game implements IActionGame {

    private static Logger LOG = LoggerFactory.getLogger(Game.class);

    private AbstMatchmaking matchmaking;

    public Game(AbstMatchmaking matchmaking){
        this.matchmaking = matchmaking;
        matchmaking.setGame(this);
        Thread myThready = new Thread(this.matchmaking);
        myThready.start();
    }

    public void putQuerySession(QueryGamerSession query){
        matchmaking.setUserSession(query);
    }

    @Override
    public void createSession(GameSession session) {
        LOG.info(session.toString());
    }
}
