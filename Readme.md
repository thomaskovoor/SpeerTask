
# Speer Application

This project is a mobile application developed in Kotlin using the Android platform. It is designed to interact with the GitHub API to fetch user profile data and display it in a user-friendly interface.

## Features

- Search for GitHub users by username
- View detailed user profiles including follower and following lists
- Pagination support for viewing followers and following lists
- Error handling for network issues and user not found scenarios

## Architecture

The project follows the MVVM (Model-View-ViewModel) architecture pattern. It uses LiveData and Coroutines for handling asynchronous tasks and data updates.

## Libraries Used

- Retrofit: For network requests
- Coroutines: For handling asynchronous tasks
- LiveData: For storing and managing UI-related data in a lifecycle conscious way
- Picasso: For image loading
- Fragment Navigation: For navigating between fragments
- RecyclerView: For displaying lists
- Splash Screen: For displaying a splash screen when the app is launched

## Fragments

- `SearchUserFragment`: Displays a search bar for searching GitHub users by username.
- `UserDetailsFragment`: Displays a user profile including follower and following lists.
- `FollowDetailsFragment`: Displays a list of followers or following users.

## ViewModel Classes

- `SearchUserViewModel`: Responsible for managing data for the `SearchUserFragment`.
- `UserSharedViewModel`: Stores and manages UI-related data in a lifecycle conscious way. This ViewModel is shared between multiple fragments.
- `FollowDetailsViewModel`: Responsible for managing data for the `FollowDetailsFragment`.

## Data Classes

- `UserProfile`: Represents a user profile.
- `Resource`: Represents a resource that can have one of three states: `Success`, `Failure`, or `Loading`.

## How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Run the project on an emulator or a real device

## Contributions

Contributions, issues, and feature requests are welcome. Feel free to check issues page if you want to contribute.

## Author

Thomas Kovoor

## License

