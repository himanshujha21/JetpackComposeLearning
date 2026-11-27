package com.example.productexplorerapp.data.repository

import com.example.productexplorerapp.data.remote.ProductApi
import com.example.productexplorerapp.data.remote.ProductDto
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.repository.ProductRepository
import javax.inject.Inject

// The real ProductRepository: it gets products from the Fake Store API.
//
// If the call fails (no internet, server down) Retrofit throws an exception.
// We let it go up to the ViewModel, which shows an error message.
class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        val productDtos = productApi.getProducts()
        return productDtos.map { mapToProduct(it) }
    }

    override suspend fun getProductById(id: Int): Product {
        val productDto = productApi.getProductById(id)
        return mapToProduct(productDto)
    }

    // Turns the API object (ProductDto) into the app's own object (Product).
    // We also flatten the nested rating and rename "image" to "imageUrl".
    // This way the rest of the app never sees ProductDto.
    private fun mapToProduct(productDto: ProductDto): Product {
        return Product(
            id = productDto.id,
            title = productDto.title,
            price = productDto.price,
            description = productDto.description,
            category = productDto.category,
            imageUrl = productDto.image,
            rating = productDto.rating.rate,
            ratingCount = productDto.rating.count,
        )
    }
}
