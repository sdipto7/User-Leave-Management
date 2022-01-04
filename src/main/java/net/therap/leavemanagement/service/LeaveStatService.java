package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.LeaveStatDao;
import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.LeaveStat;
import net.therap.leavemanagement.domain.LeaveType;
import net.therap.leavemanagement.domain.User;
import net.therap.leavemanagement.util.DateTimeUtil;
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
    private LeaveStatDao leaveStatDao;

    public LeaveStat findLeaveStatByUserId(long userId) {
        return leaveStatDao.findLeaveStatByUserId(userId);
    }

    @Transactional
    public void saveOrUpdate(LeaveStat leaveStat) {
        leaveStat.setSickLeaveCount(0);
        leaveStat.setCasualLeaveCount(0);

        leaveStatDao.saveOrUpdate(leaveStat);
    }

    @Transactional
    public void createAndSaveWithNewUser(User user) {
        LeaveStat leaveStat = new LeaveStat();
        leaveStat.setUser(user);

        leaveStatDao.saveOrUpdate(leaveStat);
    }

    @Transactional
    public void updateByLeave(Leave leave) {
        LeaveStat leaveStat = findLeaveStatByUserId(leave.getUser().getId());
        int dayCount = DateTimeUtil.getLeaveDayCount(leave.getStartDate(), leave.getEndDate());

        if (leave.getLeaveType().equals(LeaveType.CASUAL)) {
            leaveStat.setCasualLeaveCount(leaveStat.getCasualLeaveCount() + dayCount);
        } else {
            leaveStat.setSickLeaveCount(leaveStat.getSickLeaveCount() + dayCount);
        }

        leaveStatDao.saveOrUpdate(leaveStat);
    }

    @Transactional
    public void delete(LeaveStat leaveStat) {
        leaveStatDao.delete(leaveStat);
    }

    @Transactional
    public void deleteByUser(User user) {
        LeaveStat leaveStat = leaveStatDao.findLeaveStatByUserId(user.getId());
        leaveStatDao.delete(leaveStat);
    }
}
