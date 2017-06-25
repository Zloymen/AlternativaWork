package ru.alternative;

import ru.alternative.structure.QueryGamerSession;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * абстрактный матч макер
 * Created by Zloy on 24.06.2017.
 */
public abstract class  AbstMatchmaking implements Runnable {

    protected ThreadPoolExecutor poll = new ThreadPoolExecutor(4, 4, 30, TimeUnit.SECONDS, new SynchronousQueue<>());

    protected IActionGame game;

    protected boolean stop = false;

    public abstract void setUserSession(QueryGamerSession session);

    public void setGame(IActionGame game){
        this.game = game;
    }

    public void stop() {
        this.stop = true;
        poll.shutdown();
    }

    public abstract void  setPair(QueryGamerSession sessionFirst , QueryGamerSession sessionLast);

}
