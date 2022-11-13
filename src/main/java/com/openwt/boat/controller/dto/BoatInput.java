package com.openwt.boat.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class BoatInput {
    @Schema(example = "boat name")
    private String name;
    @Schema(example = "boat description")
    private String description;
}
