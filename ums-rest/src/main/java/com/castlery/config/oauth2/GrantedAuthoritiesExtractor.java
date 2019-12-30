package com.castlery.config.oauth2;

import com.castlery.system.domain.Menu;
import com.castlery.system.domain.User;
import com.castlery.system.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Autowired
  private UserRepository userRepository;

  public Collection<GrantedAuthority> convert(Jwt jwt) {
    User userInfo = userRepository.findByEmail(jwt.getSubject());
    List<String> authorities = userInfo.getRoles().stream()
        .flatMap(role -> role.getMenus().stream().map(Menu::getPermission))
        .collect(Collectors.toList());
    return authorities.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
