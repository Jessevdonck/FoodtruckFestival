package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Registration;

import java.util.List;

public interface RegistrationService {
Registration findById(Long id);
List<Registration> findAll();
Registration save(Registration registration);
void deleteById(Long id);
}
