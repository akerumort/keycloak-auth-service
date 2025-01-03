package com.akerumort.keycloak_auth_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Endpoints for authentication pages")
public class AuthContoller {

    @Operation(summary = "Admin page", description = "Returns the admin page")
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @Operation(summary = "User page", description = "Returns the user page for authenticated users")
    @GetMapping("/user")
    public String authenticatedPage() {
        return "authenticated";
    }
}
