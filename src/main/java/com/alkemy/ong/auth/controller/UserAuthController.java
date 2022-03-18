package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.AuthRequestDTO;
import com.alkemy.ong.auth.dto.AuthResponseDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.util.DocumentationMessages;
import com.alkemy.ong.util.DocumentationResponse;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = DocumentationMessages.AUTHENTICATION_CONTROLLER_DESCRIPTION)
public class UserAuthController {

    private UserDetailsCustomService userDetailsCustomService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtTokenUtil;

    public UserAuthController(UserDetailsCustomService userDetailsCustomService, AuthenticationManager authenticationManager, JwtUtils jwtTokenUtil) {
        this.userDetailsCustomService = userDetailsCustomService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /*
    //SIGNUP
    Modification of the registration method so that it generates the token and there is an automatic login.
    Return jwt.
     */
    @Tag(name = "Authentication")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = DocumentationMessages.AUTHENTICATION_SUMMARY_REGISTER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = DocumentationResponse.CODE_OK,
                    description = DocumentationMessages.AUTHENTICATION_CONTROLLER_RESPONSE_200_DESCRIPTION)})
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody @Valid UserEntity user) {
        userDetailsCustomService.save(user);
        Authentication auth;
        auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        final String jwt = jwtTokenUtil.generateToken(auth);
        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

    /*Method to authenticate, the server generates a JWT with the user's data and will return it as a response.*/
    @Tag(name = DocumentationMessages.AUTHENTICATION_CONTROLLER)

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signin(@RequestBody AuthRequestDTO authRequest) throws Exception {
        Authentication auth;
        auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        final String jwt = jwtTokenUtil.generateToken(auth);
        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }
}
