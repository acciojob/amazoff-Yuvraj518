package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private Map<String,Order> orderMap=new HashMap<>();
    private Map<String,DeliveryPartner> partnerMap=new HashMap<>();
    private Map<String,String> orderPartnerMap=new HashMap<>();
    private Map<String,List<String>> partnerOrderMap=new HashMap<>();
    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
        return;
    }

    public void addPartner(DeliveryPartner p) {
        partnerMap.put(p.getId(),p);
        return;
    }

    public Optional<Order> getOrderByID(String orderId) {
        if(orderMap.containsKey(orderId)){
            return Optional.of(orderMap.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerByID(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerId));
        }
        return Optional.empty();
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderPartnerMap.put(orderId,partnerId);

        List<String> updatedOrdes=new ArrayList<>();
        if(partnerOrderMap.containsKey(partnerId)){
            updatedOrdes=partnerOrderMap.get(partnerId);
        }
        updatedOrdes.add(orderId);
        partnerOrderMap.put(partnerId,updatedOrdes);
        return;
    }

    public List<String> getorderbyPartnerId(String partnerId) {
        return partnerOrderMap.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        return orderMap.size()-orderPartnerMap.size();
    }

    public void deletePartnerById(String partnerId) {
        partnerMap.remove(partnerId);
        partnerOrderMap.remove(partnerId);
        return;
    }

    public void removeOrderPartnerMapById(String x) {
        orderPartnerMap.remove(x);
        return;
    }

    public void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        if(orderPartnerMap.containsKey(orderId)) {
            String pp=orderPartnerMap.get(orderId);
            List<String> list1=getorderbyPartnerId(pp);
            list1.remove(orderId);
            partnerOrderMap.put(pp,list1);

            orderPartnerMap.remove(orderId);
        }
        return;
    }
}
