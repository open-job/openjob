package io.openjob.server.common.actor;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class PropsFactoryManager extends AbstractExtensionId<PropsFactory> {
    private static PropsFactoryManager provider = new PropsFactoryManager();

    public static PropsFactoryManager getFactory() {
        return provider;
    }

    @Override
    public PropsFactory createExtension(ExtendedActorSystem system) {
        return new PropsFactory();
    }
}
