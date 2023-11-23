package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
@CrossOrigin("*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // Ham nay tim voucher bang user neu nhu no khong co thi tra null
    @GetMapping("/id/{voucherID}")
    public Voucher getVoucherByID(@PathVariable String voucherID) {
        return voucherService.findVoucherByID(voucherID);
    }

}
