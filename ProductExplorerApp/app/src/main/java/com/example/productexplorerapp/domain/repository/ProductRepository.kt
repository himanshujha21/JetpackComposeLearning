package com.example.productexplorerapp.domain.repository

import com.example.productexplorerapp.domain.model.Product

// WHAT the app can ask for, not HOW it is done.
//
// The domain layer only knows this interface. The real code that calls the
// API lives in the data layer (ProductRepositoryImpl), and Hilt connects the two.
// Both functions are "suspend" because downloading takes time and must not block the UI.
interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getProductById(id: Int): Product
}
