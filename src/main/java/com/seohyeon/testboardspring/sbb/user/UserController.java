package com.seohyeon.testboardspring.sbb.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/signup")
    public String signup(@ModelAttribute("userForm") SiteUserForm userForm){
        return "signup_form";
    }

    @GetMapping("/user/login")
    public String login(){
        return "login_form";
    }

    @PostMapping("/api/user/signup")
    public String signup(@Valid @ModelAttribute("userForm") SiteUserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrection",
                    "비밀번호와 비밀번호 확인이 다릅니다.");
            return "signup_form";
        }
        try {
            userService.create(userForm.getUsername(), userForm.getEmail(), userForm.getPassword());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }
}
