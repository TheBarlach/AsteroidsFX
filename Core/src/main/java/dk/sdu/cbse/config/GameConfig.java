package dk.sdu.cbse.config;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.GameWorld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dk.sdu.cbse.services.GameServiceRegistry;

@Configuration
public class GameConfig {

    @Bean
    public GameData gameData() {
        return new GameData(800, 600);
    }

    @Bean
    public GameWorld gameWorld() {
        return new GameWorld();
    }

    @Bean
    public GameServiceRegistry gameServiceRegistry() {
        return new GameServiceRegistry();
    }
}