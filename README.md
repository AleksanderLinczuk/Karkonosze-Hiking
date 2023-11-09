# Karkonosze Hiking Application

## Table of contents
* [General info](#general-info)
* [Features](#features)
* [Technologies](#technologies)
* [Presentaton](#presentation)
* [Setup](#setup)

## General info

The goal of the application is to enable users to easily and efficiently record their hiking waypoints, calculate distances and pace, store personal records, and plan future trips without revisiting the same hiking locations.

The user begins by selecting a starting hike waypoint and can then enter as many additional waypoints as desired. When selecting a current waypoint, the application restricts available options to those that correspond to real-life paths, making it faster for the user to choose the next waypoint. This streamlines the process, allowing the user to select from a limited set of available options instead of sifting through the entire list of existing waypoints.

Upon completing the hike, the application calculates the actual distance traveled, pace, and speed. Currently, the application covers the Karkonosze mountains area as shown below: 

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


