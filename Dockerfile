FROM openjdk:8-jre-alpine

# Copy your fat jar to the container
COPY 'build/libs/shareNotesApp-1.0.0-SNAPSHOT.jar' /usr/app/

# Launch the App
RUN chmod +x /usr/app/shareNotesApp-1.0.0-SNAPSHOT.jar
CMD ["java", "-Xmx512m", "-jar", "./usr/app/shareNotesApp-1.0.0-SNAPSHOT.jar"]