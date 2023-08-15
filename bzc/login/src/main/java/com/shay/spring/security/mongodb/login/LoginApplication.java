package com.shay.spring.security.mongodb.login;


import com.shay.spring.security.mongodb.login.movies.Movie;
import com.shay.spring.security.mongodb.login.movies.MovieRepository;
import com.shay.spring.security.mongodb.login.movies.MovieService;
import com.shay.spring.security.mongodb.login.prod.Product;
import com.shay.spring.security.mongodb.login.prod.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Map;


@EnableMongoRepositories
@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        // Dotenv dotenv = Dotenv.configure().load();
        //
        // // Get the environment variables
        // String mongoDatabase = dotenv.get("MONGO_DATABASE");
        // String mongoUser = dotenv.get("MONGO_USER");
        // String mongoPassword = dotenv.get("MONGO_PASSWORD");
        // String mongoCluster = dotenv.get("MONGO_CLUSTER");
        //
        // // Set the environment variables as system properties
        // System.setProperty("spring.data.mongodb.database", mongoDatabase);
        // System.setProperty("spring.data.mongodb.uri", "mongodb+srv://" + mongoUser + ":" + mongoPassword + "@" + mongoCluster);


        SpringApplication.run(LoginApplication.class, args);



    }

    @Bean
    CommandLineRunner runner(MovieService service) {
        return args -> {
            var mov = Movie.builder()
                    .imdbId("name1")
                    .build();
            service.addMovie(mov);
            System.out.println(mov);


        };
    }

    // @Bean
    // CommandLineRunner runner(UserRepositoryMongodb repository) {
    //     return args -> {
    //         final User user = new User(
    //                 "dan",
    //                 "abc@gmail.com",
    //                 "123321123",
    //                 ERole.USER,
    //                 false,
    //                 true
    //         );
    //         repository.insert(user);
    //     };
    //
    // }


}
