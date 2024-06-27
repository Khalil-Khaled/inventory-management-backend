package com.sesame.inventorymanagement.services;

import com.sesame.inventorymanagement.entities.Admin;
import com.sesame.inventorymanagement.repositories.AdminRepository;
import com.sesame.inventorymanagement.utils.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getAdminByLoginAndPassword(String login, String password) throws NotFoundException {
        Admin admin = adminRepository.getAdminByLoginAndPassword(login, password);
        if (admin != null) {
            return admin;
        }
        throw new NotFoundException("Please verify your credentials");
    }
}
