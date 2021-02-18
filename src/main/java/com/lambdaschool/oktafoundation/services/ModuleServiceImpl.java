package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Course;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "moduleService")
public class ModuleServiceImpl implements ModuleService{
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CourseService courseService;

    @Override
    public Module fetchModuleById(long moduleid) throws EntityNotFoundException {
        return moduleRepository.findById(moduleid)
                .orElseThrow(() -> new EntityNotFoundException("Module " + moduleid + " not found!"));
    }

    @Override
    public Module save(Module module){
        Module newModule = new Module();

        if (module.getModuleid() != 0){
            moduleRepository.findById(module.getModuleid())
                    .orElseThrow(() -> new EntityNotFoundException("Module " + module.getModuleid() + " not found!"));
            newModule.setModuleid(module.getModuleid());
        }

        newModule.setModulename(module.getModulename());
        newModule.setModulecontent(module.getModulecontent());
        newModule.setModuledescription(module.getModuledescription());

        // Do we give user ability to change course of module? Right now we are.
        newModule.setCourse(module.getCourse());

        return moduleRepository.save(newModule);
    }

    @Override
    public void deleteModuleById(long moduleid){ moduleRepository.deleteById(moduleid);}

    @Override
    public Module edit(Module partiallyEditedModule)
    {
        Module newModule = moduleRepository.findById(partiallyEditedModule.getModuleid())
                .orElseThrow(() -> new EntityNotFoundException("Module with id " + partiallyEditedModule.getModuleid() + " not found!"));

        if (partiallyEditedModule.getModulename() != null)
        {
            newModule.setModulename(partiallyEditedModule.getModulename());
        }

        if (partiallyEditedModule.getModuledescription() != null)
        {
            newModule.setModuledescription(partiallyEditedModule.getModuledescription());
        }

        if (partiallyEditedModule.getModulecontent() != null)
        {
            newModule.setModulecontent(partiallyEditedModule.getModulecontent());
        }

        //I'm unsure if we will want to give users ability to edit course associated with module through patch
        //I will give ability now, but that may be subject to change.
        if (partiallyEditedModule.getCourse() != null)
        {
            Course newCourse = courseService.fetchCourseById(partiallyEditedModule.getModuleid());
            newModule.setCourse(newCourse);
        }

        return moduleRepository.save(newModule);
    }

    @Override
    public List<Module> fetchAllModules()
    {
        List<Module> rtnList = new ArrayList<>();
        moduleRepository.findAll().iterator().forEachRemaining(rtnList::add);

        return rtnList;
    }
}
