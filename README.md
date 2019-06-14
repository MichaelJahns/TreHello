# TreHello
My unique interpretation of a a trello board, now with an available APK!
If you would like to install this App on your android device, TreHello.apk is available for download, you can use this [resource](https://www.androidpit.com/android-for-beginners-what-is-an-apk-file) if you arent sure how to download it on your machine

## Log

#### Day Eight
* Builds APK and places it at top level of REPO
* Adds an accessibility test to every view important for accessibility

#### Day Seven
* Cleaned Strings
* Added hints and labelFors
* Added layouts and focusables
* Adjust some layouts
* Added button bar to one page

#### Day Six
* Wrote a cloud function to alert members to changes
* Wrote and Wired an UpdateTaskActivity
##### References
[cloud functions ref](https://github.com/firebase/functions-samples)

[getAllDocumentsByID](https://stackoverflow.com/questions/46721517/google-firestore-how-to-get-document-by-multiple-ids-in-one-round-trip)

#### Day Five
* Device Id is now being saved to a user account preferences on every save
* Members with their device ID set can now recieve background notifications

#### Day Four
* Adjusts the task view page to allow the creation, and joinging of teams, Teams are a POJO object that has a top level reference in NOSQL
* Removes TaskView and plans to integrate that into the profile Viewer
* When a user clicks on a user in the team recycler they are taken to that users profile page
* Repairs broken editProfileRoute

#### Day Three
* Adjusts where users can go
* Adds user profiles, the ability to edit user profiles, and redirects for users who havent
* Road Maps Single Task View, and User Tasks View
* Adjusts timing and reloads for better user experience
##### References
[Auth Listener](https://stackoverflow.com/questions/42571618/how-to-make-a-user-sign-out-in-firebase)

#### Day Two
* Adjust Styling
* Reformats Task Form to better control inputs
* Sets up Firebase Auth for Email
* Toggle Button for Log Actions

Blocked
* My desired approach for setting state is dependent on users seeing other users, users being able to create teams, and users being able to assign a Task based off the Team associated with it. Requires Unique Task Pages for edditing purposes

#### Day One
* Wires Firebase
* Creates Recycle Viewer

WIP
* Populate Recycler Viewer with Tasks

