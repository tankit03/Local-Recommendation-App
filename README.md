

# Local Recommendations App

## High-Level Description

Local Recommendations App is a mobile application designed to provide users with personalized recommendations for local restaurants, cafes, activities, and more. Utilizing user preferences and location data, the app aims to enhance the experience of exploring college towns and other areas by suggesting places to eat, activities to try, and things to do. Powered by the Yelp Fusion API, it delivers up-to-date information directly to the user's fingertips.

## Features

- **Personalized Recommendations:** Users receive suggestions based on their location.
- **Diverse Categories:** Explore options across various categories such as food, activities, and Night Life.
- **Location Services:** Leverages location services to offer suggestions based on the user's current location.

## API Integration

- **Yelp Fusion API:** [Yelp Fusion API Documentation](https://docs.developer.yelp.com/docs/fusion-intro)
- **API Methods Used:** GET
- **Usage:** Retrieves data from the Yelp API based on user-entered categories and preferences. The results are displayed on a dedicated results page.

## User Interface

- **Activity Flow:** The UI is designed to be intuitive, starting from location selection to category choice, followed by a detailed questionnaire, and culminating in the display of recommendations.
- **Navigation:** Users navigate through a series of screens, from selecting their preferences to viewing the results.
- **Interactions:** Users can interact with the app through selection screens, input fields, dropdown menus, and more.
- **Notifications:** The app may display notifications as part of its functionality (to be detailed further).

## Architecture

This app utilizes a clean architecture with a focus on separation of concerns, modularity, and scalability. Key components include:

- **YelpRepository:** Handles data operations, fetching data from the Yelp API, and caching results.
- **ViewModels:** Manage UI-related data and handle lifecycle-aware data loading.
- **Fragments:** Serve as the primary UI components, displaying data and interfacing with user inputs.

## Additional Features

- **Enhanced Location Services:** To refine recommendations based on the user's exact location, improving the relevance of suggestions.

## Getting Started

To get a local copy up and running, follow these simple steps:

1. Clone the repo
   ```
   git clone https://github.com/your_username_/Project-Name.git
   ```
2. Open the project in your IDE of choice (Android Studio recommended for Android apps).
3. Obtain a Yelp Fusion API key and add it to your project as indicated in the YelpService class.
4. Run the app on an emulator or a physical device.

## Prerequisites

- Android Studio or similar IDE
- Android SDK installed
- Yelp API Key

## Built With

- [Kotlin](https://kotlinlang.org/) - The primary programming language
- [Retrofit](https://square.github.io/retrofit/) - HTTP client for Android and Java
- [Moshi](https://github.com/square/moshi) - JSON library for Android and Java


## Acknowledgements

- [Yelp Fusion API](https://www.yelp.com/fusion)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- Contribution by Kaushik Dontula, Brandon enghauser, Grant Towers, Tanish Hupare

