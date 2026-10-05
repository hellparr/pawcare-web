package com.pawcare.web.controller;

import com.pawcare.web.model.Pet;
import com.pawcare.web.model.User;
import com.pawcare.web.repository.PetRepository;
import com.pawcare.web.repository.UserRepository;
import com.pawcare.web.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class HomeController {
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final UserService userService;

    public HomeController(UserRepository userRepository, PetRepository petRepository, UserService userService) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/registro")
    public String registerForm() { return "registro"; }

    @PostMapping("/registro")
    public String register(@RequestParam @NotBlank String fullName,
                           @RequestParam @Email String email,
                           @RequestParam @Size(min = 8) String password,
                           RedirectAttributes redirect) {
        try {
            userService.register(fullName, email, password);
            redirect.addFlashAttribute("success", "Cuenta creada. Ahora puedes iniciar sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
            return "redirect:/registro";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("pets", petRepository.findByOwnerOrderByNameAsc(user));
        model.addAttribute("pet", new Pet());
        return "dashboard";
    }

    @PostMapping("/mascotas")
    public String addPet(Authentication authentication,
                          @RequestParam String name,
                          @RequestParam String species,
                          @RequestParam(required = false) String breed,
                          @RequestParam(required = false) Double weight,
                          @RequestParam(required = false) String notes,
                          RedirectAttributes redirect) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Pet pet = new Pet();
        pet.setName(name.trim());
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setWeight(weight);
        pet.setNotes(notes);
        pet.setOwner(user);
        petRepository.save(pet);
        redirect.addFlashAttribute("success", "Mascota guardada correctamente.");
        return "redirect:/dashboard";
    }
}
