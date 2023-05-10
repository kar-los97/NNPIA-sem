package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.repository.UserRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        if (validateUser(user)) {
            User existingEmail = userRepository.getUserByEmailAndDeletedAtIsNull(user.getEmail());
            if (existingEmail != null) return null;
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    private boolean validateUser(User user){
        return isValidEmail(user.getEmail()) &&isValidPassword(user.getPassword());
    }

    private boolean isValidPassword(String password) {
        boolean validLength = password.length()>=8;
        boolean hasChars = password.matches(".*[*,+,§]+.*");
        boolean hasNumbers = password.matches(".*\\d+.*");
        boolean hasLetters = password.matches(".*[A-Za-z]+.*");
        return validLength && hasChars && hasNumbers && hasLetters;

    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }

    public List<User> getAll() {
        return userRepository.findAllByDeletedAtIsNull();
    }

    public User getById(int id) {
        return userRepository.getUserByIdAndDeletedAtIsNull(id);
    }

    public User getByEmail(String email) {
        return userRepository.getUserByEmailAndDeletedAtIsNull(email);
    }

    public User updateUser(User user, int id) {
        User userToUpdate = userRepository.getById(id);
        if (!user.getEmail().equals(userToUpdate.getEmail())) {
            User existingEmail = userRepository.getUserByEmailAndDeletedAtIsNull(user.getEmail());
            if (existingEmail != null) return null;
        }
        userToUpdate.setFirstname(user.getFirstname());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.setEmail(user.getEmail());
        if (user.getPassword() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userToUpdate.setLastUpdate(new Date());
        return userRepository.save(userToUpdate);
    }

    public User deleteUser(int id) {
        User user = userRepository.getById(id);
        user.setDeletedAt(new Date());
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.getUserByEmailAndDeletedAtIsNull(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }

    }
}
