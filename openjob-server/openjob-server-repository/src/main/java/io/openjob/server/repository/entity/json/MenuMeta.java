package io.openjob.server.repository.entity.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author inhere
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuMeta implements Serializable {

    private String title;

    private String isLink;

    private Boolean isHide;

    private Boolean isKeepAlive;

    private Boolean isAffix;

    private Boolean isIframe;

    private String icon;

    private List<String> roles;

    /**
     * view component path
     */
    private String component;
}
