package dev.ra.springbatch.micrometer.dao;

import dev.ra.springbatch.micrometer.domain.Player;
import org.springframework.jdbc.core.namedparam.*;

import javax.sql.DataSource;

public class PlayerDao {
    public static final String INSERT_PLAYER = """
            INSERT into player (id, firstname, lastname)
            values (:id, :firstname, :lastname)""";

    private DataSource dataSource;

    public void save(Player player){
        SqlParameterSource values = new MapSqlParameterSource()
                .addValue("id", Integer.valueOf(player.getId()))
                .addValue("firstname", player.getFirstName())
                .addValue("lastname", player.getLastName());
      new NamedParameterJdbcTemplate(dataSource).update(INSERT_PLAYER, values);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
