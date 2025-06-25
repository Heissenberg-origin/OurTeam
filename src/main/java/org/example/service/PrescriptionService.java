package org.example.service;

import org.example.entity.PrescriptionInfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PrescriptionService {

    void createPrescription(PrescriptionInfo prescriptionInfo);
    List<PrescriptionInfo> getAllPrescriptions();
    PrescriptionInfo getPrescriptionBySequence(int sequence);
    void deletePrescription(int sequence);

    List<PrescriptionInfo> getPrescriptionsByPrescriptionId(int prescriptionId);
    List<PrescriptionInfo> getPrescriptionsByRegistrationId(int registrationId);
    List<PrescriptionInfo>getPrescriptionsByhcardId(int hcardId);
    List<PrescriptionInfo>getPrescriptionsByID(String IDtype,String Id);
    List<PrescriptionInfo>getPrescriptionsBydocname(String docname);
    List<PrescriptionInfo>getPrescriptionsBydepname(String depname);
    List<PrescriptionInfo> getPrescriptionsByState(PrescriptionInfo.PrescriptionState state);
    List<PrescriptionInfo> getPrescriptionsByDealerId(int dealerId);
    List<PrescriptionInfo> getPrescriptionsByCreateTimeRange(Timestamp startDate, Timestamp endDate);
    List<PrescriptionInfo> getPrescriptionsByPaidTimeRange(Timestamp startDate, Timestamp endDate);
    List<PrescriptionInfo> getPrescriptionsByPaymentType(PrescriptionInfo.PaymentType paymentType);
    List<PrescriptionInfo> getPrescriptionsByChargeItemId(int chargeItemId);

    void updatePrescriptionState(int sequence,int dealerId,PrescriptionInfo.PrescriptionState state);
    void payPrescription(int sequence, int dealerId, PrescriptionInfo.PaymentType paymentType);
    void refundPrescription(int sequence, int dealerId);
    Map<String, Object> getPrescriptionStatistics();
    List<Map<String, Integer>> getStatisticsByState();
    List<Map<String, Object>> getStatisticsByPaymentType();
    Map<String, Object> getStatisticsByTimeRange(Timestamp startDate, Timestamp endDate);
}