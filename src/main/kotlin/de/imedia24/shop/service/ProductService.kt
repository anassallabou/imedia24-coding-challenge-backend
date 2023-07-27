package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {


    fun findProductBySku(sku: String): ProductResponse? {
        val product = productRepository.findBySku(sku);

        if (product != null) {
            return product.description?.let { ProductResponse(product.name, product.sku, it, product.price) }
        };
        return null;
    }

    fun findProductBySkus(skus: ArrayList<String>): ArrayList<ProductResponse>? {
        val products: ArrayList<ProductResponse>? = null;
        for(sku in skus){
            val product = productRepository.findBySku(sku);
            if (product != null) {
               val productRes = product.description?.let { ProductResponse(product.name, product.sku, it, product.price) }!!
                products?.add(productRes);
            }
        }

        return products;
    }

    fun addProduct(product: ProductEntity): ProductEntity? {
        return productRepository.save(product);
    }

    fun updateProduct(product: ProductEntity, sku: String): ProductEntity? {
        val productUpdater = productRepository.findBySku(sku);
        if (productUpdater != null) {
            productUpdater.name = product.name;
            productUpdater.price = product.price;
            productUpdater.description = product.description
        }
        return productUpdater?.let { addProduct(it) };
    }
}
