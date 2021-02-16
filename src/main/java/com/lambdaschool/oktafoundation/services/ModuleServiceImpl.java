package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "moduleService")
public class ModuleServiceImpl implements ModuleService{
    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public Module fetchModuleById(long moduleid) throws Exception {
        return moduleRepository.findById(moduleid)
                .orElseThrow(() -> new Exception("Module " + moduleid + " not found!"));
    }

    @Override
    public Module save(Module module){
        Module newModule = new Module();

        if (module.getModuleId() != 0){
            moduleRepository.findById(module.getModuleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module " + module.getModuleId() + " not found!"));
            newModule.setModuleId(module.getModuleId());
        }

        newModule.setModuleName(module.getModuleName());
        newModule.setModuleContent(module.getModuleContent());
        newModule.setModuleDescription(module.getModuleDescription());

        return moduleRepository.save(module);
    }

    @Override
    public void deleteModuleById(long moduleid){ moduleRepository.deleteById(moduleid);}
}
