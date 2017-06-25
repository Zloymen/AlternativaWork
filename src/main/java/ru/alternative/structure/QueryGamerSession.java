package ru.alternative.structure;

/**
 * запрос на игровую сессию
 * Created by Zloy on 21.06.2017.
 */
public class QueryGamerSession {
    //user - уникальный идентификатор пользователя
    private String user;
    //rank - уровень пользователя в игре, от 1 до 30
    private Integer rank;
    //enter_time(user) - время получения команды регистрации
    private Long createTime;

    public QueryGamerSession(String user, Integer rank, Long createTime) {
        this.user = user;
        this.rank = rank;
        this.createTime = createTime;
    }

    public String getUser() {
        return user;
    }

    public Integer getRank() {
        return rank;
    }

    public Long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "{User = " + user + ",rank = " + rank + ",createTime =" + createTime +'}';
    }
}
