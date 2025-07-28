package org.foodtruckfestival.foodtruckfestival.exceptions;

public class RegistrationNotFoundException extends RuntimeException {

    public RegistrationNotFoundException(Long id) {
        super("Registration not found with ID: " + id);
    }
}
