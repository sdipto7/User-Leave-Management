package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.LeaveDao;
import net.therap.leavemanagement.domain.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rumi.dipto
 * @since 12/1/21
 */
@Service
public class LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    public Leave find(long id) {
        return leaveDao.find(id);
    }

    public List<Leave> findUserLeaveList(long id) {
        return leaveDao.findUserLeaveList(id);
    }

    public List<Leave> findUserPendingLeaveList(long id) {
        return leaveDao.findUserPendingLeaveList(id);
    }

    public List<Leave> findAllLeave() {
        return leaveDao.findAllLeave();
    }

    public List<Leave> findAllPendingLeave() {
        return leaveDao.findAllPendingLeave();
    }
}
