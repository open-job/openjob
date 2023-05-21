package io.openjob.server.autoconfigure;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.openjob.common.OpenjobSpringContext;
import io.openjob.common.constant.AkkaConstant;
import io.openjob.common.util.IpUtil;
import io.openjob.server.cluster.ClusterServer;
import io.openjob.server.common.actor.PropsFactoryManager;
import io.openjob.server.common.constant.AkkaConfigConstant;
import io.openjob.server.event.ApplicationReadyEventListener;
import io.openjob.server.scheduler.Scheduler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {AkkaProperties.class})
public class ServerAutoConfiguration {

    /**
     * Application ready event listener.
     *
     * @param clusterManager clusterManager
     * @param scheduler      scheduler
     * @return ApplicationReadyEventListener
     */
    @Bean
    public ApplicationReadyEventListener listener(ClusterServer clusterManager, Scheduler scheduler) {
        return new ApplicationReadyEventListener(clusterManager, scheduler);
    }

    @Bean
    public OpenjobSpringContext openjobSpringContext() {
        return new OpenjobSpringContext();
    }

    /**
     * Actor system.
     *
     * @param applicationContext applicationContext
     * @return ActorSystem
     */
    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext, AkkaProperties akkaProperties) {
        // Remote hostname
        String remoteHostname = akkaProperties.getRemote().getHostname();
        if (StringUtils.isEmpty(remoteHostname)) {
            remoteHostname = IpUtil.getLocalAddress();
        }

        // Bind hostname
        String bindHostname = akkaProperties.getBind().getHostname();
        if (StringUtils.isEmpty(bindHostname)) {
            bindHostname = IpUtil.getLocalAddress();
        }

        // Merge config
        Map<String, Object> newConfig = new HashMap<>(8);
        newConfig.put("akka.remote.artery.canonical.hostname", remoteHostname);
        newConfig.put("akka.remote.artery.canonical.port", String.valueOf(akkaProperties.getRemote().getPort()));
        newConfig.put("akka.remote.artery.bind.hostname", bindHostname);
        newConfig.put("akka.remote.artery.bind.port", String.valueOf(akkaProperties.getBind().getPort()));

        Config defaultConfig = ConfigFactory.load(AkkaConfigConstant.AKKA_CONFIG);
        Config mergedConfig = ConfigFactory.parseMap(newConfig).withFallback(defaultConfig);

        // Create actor system
        ActorSystem system = ActorSystem.create(AkkaConstant.SERVER_SYSTEM_NAME, mergedConfig);

        // Set ApplicationContext
        PropsFactoryManager.getFactory().get(system).init(applicationContext);
        return system;
    }
}
