package io.openjob.server.autoconfigure;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.server.cluster.ClusterServer;
import io.openjob.server.common.SpringContext;
import io.openjob.server.common.actor.PropsFactoryManager;
import io.openjob.server.common.constant.ActorConstant;
import io.openjob.server.common.constant.AkkaConfigConstant;
import io.openjob.server.event.ApplicationReadyEventListener;
import io.openjob.server.scheduler.Scheduler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(ServerProperties.class)
public class ServerAutoConfiguration {

    @Bean
    public ApplicationReadyEventListener listener(ClusterServer clusterManager) {
        return new ApplicationReadyEventListener(clusterManager);
    }

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        // Merge config
        Map<String, Object> newConfig = new HashMap<>(16);
        Config defaultConfig = ConfigFactory.load(AkkaConfigConstant.AKKA_CONFIG);
        Config mergedConfig = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);

        // Create actor system
        ActorSystem system = ActorSystem.create(ActorConstant.SYSTEM_NAME, mergedConfig);

        // Set ApplicationContext
        PropsFactoryManager.getFactory().get(system).init(applicationContext);
        return system;
    }
}
