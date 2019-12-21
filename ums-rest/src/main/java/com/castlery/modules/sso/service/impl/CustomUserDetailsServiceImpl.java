package com.castlery.modules.sso.service.impl;

import com.castlery.modules.system.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import com.castlery.modules.system.repository.UserRepository;
import com.castlery.modules.system.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsServiceImpl implements UserDetailsService {
  @Autowired private UserRepository userRepository;

  @Override
  //    @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    com.castlery.modules.system.domain.User user = userRepository.findByEmail(email);

    if (null == user) {
      throw new UsernameNotFoundException("User not found with email : " + email);
    } else {
      //      Set<String> permissions =
      List<SimpleGrantedAuthority> authorities =
          user.getRoles().stream()
              .flatMap(role -> role.getMenus().stream())
              .map(Menu::getPermission)
              .collect(Collectors.toSet())
              .stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
      return new User(user.getEmail(), user.getPassword(), authorities);
    }
    //                    .orElseThrow(() ->
    //                            new UsernameNotFoundException("User not found with email : " +
    // email)
    //            );
    //    System.out.println("test");
    //    List<GrantedAuthority> authorities = new ArrayList<>();
    //    SimpleGrantedAuthority test = new SimpleGrantedAuthority("test");
    //    authorities.add(test);
    //    return new User("a", "b", authorities);
    //        return UserPrincipal.create(user);
  }
}
