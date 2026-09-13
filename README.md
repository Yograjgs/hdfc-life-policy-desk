HDFC Life Policy Desk

A Spring Boot application for managing HDFC Life policy desk operations.
How to Run
Using Maven Wrapper:
./mvnw spring-boot:run

Or using Maven:
mvn spring-boot:run

API Endpoints

Method	Path	Status Codes
GET	/api/policies	200, 404
GET	/api/policies/{id}	200, 404
POST	/api/policies	201, 400
PUT	/api/policies/{id}	200, 400, 404
DELETE	/api/policies/{id}	204, 404
GET	/api/customers	200, 404
GET	/api/customers/{id}	200, 404
POST	/api/customers	201, 400
PUT	/api/customers/{id}	200, 400, 404
DELETE	/api/customers/{id}	204, 404

Entity Relationships
The application contains five database tables:

customers — main customer table.
policies — contains customer_id as a foreign key referencing customers(id).
policy_details — contains policy_id as a foreign key referencing policies(id).
claims — contains policy_id as a foreign key referencing policies(id).
payments — contains policy_id as a foreign key referencing policies(id).
In-Memory Store vs PostgreSQL
An in-memory store is useful for development, testing, or a small demo where data does not need to survive application restarts. PostgreSQL is better when the application needs persistent data, multiple users, transactions, and production-scale storage. Flyway provides version-controlled and repeatable database migrations. Unlike ddl-auto=update, Flyway gives explicit control over schema changes and keeps a history of which migrations have been applied. This makes database changes safer and easier to manage across different environments.

Technologies
Java
Spring Boot
Spring Data JPA
Maven
Flyway
H2
PostgreSQL
