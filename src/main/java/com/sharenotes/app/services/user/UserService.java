package com.sharenotes.app.services.user;

import com.sharenotes.app.models.user.User;
import com.sharenotes.app.repositories.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service
 * @see User
 * @see UserRepository
 */
@Service
public interface UserService extends UserDetailsService {

    /**
     * Receive user by email
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * Receive user by username
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * Receive user by user id
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * Save user in the database
     * @param user
     */
    void saveUser(User user);

    /**
     * Enable user
     * @param user
     */
    void enableUser(User user);

    /**
     * Disable user
     * @param user
     */
    void disableUser(User user);

    /**
     * Receive all users
     * @return
     */
    List<User> getAllUsers();

    /**
     * Delete user
     * @param user
     */
    void deleteUser(User user);

    /**
     * Delete all users
     */
    void deleteAllUsers();

    /**
     * Change user password
     * @param user
     * @param newPassword - new password
     */
    void changePassword(User user, String newPassword);

    /**
     * Check if user exists
     * @param user
     * @return true if user exists
     */
    boolean isUserExists(User user);
}
