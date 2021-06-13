package com.gmail.vx.rnd_news_generator.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Sellers")
public class Seller {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy="seller", cascade=CascadeType.ALL)
    private List<Product> products = new ArrayList<Product>();

    private String name;

    public Seller() {
    }

    public Seller(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProduct(Product product) {
        this.products.add(product);
    }


}
