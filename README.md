Hotel Booking System
This is a simple Java-based Hotel Booking System that allows users to manage rooms, customers, and bookings. It demonstrates key object-oriented programming (OOP) principles such as inheritance, encapsulation, abstraction, polymorphism, and also includes file I/O, exception handling, GUI components, and database interaction using SQLite.

#Features
Add, update, delete customers
Book and cancel hotel rooms
View available rooms
GUI interface using Java Swing (RoomBookingGUI)
SQLite database integration
Log actions to a text file (log.txt)
Modular architecture following OOP principles

Project Structure
dao/            --> Data Access Objects for DB operations  
model/          --> Java classes representing system entities (Room, Customer, Booking)  
service/        --> Business logic (Booking, Room availability)  
ui/             --> UI components like Swing GUI and CLI menus  
exception/      --> Custom exceptions  
util/           --> Utility classes for logging and config  
interfaces/     --> Interface(s) used for report generation 

Technologies Used
Java
SQLite (hotel.db)
Java Swing (GUI)
JDBC
File I/O
Gradle (build tool)

#How to Run
#Using Command Line
javac -cp ".;sqlite-jdbc.jar" src/main/java/ui/RoomBookingGUI.java
java -cp ".;sqlite-jdbc.jar;src/main/java" ui.RoomBookingGUI
**Ensure sqlite-jdbc.jar is in your classpath.

#Using IDE
Open the project in IntelliJ or Eclipse.
Ensure hotel.db is in the working directory.
Run RoomBookingGUI.java or MainMenu.java.

#Database
File: hotel.db
Tables: Room, Customer, Booking
Initializes using DatabaseInitializer.java if not present

#Exception Handling
Uses custom exceptions like:
RoomUnavailableException
InvalidBookingException
ServiceException

# Logging
User actions are logged into log.txt using FileLogger.java.