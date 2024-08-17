package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.Game;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcGameDao extends JdbcDaoSupport implements ItemWriter<Game> {

	private SimpleJdbcInsert insertGame;

	@Override
	protected void initDao() throws Exception {
		super.initDao();
		insertGame = new SimpleJdbcInsert(getDataSource()).withTableName("GAMES")
			.usingColumns("player_id", "year_no", "team", "week", "opponent", " completes", "attempts", "passing_yards",
					"passing_td", "interceptions", "rushes", "rush_yards", "receptions", "receptions_yards",
					"total_td");
	}

	@Override
	public void write(Chunk<? extends Game> games) {

		for (Game game : games) {

			SqlParameterSource values = new MapSqlParameterSource().addValue("player_id", game.getId())
				.addValue("year_no", game.getYear())
				.addValue("team", game.getTeam())
				.addValue("week", game.getWeek())
				.addValue("opponent", game.getOpponent())
				.addValue("completes", game.getCompletes())
				.addValue("attempts", game.getAttempts())
				.addValue("passing_yards", game.getPassingYards())
				.addValue("passing_td", game.getPassingTd())
				.addValue("interceptions", game.getInterceptions())
				.addValue("rushes", game.getRushes())
				.addValue("rush_yards", game.getRushYards())
				.addValue("receptions", game.getReceptions())
				.addValue("receptions_yards", game.getReceptionYards())
				.addValue("total_td", game.getTotalTd());
			this.insertGame.execute(values);

		}

	}

}
