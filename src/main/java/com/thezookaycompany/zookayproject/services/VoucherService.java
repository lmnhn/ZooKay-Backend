package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.model.entity.Voucher;

import java.util.List;

public interface VoucherService {
    String createVoucher(VoucherDto voucherDto);
    String updateVoucher(VoucherDto voucherDto);
    String deleteVoucher(String id);
    List<Voucher> getAllVoucher();
    Voucher findVoucherByID(String voucherID);
    String applyVoucherToTicket(String voucherID, String ticketID);
    String genVoucher(Double coupon);

}
