package io.openjob.server.repository.util;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dto.BaseFieldsDTO;

/**
 * @author inhere <in.798@qq.com>
 */
public class EntityUtil {

    /**
     * init entity dto
     *
     * @param dto dto
     * @param <T> BaseFieldsDTO
     * @return T dto
     */
    public static <T extends BaseFieldsDTO> T initDefaults(T dto) {
        long curTime = DateUtil.timestamp();

        dto.setCreateTime(curTime);
        dto.setUpdateTime(curTime);
        dto.setDeleted(CommonConstant.NO);
        dto.setDeleteTime(CommonConstant.LONG_ZERO);

        return dto;
    }
}
