package io.openjob.worker.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MrTaskTest {
    private Integer id;
    private List<String> names;
}
