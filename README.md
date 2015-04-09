# dataextractor

Small application that has a UI for setting the url, email, password and uses the DataExtractorLib module to send data to the senta server.  

DataExtractor is a lightweight client for the data collection service. 
It includes 2 activities: 
LoginActivity: This activity gives the opportunity to the user to enter the senta server url, along with his credentials and start the Data extraction service.
MainActivity: The activity that runs after the login. Offers the functionality of exiting the app
The application starts the service when the phone boots using a BroadcastReceiver.
Moreover,  the broadcast receiver, receives the BOOT_COMPLETED intent when the phone completes booting and starts the DataExtractionService.