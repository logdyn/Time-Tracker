# Time-Tracker [![Build Status](https://travis-ci.org/logdyn/Time-Tracker.svg?branch=master)](https://travis-ci.org/logdyn/Time-Tracker)
This project is to help remind me how much time I have spent on certain tasks.

[![Main Window](docs/main.png)](docs/main.png)

//TODO complete readme

## Main Interface
The main interface has three main components.

### Menu Bar
Standard Windows application feature, in the standard Windows application location.

#### File

##### Add Issue
Allows the user to add an issue to the time tracker. 
A user cannot input an issue without a name, however the URL field can be ommitted. If the URL field is present, the user can press on the name to be taken to the issue's location.

##### Clear Issues
Allows the user to remove all issues presently stored within the Time Tracker application. 
Note: this is irreversible

##### Close
Closes the application.

#### Control

##### Stop
Pauses the currently running timer. Can also be triggered by pressing Control + Q.

#### Help

### Button Bar
This is the series of buttons located just below the menu bar.

#### Plus
Refer to [Add Issue](#Add-Issue).

#### Trash
Refer to [Clear Issues](#Clear-Issues).

#### Stop
Refer to [Stop](#Stop).

### Timer Monitor
The timers themselves can be seen as a series of vertically stacked rows, each with two columns; the left column is the name of the task, the right column is the time spent on the task.

#### Name Field
If the user assigned a URL to their timer, it can be clicked on to access the file or webpage in the default application.

#### Timer Field

#### Context Menu
If the user right-clicks on a timer, some options are presented:

##### Show Work-Log

##### Add Milliseconds

##### Remove

