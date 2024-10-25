package dev.ra.springbatch.micrometer.internal;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import dev.ra.springbatch.micrometer.CricketPlayer;

public class CricketPlayerFieldSetMapper implements FieldSetMapper<CricketPlayer> {

	@Override
	public CricketPlayer mapFieldSet(FieldSet fieldSet) throws BindException {
		if (fieldSet == null) {
			return null;
		}
		CricketPlayer cricketPlayer = new CricketPlayer();
		//"id", "lastName", "firstName", "type"
		cricketPlayer.setId(fieldSet.readString("id"));
		cricketPlayer.setLastName(fieldSet.readString("lastName"));
		cricketPlayer.setFirstName(fieldSet.readString("firstName"));
		cricketPlayer.setType(fieldSet.readString("type"));
		return cricketPlayer;
	}

}
