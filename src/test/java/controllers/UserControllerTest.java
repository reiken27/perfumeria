package controllers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tienda.perfumeria.controllers.UserController;
import com.tienda.perfumeria.entities.User;
import com.tienda.perfumeria.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void authenticatedUser_ShouldReturnUser_WhenAuthenticated() {
        User mockUser = new User();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<User> response = userController.authenticatedUser();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUser, response.getBody());
    }

    @Test
    void authenticatedUser_ShouldThrowBadCredentialsException_WhenNotAuthenticated() {
        when(securityContext.getAuthentication()).thenReturn(null);

        Exception exception = assertThrows(BadCredentialsException.class, () -> userController.authenticatedUser());
        assertNotNull(exception.getMessage());
    }

    @Test
    void authenticatedUser_ShouldThrowAccessDeniedException_WhenPrincipalIsNotUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("InvalidUser");

        Exception exception = assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> userController.authenticatedUser());
        assertNotNull(exception.getMessage());
    }

    @Test
    void allUsers_ShouldReturnListOfUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userService.allUsers()).thenReturn(mockUsers);

        ResponseEntity<List<User>> response = userController.allUsers();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUsers.size(), response.getBody().size());
    }
}
