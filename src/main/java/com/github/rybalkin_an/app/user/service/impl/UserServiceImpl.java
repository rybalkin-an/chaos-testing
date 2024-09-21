package com.github.rybalkin_an.app.user.service.impl;

import com.github.rybalkin_an.app.user.exception.BusinessException;
import com.github.rybalkin_an.app.user.exception.NotFoundException;
import com.github.rybalkin_an.app.user.model.User;
import com.github.rybalkin_an.app.user.repository.UserRepository;
import com.github.rybalkin_an.app.user.service.CrudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements CrudService<UUID, User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User create(@Valid User userToCreate) {
        return this.userRepository.save(userToCreate);
    }

    public User update(UUID id, @Valid User userToUpdate) {
        User dbUser = this.findById(id);
        if (!dbUser.getId().equals(userToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }
        dbUser.setFirstName(userToUpdate.getFirstName());
        dbUser.setLastName(userToUpdate.getLastName());
        return this.userRepository.save(dbUser);
    }

    public void delete(UUID id) {
        User dbUser = this.findById(id);
        this.userRepository.delete(dbUser);
    }
}