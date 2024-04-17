package com.seohyeon.testboardspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HtmlController {

//    @GetMapping("/")
//    public String hello(Model model) {
//        model.addAttribute("message", "Hello, World!?");
//        return "index";
//    }
//
//    @GetMapping("/submit")
//    public String getValuesTest(Model model){
//        return "get-values-test";
//    }
//
//    @PostMapping("/submitForm")
//    public String submitForm(@RequestParam("name") String name, @RequestParam("email") String email) {
//        // 여기서 name과 email 값을 받아서 사용할 수 있습니다.
//        System.out.println("Name: " + name);
//        System.out.println("Email: " + email);
//
//        // 여기에 필요한 로직을 추가할 수 있습니다.
//
//        return "redirect:/"; // 제출 후에는 다시 첫 페이지로 리다이렉션합니다.
//    }
}
