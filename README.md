
# CAPSTONE PROJECT FOR 2022 JAVA FULL-STACK COHORT (4)
Created by John Choi circa 2022

## Authors

- [@JohnJChoi](https://www.github.com/jcette)


## Backstory
Welcome. You have arrived at my first comprehensive software project. I have a background in medical research and pharmacy, and will be working for an insurance company; hence I decided to create an app that targets insurance companies as clients. 

## App Description
A web application for building, managing, and manipulating a database of patient information, that also provides predictions for future health outcomes and recommendations for insurance coverage. 

### What does the app do, specifically?
1. Takes patient data (HIPAA sensitive data, health data, insurance/financial data) and populates a database that stores the data for use in predictions/recommendations
2. Uses the data to 'predict' specific health outcomes for individual patients and for the population as a whole
3. Takes those predictions and makes recommendations on what individuals / the population should do to adequately insure themselves

## Important User Stories
1. As a client, I want to see the patient data, health predictions, and insurance recommendations so that I can obtain valuable advice for my business
2. As an admin, I want to be able to edit the clients' patient data, adjust predictions, and adjust recommendations, so that I can provide a useful service to the clients
3. As a user, I want to be able to log in and have the app save my credentials so that I can use the app with ease
4. As a user, I need the ability to register as either an admin or client, so that different types of users can access the appropriate functions
5. As an admin, I want to be able to easily upload patient data, so I don't have to manually type in everything

## Technical Challenges / Highlights
1. Spring security:
Learning Spring Security and the theories of how it works
2. Algorithm: Having to use a dummy algorithm for the present, and what to do in future
3. Testing: Using mock repositories for controller and service tests
4. Models, models, models: for this project, have an abundance of models. Each model does something rather specific in my code. If I were to redesign the app under a different philosophy, I could have fewer models but have to tailor each rendition of each model for specific usages.

## Functional Requirements (completed)
1. Users (Clients and Admins) Access Management
      1. Admins may sign in and manage data in the database as well as modify algorithms and prediction outcomes.
      2. Clients may sign up and/or login to run the database according to specifications they give to the admins. For example, the clients would supply data and desired outcomes for prediction and the admins would be responsible for tailoring the app for the client’s behalf.
2. Account Info Management
      Store and manage the details of the users (clients and admins)
3. Patient Details and Data Management
      1. Maintain a database of all patient details (i.e., name, relevant health metrics, health outcomes, insurance coverage) with security and access dependent on the sensitivity of the data (HIPPA data should be more secure, and anonymity is important for patient data in general)
      2. Add/update/modify database as new data is generated or acquired
4. Uploading data
      Admin should be able to upload data (e.g. in CSV-format) to populate the backend
5. Prediction of Health Outcomes
      1. Create customizable algorithms with selectable inputs and outputs (inputs: health metrics. Outputs: key outcomes such as visits to the ER, utilization of expensive health services, severe disease states that drastically reduce quality of life)
      2. Modify to meet the needs of each individual client
6. Optimization of Insurance Services
      1. Compares the likelihood of requiring certain services to the existing insurance coverage of the patient/population and creates recommendations to increase, decrease, or maintain current level of coverage
      2. Modifiable based on insurance coverage types, cost of relevant services
7. Frontend UI/UX Design and Management
      Design and manage frontend UI/UX (e.g., home page, account page, prediction model pages, and result pages, etc.)

## Functional requirements: (to-do):
1. Machine Learning for Algorithm: 
   Implement automated machine learning kit to create a more real-world algorithm (instead of one supplied ad-hoc by the developer for the MVP or proof-of-concept)
2. History of previous algorithms:
   Maintain a history of prior algorithms used

## Tech-stack:
1. Database: MariaDB, HeidiSQL
2. Persistence Layer: Spring Data JPA
3. Business Layer: Java, Spring Boot, Spring Security
4. Presentation Layer: HTML/CSS/JS, Bootstrap, JQuery, Thymeleaf / Thymeleaf Security

## Technical-requirements provided by TEK and Per Scholas:

- Application naming format for canvas submission: LastName_FirstName_ProjectName_CaseStudy
- Use Tomcat as the webserver
- Views:
  - Use an external CSS stylesheet (internal styling may be used along with frameworks such as Bootstrap, but you must still include and utilize a custom CSS external file)
  - Your application should include six different views/pages (wireframes of the pages should be submitted with the project)
  - Use HTML to layout the pages and Thymeleaf to make the pages dynamic (frameworks such as Angular or React can be used as well but will not be covered in the course. The application’s presentation must meet the general view requirements.)
  - Use CSS to style the web pages
  - Use at least one JavaScript script linked from an external script file (internal scripts may be used along with frameworks such a jQuery, but you must still include and utilize a custom JavaScript external file)
  - Include a navigation section that is included across multiple pages
- Models and Database:
  - The configuration file must be set up correctly (e.g., persistence.xml or application.properties)
  - Include at least three custom queries
  - Test each custom query created in each repository
  - Test at least one method in each of the service classes
  - Include at least one parameterized test
  - Include at least one test suite
  - Use MariaDB as your DBMS
  - Have at least four models along with tables in a relational database (if four models/tables do not make sense for your application, discuss this with your instructor)
  - Include a schema diagram of the tables
  - Use Jakarta Persistence API (JPA) directly or through Spring Data JPA
  - Include and implement repository and service classes/interfaces
  - Models should be annotated for binding using Spring data binding through Jakarta and/or Hibernate validation
  - Your application should include at least one example of each of the CRUD operations
  - Use JUnit to perform unit tests on the JPA repositories/services
- Spring
  - Use Spring Boot to implement the project
  - Include at least two ways of creating a managed bean/object
  - Use correct implementations of dependency injection with appropriate use of the @Autowired annotation
  - Include at least one example of session management (Spring Security can be used for session management)
  - Apply exception handling where required by the code
  - Use of Web Services (when applicable)
- Include sign up and login functionality with encrypted passwords using bcrypt (use of Spring Security will satisfy this requirement)
- The project package structure should be shown in class where the models, DAO/repositories, services, controllers, exceptions, etc., have a package. Views or templates don’t require a package.
- Standard Java naming conventions should be followed:
  - Classes should be written in Pascal case
  - Variables, methods, and URLs should be written in camel case
  - Files, including view files, should be written in snake case
  - Packages should be in all lowercase with each word separated by dots (.)
  - Packages should include the name of your project and your name (e.g., “org.johndoe.myprojectname”)
- Each class should include comments to describe the class and the methods (see Java - JavaDoc discussion topic in Canvas)
- Have the project hosted on GitHub with a “readme” file documenting user stories and technical challenges along with how they were resolved.