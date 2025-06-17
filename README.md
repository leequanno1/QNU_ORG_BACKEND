# Organization Management System - Backend

This is the backend project for the Organization Management System at Quy Nhon University, developed using Spring Boot. The system provides comprehensive management for university organizations with the following main features:

## Main Features

- **JWT Authentication & Authorization**
- **Organization Management**
- **Member Management**
- **Post Management**
- **Event Management**
- **Content Moderation**
- **Email Notification System**

## Configuration

Before running the project, configure the following properties in `src/main/resources/application.properties`:

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT configuration
jwt.secret=your_jwt_secret
jwt.expiration=86400000

# Email configuration
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_email_password
```

## Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/leequanno1/QNU_ORG_BACKEND.git
    ```

2. **Configure the database and application properties as above.**

3. **Install dependencies:**
    ```bash
    ./mvnw clean install
    ```

4. **Run database migrations (if any).**

## Build & Run

- **Build the project:**
  ```bash
  ./mvnw clean package
  ```

- **Run the application:**
  ```bash
  ./mvnw spring-boot:run
  ```
  or run the generated JAR:
  ```bash
  java -jar target/backend-0.0.1-SNAPSHOT.jar
  ```

## Contact

For more information, please contact the project maintainer.
