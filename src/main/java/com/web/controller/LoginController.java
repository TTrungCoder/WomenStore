package com.web.controller;

import com.web.domain.Account;
import com.web.model.AccountDTO;
import com.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller

public class LoginController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;
    @GetMapping("/login/form")
    public String login(ModelMap model) {
        model.addAttribute("account",new AccountDTO());
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(ModelMap model, @Valid @ModelAttribute("account") AccountDTO dto, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return new ModelAndView("login", model);
        }
        Account account = accountService.login(dto.getUsername(), dto.getPassword());
        System.out.println(dto.getPassword());
        if (account == null) {
            model.addAttribute("message", "Invalid username or password");
            return new ModelAndView("login", model);
        }

//        session.setAttribute("username", account);
//        Object ruri = session.getAttribute("redirect-uri");
//        System.out.println("ruri login = " + ruri);
//        if (ruri != null) {
//            session.removeAttribute("redirect-uri");
//            return new ModelAndView("redirect:" + ruri);
//        }
        return new ModelAndView("forward:/home", model);
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        // Xóa thuộc tính 'username' khỏi session
        session.removeAttribute("username");

        // Chuyển hướng về trang đăng nhập
        return "redirect:/login";
    }

}
