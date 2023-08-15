package com.web.controller;

import com.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    @Autowired
    AccountService accountService;
    @GetMapping ("/oauth2/login/form")
    public String form() {
        return "login";
    }

    @GetMapping("/oauth2/login/success")
    public String oauth2Success(OAuth2AuthenticationToken oauth2) {
        String fullname = oauth2.getPrincipal().getAttribute("name");
        String password = Long.toHexString(System.currentTimeMillis());

        UserDetails user = User.withUsername(fullname)
                .password("").roles("CUST").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "forward:/oauth2/login/success";
    }


    @GetMapping("/oauth2/login/error")
    public String error() {
        return "forward:/login/form";
    }
}
