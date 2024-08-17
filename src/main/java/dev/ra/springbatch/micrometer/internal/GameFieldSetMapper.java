package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.Game;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;


public class GameFieldSetMapper implements FieldSetMapper<Game> {

	@Override
	public Game mapFieldSet(FieldSet fs) {

		if (fs == null) {
			return null;
		}

		Game game = new Game();
		game.setId(fs.readString("id"));
		game.setYear(fs.readInt("year"));
		game.setTeam(fs.readString("team"));
		game.setWeek(fs.readInt("week"));
		game.setOpponent(fs.readString("opponent"));
		game.setCompletes(fs.readInt("completes"));
		game.setAttempts(fs.readInt("attempts"));
		game.setPassingYards(fs.readInt("passingYards"));
		game.setPassingTd(fs.readInt("passingTd"));
		game.setInterceptions(fs.readInt("interceptions"));
		game.setRushes(fs.readInt("rushes"));
		game.setRushYards(fs.readInt("rushYards"));
		game.setReceptions(fs.readInt("receptions", 0));
		game.setReceptionYards(fs.readInt("receptionYards"));
		game.setTotalTd(fs.readInt("totalTd"));

		return game;
	}

}
