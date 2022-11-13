package com.openwt.boat.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class BoatDto {
    @Schema(example = "12")
    @NonNull
    private Integer id;
    @Schema(example = "boat name")
    @NotBlank
    private String name;
    @Schema(example = "boat description")
    @NotBlank
    private String description;
}
