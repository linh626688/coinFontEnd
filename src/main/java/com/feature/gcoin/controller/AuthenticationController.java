package com.feature.gcoin.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feature.gcoin.dto.RequestObject;
import com.feature.gcoin.dto.UserTokenState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feature.gcoin.common.DeviceProvider;
import com.feature.gcoin.dto.ResponseObject;
import com.feature.gcoin.model.User;
import com.feature.gcoin.security.TokenHelper;
import com.feature.gcoin.service.impl.CustomUserDetailsService;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DeviceProvider deviceProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            //@RequestBody JwtAuthenticationRequest authenticationRequest,
            @RequestBody RequestObject requestObject,
            HttpServletResponse response,
            Device device
    ) throws AuthenticationException, IOException {

        // Perform the security
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authenticationRequest.getUsername(),
//                        authenticationRequest.getPassword()
//                )
//        );

        // Inject into security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
//        User user = (User)authentication.getPrincipal();
//        String jws = tokenHelper.generateToken( user.getUsername(), device);
//        int expiresIn = tokenHelper.getExpiredIn(device);
//        // Return the token
//        return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
        return ResponseEntity.ok(new UserTokenState("access_token", 342243));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
    ) {

//        String authToken = tokenHelper.getToken( request );
//
//        Device device = deviceProvider.getCurrentDevice(request);
//
//        if (authToken != null && principal != null) {
//
//            // TODO check user password last update
//            String refreshedToken = tokenHelper.refreshToken(authToken, device);
//            int expiresIn = tokenHelper.getExpiredIn(device);
//
//            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
//        } else {
//            UserTokenState userTokenState = new UserTokenState();
//            return ResponseEntity.accepted().body(userTokenState);
//        }
        return null;
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResult(result);
        return ResponseEntity.accepted().body(responseObject);
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        userDetailsService.signUp(user);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}