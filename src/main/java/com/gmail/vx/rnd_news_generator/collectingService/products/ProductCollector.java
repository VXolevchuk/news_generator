package com.gmail.vx.rnd_news_generator.collectingService.products;

import com.gmail.vx.rnd_news_generator.dto.ProductDTO;

import java.util.ArrayList;

public interface ProductCollector {
    ArrayList<ProductDTO> collectNewProducts();
}
