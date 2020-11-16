#Setup Instructions
1. Create a package named data in phase1\src
2. Copy [user_data.csv](user_data.csv) and [event_data.csv](event_data.csv) from phase1\src\README 
(where one would find this file) and into the newly 
created phase1\src\data directory
3. Create a new run configuration (application), and set
 the main class to EntryPoint, JRE to 1.8, and
 and the working directory to ~group_0489\phase1\src
4. Run EntryPoint with these aforementioned configurations.

#Creating users
##Through TextUI
- It is **VERY STRONGLY** recommended to create a 
new user this way. Simply log in via the premade admin account that 
innately has access to all methods (username: admin, password: admin),
and create a user from there, selecting the permission template desired.
See Template and Permission in the Users package for more clarification.

##Through the pre-loaded file
- Add a new row to user_data.csv in the data package, with the format
"username","password","permission1,permission2,permission3". 
The quotation marks are necessary.

#Creating events
Likewise, creating events is almost entirely meant to be done through the UI, 
as ID's are stored as an instant, meaning they are a very long, unreadable number.

#Attending events
Anyone with the event_self_enroll method can enrol themselves in an event. Follow these easy steps!
