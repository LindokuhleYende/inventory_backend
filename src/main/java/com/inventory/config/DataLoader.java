package com.inventory.config;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Product p1 = new Product();
                p1.setName("Grass-fed Beef Steak");
                p1.setSku("BEEF-1000");
                p1.setPrice(new BigDecimal("129.99"));
                p1.setStock(12);
                p1.setCategory("Meat");
                repo.save(p1);

                Product p2 = new Product();
                p2.setName("Braai Sauce 500ml");
                p2.setSku("SAUCE-500");
                p2.setPrice(new BigDecimal("34.50"));
                p2.setStock(50);
                p2.setCategory("Sauces");
                repo.save(p2);

                // Add more if you'd like.
            }
        };
    }
}

