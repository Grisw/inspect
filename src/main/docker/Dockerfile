FROM frolvlad/alpine-oraclejdk8:slim

# Copy app.jar
COPY ./inspect.jar /app.jar

# Expose port 8080
EXPOSE 8080

# Run app.jar
ENTRYPOINT ["java", "-jar", "/app.jar" ]