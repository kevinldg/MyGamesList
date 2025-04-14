![MyGamesList_Banner](https://github.com/user-attachments/assets/5dda1c89-cedd-4008-90e3-158962196a07)

# MyGamesList

An App where you can list your games and at which point you're at each game, inspired by [MyAnimeList](https://myanimelist.net).

Frontend

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Afrontend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Afrontend)

Backend

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Abackend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)

## Capstone Project

This app was a capstone project, part of the course nf-java-onl-de-27015 🦀 from the java development bootcamp by neue fische GmbH.

## Features

- List games on your profile
- Set a state on each game: Playing, Completed, On Hold, Dropped, Planned to play
- Show your favorite game on your profile
- View other profiles
- Authentication via username and password

## Tech Stack

### Frontend
- Vite with Typescript and React
- Axios
- Tailwind CSS
- Font Awesome Icons

### Backend
- Spring Boot Web
- Spring Boot MongoDB
- Spring Boot Security
- Mockito
- Flapdoodle
- Lombok

### DevOps
- Linux
- Docker
- Github Actions
- SonarQube

## Setup with Docker

For running the application, you need a MongoDB database and a Client ID + Bearer token from IGDB. Take a look at the [IGDB API docs](https://api-docs.igdb.com/#account-creation) to get them.

Via `docker run` command:

`docker run -d --name my-games-list -p 8080:8080 -e MONGODB_URI=<MONGODB_URI> -e JWT_SECRET=<JWT_SECRET> -e IGDB_CLIENT_ID=<IGDB_CLIENT_ID> -e IGDB_BEARER_TOKEN=<IGDB_BEARER_TOKEN> kevinldg/mygameslist:latest`

---

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Abackend&metric=coverage)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Abackend&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Abackend&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Abackend&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.github.kevinldg.mygameslist%3Abackend&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=com.github.kevinldg.mygameslist%3Abackend)