# Java User Model Final Version / Exceptions Final Application / Security Initial Application

## Introduction

This is a database scheme with Users, Roles, UserRoles, UserEmails, Programs, Courses, Modules, UserTeachers, and UserStudents. This Java Spring REST API application will provide endpoints for clients to read various data sets contained in the application's data. This application will also form the basis of a user authentication application developed elsewhere in the course.

# Reach Learning Management System

You can find the frontend project deployed at: https://b.reachlms.dev/ <br>
You can find the backend project deployed at: https://reach-team-b-be.herokuapp.com/ <br>
You can find the backend endpoints documentation at: https://reach-team-b-be.herokuapp.com/swagger-ui.html

## Contributors
|                                          [Wayne Abbruscato](https://github.com/Callisto1981)                                                            |                                                       [Seth Bradshaw](https://github.com/seth-bradshaw)                                                    |                                                      [Sara Cearc](https://github.com/cearc-sara)                                                       |                                                       [Christopher Girvin](https://github.com/)                                               |                                                      [Matias Iturbide](https://github.com/JDMTias)                                                 |                                                      [Chaz Kiker](https://github.com/chazkiker2/)                                                     |                                         [Brendan Lai-Tong](https://github.com/kai-blt/)                                                             |                                         [Shane Slone](https://github.com/shaneslone)                                                                   |                            
| :-----------------------------------------------------------------------------------------------------------------------------------------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------------------------------------------------: |:-----------------------------------------------------------------------------------------------------------------------------------------------------: |
| [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/Callisto1981) | [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/seth-bradshaw)   |[<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-female.png" width = "200" />](https://github.com/cearc-sara) | [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/)   | [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/JDMTias) | [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/chazkiker2) | [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/kai-blt/) |  [<img src="https://www.dalesjewelers.com/wp-content/uploads/2018/10/placeholder-silhouette-male.png" width = "200" />](https://github.com/shaneslone) |
|                                [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/Callisto1981)                                |                            [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/seth-bradshaw)                                      |                          [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/cearc-sara)                                       |                          [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/NandoTheessen)                           |                           [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/JDMTias)                                     |                           [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/chazkiker2)                                     |                           [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/kai-blt/)                                     |                           [<img src="https://github.com/favicon.ico" width="15"> ](https://github.com/shaneslone)                                      |
|     [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/wayne-abbruscato-orr-767a2b90/)      |                 [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/seth-bradshaw/)             |                [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/sara-cearc/)             |      [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/christopher-girvin/)      |                [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/matias-iturbide/)    |                [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/chaz-kiker/)            |                [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/brendan-lai-tong/)    |                [ <img src="https://static.licdn.com/sc/h/al2o9zrvru7aqj8e1x2rzsrca" width="15"> ](https://www.linkedin.com/in/shane-slone/)            |

<br>
<br>


![MIT](https://img.shields.io/packagist/l/doctrine/orm.svg)

### Database layout

The table layout is similar to the initial version with the following exceptions:

* The join table userroles is explicitly created. This allows us to add additional columns to the join table
* Since we are creating the join table ourselves, the Many to Many relationship that formed the join table is now two Many to One relationships
* All tables now have audit fields

Thus the new table layout is as follows

* User is the driving table.
* Useremails have a Many-To-One relationship with User. Each User has many user email combinations. Each user email combination has only one User.
* Roles have a Many-To-Many relationship with Users.
* Programs have three distinct relationships to User. 
   * One-To-One - Authenticated user is assigned as Admin upon program initialization.
   * Many-To-Many - UserTeachers is a join table between User and Program. (filter users by role teacher)
   * Many-To-Many - UserStudents is a join table between User and Program. (filter users by role student)
   
* Program is the driving table.
* Courses have a Many-To-One relationship to Program.

* Course is the driving table.
* Modules have a Many-To-One relationship to Course.


![Image of Database Layout](reachlmsdbschema.png)

### Installation Instructions(for running locally)
##### With IntelliJ IDEA
* Clone repository
* Make sure to have enviornment variables set correctly, including JAVA_HOME
* Open in IDEA
* Click run
* Celebrate
##### Run in Terminal
* Clone repository
* cd into repository
* Make sure to have enviornment variables set correctly, including JAVA_HOME
* Mac and Linux 
```
chmod +x nvmw
./nvmw spring-boot:run
```
* Windows  
```
.\mvnw.cmd spring-boot:run
```
