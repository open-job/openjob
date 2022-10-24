package io.openjob.server.scheduler.timer;

import io.openjob.server.scheduler.dto.DelayDTO;
import io.openjob.server.scheduler.util.CacheUtil;
import io.openjob.server.repository.util.RedisUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@Getter
@Setter
public class DelayTimerTask extends TimerTask {

    private Long delayId;

    private String delayParams;

    private String delayExtra;

    private String topic;

    private String processorInfo;

    private Integer failRetryTimes;

    private Integer failRetryInterval;

    private Integer executeTimeout;

    private Integer blockingSize;

    private Integer concurrency;

    /**
     * Timer task.
     *
     * @param taskId     taskId
     * @param slotsId    slotsId
     * @param expiration expiration
     */
    public DelayTimerTask(Long taskId, Long slotsId, Long expiration) {
        super(taskId, slotsId, expiration);
    }

    @Override
    public void run() {
        DelayDTO delayDTO = new DelayDTO();
        delayDTO.setDelayId(this.delayId);
        delayDTO.setDelayParams(this.delayParams);
        delayDTO.setDelayExtra(this.delayExtra);
        delayDTO.setTopic(this.topic);
        delayDTO.setProcessorInfo(this.processorInfo);
        delayDTO.setFailRetryTimes(this.failRetryTimes);
        delayDTO.setFailRetryInterval(this.failRetryInterval);
        delayDTO.setExecuteTimeout(this.executeTimeout);
        delayDTO.setBlockingSize(this.blockingSize);
        delayDTO.setConcurrency(this.concurrency);
        RedisUtil.getTemplate().opsForList().rightPush(CacheUtil.getTopicKey(this.topic), delayDTO);
    }
}
