package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTO.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public List<CategoryDTO> findAll(){
       List<Category> categoryList = categoryRepository.findAll();

     return categoryList.stream().map(CategoryDTO::new).toList();
    }

    public CategoryDTO findById(Long id){
       Optional<Category> obj = categoryRepository.findById(id);
       Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada"));
       return new CategoryDTO(entity);
    }

}
