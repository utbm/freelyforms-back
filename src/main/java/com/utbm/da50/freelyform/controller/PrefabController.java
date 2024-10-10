package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.PrefabInput;
import com.utbm.da50.freelyform.dto.PrefabOutput;
import com.utbm.da50.freelyform.model.Prefab;
import com.utbm.da50.freelyform.service.PrefabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/v1/prefabs")
@RestController
public class PrefabController {
    @Autowired
    private PrefabService service;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<PrefabOutput>> getAllPrefabs() {
        return ResponseEntity.ok(service.getAllPrefabs().stream().map(Prefab::toRest).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PrefabOutput> getPrefab(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getPrefab(id).toRest());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<PrefabInput> createPrefab(@RequestBody PrefabInput newPrefab){
        try {
            return ResponseEntity.ok(service.createPrefab(newPrefab.toPrefab()).toRest());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PrefabInput> updatePrefab(@PathVariable String id, @RequestBody PrefabInput pref) {
        try {
            return ResponseEntity.ok(service.updatePrefab(id, pref.toPrefab()).toRest());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PrefabOutput> deletePrefab(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.deletePrefab(id).toRest());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    
}
