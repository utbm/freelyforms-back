package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.PrefabInput;
import com.utbm.da50.freelyform.model.Prefab;
import com.utbm.da50.freelyform.service.PrefabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<PrefabInput> getAllPrefabs() {
        return service.getAllPrefabs().stream().map(Prefab::toRest).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PrefabInput getPrefab(@PathVariable String id) {
        try {
            return service.getPrefab(id).toRest();
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("")
    @ResponseBody
    public PrefabInput createPrefab(@RequestBody PrefabInput newPrefab){// todo remove from prefabInput, groups
        try {
            return service.createPrefab(newPrefab.toPrefab()).toRest();
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public PrefabInput updatePrefab(@PathVariable String id, @RequestBody PrefabInput prefabInput) {
        try {
            return service.updatePrefab(id, prefabInput.toPrefab()).toRest();
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletePrefab(@PathVariable String id) {
        try {
            service.deletePrefab(id);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    
}
