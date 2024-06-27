package com.sesame.inventorymanagement.controllers;

import com.sesame.inventorymanagement.entities.Admin;
import com.sesame.inventorymanagement.services.AdminService;
import com.sesame.inventorymanagement.utils.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get/{login}/{password}")
    public Admin authenticate(@PathVariable("login") String login, @PathVariable("password") String password) throws NotFoundException {
        return adminService.getAdminByLoginAndPassword(login, password);
    }
}
