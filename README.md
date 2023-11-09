# Karkonosze Hiking Application

## Table of contents
* [General info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Presentaton](#presentation)
* [Setup](#setup)

## General info
The goal of the application is to let users record their hike waypoints easily and quickly, calculate distance, pace, store personal records and plan future trips without revisiting the same hiking spots.	</br>
The user picks a starting hike waypoint and then can enter as many other waypoints as they like. When a current waypoint is selected, it limits available waypoints to only those that match real life available paths. In this way selecting next waypoints is much faster and the user has to choose from only few available options instead of selecting from the whole pack of existing waypoints.
After the hike is finished, the application calculates real distance traveled, the pace and speed.
For now on the application covers an area of Karkonosze mountains shown below:

![image](https://github.com/AleksanderLinczuk/Karkonosze-Hiking/assets/120337814/8a3214e2-6ff7-4b00-8939-ffb399e6b013)



## Features
* Save new hikes by selecting waypoints from the list, the next available waypoint selection is always limited to real life available options
* Calculate real distance based on selected waypoints
* Edit existing hikes
* Delete existing hikes
* View list of all visited and unvisited waypoints to plan new hikes

## Technologies
Project is created with:
* Java 17
* Spring Boot 3
* Vaadin 24
* MySQL, JS, HTML, CSS

Database structure - diagram:
![image](https://github.com/AleksanderLinczuk/Karkonosze-Hiking/assets/120337814/2f420233-ef66-4ebc-afc0-fc8ea7bb75c6)

## Presentation
Adding new hike and editing/ removing saved hikes:
![image](https://github.com/AleksanderLinczuk/Karkonosze-Hiking/assets/120337814/97d0f343-cbbe-4e47-9c5d-c62787d131df)

	
## Setup
To start this project, run the application and enter http://localhost:8080 in browser.


