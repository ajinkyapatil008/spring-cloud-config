# spring-cloud-config

🌩️ Spring Cloud Config Server & Client
Centralized Configuration Management for Microservices
🧭 Overview

This project demonstrates a Spring Cloud Config Server and Client setup that allows centralized management of application configurations across multiple microservices.

Instead of maintaining separate property files in each service, configuration files are stored in a remote GitHub repository. The Config Server fetches these configurations and serves them to client applications based on their environment (development, staging, production, etc.).


🧱 Architecture


                    ┌──────────────────────────────────────────────┐
                    │            GitHub Repository                 │
                    │----------------------------------------------│
                    │    config-client-development.properties      │
                    │    config-client-production.properties       │
                    └──────────────────────────────────────────────┘
                                        │
                                        ▼
                       ┌────────────────────────────────┐
                       │     Spring Cloud Config Server  │
                       │  (http://localhost:8888)        │
                       │---------------------------------│
                       │ Fetches config from GitHub repo │
                       └────────────────────────────────┘
                                        │
                                        ▼
                     ┌────────────────────────────────────┐
                     │        Config Client Service         │
                     │    (http://localhost:8080)           │
                     │------------------------------------  │
                     │ Fetches properties from Config Server│
                     │    and refreshes dynamically         │
                     └────────────────────────────────────┘

🚀 Features

✅ Centralized configuration management

✅ Profile-based configuration (e.g., development, production)

✅ Dynamic property refresh using Spring Actuator

✅ Seamless GitHub integration for configuration storage

✅ Simplified maintenance for microservices

✅ Works across distributed environments



🧰 Tech Stack

    Category  Technology
    
    Language	Java 17
    
    Framework	Spring Boot 3.3.5
    
    Cloud	Spring Cloud 2025.0.0
    
    Config Storage	GitHub Repository
    
    Build Tool	Maven

Web Server	Embedded Tomcat


🗂️ Project Structure

spring-cloud-config/

    │
    ├── config-server/
    │   ├── src/main/java/com/example/configserver/
    │   │   └── ConfigServerApplication.java
    │   ├── src/main/resources/
    │   │   └── application.properties
    │   ├── pom.xml
    |
    ├── config-repo/
    │   ├── config-client-development.yml
    │   ├── .git
    |
    ├── config-client/
    │   ├── src/main/java/com/example/configclient/
    │   │   ├── ConfigClientApplication.java
    │   │   └── MessageController.java
    │   ├── src/main/resources/
    │   │   ├── bootstrap.properties
    │   │   └── application.properties
    │   ├── pom.xml
    │
    └── README.md

⚙️ Setup Guide
1️⃣ Prerequisites

Java 17+ installed

Maven 3.8+ installed

Git installed

Active internet connection (to fetch from GitHub repository)

2️⃣ Clone the Project
git clone https://github.com/ajinkyapatil008/spring-cloud-config.git
cd spring-cloud-config

3️⃣ Configure GitHub Repository

Create a new GitHub repo (for example:
👉 https://github.com/ajinkyapatil008/spring-config-repo
)

Inside it, add environment-specific property files:

Example:
config-client-development.properties

example.message=Hello from the Development Environment!


config-client-production.properties

example.message=Hello from the Production Environment!

4️⃣ Run Config Server

Navigate to the config-server directory:

cd config-server
mvn spring-boot:run


It starts on port 8888 by default.

Verify by visiting:
👉 http://localhost:8888/config-client/development

Expected Output:

    {
      "name": "config-client",
      "profiles": ["development"],
      "propertySources": [
        {
          "name": "https://github.com/ajinkyapatil008/spring-config-repo/config-client-development.properties",
          "source": {
            "example.message": "Hello from the Development Environment!"
          }
        }
      ]
    }

5️⃣ Run Config Client

In a new terminal:

    cd ../config-client
    mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=development"


    Client starts on port 8080.

      Now, open:
      👉 http://localhost:8080/message

You should see:

    Hello from the Development Environment!

6️⃣ Refresh Configurations Dynamically

If you change any property in the GitHub config file, refresh without restarting the client:

    curl -X POST http://localhost:8080/actuator/refresh


    Then recheck:
    👉 http://localhost:8080/message

📜 Important Files

      config-server → application.properties
      
      server.port=8888
      spring.cloud.config.server.git.uri=https://github.com/ajinkyapatil008/spring-config-repo
      spring.cloud.config.server.git.clone-on-start=true


      
      config-client → bootstrap.properties
      
      spring.application.name=config-client
      spring.profiles.active=development
      spring.config.import=configserver:http://localhost:8888
      management.endpoints.web.exposure.include=refresh
      
      config-client → MessageController.java


      
      @RestController
      public class MessageController {
      
          @Value("${example.message}")
          private String message;
      
          @GetMapping("/message")
          public String getMessage() {
              return message;
          }
      }

🧩 Example Log Output

Config Server:

Fetching config from server at : http://localhost:8888
Located environment: name=config-client, profiles=[development]


Config Client:

The following 1 profile is active: "development"
Fetched configuration successfully from Config Server

🧠 Key Learnings

Implemented centralized configuration using Spring Cloud Config

Connected Spring Boot applications with external GitHub-based config storage

Enabled runtime configuration refresh using Spring Boot Actuator

Learned how to handle multi-environment property management


⚠️ Troubleshooting

Issue	Cause	Solution
No spring.config.import property has been defined	Missing bootstrap configuration	Add spring.config.import=configserver:http://localhost:8888
Could not resolve placeholder	Property not found in GitHub repo	Check file names and active profile
Client not updating values	Refresh not triggered	Use /actuator/refresh endpoint
Connection refused: localhost:8888	Config Server not running	Start config server first

🌟 Future Enhancements

Integrate with Spring Cloud Bus for automatic config updates

Add Docker Compose for containerized setup

Implement JWT-based security for the Config Server

Extend support for multiple client microservices


🏁 Conclusion

This project provides a complete hands-on example of setting up Spring Cloud Config Server and Client from scratch using GitHub as a configuration store.
It’s a perfect reference for microservice-based architecture, where maintainability and scalability of configurations are critical.
