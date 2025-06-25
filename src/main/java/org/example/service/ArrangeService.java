package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;
import org.example.entity.ArrangeInfo;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

public interface ArrangeService extends IService<ArrangeInfo> {
    public List<ArrangeInfo> listall();
    public ArrangeInfo getArrangeById(int id);
    public void setArrangeByid(ArrangeInfo arrange,int id);
    public void deleteById(int id);
    public List<ArrangeInfo> getArrangeInfosByDocId(int docid);
    public List<ArrangeInfo> getArrangeInfosByDate(Date date1);
    public List<ArrangeInfo> getArrangeInfosByDateRange(Date startDate, Date endDate);
    public List<ArrangeInfo.TimeSlot> getAvailableSlotsAsEnum(int doctorId,Date date);
    public List<ArrangeInfo> getArrangeInfosByDoctorAndDate(int doctorId,Date date);
    public List<ArrangeInfo>getArrangeInfosWithRemaining();
    public void updateArrangeBalance(int id,int balance);
    public void decreaseArrangeBalance(int id,int amount);
    public void increaseArrangeBalance(int id,int amount);
    public long countArrangeInfosByDoctorId(int doctorId);
    public long countRemainingArrangeInfosByDoctorId(int doctorId);
}
