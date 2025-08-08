package com.example.enotes.service.impl;

import com.example.enotes.dto.UserDto;
import com.example.enotes.entity.Role;
import com.example.enotes.entity.User;
import com.example.enotes.repository.RoleRepository;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.UserService;
import com.example.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean register(UserDto userDto) {
        validation.userValidation(userDto);

        User user = mapper.map(userDto, User.class);

        setRole(userDto, user);

        User saveUser = userRepo.save(user);

        if (ObjectUtils.isEmpty(saveUser)) {
            return false;
        }

        return true;
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> reqRoleId = userDto
                .getRoles()
                .stream()
                .map(r -> r.getId())
                .toList();

        List<Role> roles = roleRepo.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
