package io.openjob.server.cluster.context;

import lombok.Data;

import java.util.Set;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
public class Slots {
    private Set<Long> slotsIds;
}
