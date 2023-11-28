# 1. Overview

The primary goal of this project is to gain a deeper understanding of Spring Security OAuth2 and explore other related technologies. It serves as a learning ground for implementing secure authentication and authorization.

# 2. Features

In addition to the main project goals, the following features have been implemented:

## 1. Filtering and Pagination with QueryDSL:

Users can be filtered and paginated using QueryDSL, providing a flexible and efficient way to manage user data.

## 2. Database Management with Liquibase:

Tables are created and managed using Liquibase, ensuring a consistent and version-controlled database schema.

## 3. JPA Auditing:

Utilizes JpaAuditing for auditing users, providing a mechanism for tracking and managing user activity.

# 3. Prerequisites

Before running the application, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) - The project is built with Java and requires a compatible JDK. [Version 17 is recommended](https://www.oracle.com/java/technologies/javase-downloads.html).
- PostgreSQL Database - The project uses PostgreSQL as the database. [Download PostgreSQL](https://www.postgresql.org/download/).

# 4. Installation

Follow these steps to set up and run the project:

1. Clone the repository.
2. Ensure you have a PostgreSQL database instance running.
3. Update the database configuration in `src/main/resources/application.properties` or `src/main/resources/application.yml`.
4. Build and run the project using the following Gradle command:

./gradlew bootRun


# 5. Authentication
To access the application, use the following default authentication credentials:

Admin Credentials:
Username: admin@gmail.com
Password: root

User Credentials:
Username: user@gmail.com
Password: root

These credentials are provided for initial testing and demonstration purposes.

# 6. Password Recovery
In the event that a registered user is not authenticated or forgets their password, 
the application provides a password recovery mechanism. Follow the steps below to recover your password:

1. Navigate to the login page and click on the "Forgot Password" link.
2. Complete the form by providing your registered email address.
3. If the entered email is associated with a valid user in the database, 
a password recovery email will be sent to that address.
4. Check your email for the recovery link and follow it to a page where you can input a new password.
5. The recovery link is valid for 5 minutes for security reasons. After this period, 
a new recovery request will be required.
