package ru.alternative.structure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alternative.AbstMatchmaking;
import ru.alternative.Matchmaking;

import java.util.Iterator;
import java.util.List;

import static java.lang.Math.abs;

/**
 * поток для поиска
 * Created by Zloy on 24.06.2017.
 */
public class FinderPairWorker implements Runnable {

    private static Logger LOG = LoggerFactory.getLogger(FinderPairWorker.class);

    private QueryGamerSession query;

    private List<QueryGamerSession> contenders;

    private Matchmaking container;

    public FinderPairWorker(QueryGamerSession query, List<QueryGamerSession> contenders, Matchmaking container) {
        this.query = query;
        this.contenders = contenders;
        this.container = container;
    }

    @Override
    public void run() {
        LOG.debug("run worker");
        while(true){

            if(contenders.isEmpty()){
                container.setUserSession(query);
                return;
            }

            Iterator<QueryGamerSession> it =  contenders.iterator();

            while (it.hasNext()){
                QueryGamerSession contender = it.next();
                if(comparable(query, contender) && container.getElement(contender)){
                    container.setPair(query, contender);
                    return;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean comparable(QueryGamerSession A, QueryGamerSession B){
        long currTime = System.currentTimeMillis();
        return  ((abs(A.getRank() - B.getRank()) >= (waitingTime(currTime, A.getCreateTime()) / 5000)) &&
                (abs(A.getRank() - B.getRank()) >= (waitingTime(currTime, B.getCreateTime()) / 5000)));

    }

    private static long waitingTime(long timeBegin, long timeEnd){
        return timeEnd - timeBegin;
    }
}
