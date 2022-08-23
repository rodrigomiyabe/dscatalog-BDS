package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTO.ProductDto;
import com.devsuperior.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(productService.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> insert(@RequestBody ProductDto ProductDto){
      ProductDto = productService.insert(ProductDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ProductDto.getId()).toUri();
        return ResponseEntity.created(uri).body(ProductDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update (@PathVariable Long id, @RequestBody ProductDto ProductDto){
       ProductDto = productService.update(id,ProductDto);
        return ResponseEntity.ok().body(ProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
