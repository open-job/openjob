package io.openjob.server.admin.vo.home;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class SystemDataVO {
    private DataItemVO app;
    private DataItemVO server;
    private DataItemVO worker;
    private DataItemVO slot;
}
