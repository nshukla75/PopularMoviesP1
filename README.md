# PopularMovies

To fetch popular movies, project is using the API from themoviedb.org. If you don’t already have an account, you will need to create one in order to request an API Key.In your request for a key, state that your usage will be for educational/non-commercial use.You will also need to provide some personal information to complete the request. Once you submit your request, you should receive your key via email shortly after.In order to request popular movies you will want to request data from the /discover/movie endpoint. An API Key is required.

Once you obtain your key, you append it to doInBackground() method in FetchMovieTask class in MainActivityFragment.java: Please Enter the key below

`String apiKeyStr =[YOUR API KEY]`

##P1 - Popular Movies App, Stage 1 Rubric
###The Rubric
####Required Components

`To “meet specifications”, your app must fulfill all the criteria listed in this section of the rubric.`

-**User Interface - Layout**
  - Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails
  - UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated
  - UI contains a screen for displaying the details for a selected movie
  - Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
  - When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.
  - When a movie poster thumbnail is selected, the movie details screen is launched

-**Network API Implementation** 

  - In a background thread, app queries the /discover/movies API with the query parameter for the sort criteria specified in the           settings menu. (Note: Each sorting criteria is a different API call.)
  - This query can also be used to fetch the related metadata needed for the detail view.

####General Project Guidelines

App conforms to common standards found in the Android Nanodegree General Project Guidelines (NOTE: For Stage 1 of the Popular Movies App, it is okay if the app does not restore the data using onSaveInstanceState/onRestoreInstanceState)

####Optional Components
  There are no optional components for stage 1.
  
  #Execution Instructions
Requires: Android Studio v1.5.0+

Dependencies:
-------------
Min SDK Version: 10 (Gingerbread 2.3.3)
Target SDK Version: 22 (Android lollipop)
Android Support Library v7 revision 22.1.1

Building/Running the app:
-------------------------

#### Setting up the Java Environment
Install the [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) on your system.

#### Set up Git Repository
*  Install git.  For Windows users, you may wish to do so by installing [GitHub for Windows](https://windows.github.com/).  For Mac users, there is [GitHub for Mac](https://mac.github.com/).
*  Clone the project repository.  To do so with the GitHub application, click the plus sign in the upper left, and select to clone a repository.
    -  The project repository URL to clone with public access is:
    [https://github.com/nshukla75/PopularMoviesP1.git](https://github.com/nshukla75/PopularMoviesP1.git)

#### Installing Android SDK Dependencies
*  Install [Android Studio](https://developer.android.com/sdk/installing/studio.html).
*  Launch Android Studio and select to 'Import Project' from the project directory cloned to above.
*  Then, in Android Studio, go to Tools->Android->SDK Manager and
install the following:
  -  Tools -> Android SDK Build-tools 22.1
  -  Android 5.1.1K (API V22) -> SDK Platform and Google APIs
       - For running in an emulator, install one or both system images.  The Intel x86 system image will run much faster than ARM, especially if hardware acceleration is enabled.
  -  Extras -> Android Support Library
  -  Extras -> Android Support Repository
  -  Extras -> Google Play Services (v.19)
  -  Extras -> Google Repository
  -  For a faster emulator on Windows, Extras -> Intel x86 Emulator Accelerator
  
#### Install device drivers and enable debug mode on device
*  Windows users will need to install the appropriate USB driver for their device in order to run the app on a device.  Please see the [list of available USB drivers](http://developer.android.com/tools/extras/oem-usb.html) and installation instructions.
* You can [enable debug mode on your device](http://developer.android.com/tools/device.html) to allow debugging over USB.

#### Running in an emulator
The app will run on an Android emulator.  
The emulator will run much faster with hardware acceleration enabled.  Please see the [directions on using the Android emulator and enabling hardware acceleration](http://developer.android.com/tools/devices/emulator.html).  For Windows, this uses the HAXM emulator accelerator package available under 'Extras' in the SDK Manager.

#### Running the app
Sync your gradle build file if your IDE asks you to, and then run the app via Run -> Run.

Troubleshooting Steps
---------------------

If the project won't build, here are a few steps to try:

1.  Shelve any changes:  VCS -> Shelve Changes
2.  Sync the gradle files:  Tools -> Android -> Sync Project with Gradle Files
3.  Clean the project build:  Build -> Clean project
