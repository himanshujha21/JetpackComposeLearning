package com.example.productexplorerapp.domain.usecase

import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.repository.ProductRepository
import javax.inject.Inject

// LAYER: DOMAIN (use case)
// Why have a use case that only calls the repository? It gives the ViewModel
// one clear action to call. If we later need a rule, like "hide products
// without a picture", we add it here and the ViewModel stays the same.

// A use case does ONE thing the user wants: here, "get all products".
//
// @Inject constructor tells Hilt how to create this class.
// Hilt sees it needs a ProductRepository and passes one in for us.
class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend fun getProducts(): List<Product> {
        return productRepository.getProducts()
    }
}
