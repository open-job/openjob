package io.openjob.server.repository.entity.json;

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
public class TemplateExtra implements Serializable {
    private String aptToken;
}
