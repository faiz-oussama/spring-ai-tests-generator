package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.domain.User;
import com.example.repository.UserRepository;
import com.example.exception.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUserSuccessfully() {
        // Given
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User savedUser = userService.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(1L);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when user is null")
    void shouldThrowIllegalArgumentExceptionWhenUserIsNull() {
        // Given & When & Then
        assertThatThrownBy(() -> userService.save(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User cannot be null");

        verifyNoInteractions(userRepository);
    }

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        User foundUser = userService.findById(1L);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user not found by id")
    void shouldThrowUserNotFoundExceptionWhenUserNotFoundById() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.findById(1L))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found with id: 1");

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should delete user by id")
    void shouldDeleteUserById() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When
        userService.deleteById(1L);

        // Then
        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when deleting non-existent user")
    void shouldThrowUserNotFoundExceptionWhenDeletingNonExistentUser() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> userService.deleteById(1L))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found with id: 1");

        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }
}