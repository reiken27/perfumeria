package services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tienda.perfumeria.dtos.LoginUserDto;
import com.tienda.perfumeria.dtos.RegisterUserDto;
import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.repositories.UserRepository;
import com.tienda.perfumeria.services.AuthenticationService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User user;
    private RegisterUserDto registerUserDto;
    private LoginUserDto loginUserDto;

    @BeforeEach
    void setUp() throws Exception {
    user = new User();
    user.setId(1);
    user.setName("John");
    user.setLastName("Doe");
    user.setEmail("john.doe@example.com");
    user.setPassword("encodedPassword");
    user.setMobileNum("123456789");

    // Convertir la cadena de texto a Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = sdf.parse("1990-01-01");
    user.setBirthDate(birthDate);

    user.setUsername(user.getEmail());

    // Inicializar RegisterUserDto
    registerUserDto = new RegisterUserDto();
    registerUserDto.setName("John");
    registerUserDto.setLastName("Doe");
    registerUserDto.setEmail("john.doe@example.com");
    registerUserDto.setPassword("password");
    registerUserDto.setMobileNum("123456789");
    registerUserDto.setBirthDate(birthDate);  // Usa el mismo formato

    // Inicializar LoginUserDto correctamente
    loginUserDto = new LoginUserDto();
    loginUserDto.setEmail("john.doe@example.com");
    loginUserDto.setPassword("password");
}

    @Test
    void testSignup_Success() {
        when(passwordEncoder.encode(registerUserDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = authenticationService.signup(registerUserDto);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
        void testAuthenticate_Success() {
        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(user));

        // Simular que la autenticación ocurre sin problemas
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // No lanza excepción

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        assertNotNull(authenticatedUser);
        assertEquals("john.doe@example.com", authenticatedUser.getEmail());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginUserDto.getEmail());
    }


    @Test
    void testAuthenticate_UserNotFound() {
        // Simulamos que el usuario no existe
        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.empty());
    
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authenticationService.authenticate(loginUserDto));
        
        assertNotNull(exception);  // Verificamos que la excepción se haya lanzado
    
        // La autenticación nunca debería intentarse si el usuario no se encuentra
        verify(authenticationManager, times(0)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(loginUserDto.getEmail());
    }
    



    @Test
    void testAllUsers_ReturnsListOfUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = authenticationService.allUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("john.doe@example.com", users.get(0).getEmail());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testAllUsers_ReturnsEmptyList_WhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = authenticationService.allUsers();

        assertNotNull(users);
        assertTrue(users.isEmpty());

        verify(userRepository, times(1)).findAll();
    }
}
