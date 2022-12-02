package io.openjob.server.repository.dao.impl;

import io.openjob.server.repository.dao.DelayDAO;
import io.openjob.server.repository.entity.Delay;
import io.openjob.server.repository.repository.DelayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Component
public class DelayDAOImpl implements DelayDAO {
    private final DelayRepository delayRepository;

    @Autowired
    public DelayDAOImpl(DelayRepository delayRepository) {
        this.delayRepository = delayRepository;
    }

    @Override
    public Long save(Delay delay) {
        return this.delayRepository.save(delay).getId();
    }

    @Override
    public Delay findByTopic(String topic) {
        return this.delayRepository.findByTopic(topic);
    }

    @Override
    public List<Delay> findByTopics(List<String> topics) {
        return this.delayRepository.findByTopicIn(topics);
    }
}
