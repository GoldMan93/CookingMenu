Course Management GUI Application
This is a Java-based graphical user interface (GUI) application for managing and viewing course information. It includes functionalities for creating, updating, and displaying course details within an intuitive user interface.

Features
Course Management: Users can create and store course details, including course ID, title, and description.
GUI Interface: All interactions take place in a GUI environment, facilitating ease of use.
Data Persistence: Allows users to manage courses with data saved during the session.
Project Structure
Main.java: The main entry point of the application. It initializes the GUI and sets up the course management system.
GUI.java: Provides base methods and attributes for other GUI components.
CourseGUI.java: A specialized GUI for viewing and managing courses.
CreateGUI.java: A GUI interface for creating and adding new course information.
MainGUI.java: Controls the main view, which includes navigation to other parts of the application.
Installation
Clone the repository:
bash
Copy code
git clone https://github.com/yourusername/course-management-gui.git
Compile the project: Navigate to the project directory and compile the Java files:
bash
Copy code
javac *.java
Run the application:
bash
Copy code
java Main
Usage
Launching the Application: Run the Main class to open the main interface.
Creating a Course: Use the 'Add Course' option to input new course information.
Viewing Courses: Navigate to the 'View Courses' section to see all added courses.
Screenshots
(Add screenshots of the application screens here for a better user understanding.)

Contributing
Fork the repository.
Create a new branch (git checkout -b feature-name).
Make your changes.
Commit your changes (git commit -am 'Add some feature').
Push to the branch (git push origin feature-name).
Create a new Pull Request.
