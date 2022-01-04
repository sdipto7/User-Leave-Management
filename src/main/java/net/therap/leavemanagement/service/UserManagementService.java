package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.UserManagementDao;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.domain.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    public List<User> findAllDeveloperUnderTeamLead(long id, int page) {
        return userManagementDao.findAllDeveloperUnderTeamLead(id, page);
    }

    public long countDeveloperUnderTeamLead(long id) {
        return userManagementDao.countDeveloperUnderTeamLead(id);
    }

    public List<User> findAllTesterUnderTeamLead(long id) {
        return userManagementDao.findAllTesterUnderTeamLead(id);
    }

    public List<User> findAllTesterUnderTeamLead(long id, int page) {
        return userManagementDao.findAllTesterUnderTeamLead(id, page);
    }

    public long countTesterUnderTeamLead(long id) {
        return userManagementDao.countTesterUnderTeamLead(id);
    }

    @Transactional
    public void saveOrUpdate(UserManagement userManagement) {
        userManagementDao.saveOrUpdate(userManagement);
    }

    @Transactional
    public void createAndSaveWithNewUser(User user, User teamLead) {
        if (canHaveTeamLead(user, teamLead)) {
            UserManagement userManagement = new UserManagement();
            userManagement.setUser(user);
            userManagement.setTeamLead(teamLead);

            userManagementDao.saveOrUpdate(userManagement);
        }
    }

    @Transactional
    public void updateTeamLeadWithUserUpdate(User user, User teamLead) {
        UserManagement userManagement = userManagementDao.findUserManagementByUserId(user.getId());
        userManagement.setTeamLead(teamLead);

        userManagementDao.saveOrUpdate(userManagement);
    }

    @Transactional
    public void delete(UserManagement userManagement) {
        userManagementDao.delete(userManagement);
    }

    @Transactional
    public void deleteByUser(User user) {
        long userId = user.getId();

        if (user.isTeamLead()) {
            List<UserManagement> userManagementList = userManagementDao.findAllUserManagementByTeamLeadId(userId);
            if (userManagementList.size() > 0) {
                userManagementList.forEach(userManagement -> userManagementDao.delete(userManagement));
            }
        } else if (user.isDeveloper() || user.isTester()) {
            UserManagement userManagement = userManagementDao.findUserManagementByUserId(userId);
            if (Objects.nonNull(userManagement)) {
                userManagementDao.delete(userManagement);
            }
        }
    }

    @Transactional
    public void deleteWithUserDesignationUpdate(User user) {
        UserManagement userManagement = userManagementDao.findUserManagementByUserId(user.getId());
        userManagementDao.delete(userManagement);
    }

    public boolean canHaveTeamLead(User user, User teamLead) {
        return (Objects.nonNull(teamLead) && (user.isDeveloper() || user.isTester()));
    }
}
