# DeadlineHero – Android Application

## Project Overview
DeadlineHero is an Android application designed to help users manage assignments and deadlines efficiently.
The app allows users to create, update, complete, and share assignments while demonstrating modern Android development practices such as MVVM architecture
, Jetpack components, Room, DataStore, and coroutines.
This project was developed as part of the Mobile Programming course final project.

## Architecture
The application follows clean architecture principles and MVVM (Model–View–ViewModel):
UI (Activity / Fragment)
   -
ViewModel
   -
Repository
   -
Local (Room) / Remote (Retrofit)

## Implemented Requirements
Activities
MainActivity
AddEditAssignmentActivity

## Fragments
Assignment list
Completed assignments
Settings

## Explicit Intents
Opening activities
Starting and stopping services
Passing data using intent extras

## Implicit Intents
Sharing assignments using ACTION_SEND

## Jetpack Navigation
NavHostFragment
Bottom Navigation
SafeArgs for type-safe navigation arguments

## SafeArgs
Used to pass data between destinations in Navigation Graph

## RecyclerView & Lists
RecyclerView with ListAdapter
DiffUtil for efficient updates

## ViewBinding
Enabled and used in all Activities, Fragments, and RecyclerView items

## Jetpack ViewModels
Separate ViewModel for each screen
Lifecycle-aware logic
ViewModel factories used correctly

## Room (Local Database)
Entity, Dao, Database
Flow<List<>> for reactive UI updates
Automatic background threading with coroutines

## Repository Pattern
Repository connects Room (local) and Retrofit (remote)
UI never accesses data sources directly

## Networking (Retrofit)
Retrofit client with fake backend endpoint
DTOs for request/response
Safe network calls using try/catch

## Coroutines & Flow
Flow from Room and DataStore
SharedFlow for UI events (toasts)
viewModelScope for lifecycle-safe background work

## Jetpack Preferences DataStore
Used for UI settings (notification toggle)
Reactive data using Flow

## Jetpack Proto DataStore
Structured persistence using .proto file
Custom serializer
Used alongside Preferences DataStore

## Services
Background Service (DeadlineReminderService)
Started and stopped from Settings screen

## Notifications (Structure)
Notification toggle implemented
Service prepared for future reminder logic

## Dialogs
AlertDialog used for assignment options (share, update, delete)

## Error Handling
Input validation
Safe database access
Network error handling with logs
User feedback via Toast messages


## Unidirectional Data Flow (UDF)
The application primarily uses Flow and SharedFlow to maintain a clear data direction:
Data flows from Repository → ViewModel → UI
UI events are emitted back to the ViewModel
UDF structure was explored and prepared while keeping the implementation simple and readable.

## Notes
The backend endpoint is mocked for demonstration purposes.
The application focuses on clean architecture and correct use of Android components rather 
than backend complexity.


## Requirements Summary
The project implements all required components including Activities, 
Fragments, MVVM architecture, Room, DataStore (Preferences and Proto), Coroutines and Flow, 
RecyclerView, Retrofit networking, Services, dialogs, and proper error handling.



## Notifications (Design and Intended Implementation)
Although a full notification display is not implemented in the current version, 
the application architecture is prepared for it. 
Notifications would be implemented using Android’s NotificationManager inside the existing `DeadlineReminderService`. 
When the service is started (for example, when notifications are enabled from the Settings screen), 
a notification channel would be created, and a notification would be built and displayed to inform the 
user about upcoming deadlines. The service is the appropriate place for this logic because it 
runs independently of the UI and can trigger notifications even when the application is not in the foreground. 
User permission for posting notifications (Android 13 and above) would be declared in the AndroidManifest file.
This approach follows Android best practices and allows notifications to be easily added without changing the existing architecture.

 
## Solution
The solution is implemented using a layered architecture based on MVVM principles to clearly separate 
responsibilities and ensure maintainability. 
The user interface (Activities and Fragments) is responsible only for displaying data and handling
user interactions.
Each screen is backed by a ViewModel, which manages UI-related state and user actions while remaining 
independent of the Android lifecycle. The ViewModel communicates with a Repository, which acts as 
a single source of truth and decides where data should come from. Local data persistence is handled using Room, 
with entities representing database tables and DAOs defining database operations. 
A LocalDatasource layer is used to isolate direct database access and convert database entities into 
domain models used by the app. Networking is implemented using Retrofit to demonstrate remote communication, 
while the application continues to function offline using local storage.
Kotlin Coroutines and Flow are used to handle asynchronous operations and reactive data updates. 
User preferences are persisted using both Preferences DataStore and 
Proto DataStore to ensure state is preserved across app restarts. 
This approach results in a clean, scalable, and testable solution that satisfies all project requirements.
