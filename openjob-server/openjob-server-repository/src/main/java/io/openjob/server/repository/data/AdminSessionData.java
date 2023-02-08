package io.openjob.server.repository.data;

import io.openjob.server.repository.dto.AdminSessionDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author inhere
 * @date 2023-02-08 14:21:20
 * @since 1.0.0
 */
public interface AdminSessionData {

    /**
     * add AdminSession
     *
     * @param dto dto
     * @return id
     */
    Long add(AdminSessionDTO dto);

    /**
     * batch add AdminSession
     *
     * @param dtoList dto list
     */
    void batchAdd(List<AdminSessionDTO> dtoList);

    /**
     * get AdminSession by ID
     *
     * @param id id
     * @return AdminSession
     */
    AdminSessionDTO getById(Long id);

    /**
     * update AdminSession by ID
     *
     * @param dto dto
     * @return number
     */
    Integer updateById(AdminSessionDTO dto);

    /**
     * get AdminSession list by page
     *
     * @param page page
     * @param size size
     * @return AdminSession list
     */
    Page<AdminSessionDTO> getPageList(Integer page, Integer size);

}

