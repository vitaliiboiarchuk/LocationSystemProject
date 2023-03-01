package com.example.locationsystemproject.location;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocationController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
