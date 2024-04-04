package com.turborvip.core.resolver;

import com.turborvip.core.domain.repositories.UserRepository;
import com.turborvip.core.model.entity.User;
import graphql.GraphQLException;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserResolver implements GraphQLQueryResolver {

    @Autowired
    UserRepository userRepository;

    public Iterable<User> users() {
        return userRepository.findAll();
    }

    public User userById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void throwEx(){
        throw new GraphQLException("SQL: XYZ");
    }
}
