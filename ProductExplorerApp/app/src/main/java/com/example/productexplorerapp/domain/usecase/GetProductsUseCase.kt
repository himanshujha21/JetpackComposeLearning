package com.example.productexplorerapp.domain.usecase

import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.repository.ProductRepository
import javax.inject.Inject

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
