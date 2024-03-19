package org.project.ecommercebackend.dto.request;

public class OrderRequestDTO {
    private Long userId;
    private String address;
    private String paymentMethod;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(Long userId, String address, String paymentMethod) {
        this.userId = userId;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
