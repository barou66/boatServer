package com.openwt.boat.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoatResponse {
    @NonNull
    private List<BoatDto> boats;
}
