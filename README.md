**HDFC Life Policy Desk**

A Spring Boot based Policy Desk application for managing insurance policy-related data through REST APIs.

Tech Stack
Java
Spring Boot
Spring Data JPA
Maven
Flyway
H2 / PostgreSQL
REST APIs
How to Run
Using Maven Wrapper

On Linux/macOS:

./mvnw spring-boot:run


On Windows:

mvnw.cmd spring-boot:run

Using Maven

If Maven is installed locally:

mvn spring-boot:run


The application will start using the Spring Boot configuration provided in the project.

API Endpoints

The application exposes 10 REST endpoints for policy-desk operations.

#	Method	Path	Status Codes
1	GET	/api/...	200 OK, 404 Not Found
2	GET	/api/.../{id}	200 OK, 404 Not Found
3	POST	/api/...	201 Created, 400 Bad Request
4	PUT	/api/.../{id}	200 OK, 400 Bad Request, 404 Not Found
5	DELETE	/api/.../{id}	204 No Content, 404 Not Found
6	GET	/api/...	200 OK
7	GET	/api/.../{id}	200 OK, 404 Not Found
8	POST	/api/...	201 Created, 400 Bad Request
9	PUT	/api/.../{id}	200 OK, 400 Bad Request, 404 Not Found
10	DELETE	/api/.../{id}	204 No Content, 404 Not Found

Replace the placeholder paths above with the exact controller mappings from the application. The repository currently exposes the project structure but its source files are not available through the GitHub fetch used here, so inventing the endpoint names would be misleading.

Entity Relationships

The database consists of five tables/entities.

Table	Foreign Key	References
table_1	—	—
table_2	table_2.table_1_id	table_1.id
table_3	table_3.table_1_id	table_1.id
table_4	table_4.table_2_id	table_2.id
table_5	table_5.table_3_id	table_3.id

The exact table and foreign-key names should match the five JPA entities and Flyway migration scripts in src/main/resources/db/migration.

In-Memory Store vs PostgreSQL

An in-memory store is suitable when the policy desk is a small development/demo application where data can be recreated on every restart and persistence is not important. PostgreSQL is preferable when policies must survive restarts, support concurrent users, provide reliable transactions, and scale beyond a single application instance. Flyway gives explicit, versioned, repeatable database migrations that can be tracked and applied consistently across environments. In contrast, ddl-auto=update lets Hibernate infer schema changes from entities and does not provide the same controlled, reviewable migration history. For production systems, Flyway provides much stronger control over how the database schema evolves.

Project Structure
hdfc-life-policy-desk/
├── .mvn/
├── src/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md

Notes

For local development, use the Maven wrapper so that the project can be built with the Maven version expected by the repository without requiring a separate Maven installation.

For production deployments, configure PostgreSQL through the application's datasource configuration and keep database schema changes under Flyway migrations rather than relying on automatic Hibernate schema updates.
