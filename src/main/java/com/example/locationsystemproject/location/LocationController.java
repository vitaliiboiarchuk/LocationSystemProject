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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return "locOperations/addLocation";
    }

    @PostMapping("/addLocation")
    public String addLocation(@AuthenticationPrincipal CurrentUser currentUser, @Valid Location location, BindingResult result, Model model) {
        User entityUser = currentUser.getUser();
        if (result.hasErrors()) {
            model.addAttribute("user", userRepository.getReferenceById(entityUser.getId()));
            return "locOperations/addLocation";
        }
        locationRepository.save(location);
        return "redirect:/";
    }

    @GetMapping("/myLocations")
    public String myLocations(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User entityUser = currentUser.getUser();
        model.addAttribute("locations",locationRepository.findAllMyLocations(entityUser.getId()));
        model.addAttribute("readOnlyLocations",locationRepository.findAllMyReadOnlyLocations(entityUser.getId()));
        model.addAttribute("adminLocations",locationRepository.findAllMyAdminLocations(entityUser.getId()));
        return "locOperations/myLocations";
    }

    @GetMapping("/showFriends/{id}/")
    public String showFriends(@PathVariable Integer id, Model model) {
        Location location = locationRepository.getReferenceById(id);
        model.addAttribute("location",location);
        model.addAttribute("readOnlyUsers",userRepository.findAllReadOnlyFriendsOnLocation(location));
        model.addAttribute("adminUsers",userRepository.findAllAdminFriendsOnLocation(location));
        return "locOperations/showFriends";
    }

    @GetMapping("/changeAccess/{locationId}/{userId}/")
    public String changeAccess(@PathVariable Integer locationId, @PathVariable Long userId, Model model) {
        User user = userRepository.getReferenceById(userId);
        Location location = locationRepository.getReferenceById(locationId);
        model.addAttribute("location",location);
        model.addAttribute("user",user);
        if (user.getReadOnlyLocations().contains(location)) {
            model.addAttribute("showAdminLocations",true);
        } else if (user.getAdminLocations().contains(location)) {
            model.addAttribute("showReadOnlyLocations",true);
        }
        return "locOperations/changeAccess";
    }

    @PostMapping("/changeAccess")
    public String changeAccess(@Valid User user) {
        userRepository.save(user);
        return "redirect:/myLocations";
    }

    @GetMapping("/shareLocation")
    public String shareLocation(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        User entityUser = currentUser.getUser();
        model.addAttribute("users",userRepository.findAllWhereIdNotLike(entityUser.getId()));
        return "locOperations/shareLocation";
    }

    @GetMapping("/shareReadOnly/{id}/")
    public String shareReadOnly(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id, Model model) {
        User entityUser = currentUser.getUser();
        User user = userRepository.getReferenceById(id);
        model.addAttribute("user",user);
        List<Location> allMyLocations = locationRepository.findAllMyLocations(entityUser.getId());
        List<Location> allMyAdminLocations = locationRepository.findAllMyAdminLocations(entityUser.getId());
        List<Location> locations = Stream.of(allMyLocations, allMyAdminLocations).flatMap(Collection::stream).collect(Collectors.toList());
        locations.removeIf(location -> user.getAdminLocations().contains(location) || user.getReadOnlyLocations().contains(location) || location.getUser().equals(user));
        if (locations.isEmpty()) {
            model.addAttribute("notAvailable",true);
        } else {
            model.addAttribute("available",true);
            model.addAttribute("locations", locations);
        }
        return "locOperations/shareReadOnly";
    }

    @PostMapping("/shareReadOnly")
    public String shareReadOnly(@Valid User user) {
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/shareAdmin/{id}/")
    public String shareAdmin(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id, Model model) {
        User entityUser = currentUser.getUser();
        User user = userRepository.getReferenceById(id);
        model.addAttribute("user",user);
        List<Location> allMyLocations = locationRepository.findAllMyLocations(entityUser.getId());
        List<Location> allMyAdminLocations = locationRepository.findAllMyAdminLocations(entityUser.getId());
        List<Location> locations = Stream.of(allMyLocations, allMyAdminLocations).flatMap(Collection::stream).collect(Collectors.toList());
        locations.removeIf(location -> user.getAdminLocations().contains(location) || user.getReadOnlyLocations().contains(location) || location.getUser().equals(user));
        if (locations.isEmpty()) {
            model.addAttribute("notAvailable",true);
        } else {
            model.addAttribute("available",true);
            model.addAttribute("locations", locations);
        }
        return "locOperations/shareAdmin";
    }

    @PostMapping("/shareAdmin")
    public String shareAdmin(@Valid User user) {
        userRepository.save(user);
        return "redirect:/";
    }

}
