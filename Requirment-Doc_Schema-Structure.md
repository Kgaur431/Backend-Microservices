### Requirement Doc & Schema Structure
URL:- [read about from the link. ](https://github.com/scaleracademy/project-module-requirement-docs/tree/main/blogging-app)
``` 
    Relations:-
                1. from User to Articles.
                    - this is first relationship. that is One to Many. becoz "One User can write Many Articles. but every Article will have only one User(Author)".
                
                2. from User to Comments.   (Mandatory Relationship)
                    - let users to write comments
                    - when we do this then that two things happens.
                        1. A comment has relationship with the Article under which The Comment was written. 
                        2. The comment has also relationship with the User(Person) who is writing the comment.
                  Many to Many Self Referencing Relationship discussing below. 
                
                3. User follows another User. like Follower & Followee. 
                    - we will have follow table. where we will have the follower_id & followee_id. so we are creating Many to Many relationship from User to User itself.
                        means there would be Foreign key to User itself. Foreign key would be via Mapping Table. 
                        
                        means:- we allowed to Users to follow each other.
                                like:- If I follow to Shree then ==> One Relationship.
                                        but if Shree follow me then ==> another row in a table. 
                                        so we are creating Many to Many relationship from User to User itself.
                    - so this is Many to Many Relationship then we need to provide the Mapping Table. becoz User to User foreign key can't be possible directly.             
                
                4. from User to Articles.      (Not Mandatory Relationship)
                    - Likes are also Many to Many Relationship.
                    - One User can like Many Articles. & One Article can be liked by Many Users. so there is Many to Many Relationship between User & Articles.
                    - so we need to create the Likes table where we will map the User & Article.

                Follow Tables
                        id  Follower_id  Followee_id

                Mandatory Relationship:-
                                  means if there is an comment then Comment has to have Commenter_id & Article_id. (Comment has to have an article on which comment has written)
                                        We can't create Comment Object without having Commenter_id & Article_id.
                Not Mandatory Relationship:- 
                                  User to Likes 
                                  means, there could be an User whould not have liked any Article. & there could be an Article which would not have liked by any User.
                                    so its simple Many to Many Relationship.  
                                        means, so for Article to exits is not compulsory for Likes to exits.
``` 