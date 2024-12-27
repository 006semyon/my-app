package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tax_payments")
public class TaxPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user; // Ссылка на пользователя

    @Column(name = "tax_type", nullable = false)
    private String taxType; // Тип налога

    @Column(name = "amount", nullable = false)
    private Double amount; // Сумма налога

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate; // Срок уплаты налога

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaxStatus status; // Статус платежа (PENDING/PAID)

    // Конструкторы, геттеры и сеттеры

    public TaxPayment() {
    }

    public TaxPayment(User user, String taxType, Double amount, LocalDate dueDate, TaxStatus status) {
        this.user = user;
        this.taxType = taxType;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaxStatus getStatus() {
        return status;
    }

    public void setStatus(TaxStatus status) {
        this.status = status;
    }
}
