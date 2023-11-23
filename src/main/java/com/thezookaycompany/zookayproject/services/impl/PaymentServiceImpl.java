package com.thezookaycompany.zookayproject.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.dto.PaymentResponse;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Payment;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.repositories.PaymentRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.services.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Value("${stripe.api.key}")
    private String secretKey;

    @PostConstruct
    public void setup() {
        Stripe.apiKey = secretKey;
    }


    @Override
    public PaymentResponse createPaymentIntent(OrdersDto ordersDto) throws StripeException {


        Orders orders = ordersRepository.findOrdersByOrderID(ordersDto.getOrderID());
        Ticket ticket = ticketRepository.findTicketByTicketId(orders.getTicket().getTicketId());

        // Tính tổng tiền
        double totalOrderPrice = getTotalOrderPrice(ordersDto, ticket, orders);

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        // createPayment for product cost how much...
                        .setAmount((long) totalOrderPrice)
                        .setCurrency("vnd")
                        .putMetadata("productName", ticket.getTicketId())
                        .setDescription("Paid for orderId: " + orders.getOrderID())
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new PaymentResponse(paymentIntent.getClientSecret(), paymentIntent.getId());
    }

    private static double getTotalOrderPrice(OrdersDto ordersDto, Ticket ticket, Orders orders) {
        double totalOrderPrice = 0.0;
        if (orders.getChildrenQuantity() > 0) {
            //tinh tong tien order voi ve tre em
            totalOrderPrice = (ticket.getTicketPrice() * orders.getQuantity()) + (ticket.getChildrenTicketPrice() * orders.getChildrenQuantity());
        } else {
            //tinh tong tien order binh thuong neu chi co ve nguoi lon
            totalOrderPrice = ticket.getTicketPrice() * orders.getQuantity();
        }


        // Check if the order has a voucher
        if (orders.getOrderVoucher() != null) {
            // Adjust the total order price based on the voucher discount
            totalOrderPrice -= totalOrderPrice * orders.getOrderVoucher().getCoupon();
        }
        return totalOrderPrice;
    }

    @Override
    public String confirmPayment(OrdersDto ordersDto, String intendId) throws StripeException {
        PaymentIntent retrieve = PaymentIntent.retrieve(intendId);
        Orders orders = ordersRepository.findOrdersByOrderID(ordersDto.getOrderID());
        Payment payment = new Payment();
        if (retrieve.getStatus() != null) {

            payment.setSuccess(true);
            payment.setOrder(orders);
            paymentRepository.save(payment);
            orders.setOrderPayments(payment);
            ordersRepository.save(orders);
        } else {
            payment.setSuccess(false);
            payment.setOrder(orders);
            paymentRepository.save(payment);
            orders.setOrderPayments(payment);
            ordersRepository.save(orders);
            return "Purchased failed";
        }
        return "Purchased successfully";
    }

    @Override
    public Payment findPaymentByOrderID(String orderId) {
        Payment payment = paymentRepository.findPaymentByOrder_OrderID(Integer.parseInt(orderId)).orElse(null);
        //    paymentRepository.findPaymentByOrder_OrderID();
        return payment;
    }

    @Override
    public String handlePaymentFailed(String orderId) {
        Orders orders = ordersRepository.findOrdersByOrderID(Integer.parseInt(orderId));
        if (orders != null) {
            Payment payment = new Payment();
            if (orders.getDescription() == null || orders.getDescription().isEmpty()) {
                orders.setDescription("PENDING PAYMENT - PURCHASED CANCELLED");
            } else {
                orders.setDescription(orders.getDescription().concat(" PENDING PAYMENT - PURCHASED CANCELLED"));
            }
            payment.setSuccess(false);
            payment.setOrder(orders);
            paymentRepository.save(payment);
            //  orders.setOrderPayments(payment);
            // ordersRepository.save(orders);
        }
        return "Cancelled Payment - Purchased failed";
    }

    @Override
    public boolean checkPaymentStatus(OrdersDto ordersDto) {
        Payment payment = paymentRepository.findPaymentByOrder(ordersRepository.findOrdersByOrderID(ordersDto.getOrderID()));
        if (payment.getSuccess() != null && payment.getSuccess()) {
            return payment.getSuccess();
        }
        return payment.getSuccess();
    }


}
