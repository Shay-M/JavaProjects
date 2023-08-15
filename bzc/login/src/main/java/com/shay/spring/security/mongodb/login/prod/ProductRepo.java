package com.shay.spring.security.mongodb.login.prod;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo
        extends MongoRepository<Product,String> {


}
