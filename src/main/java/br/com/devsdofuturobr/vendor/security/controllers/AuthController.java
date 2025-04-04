package br.com.devsdofuturobr.vendor.security.controllers;

import br.com.devsdofuturobr.vendor.security.dto.request.LoginRequestDTO;
import br.com.devsdofuturobr.vendor.security.dto.response.LoginResponseDTO;
import br.com.devsdofuturobr.vendor.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vendor/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request){
        return userService.login(request);
    }
}
