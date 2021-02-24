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

        if (module.getModuleId() != 0){
            moduleRepository.findById(module.getModuleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module " + module.getModuleId() + " not found!"));
            newModule.setModuleId(module.getModuleId());
        }

        newModule.setModuleName(module.getModuleName());
        newModule.setModuleContent(module.getModuleContent());
        newModule.setModuleDescription(module.getModuleDescription());

        // Do we give user ability to change course of module? Right now we are.
        Course c = courseService.fetchCourseById(module.getCourse().getCourseid());
        newModule.setCourse(c);

        return moduleRepository.save(newModule);
    }

    @Override
    public void deleteModuleById(long moduleid){
        moduleRepository.findById(moduleid).orElseThrow(() -> new EntityNotFoundException("Module with id " + moduleid + " not found!"));
        moduleRepository.deleteById(moduleid);}

    @Override
    public Module edit(Module partiallyEditedModule)
    {
        Module newModule = moduleRepository.findById(partiallyEditedModule.getModuleId())
                .orElseThrow(() -> new EntityNotFoundException("Module with id " + partiallyEditedModule.getModuleId() + " not found!"));

        if (partiallyEditedModule.getModuleName() != null)
        {
            newModule.setModuleName(partiallyEditedModule.getModuleName());
        }

        if (partiallyEditedModule.getModuleDescription() != null)
        {
            newModule.setModuleDescription(partiallyEditedModule.getModuleDescription());
        }

        if (partiallyEditedModule.getModuleContent() != null)
        {
            newModule.setModuleContent(partiallyEditedModule.getModuleContent());
        }

        //I'm unsure if we will want to give users ability to edit course associated with module through patch
        //I will give ability now, but that may be subject to change.
        if (partiallyEditedModule.getCourse() != null)
        {
            Course newCourse = courseService.fetchCourseById(partiallyEditedModule.getCourse().getCourseid());
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
