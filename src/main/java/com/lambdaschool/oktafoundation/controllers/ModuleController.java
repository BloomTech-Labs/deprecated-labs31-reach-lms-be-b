package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping(value = "/modules/{moduleid}", produces = "application/json")
    public ResponseEntity<?> fetchSingleModule(@PathVariable long moduleid) throws Exception {
        Module module = moduleService.fetchModuleById(moduleid);

        return new ResponseEntity<>(module, HttpStatus.OK);
    }
    @PostMapping(value = "/modules", consumes = "application/json")
    public ResponseEntity<?> postModule(@RequestBody @Valid Module newModule){
        newModule.setModuleId(0);
        moduleService.save(newModule);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping(value = "/modules/{moduleid}", consumes = "application/json")
    public ResponseEntity<?> putCourse(@PathVariable long moduleid,
                                       @RequestBody @Valid Module updatedModule){
        updatedModule.setModuleId(moduleid);
        moduleService.save(updatedModule);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping(value = "/modules/{moduleid}")
    public ResponseEntity<?> deleteSingleModule(@PathVariable long moduleid){
        moduleService.deleteModuleById(moduleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
