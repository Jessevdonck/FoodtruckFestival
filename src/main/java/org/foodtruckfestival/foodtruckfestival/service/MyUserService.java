package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.MyUser;

import java.util.List;

public interface MyUserService {
MyUser findById(Long id);
MyUser findByUsername(String username);
List<MyUser> findAll();
MyUser save(MyUser user);
void deleteById(Long id);
}
