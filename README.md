# observability-from-scratch
Showcase how to setup a Grafana stack with logging, metrics and traces. Includes a demo application

## Java services

### Building the application

You can build the application using Gradle:

```shell
$ ./gradlew build
```

This will build the Java application and a docker image. The docker image will be registered as `qaware/tle-fetcher:1.0.0` within your docker daemon:

```shell
$ docker images

REPOSITORY           TAG     IMAGE ID       CREATED         SIZE
qaware/tle-fetcher   1.0.0   55bd6d637c77   7 seconds ago   371MB
```