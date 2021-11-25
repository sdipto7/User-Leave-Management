package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.UserDao;
import net.therap.leavemanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/24/21
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User find(long id) {
        return userDao.find(id);
    }

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void saveOrUpdate(User user) {
        userDao.saveOrUpdate(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }
}
