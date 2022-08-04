package com.royalfoods.tastytables.data.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
@Data
public class Address extends EntityAbstractModel{
    @Column(name = "ADDR_TYPE")
    private String addressType;
    @Column(name = "ADDR_L1")
    private String addressLine1;
    @Column(name = "ADDR_L2")
    private String addressLine2;
    @Column(name = "LOCALITY")
    private String locality;
    @Column(name = "CITY")
    private String city;
    @Column(name = "DISTRICT")
    private String district;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP")
    private String zip;
    @Column(name = "COUNTRY")
    private String country;
    @ManyToOne
    private User user;
}
