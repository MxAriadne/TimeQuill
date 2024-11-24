# TimeQuill
 A time tracking and invoicing software made for CSCI 6180
 
## Prerequisites:
* jdk 23.0.1
* SpringBoot
* Bulma
* MySql Server
 
## Installation Instructions:
Step-by-step instructions to set up the environment.
- Install MariaDB or MySQL
- Download the schema.sql source file from the root of the repository.
- Import the file using SQL Workbench or create a database named timequill and run 'timequill < schema.sql' in SQL CLI
- Download and extract the latest release in the releases tab.
- Open the batch file (Based on whether you chose MySQL or MariaDB) and enter your database name, password, and port.
- Run the batch file.
 
## Execution Instructions:
Clearly state how to run the project. Include any scripts or commands needed to start the application.
 
## Input/Output Explanation:
Describe the expected inputs and outputs. Include example input files, formats, and output data.
#### Inputs:

* For login: username and password
  * Output: Login session
        
* For creating projects: Project fields
  * Output: Project items is loaded into the database and displayed
        
* For creating times: Time fields
  * Output: Times items is loaded into the database and displayed
        
* For admins creating users: Creating user fields
  * Output: New login credentials are logged into the authentication table
        
* For generating invoice pdfs:
  * Output: Download of a .pdf file with data of the invoice
    
## Features:

Encrypted authentication system

Creating and editing projects and working times

Generating PDFs of projects based on rates and hours

Being able to edit who is working on a project and their assignments
 
## Troubleshooting:
Common Errors will be in this section if any are found to be re-occuring.
 
## Acknowledgements: 
Credit to Slack Overflow for its usefulness in debugging and explaining code.

All code for this assignment was completed by the team members listed at the beginning of the document and entirely without the use of artificial intelligence tools such as ChatGPT, MS Copilot, other LLMs, etc.

The image/logo for this project was created using Dall-E
