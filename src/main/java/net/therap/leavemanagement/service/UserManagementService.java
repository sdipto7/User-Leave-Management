package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.UserManagementDao;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.domain.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/27/21
 */
@Service
public class UserManagementService {

    @Autowired
    private UserManagementDao userManagementDao;

    public List<UserManagement> findAllUserManagementByTeamLeadId(long teamLeadId) {
        return userManagementDao.findAllUserManagementByTeamLeadId(teamLeadId);
    }

    public UserManagement findUserManagementByUserId(long userId) {
        return userManagementDao.findUserManagementByUserId(userId);
    }

    public User findTeamLeadByUserId(long id) {
        return userManagementDao.findTeamLeadByUserId(id);
    }

    public List<User> findAllDeveloperUnderTeamLead(long id) {
        return userManagementDao.findAllDeveloperUnderTeamLead(id);
    }

    public List<User> findAllTesterUnderTeamLead(long id) {
        return userManagementDao.findAllTesterUnderTeamLead(id);
    }

    @Transactional
    public void saveOrUpdate(User user, User teamLead) {
        UserManagement userManagement = new UserManagement();
        userManagement.setUser(user);
        userManagement.setTeamLead(teamLead);

        userManagementDao.saveOrUpdate(userManagement);
    }

    @Transactional
    public void delete(UserManagement userManagement) {
        userManagementDao.delete(userManagement);
    }
}
