  # Balloon 
  
  The project (Balloon), is a back-end application that is build for local non-government organizations
  to collect survey. It accepts the data collected by the mobile client validate and
  persist it.
  
  The application gives the administrators of the system the ability to add the questions
  and their possible response they want and it will automatically be reflected on the
   mobile clients that are use in collecting the survey data from participants.
   It also provides data to the web dashboard for statistical analysis (graphs & charts).
  
  #### Prerequisites:
  To run this project you need to have the following installed.
  * Java SE 1.8 or higher
  * SBT (Scala Build Tool)
  
  #### This project is setup with:
  * [Play framework - Scala](https://www.playframework.com/)
  * [MySQL](https://dev.mysql.com/) 
  * [Quill](https://dev.mysql.com/)
  

  ### How to run:
  After cloning the project, change into the parent directory and then run: 
  
    sbt run
  
  It will download all the dependencies and start the application on [http://localhost:9000](http://localhost:9000).
