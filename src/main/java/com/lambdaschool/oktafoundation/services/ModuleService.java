package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Module;

public interface ModuleService {
    Module fetchModuleById(long moduleid) throws Exception;
    Module save(Module module);
    void deleteModuleById(long moduleid);

   Module edit(Module partiallyEditedModule);
}
