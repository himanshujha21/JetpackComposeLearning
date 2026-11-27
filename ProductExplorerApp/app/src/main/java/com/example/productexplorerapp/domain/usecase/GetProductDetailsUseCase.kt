package com.example.productexplorerapp.domain.usecase

import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.repository.ProductRepository
import javax.inject.Inject

// LAYER: DOMAIN (use case)
// Why: the detail screen asks for "one product" through this class instead of
// calling the repository itself, the same way the list screen does.

// Gets one product by its id, for the detail screen.
class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend fun getProductDetails(productId: Int): Product {
        return productRepository.getProductById(productId)
    }
}
