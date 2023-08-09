
#### Continuation 

```

        How does we take our Java Project into docker container ?
            1.  build our project into jar file.
                    1.1 we can build via terminal by using ./gradlew build or via IDE.      (if we using command line then it can build in any os)
            2.  we get the jar files in build/libs folder.
                    2.1 we can open this jar file with 7zip || winrar. just rename the jar file to zip file. & open it.
                    2.2 jar file means Java Archive file. it is basically zip file. but it does some extra things.
                            2.2.1   it create Manifest file & in it the cryptographic signature of everything if we signed it & some extra info like which class to load & all.  (META-INF/MANIFEST.MF)
                            2.2.2   it contains the .yaml file. || properties file that we used, that go inside the zip file.
                            2.2.3   it contains the .class file(Compiled java byte code). not the .java file(Source Code).
                         two files will be there in jar file
                            like:-
                                    app-0.0.21-SNAPSHOT.jar         --> it contain the spring application & all the dependencies everyting in it.  (new) 
                                    app-0.0.21-SNAPSHOT.plain.jar   --> it contain the source code, it is used in old java app server where the servlets is there & all.  (old)
                            How we can compiled the jar file using terminal ?
                                1.  go to the location where jar file is there.
                                2.  open the terminal there.
                                3.  run the command with jar file (not plain.jar file)
                                        java -jar app-0.0.21-SNAPSHOT.jar
                                        it will run the application.
                                    so this jar file can be runned by anybody who has java installed. 
                                    " if we share this file with anybody then they can run this file. but they need to installe the java with the same version that we used to build this jar file.
                                    so the step after this is how do i put the jar & all things into docker container so that everything that jar file need to run inlcuding java itself is also there.
                                        therefore wherever it will run only docker is require nothing else."

                                    benefits of docker:-
                                                in the world where we are living here, so many technologies are there.
                                                so with the help of docker we can ship java app, node js app ... etc. 
                                                so the people does not need to install anything specific. (even JRE they does not need to install)
                                                they just need the docker installed in their system. they can run the app. 
                    (how to dockerise the app)
            3.  we have to create docker file. 
                    3.1 we need to write FROM (means From where, like this from tell that which days that docker image am i building on top of) like scratch if we build fresh docker image.
                            (from scratch means there is an empty docker container so we will based on the image that have already contains java & all the dependencies that we need to run our app.)
                            3.1.1   search on google to "docker version (which we using) java images" link:- https://hub.docker.com/_/openjdk/tags?page=1&name=20-slim
                                    so we will use the openjdk:11-jre-slim image.   (slim becoz it is small in size & not contains the UI related things.)
                                    so we will write in docker file like this:-
                                        FROM openjdk:20-slim
                    3.2 we need to write COPY (means copy the jar file from our local system into the docker container(in root folder of docker container only))
                    3.3 we need to write CMD (it will tell that what happens when the docker container run) 
                            3.3.1   CMD ["java", "-jar", "app-0.0.21-SNAPSHOT.jar"]
                                    so it will run the jar file when the docker container run.
                            Why we write like this ?
                                    the way of writing CMD is like this:-
                                        CMD ["executable", "param1", "param2"]
                                            we can't write as string directly it does not work directly. becouse it tries to run the string as a command/program.
                                            so we will write in the array formate.
            4.  now we build the project becoz we added the docker file.  (docker app should be running)
                    (after we build, what docker will do ?)
                        -   docker will download the image using the FROM command.
                        -   then it will copy the jar file into the docker container.
                        -   then it will build the image & inside the image it will write the CMD command.  
                        -   whenever we run the docker container, docker know that this is command to run the jar file.  
            5.  we do "docker build -t kgour624/backend-microservice ." command to build the docker image.
                    -   -t means tag. it is used to give the name to the docker image.
                    -   kgour624 is the name of the docker image.
                    -   backend-microservice is the tag name.
                    -   . means current directory. means inside the current dir whatever docker file is there it will build the image from that docker file.
                    once we published the image then it will publish at this docker.io/library/kgour624:backend-microservice
            6.  now we can run the docker container from the docker image.
                        if we run the docker run kgour624/backend-microservice even image is running but we find nothing in the browser.
                            becoz "Docker runs inside it own virtualised network which is not same as my localhost network
                            so we have to Bridge the network so I will have to make the docker container port available on my main computer port
                            becoz docker has containerised it, it has network level isolation." network isolation means if i want that certain ports should be accessible then i have to expose that port.
                    -   docker run -p 8080:8080 kgour624/backend-microservice   (now we are exposing the port 8080 of docker container to the port 8080 of our system.)
                            -p means port. it will map the port 8080 of docker container to the port 8080 of our system.
                            so we can access the app from the browser by typing localhost:8080
            7.  now we run the docker container from the docker image.
                    -   docker run -p 8282:8282 kgour624/backend-microservice           terminal 1.
                    -   now we can access the app from the browser by typing localhost:8282
                    
                        docker run -p 8181:8282 kgour624/backend-microservice           terminal 2.
                         both are running, how ?
                            "While iniside the docker container my app is running on port no. 8282 but i have bound port 8282 of the docker to port 8181 of my computer."
                                so if we bound the port 8282 of the docker to port 8383 of my computer then we can access the app from the browser by typing localhost:8383
                                so two seprate instances are running from two terminals. (now we can access the app from the docker container) 
                                
                                therefore we can run same server with multiple instances on different ports.
                                internally both app run on port 8282, one of them i have bound to port 8181 & another one i have bound to port 8383.
                         here docker does the port mapping to us.
            8.  now we push the docker image to the docker hub.
                    -   docker push kgour624/backend-microservice
                    -   it will push the docker image to the docker hub.
                    -   (we can see the image on docker hub by searching the name of the image.) 
                                    ||
                    -   we can push via docker app also.
                    -   in the docker app we can see the image that we have build.
                    -   by clicking on the image we can see the tag of the image.
                    -   by clicking on the tag we can see the push to hub button.
                    -   by clicking on the push to hub button we can push the image to the docker hub.       
                (we have build the image for linux/amd64 architecture. but we can build in other architecture also.)
            9.  docker build --platform linux/arm64 -t kgour624/backend-microservice .
                    -   it will build the image for linux/arm64 architecture.
                    -   we can see the image in the docker app.
                    -   we can push the image to the docker hub.
                    -   we can pull the image from the docker hub.
                    -   we can run the image from the docker hub.
                    -   we can run the image from the docker app.
                    -   we can run the image from the terminal.
                  (we can define the version of the image also.)
            10. docker build --platform linux/amd64 -t kgour624/backend-microservice:0.0.1.
                  (now we deploy the docker image on actual cloud)
            11. 
                    11.1    There are bunch of ways to deployed it like EC2 (setting the usage storage) that we not do. 
                    11.2    we are using LightSail (it is a service of AWS) (it is a virtual machine) (it is not dedicated server)
                            11.2.1  we have to define the location like where to create it 
                            11.2.2  we select the ubuntu as os 
                            11.2.3  Enable the SSH Key so that we can login to the server. 
                            11.2.4  we have to set the size of the server. (might be paid) for java project at least 1gb should be required.
                            11.2.5  we have to give the name to the instance.
                    11.3    we have to login to it. (via terminal)
                            11.3.1  In termainal "ssh ipaddress of instances -l username"  
                            11.3.2  we can check the details of the instance by installing "sudo apt install htop" & then execute "htop" command.  
                            11.3.3  then we run our project we need to install docker. "sudo apt install docker.io" --> "sudo apt update" --> 
                                        "sudo docker ps" (to check the docker image is running or not)
                                        --> "sudo docker run -p 8080:8080 kgour624/backend-microservice:0.0.1" (to run the docker image)   (if its unable to find locally then it will download from docker hub)    
                                    watch video 13 from 1:45:00 to 1:59:59 (not done this part) 
         
            
                         

Command for Docker (in terminal):-
            1. docker image ls  
                    it will show all the image that we have in our system.
            2. docker container ls
                    it will show all the container that we have in our system.
            3. docker build -t kgour624:backend-microservice .
                    it will build the docker image from the docker file.
            4. docker run -p 8080:8080 kgour624:backend-microservice
                    it will run the docker container from the docker image.
                    -p means port. it will map the port 8080 of docker container to the port 8080 of our system.
            5.  docker push kgour624:backend-microservice
                    it will push the docker image to the docker hub.
                    (we can see the image on docker hub by searching the name of the image.)
            6.  docker pull kgour624:backend-microservice
                    it will pull the docker image from the docker hub.
            7.  docker image rm kgour624:backend-microservice
                    it will remove the docker image from our system.
       
In docker app push is fast into hub becoz docker is not push it from the FROM part of the Dockerfile becoz the openjdk layer is already exist 
& we are building an image on top of the existing image so when we push our image then docke does not need to push the openjdk layer again.

Note:-  it is when we build multiple image for our same app. 

If we use Oracle JDK (paid) that we can redistribute it. only JVM & JRE we can put into the docker image.



``` 
    
    
     
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
1.  Kubernates' purpose is to orchestrate not the containerization.
    
    
    


[Guide 1](https://spring.io/guides/gs/securing-web/):- to implement the JWT token in spring boot application.
[Guide 2](https://baeldung.com/get-user-in-spring-security):- to implement the JWT token in spring boot application.    both are same. 