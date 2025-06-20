package com.crashero.user.adapters.in;

import com.crashero.user.adapters.service.TokenService;
import com.crashero.user.model.AuthToken;
import com.crashero.user.model.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.crashero.user.adapters.model.common.TokenConstants.AUTH_HEADER_NAME;

@Tag(name = "Registration operations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RegistrationController {
    private final TokenService tokenService;

    @Operation(summary = "Login by username and password")
    @ApiResponse(responseCode = "200", description = "Account found")
    @PostMapping("/login")
    public AuthToken registerUser(@RequestBody LoginUser loginUser) {
        return tokenService.generateToken(loginUser);
    }

    @Operation(summary = "Logout")
    @PostMapping("/log-out")
    public void logout(HttpServletRequest req) {
        String tokenHeader = req.getHeader(AUTH_HEADER_NAME);
        tokenService.logout(tokenHeader);
    }
}