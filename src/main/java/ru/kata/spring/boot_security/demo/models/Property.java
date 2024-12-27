package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user; // Ссылка на пользователя

    @Column(name = "type", nullable = false)
    private String type; // Тип имущества (например, "Квартира", "Автомобиль")

    @Column(name = "description", nullable = false)
    private String description; // Описание (например, "Москва, 3-комн. квартира")

    @Column(name = "value", nullable = false)
    private Double value; // Оценочная стоимость имущества

    // Конструкторы, геттеры и сеттеры

    public Property() {
    }

    public Property(User user, String type, String description, Double value) {
        this.user = user;
        this.type = type;
        this.description = description;
        this.value = value;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
