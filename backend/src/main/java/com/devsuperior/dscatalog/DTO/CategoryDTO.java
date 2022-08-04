package com.devsuperior.dscatalog.DTO;

import com.devsuperior.dscatalog.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long id;
    private String name;

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
