package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Registration;
import org.foodtruckfestival.foodtruckfestival.exceptions.RegistrationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.RegistrationRepository;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService
    {

private final RegistrationRepository registrationRepository;

@Autowired
public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
this.registrationRepository = registrationRepository;
}

@Override
public Registration findById(Long id) {
    return registrationRepository.findById(id).orElseThrow(()->new RegistrationNotFoundException(id));
}

@Override
public List<Registration> findAll() {
return registrationRepository.findAll();
}

@Override
public Registration save(Registration registration) {
return registrationRepository.save(registration);
}

@Override
public void deleteById(Long id) {
registrationRepository.deleteById(id);
}
}
