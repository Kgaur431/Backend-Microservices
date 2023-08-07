package com.kartik.backend_microservices.about;

import com.kartik.backend_microservices.user.dtos.UserResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class AboutController {

    @GetMapping("")
    String about() {
        return "This is the about page";
    }

    @GetMapping("/privateAbout")
    String privateAbout(@AuthenticationPrincipal UserResponseDto user) {
        String username = user.getUsername();
        return "This is private about information for the logged in user: " + username; // we able to get user info becoz of context (read in readme.md file & projectworkflow.md file)
}
}

