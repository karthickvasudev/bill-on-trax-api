package com.billontrax.modules.customer.entities;

import com.billontrax.common.entities.TimestampedWithUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Customer")
@Table(name = "customers")
public class Customer extends TimestampedWithUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id")
    private Long businessId;

    @Column(name = "customer_code")
    private String customerCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerType type;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "alternate_phone")
    private String alternatePhone;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "outstanding_limit")
    private BigDecimal outstandingLimit;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerContact> contacts = new ArrayList<>();

    public void addContact(CustomerContact contact) {
        contacts.add(contact);
        contact.setCustomer(this);
    }

    public void removeContact(CustomerContact contact) {
        contacts.remove(contact);
        contact.setCustomer(null);
    }

    public List<CustomerContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<CustomerContact> contacts) {
        this.contacts = contacts;
    }
}
