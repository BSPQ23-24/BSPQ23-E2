# Bike Lending App

## Introduction

The Bike Lending App is a Maven-based Java project designed to facilitate bike rentals and management. It includes a client-server architecture where the server handles bike lending logic, and the client provides an interface for users to interact with the system. The application uses a SQL database for data persistence and requires configuration and schema setup before use.

## Getting Started

To get started with the Bike Lending App, ensure you have Maven and MySQL installed on your system. Follow the steps below to configure the database, build the project, and run the application.

### Database Setup

1. **Create the Database Schema**  
   Before running the application, you need to create the database schema. Navigate to the `sql` folder and run the following command to create the database and grant privileges:

   ```shell
   mysql -u root -p < create-bikes.sql
   ```

### Building the Project

2. **Compile the Project**  
   Compile the project and enhance the database classes by running:

   ```shell
   mvn clean compile
   ```

3. **Enhance Classes**  
   Make sure the classes are enhanced for persistence:

   ```shell
   mvn datanucleus:enhance
   ```

4. **Create Database Schema**  
   To create the database schema for the sample, execute:

   ```shell
   mvn datanucleus:schema-create
   ```

### Running the Application

5. **Launch the Server**  
   Start the server using the Jetty plugin with:

   ```shell
   mvn jetty:run
   ```

6. **Run the Client**  
   In a new command window, execute the client sample code with:

   ```shell
   mvn exec:java -Pclient
   ```

After these steps, the Bike Lending App server should be running, and the client application will be ready to use.

## Contributing

Contributions are welcome. Please fork the repository and submit pull requests with your enhancements.

## License

This project is open-sourced under the Apache License. See the LICENSE file for more details.