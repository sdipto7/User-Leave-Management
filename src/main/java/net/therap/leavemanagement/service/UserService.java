package net.therap.leavemanagement.service;

import net.therap.leavemanagement.command.UserSaveCommand;
import net.therap.leavemanagement.dao.UserDao;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.domain.UserManagement;
import net.therap.leavemanagement.util.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private LeaveStatService leaveStatService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private NotificationService notificationService;

    public User find(long id) {
        return userDao.find(id);
    }

    public User findHrExecutive() {
        return userDao.findHrExecutive();
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findAllTeamLead() {
        return userDao.findAllTeamLead();
    }

    public List<User> findAllTeamLead(int page) {
        return userDao.findAllTeamLead(page);
    }

    public long countTeamLead() {
        return userDao.countTeamLead();
    }

    public List<User> findAllDeveloper(User sessionUser, int page) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return userDao.findAllDeveloper(page);
            case TEAM_LEAD:
                return userManagementService.findAllDeveloperUnderTeamLead(sessionUser.getId(), page);
            default:
                return null;
        }
    }

    public long countDeveloper(User sessionUser) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return userDao.countDeveloper();
            case TEAM_LEAD:
                return userManagementService.countDeveloperUnderTeamLead(sessionUser.getId());
            default:
                return 0;
        }
    }

    public List<User> findAllTester(User sessionUser, int page) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return userDao.findAllTester(page);
            case TEAM_LEAD:
                return userManagementService.findAllTesterUnderTeamLead(sessionUser.getId(), page);
            default:
                return null;
        }
    }

    public long countTester(User sessionUser) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return userDao.countTester();
            case TEAM_LEAD:
                return userManagementService.countTesterUnderTeamLead(sessionUser.getId());
            default:
                return 0;
        }
    }

    public List<User> findAll(int page) {
        return userDao.findAll(page);
    }

    public Long countAll() {
        return userDao.countAll();
    }

    @Transactional
    public void saveOrUpdate(UserSaveCommand userSaveCommand) {
        User user = userSaveCommand.getUser();
        long id = user.getId();

        if (userSaveCommand.isRoleChanged()) {
            updateWithRoleChange(user);
        } else {
            if (id == 0) {
                user.setPassword(HashGenerator.getMd5(user.getPassword()));
                user.setActivated(false);
            }
            userDao.saveOrUpdate(user);

            if (user.isDeveloper() || user.isTester()) {
                User teamLead = userSaveCommand.getTeamLead();
                userManagementService.saveOrUpdate(user, teamLead);
            }

            if (id == 0) {
                LeaveStat leaveStat = new LeaveStat();
                leaveStat.setUser(user);
                leaveStatService.save(leaveStat);
            }
        }
    }

    @Transactional
    public void updateWithRoleChange(User user) {
        long userId = user.getId();

        UserManagement userManagement = userManagementService.findUserManagementByUserId(userId);
        userManagementService.delete(userManagement);

        leaveService.updateLeaveStatusWithUserDesignationUpdate(userId);

        userDao.saveOrUpdate(user);
    }

    @Transactional
    public void updatePassword(User user) {
        if (!user.isActivated()) {
            user.setActivated(true);
        }
        userDao.saveOrUpdate(user);
    }

    @Transactional
    public void delete(User user) {
        leaveStatService.deleteByUser(user);

        notificationService.deleteByUser(user);

        leaveService.deleteByUser(user);

        userManagementService.deleteByUser(user);

        userDao.delete(user);
    }
}
