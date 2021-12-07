package net.therap.leavemanagement.service;

import net.therap.leavemanagement.command.UserProfileCommand;
import net.therap.leavemanagement.command.UserSaveCommand;
import net.therap.leavemanagement.dao.UserDao;
import net.therap.leavemanagement.domain.Designation;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.domain.UserManagement;
import net.therap.leavemanagement.util.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private LeaveStatService leaveStatService;

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

        if (sessionUser.getDesignation().equals(Designation.HR_EXECUTIVE)) {
            return userDao.findAllDeveloper();
        } else {
            return userManagementService.findAllDeveloperUnderTeamLead(sessionUser.getId());
        }
    }

    public List<User> findAllTester(HttpSession session) {
        User sessionUser = (User) session.getAttribute("SESSION_USER");

        if (sessionUser.getDesignation().equals(Designation.HR_EXECUTIVE)) {
            return userDao.findAllTester();
        } else {
            return userManagementService.findAllTesterUnderTeamLead(sessionUser.getId());
        }
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public void saveOrUpdate(UserSaveCommand userSaveCommand) {
        User user = userSaveCommand.getUser();
        long id = user.getId();

        user.setPassword(HashGenerator.getMd5(user.getPassword()));
        user.setActivated(false);
        userDao.saveOrUpdate(user);

        if ((user.getDesignation().equals(Designation.DEVELOPER)) ||
                (user.getDesignation().equals(Designation.TESTER))) {

            User teamLead = userSaveCommand.getTeamLead();
            userManagementService.saveOrUpdate(user, teamLead);
        }

        if (id == 0) {
            LeaveStat leaveStat = new LeaveStat();
            leaveStat.setUser(user);
            leaveStatService.save(leaveStat);
        }
    }

    public void updatePassword(UserProfileCommand userProfileCommand) {
        User user = userProfileCommand.getUser();
        user.setPassword(HashGenerator.getMd5(userProfileCommand.getNewPassword()));
        userDao.updatePassword(user);
    }

    @Transactional
    public void delete(User user) {
        long userId = user.getId();

        LeaveStat leaveStat = leaveStatService.findLeaveStatByUserId(userId);
        leaveStatService.delete(leaveStat);

        if (user.getDesignation().equals(Designation.TEAM_LEAD)) {
            for (UserManagement userManagement : userManagementService.findAllUserManagementByTeamLeadId(userId)) {
                userManagementService.delete(userManagement);
            }
        } else if ((user.getDesignation().equals(Designation.DEVELOPER)) ||
                (user.getDesignation().equals(Designation.TESTER))) {

            userManagementService.delete(userManagementService.findUserManagementByUserId(userId));
        }

        userDao.delete(user);
    }
}
