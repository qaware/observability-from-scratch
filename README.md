# observability-from-scratch
Showcase how to setup a Grafana stack with logging, metrics and traces. Includes a demo application

## Prerequisites

To build and run this application, you will need the following dependencies on your system:

| Name           | Version |
|----------------|---------|
| Docker         | *       |
| Docker-Compose | 1.13.0+ |
| Java           | 17      |


## Java services

The TLE fetcher retrieves TLE (two-line element set) data for calculating satellites trajectories from a NASA related API.
You can learn more information on the TLE format [here](https://en.wikipedia.org/wiki/Two-line_element_set), on orbital
mechanics [here](https://en.wikipedia.org/wiki/Orbital_mechanics) and while playing some rounds of Kerbal Space Program.

This project uses Quarkus. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

### Building the applications

You can build the application using Gradle:

```shell
$ ./gradlew build
```

This will build the Java application and a docker image. The docker image will be registered as `qaware/tle-fetcher:1.0.0` within your docker daemon:

```shell
$ docker images

REPOSITORY           TAG     IMAGE ID       CREATED         SIZE
qaware/tle-fetcher   1.0.0   55bd6d637c77   12 seconds ago  371MB
qaware/sky-map       1.0.0   76c9807e0e3c   19 seconds ago  395MB
```

### Testing the tle-fetcher application

#### Running the service

You can start the tle-fetcher application in quarkus-developter mode by 

```shell
$  ./gradlew :tle-fetcher:quarkusDev
```

To start onle the tle-fetcher with docker compose you can use

```shell
$ docker-compose up -d tle-fetcher
```

#### API

The service runs on port ```8080```. You can view the tle-fetcher service api with swagger: http://localhost:8080/q/swagger-ui/

#### Configuration

All relevant configuration can be found in `tle-fetcher/src/main/resources/application.properties`.

### Testing the sky-map application

#### Running the service

You can start the tle-fetcher application in quarkus-developter mode by

```shell
$  ./gradlew :sky-map:quarkusDev
```

To start onle the tle-fetcher with docker compose you can use

```shell
$ docker-compose up -d sky-map
```

This starts the sky-map and the tle-fetcher services

#### API

The service runs on port ```8088```. You can view the tle-fetcher service api with swagger: http://localhost:8088/q/swagger-ui/

#### Configuration

All relevant configuration can be found in `sky-map/src/main/resources/application.properties`.