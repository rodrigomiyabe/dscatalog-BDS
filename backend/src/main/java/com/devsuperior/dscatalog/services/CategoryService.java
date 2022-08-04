package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTO.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
       Page<Category> categoryList = categoryRepository.findAll(pageRequest);

     return categoryList.map(CategoryDTO::new);
    }
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
       Optional<Category> obj = categoryRepository.findById(id);
       Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
       return new CategoryDTO(entity);
    }

    @Transactional()
    public CategoryDTO insert (CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional()
    public CategoryDTO update( Long id, CategoryDTO dto){
        try {
            Category category = categoryRepository.getOne(id);
            category.setName(dto.getName());
            categoryRepository.save(category);
            return new CategoryDTO(category);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("ID not found " + id);
        }

    }

    public void delete(Long id){
        try{
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("ID not found " + id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }

}
