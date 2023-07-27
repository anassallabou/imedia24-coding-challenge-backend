package de.imedia24.shop.controller

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.websocket.server.PathParam

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }
    @GetMapping("/products/list", produces = ["application/json;charset=utf-8"])
    fun findProductsBySkus(
            @PathParam("skus") skus: ArrayList<String>
    ): ResponseEntity<ArrayList<ProductResponse>> {
        //logger.info("Request for product $sku")

        val products = productService.findProductBySkus(skus)
        return if(products == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }

    @PostMapping("/products/add", produces = ["application/json;charset=utf-8"])
    fun addProduct(
            @PathParam("product") product: ProductEntity
    ): ProductEntity? {
        return productService.addProduct(product)
    }

    @PatchMapping("/products/update/{sku}", produces = ["application/json;charset=utf-8"])
    fun updateProduct(
            @PathVariable("sku") sku: String,
            @RequestBody product: ProductEntity
    ): ProductEntity? {
        return productService.addProduct(product)
    }
}
