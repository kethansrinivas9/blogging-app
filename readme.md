# React Next.js & Spring Boot Application

This project consists of a **React Next.js** frontend and a **Spring Boot** backend.

## Prerequisites

Ensure you have the following installed:

- [Node.js](https://nodejs.org/) (Recommended: LTS version)
- NPM
- [Java 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/) (or any other configured database)

## Setup & Run Instructions

### 1. Backend (Spring Boot)

#### Steps to build and run:

1. Navigate to the backend directory:
   ```sh
   cd backend
   ```
2. Configure `application.properties` (or `application.yml`) in `src/main/resources/` with database settings.
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```
5. The backend will be available at `http://localhost:8080`.

### 2. Frontend (React Next.js)

#### Steps to build and run:

1. Navigate to the frontend directory:
   ```sh
   cd frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Create a `.env.local` file for environment variables (if required).
4. Start the Next.js development server:
   ```sh
   npm run dev
   ```
5. The frontend will be available at `http://localhost:8000`.

## Running in Production

### Backend

1. Build a JAR file:
   ```sh
   mvn package
   ```
2. Run the JAR:
   ```sh
   java -jar target/your-app-name.jar
   ```

### Frontend

1. Build the Next.js app:
   ```sh
   npm run build
   # or
   yarn build
   ```
2. Start the production server:
   ```sh
   npm start
   # or
   yarn start
   ```

## API Endpoints

To view the backend endpoints - visit http://localhost:8080/swagger-ui/index.html which displays the list of endpoints using Swagger.

## License

This project is licensed under the [MIT License](LICENSE).
