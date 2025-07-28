package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.MyUserRepository;

import java.util.List;

@Service
public class MyUserServiceImpl implements MyUserService
    {

    private final MyUserRepository myUserRepository;

    @Autowired
    public MyUserServiceImpl(MyUserRepository myUserRepository)
        {
        this.myUserRepository = myUserRepository;
        }

    @Override
    public MyUser findById(Long id)
        {
        return myUserRepository.findById(id).orElse(null);
        }

    @Override
    public MyUser findByUsername(String username)
        {
            MyUser myUser = myUserRepository.findByUsername(username);

            if(myUser==null)
            {
                throw new UserNotFoundException();
            }
            return myUser;
        }

    @Override
    public List<MyUser> findAll()
        {
        return myUserRepository.findAll();
        }

    @Override
    public MyUser save(MyUser user)
        {
        return myUserRepository.save(user);
        }

    @Override
    public void deleteById(Long id)
        {
        myUserRepository.deleteById(id);
        }
    }
