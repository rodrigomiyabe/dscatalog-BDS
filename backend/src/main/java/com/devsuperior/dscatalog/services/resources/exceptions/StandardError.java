package com.devsuperior.dscatalog.services.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class StandardError implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
