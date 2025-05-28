package dev.ra.springbatch.micrometer.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.Types;

// escapeSyntaxCallMode=callIfNoReturn
public class PlayerInsertionService {
    private static final Logger _log = LoggerFactory.getLogger(PlayerInsertionService.class);

    private final DataSource dataSource;
    private final SimpleJdbcCall playerByBirthProc;
    public PlayerInsertionService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.playerByBirthProc = new SimpleJdbcCall(dataSource)
                .withSchemaName("public")
                .withProcedureName("player_by_birth")
                .declareParameters(
                        new SqlParameter("p_player_id", Types.CHAR),
                        new SqlParameter("p_first_name", Types.VARCHAR),
                        new SqlParameter("p_last_name", Types.VARCHAR),
                        new SqlParameter("p_year_of_birth", Types.BIGINT),
                        new SqlParameter("p_year_drafted", Types.BIGINT))
                ;
    }

    public void insertPlayers(String playerId,
                              String firstName,
                              String lastName,
                              String yearOfBirth,
                              String yearOfDraft) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("p_player_id", playerId)
                .addValue("p_first_name", firstName)
                .addValue("p_last_name", lastName)
                .addValue("p_year_of_birth", Long.valueOf(yearOfBirth))
                .addValue("p_year_drafted", Long.valueOf(yearOfDraft));

        playerByBirthProc.execute(paramMap);

    }
}
