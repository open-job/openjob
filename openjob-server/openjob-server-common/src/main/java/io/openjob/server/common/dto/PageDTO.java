package io.openjob.server.common.dto;

import lombok.Data;

import java.util.List;

/**
 * @author inhere <in.798@qq.com>
 */
@Data
public class PageDTO<T> {
    /**
     * Current page.
     */
    private Integer page;

    /**
     * Current size.
     */
    private Integer size;

    /**
     * Total.
     */
    private Long total;

    /**
     * Page List
     */
    private List<T> list;
}
