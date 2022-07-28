package com.hjlowe.harryspringproject.service;

import com.hjlowe.harryspringproject.dto.AuthenticationResponse;
import com.hjlowe.harryspringproject.dto.LoginRequest;
import com.hjlowe.harryspringproject.dto.RegisterRequest;
import com.hjlowe.harryspringproject.exceptions.SpringRedditException;
import com.hjlowe.harryspringproject.model.NotificationEmail;
import com.hjlowe.harryspringproject.model.User;
import com.hjlowe.harryspringproject.model.VerificationToken;
import com.hjlowe.harryspringproject.repository.UserRepository;
import com.hjlowe.harryspringproject.repository.VerificationTokenRepository;
import com.hjlowe.harryspringproject.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(), "thank you bla bla bla http://localhost:8080/api/auth/accountVerification/" + token));
    }
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));

        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken){
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken((authentication));
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
