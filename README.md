# Infoodmatik ein Projekt im Rahmen des Moduls SWEIII

## Table of contents
* [Requirements](#requirements)
* [Installation](#installation)
* [Userguide](#user_guide)





## Requirements
- Apache maven version 3.6.3
- Wildfly 21.0.0.Final unser Anwendungsserver
- JBoss Tools
- JDK 15.0.2

## Installation
- All specified software packages from the requirements must be installed
- Create a deployment in the IDE via "Run Configurations" Goal:"package wildfly:deploy"


## User guide
- open the index.xhtml

#### Admin :
- If you want to register as an admin, you have to enter the admin password "admin" when registering
- As an admin you have the possibility to edit and delete all recipes

#### Signed in user:
- In order to create a recipe, you must first be logged in as a user
- You can only rate each recipe once
- Edit and delete your own recipes

#### Not logged in user:
- View, filter and search for recipes
