package com.example.enotes.service.impl;

import com.example.enotes.dto.EmailRequest;
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

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Override
    public Boolean register(UserDto userDto) throws Exception{
        validation.userValidation(userDto);

        User user = mapper.map(userDto, User.class);

        setRole(userDto, user);

        User saveUser = userRepo.save(user);

        if (ObjectUtils.isEmpty(saveUser)) {
            return false;
        }

        // Send email
        emailSend(saveUser);

        return true;
    }

    private void emailSend(User saveUser) throws Exception {
        String message = "Dear " + saveUser.getFirstName() + ","
                + "<br><br>Thank you for registering with Enotes. Your account has been successfully created."
                + "<br><br>You can now log in to your account by clicking the link below:"
                + "<br><a href=\"#\">Click here to Login</a>"
                + "<br><br>If you have any questions, please do not hesitate to contact our support team."
                + "<br><br>Best regards,<br>The Enotes Team";

        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Enotes Account Registration Confirmation")
                .subject("Welcome to Enotes - Account Created Successfully")
                .message(message)
                .build();

        emailServiceImpl.sendEmail(emailRequest);
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
