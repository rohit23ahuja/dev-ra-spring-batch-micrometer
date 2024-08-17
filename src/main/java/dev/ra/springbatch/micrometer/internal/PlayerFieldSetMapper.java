package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.Player;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class PlayerFieldSetMapper implements FieldSetMapper<Player> {

	@Override
	public Player mapFieldSet(FieldSet fs) {

		if (fs == null) {
			return null;
		}

		Player player = new Player();
		player.setId(fs.readString("ID"));
		player.setLastName(fs.readString("lastName"));
		player.setFirstName(fs.readString("firstName"));
		player.setPosition(fs.readString("position"));
		player.setDebutYear(fs.readInt("debutYear"));
		player.setBirthYear(fs.readInt("birthYear"));

		return player;
	}

}
