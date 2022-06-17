package io.openjob.server.common.autoconfigure;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.server.common.SpringExtensionProvider;
import io.openjob.server.common.constant.ActorConstant;
import io.openjob.server.common.constant.AkkaConfigConstant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Configuration
public class CommonAutoConfiguration {
    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        // Merge config
        Map<String, Object> newConfig = new HashMap<>();
        Config defaultConfig = ConfigFactory.load(AkkaConfigConstant.AKKA_CONFIG_NAME);
        Config mergedConfig = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);

        // Create actor system
        ActorSystem system = ActorSystem.create(ActorConstant.SYSTEM_NAME, mergedConfig);

        SpringExtensionProvider.getProvider().get(system).init(applicationContext);
        return system;
    }
}
