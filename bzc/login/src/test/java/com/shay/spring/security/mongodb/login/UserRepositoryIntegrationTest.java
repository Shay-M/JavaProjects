// package com.shay.spring.security.mongodb.login;
//
// import com.shay.spring.security.mongodb.login.model.User;
// import com.shay.spring.security.mongodb.login.model.UserRepository;
// import com.shay.spring.security.mongodb.login.model.UserRepositoryMongodb;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//
// import java.util.Optional;
//
// import static com.shay.spring.security.mongodb.login.model.ERole.USER;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
//
// @DataMongoTest
// public class UserRepositoryIntegrationTest {
//
//     @Autowired
//     private UserRepositoryMongodb userRepository;
//
//     @Test
//     public void testSaveUser() {
//         // Create a new User object
//         User user = new User(
//                 "dan",
//                 "abc@gmail.com",
//                 "123321123",
//                 USER,
//                 false,
//                 true
//         );
//
//
//         // Save the user to the database
//         userRepository.insert(user);
//
//         // Retrieve the saved user from the database
//         Optional<User> savedUser = userRepository.findByUsername("john_doe");
//
//         // Assert that the saved user is not null and its fields match the input
//         assertNotNull(savedUser);
//         assertEquals("john_doe", savedUser.get().getUsername());
//         assertEquals("john.doe@example.com", savedUser.get().getEmail());
//         assertEquals("password123", savedUser.get().getPassword());
//     }
// }
