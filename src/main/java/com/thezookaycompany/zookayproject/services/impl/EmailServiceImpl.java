package com.thezookaycompany.zookayproject.services.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.thezookaycompany.zookayproject.model.dto.EmailTokenResponse;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.DateFormatToSimpleDateFormat;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccountService accountService;
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=cs110032002f9d9b454;AccountKey=oNT2cJxNjZ9QBPeAYe0sRYKOV54vN8zGaxpy8LemIM96nWfCdBzxahLB3V2l8AgE+loUEI2sr5Dk+AStzvPigg==;EndpointSuffix=core.windows.net";
    private static final String containerName = "qrcode";

    @Override
    public void sendEmailResetPwd(Account account, String resetPwdLink) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(account.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Please reset your password");

        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"display: inline-block; border: 1px solid #ccc; padding: 20px; border-radius: 5px; text-align: center; margin: 0 auto;\">\n" +
                "        <h3 style=\"color: #122316;\">ZooKay Password Reset</h3>\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">We heard that you lost your ZooKay password. Sorry about that!</p>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">But don't worry! You can use the following button to reset your password:</p>\n" +
                "        <a href=\"" + resetPwdLink + "\" style=\"text-decoration: none;\"><button style=\"background-color: #22a168; color: #fff; border: none; padding: 10px 20px; text-align: center; font-size: 16px; margin: 10px 0; cursor: pointer;\">Reset your password</button></a>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">If you don't use this link within 3 hours, it will expire. To get a new password reset link, visit:</p>\n" +
                "        <p><a href=\"https://zookay-web.vercel.app/forgotpassword\" style=\"color: #007BFF; text-decoration: none;\">Reset my password</a></p>\n" +
                "\n" +
                "        <p>Thanks,<br>The ZooKay Devs Team</p>\n" +
                " <p style=\" \n" +
                "            color: #938d8d;\n" +
                "            font-size: .31cm;\n" +
                "            padding: 0 0 5px 10px;\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        \">Copyright © 2023 The ZooKay Park - TP.HCM . All Rights Reserved.</p>"+
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";


        mimeMessageHelper.setText(content, true);
        javaMailSender.send(mimeMessage);

    }


    @Override
    public void sendAfterPaymentEmail(OrdersDto ordersDto) throws MessagingException {
        Orders orders = ordersRepository.findOrdersByOrderID(ordersDto.getOrderID());
        Member mem = memberRepository.findMemberByEmail(orders.getEmail());
        //get name
        String name = mem.getName();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(orders.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Ticket Purchase Confirmation");

        // format visitDate
        String visitDate = DateFormatToSimpleDateFormat.formatDateToSimpleDate(orders.getTicket().getVisitDate());
        double subTotal = (orders.getQuantity()* orders.getTicket().getTicketPrice())+(orders.getChildrenQuantity()*orders.getTicket().getChildrenTicketPrice());
        int totalQuantity = orders.getQuantity()+ orders.getChildrenQuantity();

        double voucherD ;
        double totalPrice ;
        if (orders.getOrderVoucher() != null) {
            voucherD =  orders.getOrderVoucher().getCoupon() * 100;
            totalPrice =subTotal -(subTotal * orders.getOrderVoucher().getCoupon());
        }else {
            voucherD =0;
            totalPrice = subTotal;
        }
        int voucher = (int) voucherD;
        // format currency
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // fformat subtotal
        String formattedSubTotal =currencyFormatter.format(subTotal);

        // format voucher discount
        String formattedVoucherDiscount = voucher + "%";

        // rceate a separate formatter for totalPrice
        NumberFormat totalPriceFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotalPrice = totalPriceFormatter.format(Double.valueOf(totalPrice));


        String mailContent ="<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"margin: 0; padding: 0; font-family: 'Arial', sans-serif; display: flex; flex-direction: column; align-items: center; justify-content: center; min-height: 100vh;\">\n" +
                "\n" +
                "  <div style=\"margin: auto; max-width: 1200px; width: 100%; padding: 50px 20px; display: flex; flex-direction: column; align-items: center; box-sizing: border-box;\" class=\"completed-order-container\">\n" +
                "\n" +
                "    <div style=\"width: 60%; background-color: #f1f8e9; border: 1px solid #000; border-radius: 15px;\" class=\"completed-order-thank-you\">\n" +
                "\n" +
                "      <div style=\"text-align: center; margin-top: 10px;\" class=\"completed-order-thank-you-header\">\n" +
                "        <h1 style=\"font-weight: 700; font-size: 50px;\">THANK YOU</h1>\n" +
                "        <h3 style=\"font-weight: 500; font-size: 35px;\">FOR BUYING TICKETS</h1>\n" +
                "        <p style=\"font-size: 25px;\">"+name+"</p>\n" +
                "      </div>\n" +
                "\n" +
                "      <div style=\"width: 70%; background-color: #f1f8e9;  margin-top: 5px; margin: 0 auto;margin-bottom: 50px;\" class=\"completed-order-your-order\">\n" +
                "        <h1 style=\"font-weight: 700; font-size: 40px; text-align: center; padding-top: 0;\">Your Order</h1>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\" class=\"order-item\">\n" +
                "          <span style=\"font-size: 16px;\">Your Visit Date:</span>\n" +
                "          <span style=\"font-size: 16px;\">"+visitDate+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\" class=\"order-item\">\n" +
                "          <span style=\"font-size: 16px;\">Ticket Quantity (1 ticket/person):</span>\n" +
                "          <span style=\"font-size: 16px;\">"+totalQuantity+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\" class=\"order-item\">\n" +
                "          <span style=\"font-size: 16px;\">Adult Ticket:</span>\n" +
                "          <span style=\"font-size: 16px;\">"+orders.getQuantity()+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-bottom: 10px;\" class=\"order-item\">\n" +
                "          <span style=\"font-size: 16px;\">Children Ticket:</span>\n" +
                "          <span style=\"font-size: 16px;\">"+orders.getChildrenQuantity()+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"border-top: 4px solid black; margin-bottom: 20px;\"></div>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-top: 20px; font-size: 20px;\" class=\"order-total\">\n" +
                "          <span style=\"font-size: 16px;\">Subtotal:</span>\n" +
                "          <span style=\"font-size: 16px;\"> "+formattedSubTotal+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-top: 20px; font-size: 20px;\" class=\"order-total\">\n" +
                "          <span style=\"font-size: 16px;\">Voucher Discount:</span>\n" +
                "          <span style=\"font-size: 16px;\">-"+formattedVoucherDiscount+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div style=\"display: flex; justify-content: space-between; margin-top: 20px; font-size: 30px;\" class=\"order-total\">\n" +
                "          <span style=\"font-size: 30px;\">Total Price:</span>\n" +
                "          <span style=\"font-size: 30px;\">"+formattedTotalPrice+"</span>\n" +
                "        </div>\n" +
                "\n" +
                "        <p style=\"color: #938d8d; font-size: .31cm; padding: 0 0 5px 10px; text-align: center; margin-top: 20px;\">\n" +
                "          Copyright © 2023 The ZooKay Park - TP.HCM. All Rights Reserved.\n" +
                "        </p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";


        mimeMessageHelper.setText(mailContent, true);


        // Attach the QR code image
        String blobName = orders.getOrderID() + "-QRCODE.png";
        byte[] qrCodeData = getQRCodeFromBlob(blobName);
        if (qrCodeData != null) {
            mimeMessageHelper.addAttachment(blobName, new ByteArrayResource(qrCodeData));
        }
        javaMailSender.send(mimeMessage);
    }

    private byte[] getQRCodeFromBlob(String blobName) {
        try {
            // Create BlobServiceClient
            var blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

            // Create BlobContainerClient
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

            BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(blobName).getBlockBlobClient();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blockBlobClient.download(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public EmailTokenResponse sendVertificationEmail(Account account) throws MessagingException {
        // Create and save OTP
        String otp = RandomTokenGenerator.generateRandomOTP();
        accountService.updateVerifyToken(otp, account);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(account.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Verify Your Email Address");

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4;\">\n" +
                "\n" +
                "    <div style=\"display: inline-block; border: 1px solid #ccc; padding: 20px; border-radius: 5px; text-align: center; margin: 0 auto; max-width: 600px; background-color: #fff;\">\n" +
                "\n" +
                "        <p style=\"color: #122316;\">ZooKay - Verify Your Email</p>\n" +
                "\n" +
                "        <p style=\"color: black;\">Thank you for registering an account with us. To complete the registration process and verify your email address, please follow the steps below:</p>\n" +
                "\n" +
                "        <div>\n" +
                "            <p style=\"font-size: 16px; margin-bottom: 20px; color: black;\">Here is your verification OTP:</p>\n" +
                "            <h3 style=\"color: #122316; margin: 0; color: black;\">" + otp + "</h3>\n" +
                "            <p style=\"font-size: 14px; margin-bottom: 20px; font-weight: bold; color: black;\">This OTP will expire in 2 minutes.</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px; color: black;\">Once you've verified your email address, you'll be able to access your account and start using our services.</p>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px; color: black;\">If you didn't initiate this registration, you can safely ignore this email.</p>\n" +
                "\n" +
                "        <p style=\"color: black;\">Thank you for joining us!</p>\n" +
                "\n" +
                "        <p style=\"margin: 0; color: black;\">Best regards,<br>The ZooKay Devs Team</p>\n" +
                "    </div>\n" +
                " <p style=\" \n" +
                "            color: #938d8d;\n" +
                "            font-size: .31cm;\n" +
                "            padding: 0 0 5px 10px;\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        \">Copyright © 2023 The ZooKay Park - TP.HCM . All Rights Reserved.</p>" +
                "</body>\n" +
                "</html>\n";

        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        return new EmailTokenResponse(account.getEmail(), otp);
    }

}
