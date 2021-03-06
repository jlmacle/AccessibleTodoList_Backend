echo "* Making sure that no PostgreSQL Ubuntu service is running."
sudo service postgresql stop
echo " Waiting for a potential PostgreSQL Ubuntu service to stop."
sleep 60

#-------------------------------------------------------------------------------------------------------
# Checking the code quality
#-------------------------------------------------------------------------------------------------------
echo "* Starting the SonarQube analysis process."
echo "****** Starting the SonarQube server."
gnome-terminal -- bash -c 'sonar.sh start; sleep 90'
echo "****** Waiting for the SonarQube server to start"
sleep 90

echo "****** Starting the code quality analysis."
cd ..
gnome-terminal -- bash -c 'mvn sonar:sonar -Dsonar.projectKey=accessible-todo-list_back-end:jl.forthem \
-Dsonar.host.url=http://localhost:9000 -Dsonar.login=6fb38c8274cb8efccba8778aef56d226e7550659; sleep 100'

echo "****** Waiting for the analysis to be done."
sleep 100 

echo "****** Starting a browser to check the result of the analysis."
chromium-browser http://localhost:9000 &
sleep 100

echo "* Docker build process."
echo "**** Login to Docker if necessary."
sudo docker login

echo "**** Building the jar file with."
sudo mvn spring-boot:build-image

echo "**** Re-taging the Docker image"
sudo docker tag accessible-todo-list_back-end:0.0.1-SNAPSHOT atl-back-end:v0.9

#--------------------------------------------------------------------------------------------------------
# Testing the new image built
#--------------------------------------------------------------------------------------------------------

echo "* Deploying with Docker stack."
sudo docker stack deploy -c z_docker_compose/docker-compose-stack-localImages.yml stack
sleep 60
echo "Waiting for the stack to be built."

echo "* Starting Chromium to check on the result."
chromium-browser http://127.0.0.1:8080 &

echo "* Clearing the code from the surefire-reports folder."
#-------------------------------------------------------------------------------------------------------
# Cleaning the code 
#-------------------------------------------------------------------------------------------------------
echo "mvn clean"
echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "The dump file was in the surefire-reports folder. "
echo "mvn clean suppresses the surefire reports. "
mvn clean



