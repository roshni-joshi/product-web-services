package com.product.productwebservices.service;

import java.util.List;

import com.product.productwebservices.dto.ProductDTO;

public interface ProductService {

	List<ProductDTO> getAllProducts();
	
	ProductDTO getProductById(final Long id);
	
	ProductDTO createProduct(final ProductDTO productDTO);
	
	ProductDTO updateProduct(final ProductDTO productDTO);
	
	void deleteProduct(final Long id);
}
