package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidVoucherException;
import com.thezookaycompany.zookayproject.exception.VoucherInUseException;
import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.repositories.VoucherRepository;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service

public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private VoucherRepository voucherRepository;


    @Override
    public String createVoucher(VoucherDto voucherDto) {
        if (voucherDto.getVoucherId() == null || voucherDto.getVoucherId().length() >= 6) {
            return "Voucher ID field is empty or the length is greater than 5 characters";
        }
        if (voucherRepository.existsById(voucherDto.getVoucherId())) {
            return "This voucher id has already existed";
        }
        if (!ordersRepository.existsById(voucherDto.getOrderId())) {
            return "This order id does not existed";
        }
        Voucher newVoucher = new Voucher();
        Orders order1 = ordersRepository.getReferenceById(voucherDto.getOrderId());
        newVoucher.setVoucherId(voucherDto.getVoucherId());
        newVoucher.setOrdersVoucher((List<Orders>) order1);
        newVoucher.setCoupon(voucherDto.getCoupon());
        newVoucher.setDescription(voucherDto.getDescription());
        newVoucher.setExpirationDate(voucherDto.getExpirationDate());
        voucherRepository.save(newVoucher);
        return "New voucher has been added successfully";
    }

    @Override
    public String updateVoucher(VoucherDto voucherDto) {
        if (voucherDto.getVoucherId() == null || voucherDto.getVoucherId().length() >= 6) {
            return "Voucher ID field is empty or the length is greater than 5 characters";
        }

        Voucher existingVoucher = voucherRepository.findById(voucherDto.getVoucherId()).orElse(null);
        if (existingVoucher != null) {
            // Check if the voucher is associated with any order
            List<Orders> ordersWithVoucher = ordersRepository.findByOrderVoucher(existingVoucher);
            if (!ordersWithVoucher.isEmpty()) {
                return "Cannot update voucher because it is associated with one or more orders.";
            }

            // Update other fields
            existingVoucher.setCoupon(voucherDto.getCoupon());
            existingVoucher.setDescription(voucherDto.getDescription());
            existingVoucher.setExpirationDate(voucherDto.getExpirationDate());

            voucherRepository.save(existingVoucher);
            return "Voucher updated successfully.";
        } else {
            return "Voucher not found with Id " + voucherDto.getVoucherId();
        }
    }



    @Override
    public String deleteVoucher(String id) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);

        // Check if the voucher is associated with any order
        List<Orders> ordersWithVoucher = ordersRepository.findByOrderVoucher(voucher);
        if (!ordersWithVoucher.isEmpty()) {
            return "Cannot delete voucher because it is associated with one or more orders.";
        }

        if (voucher != null) {
            voucherRepository.delete(voucher);
            return "Voucher deleted successfully";
        } else {
            return "Voucher not found";
        }
    }



    @Override
    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findVoucherByID(String voucherID) {
        Voucher voucher = voucherRepository.findById(voucherID).orElse(null);

        // Check if the voucher is not null and has not expired
        if (voucher != null && !isVoucherExpired(voucher)) {
            return voucher;
        }

        // Voucher is either null or expired
        return null;
    }

    private boolean isVoucherExpired(Voucher voucher) {
        Date expirationDate = voucher.getExpirationDate();
        Date currentDate = new Date();

        // Compare the expiration date with the current date
        return expirationDate != null && currentDate.after(expirationDate);
    }

    @Override
    public String applyVoucherToTicket(String voucherID, String ticketID) {
        return null;
    }

    @Override
    public String genVoucher(Double coupon) {
        if (coupon == null || coupon > 1 || coupon < 0) {
            return "The coupon of voucher must be above zero and below one";
        }
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i < 7; i++) {
                // Generate Voucher
                String voucherId = generateCustomVoucherId();

                Voucher newVoucher = new Voucher();
                newVoucher.setVoucherId(voucherId);
                newVoucher.setCoupon(coupon);
                newVoucher.setExpirationDate(calendar.getTime());
                // Set other voucher properties if needed
                if(!voucherRepository.existsByExpirationDate(calendar.getTime())) {
                    // Save the new voucher to the database
                    voucherRepository.save(newVoucher);
                }

                // Print or log the generated voucher
                System.out.println("Generated Voucher: " + newVoucher.toString());

                // Move to the next day
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            return "Vouchers generated successfully for the next 7 days";
        } catch (Exception e) {
            // Handle any exceptions appropriately
            return "Failed to generate vouchers. Error: " + e.getMessage();
        }
    }

    private String generateCustomVoucherId() {
        // Generate a random 5-character voucher ID using characters and numbers
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder voucherId = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * characters.length());
            voucherId.append(characters.charAt(index));
        }

        return voucherId.toString();
    }


}
