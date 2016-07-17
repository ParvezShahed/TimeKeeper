# TimeKeeper
This android application consumes a timekeeping cloud api.

https://github.com/jonathanpike/timekeeping-api

## Use Interface
This application contains three main pages.

1. Login Page

2. Timecard view page

3. Time view page

### Loging page
This is a very basic login page. The username and passwaed is preset for now. User just needs to press ok to go to the next page.

### Timecard view page
This page shows all the time cards (With date and total working hours) in a recycler view. It contains two buttons one for punch in and another for punch out.
Touching each item will move to Time view page.

### Time view page
This page shows in time and out time corresponing to that day.


## High level architecture 
This application applies MVP (model view presenter) architecture pattern. 

It uses android Retrofit RESTful apis to communicate with the cloud.

To show data items it uses android Recycler view.

To parse network data it uses JSON/Gson.


## Limitations / further improvement scope
- Did not use any local storage to minimize network operation.

- Can be used sync adapter to improve performance.

- Some code refactor may be required.



## An Innovative plan for practical use purpose

Later this app can be modified in the following way.

User will take picture of a big analog or digital clock (Which shows both date and time) at the front door of the office to log in/out.
and application will process image and update the cloud.





