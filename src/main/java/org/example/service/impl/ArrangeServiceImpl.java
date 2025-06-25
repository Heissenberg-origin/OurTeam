package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Select;
import org.example.entity.ArrangeInfo;
import org.example.mapper.ArrangeInfoMapper;
import org.example.service.ArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArrangeServiceImpl extends ServiceImpl<ArrangeInfoMapper, ArrangeInfo> implements ArrangeService {
    @Autowired
    private ArrangeInfoMapper arrangeInfoMapper;
    public ArrangeInfo getArrangeById(int id){
        return arrangeInfoMapper.selectArrangeById(id);
    }
    public void setArrangeByid(ArrangeInfo arrangeInfo,int id ){
        arrangeInfoMapper.updateArrangeInfo(arrangeInfo,id);
    }
    public List<ArrangeInfo> listall(){
        List<ArrangeInfo>list=arrangeInfoMapper.listallInfo();
        return list;
    }
    public void deleteById(int id){
        arrangeInfoMapper.deleteArrangeById(id);
    }
    public List<ArrangeInfo> getArrangeInfosByDocId(int docid){
        List<ArrangeInfo>list=arrangeInfoMapper.selectBydocId(docid);
        System.out.println(list);
        return list;
    }
    public List<ArrangeInfo> getArrangeInfosByDate(Date date1){
        List<ArrangeInfo>list=arrangeInfoMapper.selectBydate(date1);
        System.out.println(list);
        return list;
    }
    public List<ArrangeInfo> getArrangeInfosByDateRange(Date startDate, Date endDate){
        System.out.println(startDate);
        System.out.println(endDate);
        List<ArrangeInfo>list=arrangeInfoMapper.selectByDaterange(startDate, endDate);
        System.out.println(list);
        return list;
    }
    public List<ArrangeInfo.TimeSlot> getAvailableSlotsAsEnum(int docid, Date date) {
        List<String> slots = arrangeInfoMapper.getAvailableSlots(docid, date);
        return slots.stream()
                .map(ArrangeInfo.TimeSlot::fromDisplayValue)
                .collect(Collectors.toList());
    }
    public List<ArrangeInfo> getArrangeInfosByDoctorAndDate(int doctorId,Date date){
        List<ArrangeInfo>list =arrangeInfoMapper.getdetailinfo(doctorId,date);
        System.out.println(list);
        return list;
    }
    public List<ArrangeInfo>getArrangeInfosWithRemaining(){
        List<ArrangeInfo>list=arrangeInfoMapper.getremainingInfo();
        System.out.println(list);
        return list;
    }
    public void updateArrangeBalance(int id,int balance){
        arrangeInfoMapper.updateBalance(id,balance);
        System.out.println(id+"对应号余量已更新为"+balance);
    }
    public void decreaseArrangeBalance(int id,int amount){
        arrangeInfoMapper.decreaseBalance(id,amount);
        System.out.println(id+"对应号余量已减"+amount);
    }
    public void increaseArrangeBalance(int id,int amount){
        arrangeInfoMapper.increaseBalance(id,amount);
        System.out.println(id+"对应号余量已加"+amount);
    }
    public long countArrangeInfosByDoctorId(int doctorId){
        long count=arrangeInfoMapper.countbydocid(doctorId);
        System.out.println(doctorId+"对应医生目前排班数量为"+count);
        return count;
    }
    public long countRemainingArrangeInfosByDoctorId(int doctorId){
        long count=arrangeInfoMapper.countremainingbydocid(doctorId);
        System.out.println(doctorId+"对应医生目前有余量的排班量为"+count);
        return count;
    }

}
