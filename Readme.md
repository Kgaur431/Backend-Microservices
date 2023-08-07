
``` 
    if we hit the localhost:4343/about then it should show the about page but it is showing the default login page
        QUES:-  Why does it do that ?
        Ans :-  because spring security by default it makes every page secured of my app. 
             
        QUES:-  Why we need that web security configure adapter ?
        Ans :-  Because if Spring Security is enabled then it makes every page secured by default & we might don't want that 
                so we need to configure it.
    
    for small project or personal project we can use BcryptPasswordEncoder for implements the 
    JWT token. but for large project we need to use the JKS (Java Key Store) for implements the JWT token.
        jsk is a file which contains the public key & private key.
        we can generate the jsk file by using the keytool command.
        keytool,validity 3650
        
        
    Salting
        Salting is a technique in which we add some random string to the password & then we encode it.
        so that if someone get the password then he/she can't decode it because he/she don't know the salt.
        so we need to add the salt to the password before encoding it.
        
        It is used for preventing from Rainbow Table.  
        
        What is Rainbow Table ?
            Example:-
                    Pawword is:-    123456  // this password is used often by the users.
                    Hashed Password is:-    123456 ->  e10adc3949ba59
                    suppose some how data breaches happend & hacker get the hashed password.
                      but hacker can't calculate the original password from the hashed password.
                      
                      What he can do is ?
                        assume this hashed has done by some hashing function like MD5. then "he can run top 10000 most common password & 
                            see what their hashes are. & he can make hash map of it for a quick reference."
                            so whenever he see the hash like e10adc... then he can easily find the password is 123456.
                            
            Salting is used for preventing from this type of attack.
                    What it does ?
                        everytime when user create the password, instead of directly creating the hash of the password. he generate the random string
                            & then he append that random string to the password & then he create the hash of it.
                            like:-
                                    123456 ->  123456.faswfsd -> e10adc3949ba59
                            & while storing the password in the database he store the salt & the hashed password.
                            like:-
                                    123456 ->  123456.faswfsd -> e10adc3949ba59.faswfsd
                            so looking at e10adc3949ba59.faswfsd  it is not possible to get that the password is 123456.     
                                because this is not normal hash function here we add some salt to the password & then we create the hash of it.
                     
                    How des it verifyable ?
                        because we are storing the salt when we store the hashed password in the database. so we know that the last few digit is the salt.
                        so when we need to verify the password then we can take the salt from the database & then we can append it to the password & then we can create the hash of it.
                    
                    so Salting is making the hash function more secure.   
                    Bcrypt is the doing both hashing & salting.               
                            
   Session Management:-
                   Example:-
                          - there is an server & there are multiple clients from where multiple requests are coming 
                          - the server have to authenticate the user becoz assume there is an another client 
                                like I am accessing facebook from mobile(A) & from laptop(B). 
                                Now the request 1, 2, 3 going to server from A (A knows that it has send the request one after another 1 2 3) 
                                but at the server there has some code which handle the request (controller) so all requests are hitting the server
                                   but at server when the request 1, 2, 3 comes then server execute the code for every request means every time its an fresh 
                                   execution of the code. so server don't know that weathere it is last request or new request.
                                by seeing the ip address & another details it can identify that requests are coming from the client. 
                                but who has send these requests ?
                                        "IT CAN HAPPEN THAT THE REQUEST 1,2 ARE AUTHENTICATED AS CLIENT A & REQUEST 3 IS AUTHENTICATED AS CLIENT B, REQUEST 4 IS AUTHENTICATED AS CLIENT A"
                                        so server need to know who is sending the request  
                                        That becomes conecpt of session comes into the picture. 
                   Session:-
                            like the particular user has logged in & the session has started, while the session they are the person who is sending the request.       
                        "It means, basically when the request has come in, THERE IS AN LOGIC TO IDENTIFY THE "WHO"."
                            THE WHO CAN BE IDENTIFIED BY THE HEADER, BY READING SOME BODY, BY SSL CERTIFICATE. etc.
                       
                           "So, session is basically the identification of the who is sending the request."
                        as server when it is indentifying the who, then ans should be the one answer, it can't have multiple answers"
                            like that particular request belongs to two users, it can't happen.     
                            
                   "THIS REQUEST BELONGS TO A SESSION WHICH HAS BEEN INITIATED BY A PARTICULAR USER." THAT ANS SHOULD BE ONE. 
                   
                   Imp;-    (creating authentication filter)
                        "Authentication does not happen for human users, The request could be authenticated on behalf of non human users as well."
                         like:-
                                users create twitter bots, twitter bots uses the api to perform certain tasks.
                                  these bots making requests continuously to the server.
                                  that request sent to the server that request is not sending by the human user
                                  but it should not authenticate the request as the human user. request should be authenticated as the bot. 
                                     so for the server Bot is also a user, human being is also a user
                         that basically called as authentication principle 
                            "WHO IS THE PRINCIPLE & WHO IS THE AUTHENTICATOR" 
                             the principle could be another client, another serveice (microservice), another bot, another human being etc.
                             so who has the authority 
                                "the authority might not necessarly be an actual human entity but session management basically deals with the fact that 
                                the request which is comes in, must belonging to some authentication principle".
                                that means that we have session management mechanism in place. but we will need to add piece of logic that will take care of that fact(
                                    whom does this request belong to ?)
                                that's why we are adding the authentication filter.
                                    "IT WILL DETECT IN THE REQUEST, IF CLIENT A USER OR NOT"
   
   Extends V/s Implements :-
                    Extends is used for extending the class.
                        which means that the class which is extending the another class, it will have all the properties of the another class.
                            all the properties or methods also ?
                                yes, all the properties & methods also.
                                because class is the concrete class, it can have the properties & methods.
                                so the class which is extending the another class, it will have all the properties & methods of the another class.
                   
                    Implements is used for implementing the interface.
                        which means that the class which is implementing the interface, it will have all the methods of the interface.
                            only methods or properites also ?
                                only methods.
                                because interface is the abstract class, it can't have the properties.
                                so the class which is implementing the interface, it will have all the methods of the interface.
                                
                    "extends" is used for class inheritance, where a subclass (child class) inherits properties and behaviors from a superclass (parent class).
                    "implements" is used for interface implementation, where a class provides specific implementations for all the abstract methods declared in an interface.
                        
                    What happen if any interface extends the another interface ?
                      The child interface inherits all the abstract methods from the parent interface.
                      The child interface can add more abstract methods of its own, or it can choose to override the abstract methods inherited from the parent interface.
                      Classes that implement the child interface must provide concrete implementations for all the abstract methods declared in both the child and parent interfaces.
                            like in TaskRepository interface we have extended the JpaRepository interface.
                                where we can access save(), findAll(), findById() methods.
                                we created fingByUsernam() method in the any class. 
                    
                    What happen if any class implements the interface ?
                        The class must provide concrete implementations for all the abstract methods declared in the interface.
                    
                    what happen if any interface implements the another interface ?
                       In Java, an interface cannot directly implement another interface. However, an interface can extend another interface.
                 
   Bearer Token:-
                    it means " any api request that is bearing this token as an carrying this token would be treated as Having the Authority 
                    as Exists with the User to Whom this token belong".         
                      like Whichever clients send the request to server (user can't send the request like byte by byte we have to send the request to the server)
                              so we are using an mobile app or any client on our behalf it is sending the request to the server.   
                                 whoever is bringing this request to the server (bearer the request) they should be treated equivalent to whoever user is signify by this token. 
                        that's why we are called it as the bearer token.
                                         ANY REQUESTING WHO IS BEARING THIS TOKEN WOULD BE TREATED AS AUTHROIZED BY THAT USER. 
                      Example:-
                                in the currency not there has return the bearer in the currency. 
                                            means "It signify that i am bearing a Certificate which  says that I have 10 rs."
                                                 
                    if we send the JWT token with the request then we can validate it & get the user details from it. like subject
                    if we send the Server side token with the request then we can validate it from the database & get the user details from it.
   
   In springboot,
            principal means user. 
            we called as Principal becoz it always not the user who is getting authenticated. it could be client, bot, etc.
            so more generic term called as principal.
            
   Context:-    
        In English, context is some background information that helps us to understand something.
        means, In terms of backend project when request has comeing in, its passes through some authentication then it goes to the controller
                then it goes to the service, then it goes to the repository, from repository it goes back to the service, this encapsulation 
                is to be understood. 
        Read:-
             there is an login function (/Get) is running from there, there is an controller(where userServiceVerify() is running) here the request has come in, then from the controller req will go to the service(userRepoFindbyUsername() is running) then from there req will go 
                to the repository, & from there we return the response back. 
                but in service class there are some code executing & then after that response is going back to the controller.          //Request
                in controller class there are some code executing & then after that response is going back to the client.               // lifecycle
             during this whole process,
                    who is authenticated ?
                      if the information is available everywhere like in controller, service, repository, etc.
             "Context is that kind of an object which is available in the entire lifecycle of the request." 
             so if we do set some information (whatever) in the context, so whenever we require like in the controller, service, repository, etc. 
             we can do getContext() & get the information from the context as long as the entire request lifecycle alive, this context travells.
             
             Security Context holds the information about the security level information or other things also.
             these information are useful for loggin purpose like we have context id, that basically uniquely identified the every request 
             so anything fails during the request lifecycle, we can trace it back & see through which all places it went.
                
                means, the meta data that request carry & available everywhere 
                that is called Context. here the context means this above information.
                    context are different in different different areas 
                    
                    
                    
             
```
### Frontend Responsibility
1.  Client could be anything like browser, mobile app, desktop app, etc.
2.  Client would store the token somewhere, its client responsibility. whoever is the client side developer,  he/she has to store the token somewhere.
3.  he/she must send the token with a certain header to the backend.


## BACKEND:- MEANS WE ARE BUILDING A SERVER. 
