package ru.alternative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alternative.structure.FinderPairWorker;
import ru.alternative.structure.GameSession;
import ru.alternative.structure.QueryGamerSession;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RejectedExecutionException;

/**
 * Построитель матча
 * Created by Zloy on 21.06.2017.
 */
public class Matchmaking extends AbstMatchmaking {

    private static Logger LOG = LoggerFactory.getLogger(Matchmaking.class);

    private List<QueryGamerSession> storage = new CopyOnWriteArrayList<>();

    private List<QueryGamerSession> storageMatch = new CopyOnWriteArrayList<>();

    private Long firstTime = System.currentTimeMillis();

    @Override
    public void setUserSession(QueryGamerSession session) {
        storage.add(session);
    }

    @Override
    public synchronized void setPair(QueryGamerSession sessionFirst, QueryGamerSession sessionLast) {

        LOG.debug(sessionFirst.toString() + " - " + sessionLast.toString());
/*        if(storageMatch.isEmpty()) {
            firstTime = //sessionFirst.getCreateTime() > sessionLast.getCreateTime() ? sessionLast.getCreateTime() : sessionFirst.getCreateTime();
        }*/
        storageMatch.add(sessionFirst);
        storageMatch.add(sessionLast);
        if(storageMatch.size() == 8){
            game.createSession(new GameSession(storageMatch, System.currentTimeMillis() - firstTime));
            storageMatch = null;
            storageMatch = new LinkedList<>();
            firstTime = System.currentTimeMillis();
        }
    }

    public synchronized boolean getElement(QueryGamerSession session){
        return storage.remove(session);
    }

    @Override
    public void run() {
        LOG.debug("RUN Matchmaking");
        while(!stop){
            try {
                QueryGamerSession session = storage.get(0);
                if(getElement(session)) poll.execute(new FinderPairWorker(session, storage, this));
            }catch(RejectedExecutionException | IndexOutOfBoundsException re){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
