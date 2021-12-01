package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.LeaveStatDao;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rumi.dipto
 * @since 11/28/21
 */
@Service
public class LeaveStatService {

    @Autowired
    LeaveStatDao leaveStatDao;

    public LeaveStat findLeaveStatByUserId(long id) {
        return leaveStatDao.findLeaveStatByUserId(id);
    }

    @Transactional
    public void saveOrUpdate(User user) {
        LeaveStat leaveStat = new LeaveStat();

        leaveStat.setUser(user);
        leaveStat.setSickLeaveCount(0);
        leaveStat.setCasualLeaveCount(0);

        leaveStatDao.saveOrUpdate(leaveStat);
    }
}
