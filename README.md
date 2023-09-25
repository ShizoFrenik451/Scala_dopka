## Installation
1. Configure the database connection in the `application.conf` file . Update the following properties with your PostgreSQL database credentials:

database {
driverClassName = "org.postgresql.Driver"
url = "jdbc:postgresql://localhost:5432/postgres"
user = "your-username"
password = "your-password"
}

2. Build the project using sbt:

sbt compile

3. Run the application:

sbt run

## Usage 
- GET `/notes` - Retrieves all notes
- GET `/notes/{noteId}` - Retrieves a specific note by ID
- POST `/notes` - Creates a new note
- PUT `/notes/{noteId}` - Updates an existing note
- DELETE `/notes/{noteId}` - Deletes a note by ID


