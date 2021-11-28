package net.therap.leavemanagement.service;

import net.therap.leavemanagement.dao.LeaveStatDao;
import net.therap.leavemanagement.domain.LeaveStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
