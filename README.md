# Bond Cinema API

A REST Web Service for booking cinema tickets.

## Technologies
- JDK 17

APIs used:
- HttpServer
- Reflection
- Regex

## Project setup
Build the project
```
./gradlew build
```

## Test the project
```
./gradlew test
```

## Run the project
```
./gradlew run
```

## Deploy the project

### 1) Gradle Distribution (zip/tar)
Creates a self-contained bundle with start scripts and all dependencies.

Build the distribution:
```
./gradlew installDist
```

Artifacts will be in `build/install/booking-api/`:
- `bin/booking-api` (Unix) or `bin/booking-api.bat` (Windows)
- `lib/` with all jars

Run the packaged app:
```
./build/install/booking-api/bin/booking-api
```

Copy the `build/install/booking-api` folder to your server and run the same script there.

### 2) Docker

Build the image:
```
docker build -t bond-cinema-api:latest .
```

Run the container:
```
docker run --rm -p 8080:8080 bond-cinema-api:latest
```

The server will be available at http://localhost:8080


### 3) Docker Compose (local development)

This repo includes a `docker-compose.yml` with two services:
- `app`: builds and runs the packaged image (similar to `docker build` + `docker run`).
- `app-dev`: mounts the source code and runs `./gradlew run` inside a JDK container.

Start the production-like service:
```
docker compose up app --build
```

Start the dev service (source mounted):
```
docker compose up app-dev
```

Stop and clean up containers:
```
docker compose down
```

Notes:
- Both services expose port `8080`; run one at a time or change the host port mapping.
- `app-dev` will re-run on container restart, but Gradle run does not hot-reload by default.
