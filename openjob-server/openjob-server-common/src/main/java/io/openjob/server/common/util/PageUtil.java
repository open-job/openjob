package io.openjob.server.common.util;

import com.google.common.collect.Lists;
import io.openjob.server.common.dto.PageDTO;
import io.openjob.server.common.vo.PageVO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
public class PageUtil {
    public static <S, T> PageVO<T> convert(PageDTO<S> pageDTO, Function<S, T> function) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setSize(pageDTO.getSize());
        pageVO.setPage(pageDTO.getPage());
        pageVO.setTotal(pageDTO.getTotal());

        List<T> list = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(pageDTO.getList())) {
            list = pageDTO.getList().stream().map(function).collect(Collectors.toList());
        }

        pageVO.setList(list);
        return pageVO;
    }
}
