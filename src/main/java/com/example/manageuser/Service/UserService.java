package com.example.manageuser.Service;

import com.example.manageuser.Data.UserJpa;
import com.example.manageuser.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserJpa userJpa;

    public List<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();
        userJpa.findAll().forEach(user -> userList.add(user));
        return userList;
    }
    public User getUserById(Long id){
        return userJpa.findById(id).get();
    }
    public boolean save(Map<String, Object> params){
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userJpa.save(user);
        return true;
    }
    public void updateUser() {

    }
    public boolean saveUser(User user) {
        User updatedUser = userJpa.save(user);
        if(getUserById(updatedUser.getId()) != null)
            return true;
        return false;

    }

    public boolean deleteUser(Long id) {
        userJpa.deleteById(id);
        if(getUserById(id) != null){
            return true;
        }
        return false;

    }

}
