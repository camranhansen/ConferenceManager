 Setup Instructions
1. Create a folder named data in phase1/
2. Copy [user_data.csv](user_data.csv) and [event_data.csv](event_data.csv) from phase1/README/
(where one would find this file) and into the newly 
created phase1/data directory
3. Create a new run configuration (application), and set
 the main class to EntryPoint, JRE to 1.8, and
 and the working directory to the phase1 folder.
4. Run EntryPoint with these aforementioned configurations.

 Creating users
 Through TextUI
- It is **VERY STRONGLY** recommended to create a 
new user this way. Simply log in via the premade admin account that 
innately has access to all methods (username: admin, password: admin),
and create a user from there, selecting the permission template desired.
See Template and Permission in the users package for more clarification.

 Through the pre-loaded file
- Add a new row to user_data.csv in the data package, with the format
"username","password","permission1,permission2,permission3".

Where , are used to separate the string values represented by the quotes both of which are necessary.

 Creating events
Likewise, creating events should be done through the UI, 
as ID's are stored as an instant, meaning they are a very long, unreadable number.

 Attending events
Anyone with the event_self_enroll method can enrol themselves in an event.
 Follow these easy steps!
1. Use the VIEW_ALL_EVENTS permission to view the events that this use can attend!
2. In an external notepad, copy-paste the event location (e.g. Main Room), and the event time of the desired event, 
concatenating them to form the string "event_location"+"event_time". For example, for the event Debugging 102, 
the ID string would be Main Room2020-12-02T09:00:00Z. You can also just copy-paste the event id from the event_data 
csv file.
3. Select event_self_enroll (or other_enroll if you want to enroll another user), and then paste the event ID of the desired event.
We realize that this method is not very user-friendly, but we devoted our team time to other things, and will be implementing a 
much nicer method of enrolling in an event through a menu of options.

 UML & Javadoc
The UML documents are located in the UML folder in phase1 and the Javadoc in the root directory.