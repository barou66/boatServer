package com.openwt.boat.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"name"})
public class Boat {
    private Integer id;
    private String name;
    private String description;
}
