package io.openjob.server.repository.util;

import io.openjob.common.constant.CommonConstant;
import io.openjob.common.util.DateUtil;
import io.openjob.server.repository.dto.BaseFieldsDTO;
import org.springframework.data.domain.Sort;

/**
 * @author inhere <in.798@qq.com>
 */
public class EntityUtil {

    /**
     * default sort
     * <p>
     * - tip: the second param is entity property name, is not table column name.
     */
    public final static Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "createTime");

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
