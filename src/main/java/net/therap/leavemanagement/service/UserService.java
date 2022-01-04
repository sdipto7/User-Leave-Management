package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.UserDao;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public void saveOrUpdate(User user, User teamLead) {
        if (user.isNew()) {
            user.setPassword(HashGenerator.getMd5(user.getPassword()));
            user.setActivated(false);

            userDao.saveOrUpdate(user);
            userManagementService.createAndSaveWithNewUser(user, teamLead);
            leaveStatService.createAndSaveWithNewUser(user);
        } else {

            if (isDesignationChanged(user)) {
                userManagementService.deleteWithUserDesignationUpdate(user);
                leaveService.updateLeaveStatusWithUserDesignationUpdate(user);
            } else if (isTeamLeadChanged(user, teamLead)) {
                userManagementService.updateTeamLeadWithUserUpdate(user, teamLead);
            }

            userDao.saveOrUpdate(user);
        }
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

    public boolean isDesignationChanged(User commandUser) {
        long id = commandUser.getId();

        if (id != 0) {
            User dbUser = userDao.find(id);
            return (commandUser.isTeamLead() && ((dbUser.isDeveloper()) || (dbUser.isTester())));
        }

        return false;
    }

    public boolean isTeamLeadChanged(User commandUser, User commandTeamLead) {
        if (Objects.nonNull(commandTeamLead) && (commandUser.isDeveloper() || commandUser.isTester())) {
            User dbTeamLead = userManagementService.findTeamLeadByUserId(commandUser.getId());
            return !(commandTeamLead.equals(dbTeamLead));
        }

        return false;
    }
}
