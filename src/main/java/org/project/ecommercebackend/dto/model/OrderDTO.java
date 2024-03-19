package org.project.ecommercebackend.dto.model;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long userId;
    private Double total;
    private String address;
    private String paymentMethod;
    private Date date;
    private List<OrderProductDTO> orderProducts;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long userId, Double total, String address, String paymentMethod, Date date, List<OrderProductDTO> orderProducts) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.orderProducts = orderProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
