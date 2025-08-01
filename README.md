# Platzi Movies

## Description

Platzi Movies is an Android application developed in Kotlin that allows users to explore the most popular movies using TheMovieDB API. It implements Clean Architecture, modularization, and development best practices to ensure maintainability and scalability.

<img width="156" height="328" alt="image" src="https://github.com/user-attachments/assets/8ec698fd-f127-4bdb-98b1-8e5b33678c7c" />

## Architecture

The application follows Clean Architecture, separating responsibilities into different layers:

- **Presentation**: Contains the UI built with Jetpack Compose and manages the application state.
- **Domain**: Defines use cases and business rules.
- **Data**: Handles data retrieval from the API and local storage.

The MVVM pattern is implemented to maintain a decoupled and scalable architecture.
 
## Key Features

- **CI/CD with Firebase App Distribution via GitHub Actions**.
- **Search for movies (local + remote)**.
- **Display list of popular movies**.
- **Movie detail screen**. with:
Full movie info
Embedded trailer
- **Modularization**: The application is divided into modules to improve maintainability and scalability, Decoupled navigation between modules.
- **Pagination**: Uses Paging 3 library for efficient data loading.
- **Real-time search**: Implements local movie filtering with instant response.
- **Light/Dark Mode support**.
- **Multi-language support (Spanish/English)**.

## Security and Credential Management

- The API Key is securely stored in the `local.properties` file, preventing exposure in the source code.
- **GitHub Secrets** are used to configure sensitive variables in CI/CD.

## CI/CD

The application is configured with GitHub Actions for:

- **Unit test execution**.
- **Automatic build and distribution** to Firebase App Distribution.

## Installation and Execution

1. Clone the repository.
2. Add TheMovieDB API Key in `local.properties`:
MK_API_KEY
3. Run the application from Android Studio.
Make sure you have:
JDK 17
Kotlin 1.9+
Android Gradle Plugin 8.x

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Dagger Hilt** (dependency injection)
- **Retrofit** (API consumption)
- **Room Database** (local storage)
- **Paging 3** (data pagination)
- **Firebase App Distribution** (app distribution)

- Additional Notes
Feature navigation is fully decoupled through contracts (MovieNavigator, etc.), enabling independent development per feature.
The MovieVideoModel abstracts video playback state cleanly from UI concerns.
Utility extensions (e.g., Double.formatRating()) live in core:utils for centralized reuse.
All navigation is handled in the app module through a centralized NavHost and implementation of navigators.
CI/CD deploys on push to main or release/* branches using secure secrets and Firebase App Distribution.

Challenge: Video Playback
One of the biggest challenges in the project was handling video playback. The API responses provided only a YouTube video key, while the documentation required integration using Media3.
To address this, I implemented Media3 to demonstrate proper usage of the library as expected. However, since Media3 does not support native YouTube playback, I also included a fallback solution using a lightweight YouTube Player to ensure functionality.
Ideally, the backend should provide a direct video file (e.g., an MP4 URL) to fully leverage Media3 without relying on third-party solutions.
