package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.CricketPlayerDao;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import dev.ra.springbatch.micrometer.CricketPlayer;

public class CricketPlayerItemWriter implements ItemWriter<CricketPlayer> {

	private CricketPlayerDao playerDao;
	@Override
	public void write(Chunk<? extends CricketPlayer> players) throws Exception {
		for (CricketPlayer player: players) {
			playerDao.save(player);
		}
	}

	public void setPlayerDao(CricketPlayerDao playerDao){
		this.playerDao = playerDao;
	}
}
