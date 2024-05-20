# Specifies the base image
FROM maven:3.9.6-eclipse-temurin-21
# Copy the source code to the image
COPY . /bike-lending-app
# Execute the maven build
RUN cd bike-lending-app && mvn compile datanucleus:enhance
# change to this directory
WORKDIR bike-lending-app
# Specify which command will be launched when starting the container
CMD ["mvn", "jetty:run"]