package com.example.locationsystemproject.user;

import com.example.locationsystemproject.location.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    private String name;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Location> readOnlyLocations;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Location> adminLocations;

    private int enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


}
