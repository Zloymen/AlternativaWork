package ru.alternative;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alternative.structure.QueryGamerSession;

/**
 * main
 */
public class App {
    //промежуток запросов
    private static final int MAX_WAIT_TIME = 3;
    //кол-во пользователь в итерацию
    private static final int MAX_USER_SPAN = 50;
    private static Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ){
        DOMConfigurator.configure( "log4j2.xml");
        LOG.info("logger configure!");
        AbstMatchmaking matchmaking;
        Game game = new Game(matchmaking = new Matchmaking());
        long count;
        try {
            count =  Long.valueOf(args[0]);
        }catch (Exception e){
            count = 10000L;
        }
        long l = 1;
        while(l < count){
            try {
                int waitTime = GenerateUserQuery.rnd(MAX_WAIT_TIME);
                int countUser = GenerateUserQuery.rnd(MAX_USER_SPAN);
                for(int i = 1; i <= countUser; i++){
                    QueryGamerSession query = GenerateUserQuery.createUserSession(l);
                    game.putQuerySession(query);
                    if(++l == count) break;
                }

                Thread.sleep(waitTime * 1000);
            }catch (InterruptedException ie){
                LOG.error("error! generate session", ie);
            }
        }
        matchmaking.stop();
    }
}
