package io.openjob.server.common;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;
import io.openjob.server.common.actor.SpringExtension;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class SpringExtensionProvider extends AbstractExtensionId<SpringExtension> {
    private static SpringExtensionProvider provider = new SpringExtensionProvider();

    public static SpringExtensionProvider getProvider() {
        return provider;
    }

    @Override
    public SpringExtension createExtension(ExtendedActorSystem system) {
        return new SpringExtension();
    }
}
