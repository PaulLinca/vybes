package com.vybes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admins")
public class AdminController {

    @GetMapping(value = "/")
    public String helloAdmin() {
        return "Admin access level";
    }
}
