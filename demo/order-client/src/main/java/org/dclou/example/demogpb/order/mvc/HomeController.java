package org.dclou.example.demogpb.order.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by msnikitin on 20.04.2017.
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("user")
    public Principal user(Principal principal) {
        return principal;
    }
}
