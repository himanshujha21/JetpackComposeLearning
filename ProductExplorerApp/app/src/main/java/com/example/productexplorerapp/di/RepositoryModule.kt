package com.example.productexplorerapp.di

import com.example.productexplorerapp.data.repository.CartRepositoryImpl
import com.example.productexplorerapp.data.repository.ProductRepositoryImpl
import com.example.productexplorerapp.domain.repository.CartRepository
import com.example.productexplorerapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// LAYER: DI
// Why: this is the only file that knows both the interface and the real class.
// To swap ProductRepositoryImpl for another class (for example a fake one),
// we change one line here.

// Connects each interface (domain layer) to its real class (data layer).
//
// "When someone asks for a ProductRepository, give them a ProductRepositoryImpl."
// @Binds is the short way to say that for interfaces.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository
}
