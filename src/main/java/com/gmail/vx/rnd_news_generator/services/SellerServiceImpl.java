package com.gmail.vx.rnd_news_generator.services;

import com.gmail.vx.rnd_news_generator.model.Seller;
import com.gmail.vx.rnd_news_generator.repos.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerServiceImpl implements SellerService{
    private final SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Transactional
    @Override
    public void addSeller(Seller seller) {
        sellerRepository.save(seller);
    }

    @Transactional(readOnly=true)
    public boolean existsByName(String name) {
        return sellerRepository.existsByName(name);
    }
}
