package com.welldev.TechBox.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_product_join")
@Getter
@Setter
@RequiredArgsConstructor
public class UserProduct implements Serializable{

    @Id
    private int userId;

    @Id
    private int productId;

    @Column(columnDefinition = "INT default 1")
    private int quantity;
}
