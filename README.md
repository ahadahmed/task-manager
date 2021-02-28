# TaskManager
taskmanager-api contains collection of REST APIs for task manager.


## How to run?

#### Run Using Docker Compose
Execute following commands to run on docker.
      
    git clone https://github.com/ahadahmed/task-manager.git
    cd task-manager/taskmanager-api/
    docker compose up
    
That's it! The application should be up and running.

`NOTE:`Make sure  PORT 8080 and 3306 on your system is available.

The database and corresponding tables will be created on successful application startup, along with two user named
``admin`` and `user1` with role `ADMIN` and `USER` respectively.

Password for signing into application and access APIs(for both user type): `letmein`.


###### API documentation can be accessed on:
 
[TaskManager APIs](https://localhost:8443/taskmanager/api-doc/swagger-ui/index.html?configUrl=/taskmanager/api-docs/swagger-config#)

N.B: *As the application uses self signed certificate, in some browser you may get privacy error while accessing Swagger API doc. `firefox` is recommended in such case.*


#### POSTMAN config to access APIs
to call API from POSTMAN import following files from `taskmanager-api/postman-collections/` directory.
* Import `task-manager.postman_environment.json`
* Import `task-manager.postmant_collectionn.json`
<hr>

#### Run from IDE

* Open the `pom.xml` as a <b>Maven Project</b> in your `IDE`
* Configure following properties in `application.properties`
```properties
spring.datasource.url=jdbc:mysql://<YOUR_DATABASE_HOST>:3306/<DBNAME>?createDatabaseIfNotExist=true
spring.datasource.username=<DATABASE_USERNAME>
spring.datasource.password=<DATABASE_PASSWORD>
```
* Run maven application from your IDE.



 
