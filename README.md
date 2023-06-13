# E mart 

## Services and ports

| SL  |  Service Name   | Port Number |URL|
|:---:|:---------------:|:-----------:|:---:|
|  1  | Gateway Service |  9080       | http://localhost:8761/ |
|  2  | Discovery Service| 8761| http://localhost:8761/ |
|  3  | Inventory service| 8081| http://localhost:8081/ |
|  4  | Order service | 8082| http://localhost:8082/ |
|  5  | Product Service| 8083| http://localhost:8083/ |



## Run application
Install docker on your local machine. 

[Install docker on Windows OS](https://docs.docker.com/desktop/install/windows-install/)
<br>
[Install docker on Mac](https://docs.docker.com/desktop/mac/permission-requirements/)
<br>
[Install docker on Linux OS](https://docs.docker.com/desktop/install/linux-install/)

Build and push to docker hub`mvn compile jib:build -Djib.from.auth.username=user -Djib.from.auth.password=pass -Djib.to.auth.username=user -Djib.to.auth.password=pass`
Go to the project directory by command line and run the `docker-compose` file using `docker-compose up`
Or if your IDE has docker support then run the docker compose file. Other dependency will be downloaded automatically from the dockerhub
