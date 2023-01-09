package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.exception.NotFoundException;
import com.github.dudekmat.reddit.model.User;
import com.github.dudekmat.reddit.repository.UserRepository;
import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("No user found with username: " + username));

    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), user.isEnabled(),
        true, true, true, getAuthorities("USER"));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(String role) {
    return Collections.singletonList(new SimpleGrantedAuthority(role));
  }
}
