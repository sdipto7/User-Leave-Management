package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.UserDao;
import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/24/21
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserManagementService userManagementService;

    public User find(long id) {
        return userDao.find(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findAllTeamLead() {
        return userDao.findAllTeamLead();
    }

    public List<User> findAllDeveloper(HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.getDesignation() == Designation.HUMAN_RESOURCE) {
            return userDao.findAllDeveloper();
        } else {
            return userManagementService.findAllDeveloperUnderTeamLead(sessionUser.getId());
        }
    }

    public List<User> findAllTester(HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.getDesignation() == Designation.HUMAN_RESOURCE) {
            return userDao.findAllTester();
        } else {
            return userManagementService.findAllTesterUnderTeamLead(sessionUser.getId());
        }
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
