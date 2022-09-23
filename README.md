# EventScope 

## EventScope is simple and open-source everything-events platform. 

* Send events to EventScope from any source like backend services, app, web, etc.
* Perform realtime aggregations on events. 
* Events can be any json.
* Self-host.

## Running the application locally

Before running the application, make sure you setup dependencies using the docker-compose in `/eventscope/docker/clickhouse-kafka`.

Run it locally using the following steps:
* Navigate to `/eventscope/docker/clickhouse-kafka` and run `docker network create clickhouse-net` followed by `docker-compose up -d`.
* Run the following command:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  I haven't been able to get the test dependencies up as containers. Clickhouse won't connect to zookeeper test container. Hence I'm currently using the same method as above to setup the environment and run tests.

