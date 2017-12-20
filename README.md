# Alex Thomas - Card Validation Service
#### Submission Endpoints
The application is available [here](http://team-elas-1vkcb06lmpx7j.tpwsd6hcdr.eu-west-1.elasticbeanstalk.com/) . 
Note that the previous link is pointing to an AWS Elastic Load Balancer which does not support persisted connections, so
to access the MQTT broker you need to directly access the instance running the app, IP address is [34.242.141.75](http://34.242.141.75)

##### Mosquitto_sub command to listen to events
> mosquitto_sub -v -h 34.242.141.75 -p 1883 -t '#'


# About this repository
This repository made as a project for MSc in Software Development for the module Software Architecture. 
We were asked to create a Proof of Concept application which could detect cloned access-cards for a 
access control systems for office premises.

#### Requirements
The requirements for the system were that when an access card is used, our system would receive the panel ID, card ID 
and if the access was allowed to the employee. Additionally, a panel location service was provided for 
us that we could integrate with, which given a panel ID, would give us the location of that panel.

A further requirement was to provide notifications to admins when a potentially cloned card was used in
the system.

#### Solution
##### Cloned card detection
To detect the use of potentially cloned cards, the system calls the external panel location micro-service provided
to get the location of the panel that was used for the swipe and stores that event with the location information in the
database. It then checks the database for the most recent swipe event for the same card ID that was used. If there was a 
previous event for that card id, both locations are compared with the Google Maps Matrix API to see what is the minimum 
amount of time a human could possible travel to reach the 2 destinations. If the time returned is less than the time between
the 2 swipe events, that system return the specified JSON structure saying that the swipe was not valid.

##### Notifications
To notify admins when a potentially cloned card is used, a message is published to an MQTT broker, admins can then 
subscribe to that broker and act upon events that are published to the broker by the system when it detects a potentially
cloned card.


# Implementation
## Software Implementation

#### Business Logic
For the business logic, 4 core service interfaces handle the different aspects of the application
###### ICardReaderLocationService
  * This interface is responsible to abstract away any implementation of a concrete class which handles
  getting a Location from a panel ID
  * This interface is currently implemented by the concrete class CardReaderLocationService which handles
  implementing a network call to the external micro-service to get a Location for a given panel ID.
###### IEventValidationService 
  * This interface abstracts away the implementation of the core business logic of this service.
  * This interface is currently implemented by the concrete class EventValidationService which handles the core
  logic of calling other interfaces, storing events in the database and getting an event validation response for the 
  rest controllers
###### ILocationAnalyser
  * This interface abstracts away the implementation of a location analyser, which given 2 Events, returns a 
  LocationAnalysis which specifies if the card used in those events is potentially cloned.
  * This interface is currently implemented by the concrete class GoogleMapsLocationAnalyser. Which implemented the
  Google Maps Matrix API functionality to see if the Events given are valid or not.
###### INotificationService
  * This interface absracts away the implementation notifications in the system
  * This interface is currently implemented by the concrete class MqttNotificationService. Which implemented notifications
  in MQTT

#### Database access
Database access is abstracted away using JPA and a Spring repository for the Events created in the application

#### Caching
To speed up the application, caching is used for both the external panel location service and the google maps API.
As given an input of a panel ID or 2 locations, results from those services are almost never going to change so once
a result is got from those services it is cached for a short period of time.

#### Domain Models
Class are created to handle the structure of the requests, responses and models used in the system.

models
* Coordinates
* Event
* EventValidationRequest
* EventValidationResponse
* Location
* LocationAnalysisResult


#### Controllers
There are 2 controllers in the system. 

The PanelEventController handles the restful interface for external systems to communicate
with this system

The HomeController handles redirecting requests to the root directory to the swagger UI html page

#### Tests
Tests are done on the most crucial aspects of the software. The test classes are CardReaderLocationService which does external calls to the panel location micro-service
and validates the response is in the expected format and GoogleMapsLocationAnalyserTest does tests to make sure given 2 different locations
that are valid the class can detect that they are valid, and also a test with 2 locations that should not be valid to see if the class
is detecting cloned cards

## Infrastructure Implementation
#### CloudFormation ( Infrastructure as Code )
The infrastructure is hosted on AWS and makes use to CloudFormation to automate the provisioning of infrastructure.
All the CloudFormation templates are in the directory cloudformation/ . In there, there is a master-template.yaml file which 
is the master stack that calls multiple smaller stacks which automates the provisioning and deployment of services needed to run the system.
A deploy.example.sh script which can be executed on your account with placeholders for variables to fill in for anyone who wants to run the system 
on their AWS account in 1 click.

Note that to run the CloudFormation stack you will need to create a keypair in your account to have SSH access to the web
servers. And also you will need to clone this github repo, and provide a GitHub OAuth token to AWS CodePipeline has permissions
to access your repository

#### VPC ( Virtual Private Cloud and Subnets)
The Virtual Private Cloud is created by the master stack by using the vpc-template.yaml stack.
It creates a private network 10.0.0.0/16 and creates 3 public subnets for the web servers and load balancers to scale in,
and 3 private subnets for the database instance and cache cluster instances

#### ElasticBeanstalk (Configuration, Auto scaling, Load Balancing)
The ElasticBeanstalk Environment is created using the eb-template.yaml stack. It creates a load balanced Java environment with
auto scaling. It takes the VPC as an input to know where to create the environment, the RDS DBName, username, password and URL
and sets them as application.properties so the application knows how to access the database and does the same for the cache
cluster

Additionally, there is a .ebextensions configuration file which sets up the mosquitto MQTT on the web servers which the application
uses

#### Relational Database Service  ( MySQL Database )
The rds-template.yaml file creates the RDS instance, by default it is a 20GB MySQL micro instance. It takes the VPC ID as an 
 input and creates placement groups in the private subnets of the VPC so the database is not accessible through the public
 internet. Additionally the security groups only allow the web instances to access the database 

#### ElastiCache  ( MemcacheD Cluster)
The elasticache-template.yaml file creates the elasticache cluster. Similarly to the RDS stack it takes the VPC ID as an input
and creates the cluster in the private subnets and creates the security groups to only allow the web instances access it

#### Code Pipeline, CodeBuild ( CI/CD )
The pipeline-template.yaml file creates a CI/CD pipeline using AWS CodePipeline and AWS CodeBuild. It takes a github OAuth
token to give it access to the github profile hosting the repository it polls its source code from. When a new commit is detected
from the GitHub Repo it pulls the code and sends it to a CodeBuild Project. CodeBuild then builds the source code using the
buildspec.yml file in the repo. Runs the Test and if the tests pass, it returns the artifact to CodePipeline which then deploys the
built & tested artifact to ElasticBeanstalk running the AutoScaling environment