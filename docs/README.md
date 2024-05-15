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

3. **Create Database Schema**  
   To create the database schema for the sample, execute:

   ```shell
   mvn datanucleus:schema-create
   ```

### Running the Application

4. **Launch the Server**  
   Start the server using the Jetty plugin with:

   ```shell
   mvn jetty:run
   ```

5. **Run the Client**  
   In a new command window, execute the client sample code with:

   ```shell
   mvn exec:java -Pclient
   ```

   After these steps, the Bike Lending App server should be running, and the client application will be ready to use. You can also verify the functionality and correctness of the application by running the automated tests.

### Testing the Application

6. **Running Unitary Tests**  
   You can run the unit tests to verify the application's functionality. Open another command window and execute the following command:

   ```shell
   mvn test
   ```

7. **Running Integration Tests**  
   To continue with this verification, you can execute the integrations tests following the next commands. Ensure that the server is running, as the tests may interact with it to validate the end-to-end functionalities.

   Note: The tests are designed to run independently of the client application. They directly test the server's REST API endpoints, simulating the actions a client would perform.

   ```shell
   mvn verify -Pintegration-tests
   ```

7. **Running Performance Tests**  
   Finally, execute the performance tests to see how well the server manages the requests. Ensure that the server is running, as the tests may interact with it to validate the end-to-end functionalities.

   Note: The tests are designed to run independently of the client application. They directly test the server's REST API endpoints, simulating the actions a client would perform.

   ```shell
   mvn verify -Pperformance-tests
   ```
   
## Contributing

Contributions are welcome. Please fork the repository and submit pull requests with your enhancements.

## License

This project is open-sourced under the Apache License. See the LICENSE file for more details.
