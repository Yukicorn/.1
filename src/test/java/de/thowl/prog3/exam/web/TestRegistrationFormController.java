package de.thowl.prog3.exam.web;

import de.thowl.prog3.exam.web.gui.RegistrationFormController;
import de.thowl.prog3.exam.web.gui.form.RegistrationForm;
import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
public class TestRegistrationFormController {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private RegistrationFormController registrationFormController;

    @BeforeEach
    public void setup() {
        // Initialisierung von Mock-Objekten und MockMvc
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationFormController).build();
    }

    @Test
    @DisplayName("Should return registration form view")
    public void testGetRegistrationForm() throws Exception {
        log.debug("Testing GET request for registration form");

        // Perform GET request to /register
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk()) // Status 200 OK
                .andExpect(view().name("registerUser")); // Erwartete View ist "registerUser"
    }

    @Test
    @DisplayName("Should process registration form and redirect to login")
    public void testProcessRegistrationForm() throws Exception {
        log.debug("Testing POST request for processing registration form");

        // Simuliere das Ausfüllen des Formulars
        RegistrationForm formdata = new RegistrationForm();
        formdata.setUsername("testuser");
        formdata.setPassword("testpassword");
        formdata.setEmail("test@example.com");

        // Mocken des UserService-Aufrufs, der die Registrierung verarbeitet
        doNothing().when(userService).registerUser(anyString(), anyString(), anyString());

        // Perform POST request to /register mit den Formulardaten
        mockMvc.perform(post("/register")
                        .param("username", formdata.getUsername())
                        .param("password", formdata.getPassword())
                        .param("email", formdata.getEmail()))
                .andExpect(status().is2xxSuccessful());
    }
}

