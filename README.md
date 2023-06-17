App  Demonstration

The following series of screenshots show how different app features.

•	The first run dialog

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/7eab3aea-8acf-4729-a9b4-28cd3521b660)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/203e5dcc-ee18-43b8-ad30-356f7090e53f)


 
Description of the above screenshots: the one on the left is for the dialog in waiting for the user input, and the one on the right is showing the autocomplete list when the user is typing.

 
•	The Actor information activity

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/196b1b9b-962d-44c9-8c2f-bcdc4a3cc47f)




Description of the above screenshots: the one on the left is the actor activity while web-scraping is ongoing asking the user to wait, and the one on the right is showing the retrieved actor’s biography and photo.

 
•	The navigation menu
 

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/0e274c51-f042-4be1-a19c-a30a5ad356cb)




•	The Filmography activity

 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/e4a30648-f336-48a1-9e25-4a7ee46c6512)
 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/d43e03eb-b511-41f8-bcbf-20279e6cede9)

	


Description of the above screenshots: the filmography activity before any film records are inserted. The screenshot on the left shows a Toast message guiding the user to the floating add button


 
•	The Add film activity

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/4f12c258-bb55-4ecb-83b2-1b15674601d1)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/08093c5f-0b86-4452-8363-a0ea6bfd8734)


The above screenshot on the left shows the Add a Film activity asking the user to input the film title, the year, the length, and the character name. Additionally, a spinner for the genre is available for the user as shown on the right.

 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/f7752e7b-e84e-49f1-836c-fcfcc29780b3)


The above screenshot shows another spinner for the user’s score out of 5 as well as a check box to save if the user has watched the film or not.

 
•	The Filmography activity after adding film records

 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/8c90fce6-1d2e-4bd8-89a2-3b75058040f7)

 
•	The popup menu for editing and deleting a film record

 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/bfe5fb16-1278-4656-9959-6d3aa47e71a5)


 
•	The Edit film activity

 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/da80b44b-65c2-4b3a-a4a0-1d5bb48ffb67)


 
•	The Delete film confirmation dialog

 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/7509c600-6646-4702-b950-bdb1293bea3f)


 
•	The updated list in the Filmography activity after a film is deleted or edited

 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/1b2fcdd4-5371-4b41-ae08-31acae937ddf)



 
•	The Search film activity
i.	Searching by title

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/82eb5683-1d8d-4e3f-9f4d-6e53c427a2e2)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/783050b3-7de4-4f6b-96d2-6c32f0a6d025)
 	 

	The search returns records with titles that partially match the query.



ii.	Searching by year

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/0f3073d0-fb9b-4790-9c13-daa490b8e2d0)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/446a9fae-f912-4f4a-ac43-e015308f03cf)



 
iii.	Searching by genre


![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/73e1d97a-1f58-4a54-a8c0-2c306ff430a7)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/c976c09c-4835-4ef5-88f8-48240d1c4578)


 
•	The Reset confirmation dialog

 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/51f976a9-e163-4a64-bb28-e5e589174870)


 
•	The Help activity

 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/1d913ac8-704b-4f65-99c2-5b92f0c6658b)

 
•	The About activity

 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/4a6dc67e-8a82-4bb6-8766-9d134bb4c9e7)

 
•	The database

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/30a376fe-85de-4c60-b754-ae7b4a7512f2)

 
The above screenshot shows the actor’s table data after web scraping in SQLite Studio.

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/ccc22400-cbd9-4c3c-be02-0a2a7c41a7ae)

 
The above screenshot shows the filmography table data entered by the user in SQLite Studio.

 
App testing for possible invalid user input 
•	The actor’s name input validation

 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/86f14099-42f1-4b3e-894d-5e0dc78f231d)
 ![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/67575af0-92d7-4a0c-9da5-787f785441a6)




 
•	The Add film input validation (and edit film input)
-	When the title missing:

![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/f4ce44f5-d0f8-4623-8d35-43f78b89c225)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/d12afd4c-04e8-4007-9a00-9e137f378a02)
 


 
-	When the same title already exists in the database

 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/d8a7b713-29c4-4666-99c3-76ddfa8e13bf)

-	When the year is not within an acceptable range

 	 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/587fbd93-556c-4e97-b598-e9661305911e)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/f2a5ef4a-ace2-4bd8-962b-a8304dcabd23)





 
•	When the user input includes single quotes

 	 
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/489a7484-5505-4f0c-940d-9fadff139544)
![image](https://github.com/fadihassoun/Film_DataBase/assets/97429326/e0feb446-9dbe-4c1a-b061-06b715b2a2ee)


 
