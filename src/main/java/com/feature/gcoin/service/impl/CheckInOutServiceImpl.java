package com.feature.gcoin.service.impl;

import com.feature.gcoin.common.TimeProvider;
import com.feature.gcoin.common.constant.Constants;
import com.feature.gcoin.common.constant.Const;
import com.feature.gcoin.dto.UserDTO;
import com.feature.gcoin.model.CheckInOut;
import com.feature.gcoin.model.TransactionLog;
import com.feature.gcoin.model.User;
import com.feature.gcoin.repository.CheckInOutRepositoty;
import com.feature.gcoin.repository.TransactionLogRepository;
import com.feature.gcoin.repository.UserRepository;
import com.feature.gcoin.service.CheckInOutService;
import com.feature.gcoin.service.TransactionLogService;
import com.feature.gcoin.service.GcoinService;
import com.feature.gcoin.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CheckInOutServiceImpl implements CheckInOutService {

    @Autowired
    private CheckInOutRepositoty checkInOutRepositoty;
    @Autowired
    private UserService userService;

    @Autowired
    private TimeProvider timeProvider;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GcoinService gcoinService;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Override
    public void updateInforCheckInOut(Long userId, boolean isCheckFirts) throws Exception {
        CheckInOut checkInOutTemp;
        Date date = new Date();
        if (isCheckFirts == true) {
            CheckInOut checkInOut = new CheckInOut();
            checkInOut.setCheckInTime(new java.sql.Timestamp(date.getTime()));
            checkInOut.setCreatAt(date);
            checkInOut.setUserId(userId);
            checkInOutRepositoty.save(checkInOut);
        } else {
            List<CheckInOut> list = checkInOutRepositoty.getCheckInOutOfDay(userId, date);
            if (list != null && list.size() > 0) {
                checkInOutTemp = list.get(0);
                checkInOutTemp.setCheckOutTime(new java.sql.Timestamp(date.getTime()));
                checkInOutTemp.setUpdateAt(date);
                if ((checkInOutTemp.getCheckOutTime().getTime() - checkInOutTemp.getCheckInTime().getTime()) >= 8 * 60 * 60 * 1000) {
                    if (checkInOutTemp.getTotal()==null) {
                        TransactionLog transactionLog = new TransactionLog();
                        transactionLog.setCreatAt(date);
                        transactionLog.setCoin(5L);
                        User user = userRepository.findById(userId);
                        String logSub = gcoinService.addCoin(user.getAddress(), BigInteger.valueOf(5L));
                        transactionLog.setTransactionLog(logSub);
                        transactionLog.setType(Constants.TransactionType.ADD_COIN.name());
                        transactionLog.setUpdateAt(date);
                        transactionLog.setUserReceiveId(userId);

                        transactionLogRepository.save(transactionLog);

                    }
                    checkInOutTemp.setTotal(1D);
                }
                checkInOutRepositoty.save(checkInOutTemp);
            }
        }
    }

    @Override
    public boolean isTheFistCheckInOut(Long userId) throws Exception {
        Date date = new Date();
        List<CheckInOut> listUser = checkInOutRepositoty.getCheckInOutOfDay(userId, date);
        if (listUser.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CheckInOut> getHistoryOfUser(Long userId) {
        List<CheckInOut> history = checkInOutRepositoty.findByUserId(userId);
        return history;
    }
}
