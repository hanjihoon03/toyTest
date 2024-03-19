package toypro.developer.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import toypro.developer.dto.AddUserRequest;
import toypro.developer.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService service;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        service.save(request);
        return "redirect:/login";
    }
}
