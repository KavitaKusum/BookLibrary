
Book LibraryBook Library is an Android application built to showcase a clean, scalable, and testable architecture using the latest Android development tools. 
The app fetches a list of books from the Open Library API and displays them in a simple, intuitive user interface.

**🌟 Features**
 - Dynamic Book List: Fetches and displays a list of books with their   
   cover, title, and author.
 - Detailed View: Tap on a book to view its details in a modal bottom sheet.
 - Modern UI: Built entirely with Jetpack Compose and Material 3 for a clean and responsive user experience
 - Robust Error Handling: Displays a user-friendly error message with a retry option if the network request fails.    
 - Efficient Image Loading: Asynchronously loads and caches book covers
   using Coil.     
 - Reactive Data Flow: Uses RxJava for handling asynchronous network operations.
  
**🏗️ Project Structure**
The project is structured into layers to promote separation of concerns, making it scalable and easy to maintain.
com.example.booklibrary  
  │  
  ├── data                # Data layer  
  │   ├── network         # Retrofit service and DTOs  
  │   └── repository      # Repository implementation  
  │  
  ├── domain              # Domain layer (framework-independent)  
  │   ├── model           # Core business models  
  │   └── repository      # Repository interfaces  
  │  
  ├── ui                  # UI layer  
  │   ├── screens         # Composable screens  
  │   ├── theme           # App theme and colors  
  │   └── viewmodels      # ViewModels  
  │  
  ├── di                  # Dependency Injection setup with Hilt modules  
  └── test_utils          # Utility classes for testing  

**🚀 How to Build**
1. Clone this repository.
2. Open the project in a recent version of Android Studio.
3. Let Gradle sync the dependencies.
4. Run the app on an emulator or a physical device.

License : 
This project is licensed under the MIT License - see the LICENSE.md file for details.

