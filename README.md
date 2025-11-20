# Book Library Android App

This is a modern Android application for browsing a library of books, built using the latest Android development tools and libraries. The project is structured to follow modern architecture patterns and best practices.

## 📚 Features

*   **Browse Books**: View a list of books fetched from a remote API.
*   **Modern UI**: A clean, responsive user interface built entirely with Jetpack Compose.
*   **Asynchronous Operations**: Uses RxJava for handling background tasks and network requests.

## 🛠️ Tech Stack & Architecture

This project follows the principles of **MVVM (Model-View-ViewModel)** and a reactive architecture.

*   **UI Layer**:
    *   **Jetpack Compose**: The entire UI is built with Compose, a modern declarative UI toolkit for Android.
    *   **Coil 3**: For efficient and modern image loading from network URLs.
    *   **ViewModel**: Manages UI-related data and state, exposing it via `StateFlow`.

*   **Domain & Data Layer**:
    *   **Hilt**: For dependency injection, managing dependencies across the app.
    *   **Retrofit**: A type-safe HTTP client for making network requests to the book API.
    *   **RxJava 3**: For managing asynchronous operations and data streams from the network layer.

*   **Testing**:
    *   **JUnit 4**: The standard for local unit testing.
    *   **Mockito & Mockito-Kotlin**: For creating mocks and stubs in tests.
    *   **Turbine**: A small library for testing `StateFlow` and other Kotlin Flows.
    *   **Espresso & Compose Test Rule**: For UI and instrumentation testing.

## 🚀 How to Build

To build and run the project, follow these steps:

1.  **Clone the repository**:
    
