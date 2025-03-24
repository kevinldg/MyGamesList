# Frontend
FROM node:20 AS frontend-build
WORKDIR /app/frontend
COPY frontend/ ./
RUN npm install
RUN npm run build

# Backend
FROM maven:3.9-amazoncorretto-23 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml ./
COPY backend/src ./src
COPY --from=frontend-build /app/frontend/dist/ ./src/main/resources/static
RUN mvn clean package -DskipTests

# Image
FROM amazoncorretto:23-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=backend-build /app/backend/target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]