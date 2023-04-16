package com.todo.app.userservice.security;

import com.todo.app.userservice.entity.User;
import com.todo.app.userservice.model.security.TodoUserDetails;
import com.todo.app.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "unittest", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class TodoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new TodoUserDetails(user);
    }
}
