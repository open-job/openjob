package io.openjob.server.repository.dao;

import io.openjob.server.repository.entity.NotifyContact;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @since 1.0.0
 */
public interface NotifyContactDAO {

    /**
     * add NotifyContact
     *
     * @param entity entity
     * @return id
     */
    Long add(NotifyContact entity);

    /**
     * batch add NotifyContact
     *
     * @param entityList entity list
     */
    void batchAdd(List<NotifyContact> entityList);

    /**
     * get NotifyContact by Id
     *
     * @param id id
     * @return NotifyContact
     */
    NotifyContact getById(Long id);

    /**
     * update NotifyContact by ID
     *
     * @param entity entity
     * @return number
     */
    Integer updateById(NotifyContact entity);

    /**
     * get NotifyContact list by page, size
     *
     * @param page page
     * @param size size
     * @return NotifyContact list
     */
    Page<NotifyContact> getPageList(Integer page, Integer size);
}

