package com.auction.auction.service;

import antlr.StringUtils;
import com.auction.auction.models.Role;
import com.auction.auction.models.User;
import com.auction.auction.repo.RoleRepo;
import com.auction.auction.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MailSender mailSender;




    public String addUser(String username, String email, String password){

        User user = new User(username, email, password);
        if (!saveUser(user)){
            return "registration";
        }
        return "sendEm";
    }

    public boolean balaceManage(Long sellerId, Long newOwnerId, int value){
            User seller = userRepository.getById(sellerId);
            User newOwner = userRepository.getById(newOwnerId);

            if(newOwner.getBalance() < value){
                return false;
            }
            seller.incBalance(value);
            newOwner.decBalance(value);
            userRepository.save(seller);
            userRepository.save(newOwner);
            return true;

    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null ||user.isActivated() == false) {
            throw new UsernameNotFoundException("User not found");
        }


        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }


    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        String message = String.format(
                "Ммм свежее мясо! %s \n" +
                        "Для активации аккаунта перейдите по ссылке: http://localhost:8080/activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );

        mailSender.Send(user.getEmail(), "Account activation", message);

        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setActivated(true);
        userRepository.save(user);

        return true;
    }
}