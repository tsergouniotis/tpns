We assume that you are trying to deploy the application on a Linux environment or a VM with Linux installed

## PRERECQUISITES
Your Linux application must contain the following software: 
Docker (https://docs.docker.com/installation/)
Fig (http://www.fig.sh/install.html)

## LOCAL ENVIRONMENT
This application will use the ports 8080 and 5432 of your system so please make sure they are not bound to another applications. 

## DEPLOYMENT
Deployment is done in the following steps:
* Copy the fig.yml file to a directory of your choice. (You can even skip this step by doing everything from the current directory). The fig.yml can be found in the same path as the file you are reading.
* Navigate to the directory where you put the fig.yml
* Execute fig -up

## VALIDATION
You can validate that everything was deployed successfully by pointing your browser to 
http://localhost:8080/user-service/user/1
You should get a user back in JSON form.

### FAREWELL NOTES
* Even Bruce Willis can do this!
