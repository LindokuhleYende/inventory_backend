## **📦 Inventory Management API**

A Spring Boot REST API for managing product inventory.

Includes PostgreSQL persistence, Swagger/OpenAPI documentation, Lombok, and MapStruct for automatic DTO mapping.



##### **🚀 Features**



CRUD operations for Products

PostgreSQL database integration

DTO 

Swagger UI auto documentation

No authentication (for now)

JPA/Hibernate with auto schema generation

Global exception handling





##### **🛠️ Tech Stack**



| Layer                 | Technology                     |

| --------------------- | ------------------------------ |

| Backend               | Spring Boot 3                  |

| Database              | PostgreSQL                     |

| ORM                   | JPA / Hibernate                |

| Documentation         | Swagger UI (Springdoc OpenAPI) |

| Mapping               | MapStruct                      |

| Boilerplate reduction | Lombok                         |

| Build tool            | Maven                          |





##### **📂 Project Structure**



**src/main/java/com/inventory**

**│**

**├── controller**

**│    └── ProductController.java**

**│**

**├── dto**

**│    └── ProductDTO.java**

**│**

**├── entity**

**│    └── Product.java**

**│**

**├── mapper**

**│    └── ProductMapper.java**

**│**

**├── repository**

**│    └── ProductRepository.java**

**│**

**├── service**

**│    └── ProductService.java**

**│**

**├── exception**

**│    ├── ResourceNotFoundException.java**

**│    └── GlobalExceptionHandler.java**

**│**

**└── Application.java**





##### **🧰 Requirements**



* **Java 17+**



* **Maven 3.8+**



* **PostgreSQL 14+**



* **Optional: Docker (for local DB)**



##### **🗄️ PostgreSQL Setup**



###### Create a database:

###### CREATE DATABASE inventory\_db;





##### **application.properties**



spring.datasource.url=jdbc:postgresql://localhost:5432/inventory\_db

spring.datasource.username=postgres

spring.datasource.password=your\_password



spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true



springdoc.api-docs.enabled=true

springdoc.swagger-ui.enabled=true





