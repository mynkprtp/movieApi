# MoviesClub

MoviesClub is a Java-based application designed for managing and exploring a collection of movies. The project aims to provide an organized way to keep track of movies, including details such as titles, genres, ratings, and more.

## Features

- REST API to interact with movie data
- Add, edit, and delete movie entries via API endpoints
- Incorporates File Handling and custom Exception Handling
- Organize movies by genre, year, etc.
- User can register and login
- User can updatePassword through Email verification using OTP
- Role based Authentication using JWT (Access and Refresh Token)

## API Endpoints

- **GET /api/v1/movie/all**: Retrieve a list of all movies.
- **GET /api/v1/movie/{movieId}**: Retrieve details of a specific movie by its ID.
- **GET /api/v1/movie/allMoviesPage**: Retrieve list of all movie with pagination.
- **GET /api/v1/movie/allMoviesPageSort**: Retrieve list of all movie with pagination and sorting.
- **POST /api/v1/movie/add-movie**: Add a new movie.
- **PUT /api/v1/movie/update/{movieId}**: Update an existing movie by its ID.
- **DELETE /api/v1/movie/delete/{movieId}**: Delete a movie by its ID.

## Technologies Used

- **Java**: Core programming language used to build the application.
- **Maven**: Dependency management and project build tool.
- **Spring Boot** : For creating a robust web application.
- **Database** (MySQL): Used for storing movie information.

## Dependencies

The project uses the following dependencies, which are managed via Maven in the `pom.xml` file:

1. **Spring Boot Starter Data JPA** - For handling database persistence using JPA.
2. **Spring Boot Starter Validation** - For validating data within the application.
3. **Spring Boot Starter Web** - To build RESTful web applications.
4. **Spring Boot Starter Mail** - For sending emails within the application.
5. **MySQL Connector** - To integrate the application with a MySQL database.
6. **Lombok** - To reduce boilerplate code for Java classes.
7. **Spring Boot Starter Test** - For writing and running unit and integration tests.
8. **Spring Boot Starter Security** - For securing the application.
9. **JWT (io.jsonwebtoken)** - For handling JSON Web Token-based authentication.

## Prerequisites

Before running this project, ensure you have the following installed:

- [Java JDK 8 or above](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- (Optional) [MySQL/PostgreSQL](https://www.mysql.com/) (if using a database)

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/mynkprtp/moviesClub.git
   cd moviesClub
2. Build the project using Maven:

   ```bash
   mvn clean install

3. Run the application:

   ```bash
   mvn spring-boot:run

4. Access the application via the browser at http://localhost:8080

## Folder Structure

-  /src: Contains the Java source code.
-  /mvnw: Maven wrapper files.
-  pom.xml: Maven Project Object Model file, defining dependencies and project configuration.
