App Mechanics Demonstration
The following series of screenshots show how different app features.
•	The first run dialog
 	

 
Description of the above screenshots: the one on the left is for the dialog in waiting for the user input, and the one on the right is showing the autocomplete list when the user is typing.

 
•	The Actor information activity





Description of the above screenshots: the one on the left is the actor activity while web-scraping is ongoing asking the user to wait, and the one on the right is showing the retrieved actor’s biography and photo.

 
•	The navigation menu
 


 
•	The Filmography activity

 	


Description of the above screenshots: the filmography activity before any film records are inserted. The screenshot on the left shows a Toast message guiding the user to the floating add button


 
•	The Add film activity

 	 


The above screenshot on the left shows the Add a Film activity asking the user to input the film title, the year, the length, and the character name. Additionally, a spinner for the genre is available for the user as shown on the right.
 

The above screenshot shows another spinner for the user’s score out of 5 as well as a check box to save if the user has watched the film or not.

 
•	The Filmography activity after adding film records

 

 
•	The popup menu for editing and deleting a film record

 

 
•	The Edit film activity

 

 
•	The Delete film confirmation dialog

 


 
•	The updated list in the Filmography activity after a film is deleted or edited

 


 
•	The Search film activity
i.	Searching by title

 	 

	The search returns records with titles that partially match the query.





ii.	Searching by year

 	 


 
iii.	Searching by genre

 	 

 
•	The Reset confirmation dialog

 


 
•	The Help activity

 


 
•	The About activity

 


 
•	The database

 
The above screenshot shows the actor’s table data after web scraping in SQLite Studio.


 
The above screenshot shows the filmography table data entered by the user in SQLite Studio.

 
App testing for possible invalid user input 
•	The actor’s name input validation

 	 



 
•	The Add film input validation (and edit film input)
-	When the title missing:

 	 


 
-	When the same title already exists in the database

 


 
-	When the year is not within an acceptable range

 	 





 
•	When the user input includes single quotes

 	 


 
