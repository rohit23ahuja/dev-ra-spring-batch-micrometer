package dev.ra.springbatch.micrometer.internal;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import dev.ra.springbatch.micrometer.CricketPlayer;

public class CricketPlayerItemWriter implements ItemWriter<CricketPlayer> {

	@Override
	public void write(Chunk<? extends CricketPlayer> arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
