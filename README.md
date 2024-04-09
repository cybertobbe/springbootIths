# Laboration 2: Spring Boot Web Application with OAuth2 Login
<img width="500" alt="Screenshot 2024-04-09 at 11 38 24" src="https://github.com/yrlacornelia/SpringbootProjektIths/assets/143391699/edf0f025-a8ea-4d60-abe5-123f5b6335bc">
<img width="500" alt="Screenshot 2024-04-09 at 11 41 38" src="https://github.com/yrlacornelia/SpringbootProjektIths/assets/143391699/4d013e80-8602-4bed-834c-139c8b1a0e1e">

## Introduction
This application is database-driven and manages users and their published messages. The interface primarily consists of HTML pages, but a REST API is also created under the endpoint `/api` to provide open access to certain information that does not require authentication.

## Login
OAuth2 login will be used for logging into the server, with at least support for logging in via GitHub, but other services can also be added. We need to store users locally in a User table along with their roles, but the authentication part will be handled by GitHub.

## Viewing Messages
- **Unauthenticated Users**: Can view published and public messages.
- **Authenticated Users**: Can view all published messages, including non-public ones.
- **Message Filtering**: Messages can be filtered based on their author.
- **Pagination**: Messages are fetched in batches, either via a link or automatically as the page is scrolled down.

## Message Management
- **Editing Messages**: Users who authored a message can edit it.
- **Auditing**: Messages store information about the last modification and the user who made it.

## User Management
- **Login and Logout**: Users can log in and out of the application.
- **Profile Modification**: Authenticated users can modify their profile information, including:
  - Unique profile name displayed to others.
  - Uploading a profile picture (a default picture is used if none is chosen).
  - Full name and email address (not visible to others).

## Internationalization
- **Language Selection**: Users can choose between different languages on the website, with support for at least Swedish and English.
- **Message Translation**: Messages can be translated using an external API, such as LibreTranslate.

## Documentation
- **API Documentation**: API endpoints are documented using Swagger and OpenAPI standards.

## Usage
1. Access the application through a web browser.
2. Log in using your GitHub account.
3. Navigate through the web pages to view and interact with messages and user information.
4. Use the REST API endpoints under `/api/` for programmatic access to certain functionalities.

## Technologies Used
- Frontend: HTML, CSS
- Backend: Java, Spring Boot
- Database: mySQL
- Authentication: OAuth2 (GitHub)
- Internationalization: LibreTranslate
- Documentation: Swagger, OpenAPI

## Contributors
- Jens Nilsson (@jn.jensnilsson@gmail.com)
- August Rydnell (@august.rydnell@gmail.com)
- Yrla Hackzell (@yrrla.hackzell@hotmail.com)
- Tobias Sjöstrom (@tobias.sjostrom@iths.se)
- Hanna Stiglad (@hanna.stigland@iths.se)
