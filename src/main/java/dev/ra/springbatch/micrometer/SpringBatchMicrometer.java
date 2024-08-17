package dev.ra.springbatch.micrometer;

import dev.ra.springbatch.micrometer.dao.PlayerDao;
import dev.ra.springbatch.micrometer.domain.Game;
import dev.ra.springbatch.micrometer.domain.Player;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchMicrometer {
    private static ApplicationContext context;
    public static void main(String[] args) {
      context = new ClassPathXmlApplicationContext("context.xml");
      String param = args[0];
      if (param.equals("player") ) {
          Player p = (Player) context.getBean("player");
          System.out.println(p);
          PlayerDao playerDao = (PlayerDao) context.getBean("playerDao");
          playerDao.save(p);
      } else if(param.equals("game")) {
          Game g = (Game) context.getBean("game");
          System.out.println(g);
      } else {
          System.out.println("invalid value");
      }

    }
}
