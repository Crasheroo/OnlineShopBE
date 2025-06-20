package com.crashero.user.adapters.service;

import com.crashero.user.adapters.jwt.TokenProvider;
import com.crashero.user.adapters.model.entity.BlockedTokenEntity;
import com.crashero.user.adapters.model.entity.UserEntity;
import com.crashero.user.adapters.repository.SpringDataBlockedTokenRepository;
import com.crashero.user.adapters.repository.SpringDataUserRepository;
import com.crashero.user.model.AuthToken;
import com.crashero.user.model.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.crashero.user.adapters.model.common.TokenConstants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.crashero.user.adapters.model.common.TokenConstants.TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final SpringDataUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder bcryptEncoder;
    private final TokenProvider jwtTokenUtil;
    private final SpringDataBlockedTokenRepository blockedTokenRepository;
    private final Clock systemClock;

    public AuthToken generateToken(LoginUser loginUser) {
        UserEntity userEntity = userRepository.findByUsername(loginUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User: " + loginUser.getUsername() + " not found."));

        if (!bcryptEncoder.matches(loginUser.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword(),
                        getAuthority(userEntity)
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new AuthToken(token);
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity userEntity) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if (userEntity.getRole() == null) {
            throw new RuntimeException("User has no role assigned!");
        }

        if (userEntity.getRole().getName() == null) {
            throw new RuntimeException("User's role name is null!");
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName()));
        return authorities;
    }

    @Transactional
    public void logout(String header) {
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new RuntimeException("Incorrect authentication header.");
        }

        String authToken = header.replace(TOKEN_PREFIX, "");
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(authToken);

        var token = new BlockedTokenEntity();
        token.setAuthToken(authToken);
        token.setExpirationTime(OffsetDateTime.ofInstant(expirationDate.toInstant(), ZoneOffset.systemDefault()));

        blockedTokenRepository.save(token);
    }

    @Scheduled(fixedDelay = ACCESS_TOKEN_VALIDITY_SECONDS * 1000)
    @Transactional
    public void checkExpiredVisits() {
        blockedTokenRepository.deleteAllByExpirationTimeBefore(OffsetDateTime.now(systemClock));
    }
}
