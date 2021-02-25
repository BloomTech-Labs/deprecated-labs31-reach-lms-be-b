package com.lambdaschool.oktafoundation;


import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.models.Module;
import com.lambdaschool.oktafoundation.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(prefix = "command.line.runner", value = "enabled", havingValue = "true", matchIfMissing = true)
@Component
public class SeedData
		implements CommandLineRunner {

	/**
	 * Connects the Role Service to this process
	 */
	@Autowired
	RoleService roleService;


	/**
	 * Connects the user service to this process
	 */
	@Autowired
	UserService userService;

	//Connects User Teachers service to this process
	@Autowired
	UserTeachersService userTeachersService;

	//Connects User Students service to this process
	@Autowired
	UserStudentsService userStudentsService;

	//Connects Program service to this process
	@Autowired
	ProgramService programService;

	//Connects Course service to this process
	@Autowired
	CourseService courseService;

	//Connects Module service to this process
	@Autowired
	ModuleService moduleService;

	/**
	 * Generates test, seed data for our application
	 * First a set of known data is seeded into our database.
	 * Second a random set of data using Java Faker is seeded into our database.
	 * Note this process does not remove data from the database. So if data exists in the database
	 * prior to running this process, that data remains in the database.
	 *
	 * @param args The parameter is required by the parent interface but is not used in this process.
	 */
	@Transactional
	@Override
	public void run(String[] args)
	throws Exception {
		roleService.deleteAll();
		Role r1 = new Role("admin");
		Role r2 = new Role("teacher");
		Role r3 = new Role("student");

		r1 = roleService.save(r1);
		r2 = roleService.save(r2);
		r3 = roleService.save(r3);

		// Admin User
		User u1 = new User("llama001@maildrop.cc", "Llama", "Admin", "18005551234");
		u1.getRoles()
				.add(new UserRoles(u1, r1));
		u1 = userService.save(u1);

		// Teachers
		User u2 = new User("llama002@maildrop.cc", "Barn", "Barn", "16155554321");
		u2.getRoles()
				.add(new UserRoles(u2, r2));
		u2 = userService.save(u2);

		User u4 = new User("llama004@maildrop.cc", "Test 1", "Teacher", "16155554321");
		u4.getRoles()
				.add(new UserRoles(u4, r2));
		u4 = userService.save(u4);

		User u5 = new User("llama005@maildrop.cc", "Test 2", "Teacher", "16155554321");
		u5.getRoles()
				.add(new UserRoles(u5, r2));
		u5 = userService.save(u5);

		// Students
		User u3 = new User("llama003@maildrop.cc", "Reach", "Student", "17774443214");
		u3.getRoles()
				.add(new UserRoles(u3, r3));
		u3 = userService.save(u3);

		User u6 = new User("llama006@maildrop.cc", "Test 2", "Student", "17774443214");
		u6.getRoles()
				.add(new UserRoles(u6, r3));
		u6 = userService.save(u6);

		User u7 = new User("llama007@maildrop.cc", "Test 3", "Student", "17774443214");
		u7.getRoles()
				.add(new UserRoles(u7, r3));
		u7 = userService.save(u7);

		User u8 = new User("llama008@maildrop.cc", "Test 4", "Student", "17774443214");
		u8.getRoles()
				.add(new UserRoles(u8, r3));
		u8 = userService.save(u8);


		Program p1 = new Program();
		p1.setProgramName("Python Basics");
		p1.setProgramDescription("This is a introduction to the basics of Python. Get ready to bang your head against the desk");
		p1.setProgramType("Introductory");
		p1.setAdmin(u1);

		Course c1 = new Course();
		c1.setCoursename("Functions");
		c1.setCoursecode("PBfunctions");
		c1.setCoursedescription("This is an introduction to Python functions! This should be a more detailed description but I can't think.");
		c1.setProgram(p1);

		//linking p1 to c1 after c1 is initialized
		p1.getCourses().add(c1);

		Module m1 = new Module();
		m1.setModuleName("Syntax");
		m1.setModuleContent("This is the content for this module. Python functions start with def didn't you know?");
		m1.setModuleDescription("This is a more in depth description about how Pythons functions start with def. How many licks does it take to get to the center of a tootsie pop? The world may never know.");
		m1.setCourse(c1);

		Module m2 = new Module();
		m2.setModuleName("Syntax");
		m2.setModuleContent("This is the content for this module. Python functions start with def didn't you know?");
		m2.setModuleDescription("This is a more in depth description about how Pythons functions start with def. How many licks does it take to get to the center of a tootsie pop? The world may never know.");
		m2.setCourse(c1);

		c1.getModules().add(m1);
		c1.getModules().add(m2);

		p1 = programService.save(p1);

		userTeachersService.save(u2.getUserid(), p1.getProgramId());
		userStudentsService.save(u3.getUserid(), p1.getProgramId());
		
		
//		TESTING COURSE ENDPOINTS
//		courseService.save(c1);
//		Course newCourse = new Course();
//		newCourse = c1;
//		//        courseService.deleteCourseById(c1.getCourseid());
//		newCourse.setCoursedescription("Does my save method work correctly?");
//		courseService.save(newCourse);
		// The following is an example user!

    /*
	    // admin, data, user
	    User u1 = new User("admin",
	        "password",
	        "admin@lambdaschool.local");
	    u1.getRoles()
	        .add(new UserRoles(u1,
	            r1));
	    u1.getRoles()
	        .add(new UserRoles(u1,
	            r2));
	    u1.getRoles()
	        .add(new UserRoles(u1,
	            r3));
	    u1.getUseremails()
	        .add(new Useremail(u1,
	            "admin@email.local"));
	    u1.getUseremails()
	        .add(new Useremail(u1,
	            "admin@mymail.local"));

	    userService.save(u1);
    */
	}

}