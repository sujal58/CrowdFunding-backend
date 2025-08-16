# CrowdFunding-backend

This repository contains the backend for a CrowdFunding application built in Java. It manages project campaigns, user authentication, pledges, and more, providing the core logic and RESTful APIs for the platform.

## Features

- User registration, authentication, and profile management
- Project creation, editing, and exploration
- Pledge management: users can support projects
- Administrative endpoints for campaign management
- Secure data handling and validation
- Configurable via external properties
- **JWT-based authentication using Spring Security**
- **Email notifications via JavaMail**
- **Real-time notifications via WebSocket and Redis**
- **External API integration for KYC verification**

## Technology Stack

- Language: Java
- Framework: Spring Boot
- Database:  PostgresSQL
- RESTful API
- Spring Security (JWT)
- JavaMail
- WebSocket (with Redis pub/sub for real-time notifications)
- External API integration (KYC verification)

## Setup Instructions

Follow these steps to set up and run the project locally:

### 1. Prerequisites

- Java 17 or newer
- Maven or Gradle
- A supported database (see configuration below)
- Redis server (for real-time notifications)
- SMTP server (for email notifications)
- Access to external KYC API

### 2. Clone the Repository

```bash
git clone https://github.com/sujal58/CrowdFunding-backend.git
cd CrowdFunding-backend
```

### 3. Configure Application Properties

- Find the file `properties.example.yml` in the repository.
- Copy it to a new file named `application.yml` (or as required by your framework).
- Edit the new file and fill in values for:
    - Database access
    - JWT secrets
    - Email credentials (JavaMail)
    - Redis configuration
    - External KYC API credentials & endpoints

### 4. Build the Project

```bash
./gradlew build
```

### 5. Run the Application

```bash
./gradlew bootRun
```

The backend server will start on the port specified in your configuration (default: 8080).

### 6. Test Real-Time Notifications

- Ensure Redis is running.
- Connect your front-end client to the WebSocket endpoint as specified in your configuration.

### 7. KYC Verification

- Ensure the external API credentials and endpoints are correctly set up in your configuration.
- Use provided endpoints to trigger and check KYC verification flow.

### 8. API Documentation

Refer to the codebase or generated documentation (e.g., Swagger UI) for details on available endpoints.

## Configuration File Example

See `properties.example.yml` for all configurable properties and an example structure.

## Contributing

- Fork the repository
- Create a new branch for your feature or fix
- Commit your changes with descriptive messages
- Open a pull request

## License

(Add your license details here)

## Author

[sujal58](https://github.com/sujal58)