package com.dailycodework.lib.dto;

import lombok.Data;

@Data
public class PublisherDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String website;
}
