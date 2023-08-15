// package com.shay.spring.security.mongodb.login.model;
//
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
// import java.util.HashSet;
// import java.util.Set;
//
// public class MongoAuthUserDetailService implements UserDetailsService {
//     @Override
//     public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//        User user = userRepository.findUserByUsername(userName);
//
//         Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//         user.getAuthorities()
//                 .forEach(role -> {
//                     grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()
//                             .getName()));
//                 });
//
//         return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
//     }
// }
