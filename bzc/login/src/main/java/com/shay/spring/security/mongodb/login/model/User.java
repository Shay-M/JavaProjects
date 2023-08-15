// package com.shay.spring.security.mongodb.login.model;
//
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
// import lombok.*;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
//
// import java.util.Collection;
// import java.util.Collections;
//
// @Data
// // @EqualsAndHashCode //  Lombok annotation generates the equals() and hashCode()
// // @NoArgsConstructor
// @Document(collection = "users") // MongoDB collection name for storing users
// public class User implements UserDetails {
//     @Id
//     private String id;
//
//     // @NotBlank
//     // @Size(max = 20)
//     private String username;
//
//     // @NotBlank
//     // @Size(max = 50)
//     // @Email
//     private String email;
//
//     // @NotBlank
//     // @Size(max = 120)
//     private String password;
//
//     // @DBRef
//     private ERole userRoles;
//
//     private Boolean locked;
//     private Boolean enabled;
//
//     public User(final String username, final String email, final String password, final ERole userRoles, final Boolean locked, final Boolean enabled) {
//         this.username = username;
//         this.email = email;
//         this.password = password;
//         this.userRoles = userRoles;
//         this.locked = locked;
//         this.enabled = enabled;
//     }
//
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoles.name().toLowerCase());
//         return Collections.singleton(authority);
//     }
//
//     @Override
//     public String getPassword() {
//         return password;
//     }
//
//     @Override
//     public String getUsername() {
//         return username;
//     }
//
//     @Override
//     public boolean isAccountNonExpired() {
//         return true; // XX
//     }
//
//     @Override
//     public boolean isAccountNonLocked() {
//         return !locked;
//     }
//
//     @Override
//     public boolean isCredentialsNonExpired() {
//         // XX
//         return true;
//     }
//
//     @Override
//     public boolean isEnabled() {
//         return enabled;
//     }
// }
