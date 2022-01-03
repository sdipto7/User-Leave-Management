package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.LeaveDao;
import net.therap.leavemanagement.domain.Leave;
import net.therap.leavemanagement.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.therap.leavemanagement.domain.LeaveStatus.PENDING_BY_HR_EXECUTIVE;

/**
 * @author rumi.dipto
 * @since 12/1/21
 */
@Service
public class LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private LeaveStatService leaveStatService;

    public Leave find(long id) {
        return leaveDao.find(id);
    }

    public List<Leave> findUserLeaveList(long userId, int page) {
        return leaveDao.findUserLeaveList(userId, page);
    }

    public List<Leave> findUserPendingLeaveList(long userId) {
        return leaveDao.findUserPendingLeaveList(userId);
    }

    public List<Leave> findUserPendingLeaveList(long userId, int page) {
        return leaveDao.findUserPendingLeaveList(userId, page);
    }

    public List<Leave> findAllLeave(User sessionUser, int page) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.findAllLeave(page);
            case TEAM_LEAD:
                return leaveDao.findAllLeaveUnderTeamLead(sessionUser.getId(), page);
            default:
                return null;
        }
    }

    public List<Leave> findAllPendingLeave(User sessionUser, int page) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.findAllPendingLeave(page);
            case TEAM_LEAD:
                return leaveDao.findAllPendingLeaveUnderTeamLead(sessionUser.getId(), page);
            default:
                return null;
        }
    }

    public long countSessionUserLeave(long userId) {
        return leaveDao.countUserLeave(userId);
    }

    public long countSessionUserPendingLeave(long userId) {
        return leaveDao.countUserPendingLeave(userId);
    }

    public long countAllLeave(User sessionUser) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.countAllLeave();
            case TEAM_LEAD:
                return leaveDao.countAllLeaveUnderTeamLead(sessionUser.getId());
            default:
                return 0;
        }
    }

    public long countAllPendingLeave(User sessionUser) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.countAllPendingLeave();
            case TEAM_LEAD:
                return leaveDao.countAllPendingLeaveUnderTeamLead(sessionUser.getId());
            default:
                return 0;
        }
    }

    @Transactional
    public void saveOrUpdate(Leave leave) {
        if (leave.isApprovedByHrExecutive()) {
            leaveStatService.updateByLeave(leave);
        }

        leaveDao.saveOrUpdate(leave);
    }

    @Transactional
    public void updateLeaveStatusWithUserDesignationUpdate(User user) {
        List<Leave> pendingLeaveList = leaveDao.findUserPendingLeaveList(user.getId());
        for (Leave pendingLeave : pendingLeaveList) {
            if (pendingLeave.isPendingByTeamLead()) {
                pendingLeave.setLeaveStatus(PENDING_BY_HR_EXECUTIVE);
                leaveDao.saveOrUpdate(pendingLeave);
            }
        }
    }

    @Transactional
    public void delete(Leave leave) {
        leaveDao.delete(leave);
    }

    @Transactional
    public void deleteByUser(User user) {
        List<Leave> leaveList = leaveDao.findUserLeaveList(user.getId());
        if (leaveList.size() > 0) {
            leaveList.forEach(leave -> leaveDao.delete(leave));
        }
    }
}
