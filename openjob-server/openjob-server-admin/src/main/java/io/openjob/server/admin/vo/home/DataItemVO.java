package io.openjob.server.admin.vo.home;

import lombok.Data;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class DataItemVO {
    private Long total;
    private Long newTotal;

    public DataItemVO(Long total, Long newTotal) {
        this.total = total;
        this.newTotal = newTotal;
    }
}
