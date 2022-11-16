package io.openjob.server.repository.dto.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author inhere
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateExtraDTO implements Serializable {
    private String aptToken;
}
