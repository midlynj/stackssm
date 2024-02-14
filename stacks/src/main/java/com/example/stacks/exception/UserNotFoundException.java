package com.example.stacks.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotFoundException extends RuntimeException{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserNotFoundException(Long id) {
        super("User not found with id " + id);
        LOGGER.error("User not found");
    }
}
