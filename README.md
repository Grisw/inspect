# Inspect service

This is a spring boot application service for the `Inspect` android app.

## Requirements

* Maven
* Docker
* Docker Compose

## How to build the image (OPTIONAL)

To build the `inspect` image yourself, you need to follow the following steps:

* `cd` to the root of this repo.
* Run `mvn clean package` to build a jar.
* Copy the built jar file in `/target` to `src/main/docker` and rename it to `inspect.jar`
* `cd` to `src/main/docker`
* Run `docker build --tag grisw/inspect .`

## How to run this application service.
   
To run this service, you need to follow the following steps:

* `cd` to `src/main/docker`
* `docker-compose up`
* visit `http://{{YOUR DOCKER MACHINE IP}}/`