# observability-from-scratch
Showcase how to setup a Grafana stack with logging, metrics and traces. Includes a demo application

## Prerequisites

To build and run this application, you will need the following dependencies on your system:

| Name           | Version |
|----------------|---------|
| Docker         | *       |
| Docker-Compose | 1.13.0+ |
| Java           | 17      |

## Running all services

Build and run the applications with `docker-compose`:

```shell
$ ./gradlew build
$ docker-compose up
```

You can access the grafana-ui at http://localhost:3000/login.
The username is ```admin```, the password is ```password123```.

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

### The tle-fetcher application

#### Running the service

You can start the tle-fetcher application in quarkus-developter mode by 

```shell
$  ./gradlew :tle-fetcher:quarkusDev
```

or with docker compose 

```shell
$ docker-compose up -d tle-fetcher
```

#### API

The service runs on port ```8080```. You can view the tle-fetcher service api with swagger: http://localhost:8080/q/swagger-ui/

#### Configuration

All relevant configuration can be found in `tle-fetcher/src/main/resources/application.properties`.

### The sky-map application

#### Running the service

You can start the tle-fetcher application in quarkus-developter mode by

```shell
$  ./gradlew :sky-map:quarkusDev
```

or with docker compose 

```shell
$ docker-compose up -d sky-map
```

This starts the sky-map and the tle-fetcher services

#### API

The service runs on port ```8088```. You can view the tle-fetcher service api with swagger: http://localhost:8088/q/swagger-ui/

#### Configuration

All relevant configuration can be found in `sky-map/src/main/resources/application.properties`.


## The Grafana stack

The Grafana stack is configured in the directories `grafana`, `loki`, `promtail`, `prometheus` and `tempo`.

**NOTE**: It is important to change the permissions on the configuration files for the Grafana stack. Run the following command:

```shell
$ chmod -R o+rX grafana loki prometheus promtail tempo
```

### Grafana

Grafana is the visualization engine of the Grafana stack.

The main configuration is done in `grafana.ini`.

You can provision several other things automatically, like dashboards and datasources. All of this is stored in `grafana/provisioning` and deployed automatically on startup.

### Tempo

Tempo stores APM and tracing data from services. It can also be connected to Grafana.
