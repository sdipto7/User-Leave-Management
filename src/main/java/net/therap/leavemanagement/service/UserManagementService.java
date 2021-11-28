package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.UserManagementDao;
import net.therap.leavemanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rumi.dipto
 * @since 11/27/21
 */
@Service
public class UserManagementService {

    @Autowired
    private UserManagementDao userManagementDao;

    public User findTeamLeadByUserId(long id) {
        return userManagementDao.findTeamLeadByUserId(id);
    }

    public List<User> findAllDeveloperUnderTeamLead(long id) {
        return userManagementDao.findAllDeveloperUnderTeamLead(id);
    }

    public List<User> findAllTesterUnderTeamLead(long id) {
        return userManagementDao.findAllTesterUnderTeamLead(id);
    }
}
