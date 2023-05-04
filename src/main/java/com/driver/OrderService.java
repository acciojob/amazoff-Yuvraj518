package com.driver;

import ch.qos.logback.core.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.driver.Order.convertDeliveryTime;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner dp=new DeliveryPartner(partnerId);
        orderRepository.addPartner(dp);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) throws NoIdExistException {
        Optional<Order> op1=orderRepository.getOrderByID(orderId);
        Optional<DeliveryPartner> op2=orderRepository.getPartnerByID(partnerId);
        if(op1.isEmpty() || op2.isEmpty()){
            throw new NoIdExistException();
        }
        int initialorder=op2.get().getNumberOfOrders();
        initialorder++;
        op2.get().setNumberOfOrders(initialorder);
        //orderRepository.addPartner(op2.get());
        orderRepository.addOrderPartnerPair(orderId,partnerId);
        return;
    }

    public Order getOrderByID(String orderId) throws NoIdExistException {
        Optional<Order> op3=orderRepository.getOrderByID(orderId);
        if(op3.isEmpty()){
            throw new NoIdExistException();
        }
        return op3.get();
    }

    public DeliveryPartner getPartnerByID(String partnerId) throws NoIdExistException {
        Optional<DeliveryPartner> op4=orderRepository.getPartnerByID(partnerId);
        if(op4.isEmpty()){
            throw new NoIdExistException();
        }
        return op4.get();
    }

    public int getOrderCountByPartnerID(String partnerId) throws Exception {
        Optional<DeliveryPartner> op5= orderRepository.getPartnerByID(partnerId);
        if(op5.isEmpty()){
            throw new Exception("no parterid found for ordercount");
        }
        return op5.get().getNumberOfOrders();
    }

    public List<String> getOrdersbyPartnerId(String partnerId) {
        return orderRepository.getorderbyPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String> l2=getOrdersbyPartnerId(partnerId);
        Integer count1=0;

        for(String x: l2){
            if(orderRepository.getOrderByID(x).get().getDeliveryTime()>(convertDeliveryTime(time))){
                count1++;
            }
        }
        return count1;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> l3=orderRepository.getorderbyPartnerId(partnerId);
        int max=0;
        for(String x: l3){
            int tim=orderRepository.getOrderByID(x).get().getDeliveryTime();
            if(tim>max){
                max=tim;
            }
        }
        String sTime="";
        sTime += String.valueOf((max/60))+":";
        sTime+=String.valueOf((max%60));
        return sTime;
    }

    public void deletePartnerById(String partnerId) {
        List<String> l4=orderRepository.getorderbyPartnerId(partnerId);
        orderRepository.deletePartnerById(partnerId);
        for(String x: l4){
            orderRepository.removeOrderPartnerMapById(x);
        }
        return;
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
        return;
    }
}
