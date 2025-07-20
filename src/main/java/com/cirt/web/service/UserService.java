package com.cirt.web.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cirt.web.dto.PasswordResetDto;
import com.cirt.web.entity.Authority;
import com.cirt.web.entity.User;
import com.cirt.web.repository.AuthorityRepository;
import com.cirt.web.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private MailService mailService;

    public Page<User> getUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public void addUserByAdmin(User user) {
        Authority authority = new Authority();
        authority.setAuthority(user.getRole());
        authority.setUsername(user.getUsername());
        authority.setUser(user);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        user.setAuthority(authority);

        this.userRepository.save(user);
        this.mailService.sendAccountEmail(user, rawPassword);
    }

    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
    }

    public boolean changePassword(PasswordResetDto passwordResetDto) {
        if(!passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmNewPassword())) {
            return false;
        }
        User user = this.userRepository.findByUsername(passwordResetDto.getUsername()).get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(passwordResetDto.getOldPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(passwordResetDto.getConfirmNewPassword()));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
