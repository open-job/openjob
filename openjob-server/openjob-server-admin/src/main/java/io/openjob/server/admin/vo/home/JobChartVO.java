package io.openjob.server.admin.vo.home;

import lombok.Data;

import java.util.List;

/**
 * @author stelin swoft@qq.com
 * @since 1.0.2
 */
@Data
public class JobChartVO {
    private List<String> xData;
    private List<Long> successData;
    private List<Long> failData;
}
