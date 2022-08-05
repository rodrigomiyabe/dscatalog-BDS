package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTO.CategoryDTO;
import com.devsuperior.dscatalog.DTO.ProductDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
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
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.repository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(PageRequest pageRequest){
       Page<Product> ProductList = repository.findAll(pageRequest);

     return ProductList.map(ProductDto::new);
    }
    @Transactional(readOnly = true)
    public ProductDto findById(Long id){
       Optional<Product> obj = repository.findById(id);
       Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
       return new ProductDto(entity,entity.getCategories());
    }

    @Transactional()
    public ProductDto insert (ProductDto dto){
        Product product = new Product();
       copyDtoToEntity(dto,product);
        repository.save(product);
        return new ProductDto(product);
    }

    @Transactional()
    public ProductDto update( Long id, ProductDto dto){
        try {
            Product product = repository.getOne(id);
            copyDtoToEntity(dto,product);
            repository.save(product);
            return new ProductDto(product);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("ID not found " + id);
        }

    }

    public void delete(Long id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("ID not found " + id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }

    }

    private void copyDtoToEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setDate(dto.getDate());
        product.setImgUrl(dto.getImgUrl());
        product.setPrice(dto.getPrice());

        product.getCategories().clear();

        for(CategoryDTO catDto : dto.getCategories()){
            Category category = categoryRepository.getOne(catDto.getId());
            product.getCategories().add(category);
        }
    }
}
