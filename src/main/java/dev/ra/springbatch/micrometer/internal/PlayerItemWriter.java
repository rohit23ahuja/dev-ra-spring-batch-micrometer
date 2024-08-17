package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.Player;
import dev.ra.springbatch.micrometer.PlayerDao;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;


public class PlayerItemWriter implements ItemWriter<Player> {

	private PlayerDao playerDao;

	@Override
	public void write(Chunk<? extends Player> players) throws Exception {
		for (Player player : players) {
			playerDao.savePlayer(player);
		}
	}

	public void setPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}

}
