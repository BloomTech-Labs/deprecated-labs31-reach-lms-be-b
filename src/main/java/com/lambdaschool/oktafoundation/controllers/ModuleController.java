package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/modules")
@RestController
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping(value = "/module/{moduleid}", produces = "application/json")
    public ResponseEntity<?> fetchSingleModule(@PathVariable long moduleid) throws Exception
    {
        Module module = moduleService.fetchModuleById(moduleid);

        return new ResponseEntity<>(module, HttpStatus.OK);
    }

    @GetMapping(value = "/modules", produces = "application/json")
    public ResponseEntity<?> fetchingAllModules()
    {
        List<Module> moduleList = moduleService.fetchAllModules();

        return new ResponseEntity<>(moduleList, HttpStatus.OK);
    }

    @PostMapping(value = "/module", consumes = "application/json")
    public ResponseEntity<?> postModule(@RequestBody @Valid Module newModule)
    {
        newModule.setModuleId(0);
        moduleService.save(newModule);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PutMapping(value = "/module/{moduleid}", consumes = "application/json")
    public ResponseEntity<?> putModule(@PathVariable long moduleid,
                                       @RequestBody @Valid Module updatedModule)
    {
        updatedModule.setModuleId(moduleid);
        moduleService.save(updatedModule);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/module/{moduleid}", consumes = "application/json")
    public ResponseEntity<?> patchCourse(@PathVariable long moduleid, @RequestBody Module partiallyEditedModule)
    {
        partiallyEditedModule.setModuleId(moduleid);
        moduleService.edit(partiallyEditedModule);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/module/{moduleid}")
    public ResponseEntity<?> deleteSingleModule(@PathVariable long moduleid)
    {
        moduleService.deleteModuleById(moduleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
