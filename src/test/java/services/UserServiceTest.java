package services;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.repositories.UserRepository;
import com.tienda.perfumeria.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
    }

    @Test
void testAllUsers_ReturnsListOfUsers() {
    // Mock del repositorio para devolver una lista de usuarios
    when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

    // Ejecutar el método de servicio
    List<User> users = userService.allUsers();

    // Verificaciones
    assertNotNull(users);
    assertEquals(2, users.size());
    assertEquals("user1@example.com", users.get(0).getUsername());
    assertEquals("user2@example.com", users.get(1).getUsername());

    verify(userRepository, times(1)).findAll();
}

    @Test
    void testAllUsers_ReturnsEmptyList_WhenNoUsersExist() {
        // Mock del repositorio para devolver una lista vacía
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecutar el método de servicio
        List<User> users = userService.allUsers();

        // Verificaciones
        assertNotNull(users);
        assertTrue(users.isEmpty());

        verify(userRepository, times(1)).findAll();
    }
}
