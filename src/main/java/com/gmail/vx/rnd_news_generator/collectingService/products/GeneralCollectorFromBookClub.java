package com.gmail.vx.rnd_news_generator.collectingService.products;

import com.gmail.vx.rnd_news_generator.dto.ProductDTO;
import com.gmail.vx.rnd_news_generator.model.Product;
import com.gmail.vx.rnd_news_generator.model.Seller;
import com.gmail.vx.rnd_news_generator.services.ProductService;
import com.gmail.vx.rnd_news_generator.services.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GeneralCollectorFromBookClub {
    @Autowired
    private SellerService sellerService;

    private Seller seller = new Seller("Book club");

    @Autowired
    private ProductCollector productCollector;
    @Autowired
    private ProductService productService;

    public void collect() {
        ArrayList<ProductDTO> productDTOS = productCollector.collectNewProducts();
        if (!(sellerService.existsByName(seller.getName()))) {
            sellerService.addSeller(seller);
        }

        for(ProductDTO productDTO : productDTOS) {
            if (!(productService.existsByUrl(productDTO.getUrl()))) {
                Product product = Product.fromDTO(productDTO);
                product.addSeller(this.seller);
                productService.addProduct(product);
            }
        }
    }

}
