package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.CricketPlayer;
import dev.ra.springbatch.micrometer.CricketPlayerDao;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class JdbcCricketPlayerDao implements CricketPlayerDao {
    private static final String INSERT_PLAYER = """
            INSERT INTO CRICKET_PLAYERS(PLAYER_ID,LAST_NAME,FIRST_NAME,TYPE)
            VALUES (:id,:lastName,:firstName,:type);
            """;
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void save(CricketPlayer cricketPlayer) {
        namedParameterJdbcOperations.update(INSERT_PLAYER, new BeanPropertySqlParameterSource(cricketPlayer));
    }

    public void setDataSource(DataSource dataSource){
        namedParameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
    }
}
