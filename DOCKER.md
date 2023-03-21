Images - что-то вроде iso file
Container - запущенная "виртуалка" на основе Image
Volume - файловое пространство, особенности работы чети : проброс портов и тд

$ docker --version

PostgreSQL
https://hub.docker.com/_/postgres

```bash
$ sudo docker run --name geocoder-postgres -e POSTGRES_PASSWORD=geocoder -d postgres:15
$ sudo docker image ls
$ sudo docker volume  ls
$ sudo docker container ls -a
$ sudo docker container stop geocoder-postgres
$ sudo docker container start geocoder-postgres
$ sudo docker container rm  geocoder-postgres
$ sudo docker exec -it geocoder-postgres /bin/bash -- Подключение к териминалке в docker
$ cat docker-compose.yml
$ sudo docker compose -f docker-compose.yml up -d  -- Запускает все, что в файле
$ sudo docker compose   down      --like docker rm, не удвляет данные , те volumes
$ sudo docker compose   down --volumes    -- удалит все и данные
$ sudo docker compose   stop --  как docke stop, не удаляет, а останавливает
$ sudo docker logs pgadmin

su - geocoder
psql -h localhost -U geocoder -- подключиться к postgres
```

```bash
$ ./gradlew bootJar
$  sudo docker build -t geocoder:latest .
$  sudo docker build --build-arg JAR_FILE="./build/libs/geocoder-0.0.1-SNAPSHOT.jar" -t geocoder:latest .
$ sudo docker compose up -d
```
