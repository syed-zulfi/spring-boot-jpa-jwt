package com.royalfoods.tastytables.data.model;

import javax.persistence.*;
@MappedSuperclass
public abstract class EntityAbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
