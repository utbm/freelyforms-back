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

    @PostMapping("")
    @ResponseBody
    public PrefabInput createPrefab(@RequestBody PrefabInput newPrefab){
        try {
            return service.createPrefab(newPrefab.toPrefab()).toRest();
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
