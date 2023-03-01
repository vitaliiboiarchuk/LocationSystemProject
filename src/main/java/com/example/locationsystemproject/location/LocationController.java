package com.example.locationsystemproject.location;

import com.example.locationsystemproject.user.CurrentUser;
import com.example.locationsystemproject.user.User;
import com.example.locationsystemproject.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_USER")
public class LocationController {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public LocationController(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }


    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/addLocation")
    public String addLocation(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User entityUser = currentUser.getUser();
        model.addAttribute("user", userRepository.getReferenceById(entityUser.getId()));
        model.addAttribute("location", new Location());
        return "addLocation";
    }

    @PostMapping("/addLocation")
    public String addLocation(@AuthenticationPrincipal CurrentUser currentUser, @Valid Location location, BindingResult result, Model model) {
        User entityUser = currentUser.getUser();
        if (result.hasErrors()) {
            model.addAttribute("user", userRepository.getReferenceById(entityUser.getId()));
            return "addLocation";
        }
        locationRepository.save(location);
        return "redirect:/";
    }

    @GetMapping("/myLocations")
    public String myLocations(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User entityUser = currentUser.getUser();
        model.addAttribute("locations",locationRepository.findAllMyLocations(entityUser.getId()));
        return "myLocations";
    }

    @GetMapping("/shareLocation")
    public String shareLocation(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User entityUser = currentUser.getUser();
        model.addAttribute("users",userRepository.findAllWhereIdNotLike(entityUser.getId()));
        return "shareLocation";
    }

}
