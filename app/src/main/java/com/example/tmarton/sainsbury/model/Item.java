package com.example.tmarton.sainsbury.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tamas Marton.
 * SainsBury's Items model class
 */
public class Item {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String imageUrl;

    @Getter
    @Setter
    private String additionalImageUrl;

    @Getter
    @Setter
    private String price;

    @Getter
    @Setter
    private String unit;

    @Getter
    @Setter
    private String pricePerMesure;
}
