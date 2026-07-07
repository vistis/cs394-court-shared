# court-shared

This is the core shared library for the Court Management System. It contains the following:

- Entity classes representing structured tables (PGSQL) and document collections (MongoDB)
- Repositories for entity CRUD operations
- Migration and seeding using Flyway for structured database
- Base Redis configuration to make cache recognizable across the panels
- S3 configuration and service for file upload and generating public access URL for files

## Project Properties

- Project: `maven`
- Language: `java`
- Spring Boot: `4.1.0`
- Group: `kh.edu.paragoniu`
- Artifact: `court-shared`
- Packaging: `jar`
- Configuration: `yaml`
- Java: `25`

### Dependencies

- Spring Data JPA
- Spring Data MongoDB
- Spring Data Redis
- Flyway Migration
- S3 (by `software.amazon.awssdk`, version `2.46.21`)
- Spring Boot DevTools
- Lombok

> The dependencies will be inherited by projects that import this project as a dependency

## Usage

This project is highly important for development. You must have a copy of this codebase when developing the panels.

For this to become a usable library, it must be installed on your local machine by running the below command in the root directory of the project.

```bash
mvn clean install
```

Re-run of the above command is needed whenever there is any update or changes to the project codebase.

> Below steps are not necessary for the panels included in this repository, as they have been setup already.

Afterwards, it needs to be imported, by adding it as a dependency in `pom.xml`.

```xml
<dependency>
    <groupId>kh.edu.paragoniu</groupId>
    <artifactId>court-shared</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Then, in the main application class add

```java
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@EntityScan(
    basePackages = {
        "kh.edu.paragoniu.court_shared.entity",
    }
)
public class Application {...}
```

## Migration

> Migration can only be run in this project!

Before running any Flyway command, make sure to configure `flyway.conf`; specifically URL, user, and password to match the database you want to migrate to.

The migration will not run automatically. It has to be manually triggered with

```bash
mvn clean flyway:migrate
```

To clean the migration, run

```bash
mvn clean flyway:clean
```

### Seed

> Currently, only basic seeds are included such as populating status tables, etc. There is plan to have it insert vast amount of data in the future.
