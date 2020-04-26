# JDBC Project 

## Introduction
JDBC allows a connection between a Java application and an RDBMS, such as PostgreSql. This app can create, update, read and delete (CRUD) data from PostgreSql database. By implementing this project, I have learnt about the concepts of Data Transfer Object(DTO), Data Access Object(DAO), and Repository design patterns, and how to build the connection between Java and RDBMS, etc.
## ER Diagram
![image](./Assets/image.png)


## Design Patterns
### Data Access Object(DAO)
Data Access Object is a structural pattern that allows isolating the application/business layer from persistence layer(usually a relational database) using an abstract API. DAO is able to access Data Transfer Object(DTO), which encapsulates domain objects. The functionality of DAO is to hide from the application all the complexities involved in performing CRUD opertaions in the underlying storage mechanism. It is able to work with multiple tables and can handle different types of objects. 

### Repository
Repository pattern is added between the domain and data mapping layers to isolate domian objects from details of the database access code and to minimize scattering and duplication of query code. It is able to access single database table, and can only handle one type of objects. Repositories typically perform joins or other data transformation for themselves instead of query from database. It is very useful in systems where number of domain classes is large or heavy querying is utilized.  
