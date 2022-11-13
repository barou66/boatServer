package com.openwt.boat.controller.v1;

import com.openwt.boat.controller.abst.ApiResponse200;
import com.openwt.boat.controller.abst.ApiResponse201;
import com.openwt.boat.controller.abst.ApiResponse204;
import com.openwt.boat.controller.conts.ApiConstants;
import com.openwt.boat.controller.dto.BoatDto;
import com.openwt.boat.controller.dto.BoatInput;
import com.openwt.boat.controller.dto.BoatResponse;
import com.openwt.boat.mapper.BoatMapper;
import com.openwt.boat.service.BoatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ApiConstants.API_VERSION_ONE + "boats", produces = APPLICATION_JSON_VALUE)
@Tag(name = "BoatApis", description = "Boat- Apis")
public class BoatApis {

    private final BoatService boatService;

    public BoatApis(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping
    @Operation(summary = "find all boat", operationId = "findBoats")
    @ApiResponse200(content = @Content(schema = @Schema(implementation = BoatResponse.class)))
    public ResponseEntity<BoatResponse> findBoats() {
        return ResponseEntity.ok(BoatMapper.boatsToResponse(boatService.findAllBoat()));
    }

    @PostMapping
    @Operation(summary = "Create Boat", operationId = "createBoat")
    @ApiResponse201(content = {@Content(schema = @Schema(implementation = BoatDto.class))})
    public ResponseEntity<BoatDto> createBoat(@RequestBody @Valid @NotNull BoatInput input) {
        return new ResponseEntity<>(BoatMapper.modelToDto(boatService.createBoat(BoatMapper.inputToModel(input))),
                HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @Operation(summary = "update Boat", operationId = "updateBoat")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = BoatDto.class))})
    public ResponseEntity<BoatDto> updateBoat(@PathVariable Integer id, @RequestBody @Valid @NotNull BoatInput input) {
        return new ResponseEntity<>(BoatMapper.modelToDto(boatService.updateBoat(id, BoatMapper.inputToModel(input))),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Boat by id", operationId = "deleteBoat")
    @ApiResponse204()
    public ResponseEntity<Void> deleteBoat(@PathVariable Integer id) {
        boatService.deleteBoatById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get Boat by id", operationId = "getBoatById")
    @ApiResponse200(content = {@Content(schema = @Schema(implementation = BoatDto.class))})
    public ResponseEntity<BoatDto> getBoatById(@PathVariable Integer id) {
        return new ResponseEntity<>(BoatMapper.modelToDto(boatService.findBoatById(id)), HttpStatus.OK);
    }
}
