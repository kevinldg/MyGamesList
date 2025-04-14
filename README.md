![MyGamesList_Banner](https://github.com/user-attachments/assets/5dda1c89-cedd-4008-90e3-158962196a07)

# MyGamesList

An App where you can list your games and at which point you're at each game, inspired by [MyAnimeList](https://myanimelist.net).

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

In your MongoDB instance, you need a database called "my-games-list" and in there a collection called "users".

Via `docker run` command:

`docker run -d --name my-games-list -p 8080:8080 -e MONGODB_URI=... -e JWT_SECRET=... -e IGDB_CLIENT_ID=... -e IGDB_BEARER_TOKEN=... kevinldg/mygameslist:latest`
