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

    public List<Leave> findProceededLeavesOfUser(long userId, int page) {
        return leaveDao.findProceededLeavesOfUser(userId, page);
    }

    public List<Leave> findPendingLeavesOfUser(long userId) {
        return leaveDao.findPendingLeavesOfUser(userId);
    }

    public List<Leave> findPendingLeavesOfUser(long userId, int page) {
        return leaveDao.findPendingLeavesOfUser(userId, page);
    }

    public List<Leave> findAllProceededLeaves(User sessionUser, int page) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.findAllProceededLeaves(page);
            case TEAM_LEAD:
                return leaveDao.findAllProceededLeavesUnderTeamLead(sessionUser.getId(), page);
            default:
                return null;
        }
    }

    public List<Leave> findAllPendingLeaves(User sessionUser, int page) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.findAllPendingLeaves(page);
            case TEAM_LEAD:
                return leaveDao.findAllPendingLeavesUnderTeamLead(sessionUser.getId(), page);
            default:
                return null;
        }
    }

    public long countProceededLeavesOfUser(long userId) {
        return leaveDao.countProceededLeavesOfUser(userId);
    }

    public long countPendingLeavesOfUser(long userId) {
        return leaveDao.countPendingLeavesOfUser(userId);
    }

    public long countAllProceededLeaves(User sessionUser) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.countAllProceededLeaves();
            case TEAM_LEAD:
                return leaveDao.countAllProceededLeavesUnderTeamLead(sessionUser.getId());
            default:
                return 0;
        }
    }

    public long countAllPendingLeaves(User sessionUser) {
        switch (sessionUser.getDesignation()) {
            case HR_EXECUTIVE:
                return leaveDao.countAllPendingLeaves();
            case TEAM_LEAD:
                return leaveDao.countAllPendingLeavesUnderTeamLead(sessionUser.getId());
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
        List<Leave> pendingLeaveList = leaveDao.findPendingLeavesOfUser(user.getId());
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
        List<Leave> leaveList = leaveDao.findAllLeavesOfUser(user.getId());
        if (leaveList.size() > 0) {
            leaveList.forEach(leave -> leaveDao.delete(leave));
        }
    }
}
