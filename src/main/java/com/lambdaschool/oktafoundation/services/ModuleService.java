package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Module;

import java.util.List;

public interface ModuleService {
    Module fetchModuleById(long moduleid) throws Exception;
    Module save(Module module);
    void deleteModuleById(long moduleid);

   Module edit(Module partiallyEditedModule);

    List<Module> fetchAllModules();
}
