# Spigot Plugin

## Overview
This project is a Spigot plugin developed in Java. It serves as a basic template for creating custom plugins for Minecraft servers using the Spigot API.

## Project Structure
```
spigot-plugin
├── src
│   └── main
│       ├── java
│       │   └── me
│       │       └── dotu
│       │           └── MMO
│       │               └── Main.java
│       └── resources
│           └── plugin.yml
├── pom.xml
└── README.md
```

## Building the Plugin
To build the plugin, navigate to the root directory of the project in your terminal and run the following command:

```
mvn clean package
```

This command will:
- Compile the Java code.
- Package it into a JAR file.
- Place the resulting JAR file in the `target` directory.

## Usage
Once the build is complete, you can find the generated JAR file in the `target` directory. Place this JAR file into the `plugins` folder of your Spigot server to use the plugin.

## Requirements
- Java Development Kit (JDK)
- Apache Maven
- Spigot Server

## License
This project is licensed under the MIT License. See the LICENSE file for more details.