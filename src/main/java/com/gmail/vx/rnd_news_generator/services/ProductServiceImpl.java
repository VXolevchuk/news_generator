package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Product;
import com.gmail.vx.rnd_news_generator.model.Subject;
import com.gmail.vx.rnd_news_generator.repos.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public void addProduct(Product product ) {
        productRepository.save(product);
    }

    @Transactional(readOnly=true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Transactional(readOnly=true)
    @Override
    public boolean existsByUrl(String url) {
        return productRepository.existsByUrl(url);
    }
}
