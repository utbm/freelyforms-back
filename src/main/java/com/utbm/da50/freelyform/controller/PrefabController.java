package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.PrefabInput;
import com.utbm.da50.freelyform.dto.PrefabOutput;
import com.utbm.da50.freelyform.exceptions.ValidationFieldException;
import com.utbm.da50.freelyform.model.Prefab;
import com.utbm.da50.freelyform.service.PrefabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Tag(name = "Prefab API", description = "Endpoints for managing prefabs")
@RequestMapping("/v1/prefabs")
@RestController
public class PrefabController {

    private final PrefabService service;

    @Autowired
    public PrefabController(PrefabService service) {
        this.service = service;
    }

    @Operation(summary = "Get a list of all prefabs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of prefabs returned")
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PrefabOutput>> getAllPrefabs() {
        return ResponseEntity.ok(service.getAllPrefabs().stream().map(Prefab::toRest).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a prefab by its unique id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prefab returned"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Prefab not found"),
    })
    @ResponseBody
    public ResponseEntity<PrefabOutput> getPrefabById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getPrefabById(id).toRest());
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("")
    @Operation(summary = "Create a new prefab")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prefab created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @ResponseBody
    public ResponseEntity<PrefabOutput> createPrefab(@RequestBody PrefabInput newPrefab) throws ValidationFieldException {
        try {
            return ResponseEntity.ok(service.createPrefab(newPrefab.toPrefab()).toRest());
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @Operation(summary = "Update an existing prefab")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prefab updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Prefab not found")
    })
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PrefabOutput> updatePrefab(@PathVariable String id, @RequestBody PrefabInput pref) throws ValidationFieldException{
        try {
            return ResponseEntity.ok(service.updatePrefab(id, pref.toPrefab()).toRest());
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @Operation(summary = "Delete an existing prefab")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prefab deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Prefab not found")
    })
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PrefabOutput> deletePrefab(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.deletePrefab(id).toRest());
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    
}
