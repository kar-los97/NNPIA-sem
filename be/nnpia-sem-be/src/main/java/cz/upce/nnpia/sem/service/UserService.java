package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        User existingEmail = userRepository.getUserByEmailAndDeletedAtIsNull(user.getEmail());
        if(existingEmail!=null) return null;
        return userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAllByDeletedAtIsNull();
    }

    public User getById(int id){
        return userRepository.getUserByIdAndDeletedAtIsNull(id);
    }

    public User updateUser(User user,int id){
        User userToUpdate = userRepository.getById(id);
        if(!user.getEmail().equals(userToUpdate.getEmail())){
            User existingEmail = userRepository.getUserByEmailAndDeletedAtIsNull(user.getEmail());
            if(existingEmail!=null) return null;
        }
        userToUpdate.setFirstname(user.getFirstname());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.setEmail(user.getEmail());
        if(user.getPassword()!=null){
            userToUpdate.setPassword(user.getPassword());
        }
        userToUpdate.setLastUpdate(new Date());
        return userRepository.save(userToUpdate);
    }

    public User deleteUser(int id){
        User user = userRepository.getById(id);
        user.setDeletedAt(new Date());
        return userRepository.save(user);
    }

    public User loginUser(String email, String password){
        return userRepository.getUserByEmailAndPassword(email,password);
    }
}
