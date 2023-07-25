### JWT + Cookies
``` 
    -   the concepts of tokens are used for Session Management.
    -   Ques:- Why do we need Session Management?
        Ans :- Authentication. and many more 
        
        Usecase of Authentication:-
            we have webapp, it have bunch of endpoints 
                /login
                /signup
                /home
                /profile
                /about  (public)
            somebody makes a GET request to /about endpoint. it will display a Page.
            like 
                Signup request is an POST request to signup. 
                it will have username and password in the body. that will be stored in the user table in database.
                once the user is created. we will send a response message to the user.
            
                Lgoin request is an POST request to login.
                it will have username and password i n the body. that will be checked in the user table in database.   
                if the username not exists. we will send a 404 response message to the user.
                if the password is wrong. we will send a 401 response message to the user.
                if the username and password is correct. we will send a 200 response message to the user. & further we will discuss later. 
                
                Home endpoint is only for the logged in users.
                but the response of the home endpoint is same for all user. 
                
                Profile endpoint is only for the logged in users.
                but the response of the profile endpoint is different for all user. based on who is logged in. 
                
                both these endpoints(Home & Profile) required the person to be logged in.
                
                Ques:- if somebody logged in then what response we should send to the user? & if somebody makes the request for the Home endpoint || Profile endpoint then how do we know that the which user is logged in?
                Ans :- 
                Basic Level :-
                    =   now we will discuss the things with respect to client & server only not like the browser, app, etc. and we have HTTP request.
                        one way:- (not secure, not convenient)
                                every time user make a request to those endpoints. which need to verify the user identity then we have to send the username & password everytime in every request. 
                                    in the header of HTTP request we will send the username & password.
                                    in the query parameter of HTTP request we will send the username & password.

                                Techincally:- correct approach.
                                Problems:- 
                                        1. Convinience:- every time if we click any link or pager then it will ask for the username & password in every request. not good.
                                        2. Security:- if somebody able to capture the header of one request(every req that goes from our system has the password in it) they get to know the password. not good.

                            so at client side we do something like there is an APP or Browser which saves the password.
                                so user type the password once. and then it will save the password in the APP or Browser.
                                and then whenever user make a request to the server. it will automatically send the password in the header of the HTTP request. 

                                Problem:-
                                        1. Security wise its not good becoz if somebody get access to the APP or Browser then they can see the password.
                                        2. if somebody captures the request still they able to see things because for every request we are sending the password, even though its not user typed but password is going in every request. 
                            
                            Now we don't want to transfer the password in every request. but something client has to send.
                                Example:-
                                    Person A & Person B are Two guy. 
                                    Person A is just had logged in. but Person B is not logged in. so at login endpoint something had happen when they logged in. and clients get some information when they logged in. 
                                    Person A is making a request to the Home endpoint. and Person B is making a request to the Home endpoint.
                                        for Person A server will send the response of the Home endpoint.
                                        for Person B server will send the 401 response message that you are not logged in. 
                                    Idea:-
                                        the idea is to server needs to know that which request came from which user. means server needs to keep track of the user.
                                            keeping track means server has to have memory and remember things  
                                            
                                            Story:-
                                                    long long back like 30-40 years back internet was not like this as it is today. every person who had use the internet have the 
                                                    seprate ip address and it was arphanet, subnetting was not used, dns-masking was not used. every person has the separate ip address.
                                                    then it was possible to server to keep track of the user like ip address of Person A & Person B. so if Person A sends new request then server will know that this ip address is already logged in. 
                                                    
                                                    but in today's world there are multiple people in the same office, let say you & your co-worker both search the what is my ip address 
                                                    then you both will get the same ip address. because google can see the public ip address of office wifi but inside the office there are separate subnet || local ip address 
                                                    but public ip address is everbody has same. so server can't be indentify || remember the user based on the ip address. 
                                            
                                            so the job of the server is to remember the client is off loaded to the client itself.
                                            How does it Work?
                                                Think like there is an 5-10 employees office and there is an Guard so everyday the employee comes to the office Guard will remember the face of the employee.
                                                but if tis an 1000 employees office then Guard can't remember the face of the employee. so what Guard will do is he will give the ID card to the employee.
                                                When do we get the ID card?
                                                    at first day of joining we have to show the aadhar card & details to the Guard. then we will get the gate pass 
                                                    so security teams, first day of office we come we have to prove our identity then they will give me the card so as long as we will show this card 
                                                        Guard will let me in the office. 
                                                    here by showing the card we are proving our identity. no need to show the face anything. 

                                            another case:-      Two Factor Authentication
                                                    we have given the card to my friend then he can actually enter the office.
                                                    unless the Guard is not cross check the face of the employee with the face of the card.
                                                        Thats where Two Factor Authentication comes in picture.

                                    We are saying that 
                                            SERVER IS GOING TO REMAIN STATELESS. so the server will not store the information about the client.   
                                            server will do, once user logged in then server will give one string that have generated (its long string so nobody can guess it) to the client.
                                            so in future if the client keep showing the string then server will agree that the client is authenticated.
                                            that waht TOKEN is.  so token is a string for now.
                                            there are many ways to created the token.
                                                JWT is one of the way to create the token.
                                            How client can present the token ?
                                                when client make the request to the server then client will send the token in the header of the HTTP request.
                                                this is also know as API Contract. where client & server both agree that this token client will send in the header of the HTTP request.
                                                so after login server will give the token, client will save the token wherever clients feels appropriate. 
                                                    like client will be browser, browser can store the token in the cookie || local storage || session storage. etc. 
                                                         if client will be app then app can store the token in the local db || local cache etc. 
                                                    so save where client wants to save.
                                                in future request if client show the token then server will let the client in. if not show the token then server will not let the client in.
                                                    then client have to log in again to get the token.
                                                    
                                            Tokens can have the expiry dates.
                                    How does first time Authentication happen ?
                                      first time authentication happen by showing the username & password. 
                                        user make the POST request to login endpoint, we pass the username & password then db finds the username row inside that 
                                        it will matches with the password if its fine then it will give the token. that's how first time token is acquired by the client.
                                        in future client has to send the token to shows that client has authenticated.
                                       
                                    Benefits of doing this is:-
                                      at the /home endpoint, any request that comes SERVER WILL TREATE EVERY REQUEST AS EQUAL. means server don't know that this request is come from the client who just logged in or 
                                       who is already logged in, who is sending the first request today ==> these are the special ways.
                                     if Request comes along with token then server accept the req other wise server will treat it as logged out.   
                                        means "There is no variable or state required inside the Home Controller(its an server only) to identify request in special way". for every request server will see just it has token or not.   
                                        so no special handling required in controller for like user who is already logged in, who is sending the first request today and so on.

                                        Ques:- how does Random token is validate ?
                                        Ans :- either the server needs to have table in the db where all valid token are there with respect to userId so from there it can validate
                                               or server might have some algorithms which can identify that the token is valid or not. like by cryptographically.
                                               Cryptographically like
                                                When we installed windows 10 or any software and pirated key which we used or the CD where the key is installed so that CD does not have the 
                                                db of all possible keys, there is an algorithm that checks this particular keys are valid or not. and there are certain set of key in the world 
                                                like 5555... 20 char key so certain 20 char combinations will be valid in the algorithm & certain any other char combination is not valid 
                                                so if we enter a key which is valid and becoz all numbers & alphabets means 36 so 36^20 would be very large number out of that number of keys 
                                                some value are valid like 1 million becoz that many keys are sold rest is not valid till it sold 
                                                so that is a cryptographically verified. so no need of db. 
                        
                            Two Usecases that we need to solve 
                                1. we have to check that the token is valid or not for the Home Page. so that we can give appropriate response. 
                                2. somebody make request for profile page then we have to send them that profile data. 
                                    here we have to do two things 
                                        1. token is valid or not. 
                                        2. we have to somehow from the token get to the user as well like who is the user. 
                                            like in blogging app to read the article then we will check if the read request is coming from authenticated user or not.
                                                 and in blogging app to write an article then we have to know who is the user who is writing the article so that we will assign it as author of the article.

                                               Story:-
                                                    suppose somebody capture the request & he get the token then we will loose the security from one website 
                                                    becoz that token he can access that website but if password has captured then he might access other website as well.
                                                    becoz human has the tendency to use the same password everywhere mostly.  
                                                    but with the token they can get the access to only one website.
                                                        even in most website if we logged in then we change the password then the website will ask to enter old password.
                                                        becoz website is taking care that may be somebody stolen the token then also they can't change the password.

                                               Story:-
                                                     In HTTP's the communication can happen between two entities so from browser to server between them 
                                                     it is going through multiple hops like wifi, Isp, telecome provider, datacenter, server etc. non of them will see the intenal things apart from domain name.
                                                     so the URL, request boy, Header and so on are encrypted. so In HTTP's when we first time open website there is a TLS(Transport Layer Security) handshake happens
                                                     like certificates is exchanged that is used to encrypted the communication channel. its a synchronous process so the same key used from both client & server side 
                                                     client can encrypt & send with data & server can decrypt & read the data. then server can encrypt & send the response & client can decrypt & read the data.
                                                     so we don't have to write any code for that its already there in the HTTP's. like at network layer 
                                                     so when we write code at application and presentation layer code then we don't have to worry about the encryption & decryption. 

                    =   How are the session Managed & what are the strategies are their.    TYPES OF TOKENS
                           for the tokens that we are managing there are two strategies that are used. for the server to manage
                           1. Server Side Token 
                                usually we can see some discussion like "should we use JWT or should we use Cookies" 
                                    here mostly people are trying to say that Cookies are Server Side Token. but its Wrong. 
                                    Cookies are "basically where we are storing the token".
                                    so server side token there are no special strategy. 
                                    UUID(it will need some sort of DB implementation), random-string generator these we can use it as Server Side Token.
                           2. Cryptographically Token
                                JWT, there can be others as well.
                                
                    =   How the tokens are stored regardless of server side or cryptographically token in the client side. 
                            client has to store the token somewhere. client can store in multiple ways. 
                            so client can store the token in the cookies(only browser can store in it, we don't have to implement cookies on client side there is an certain header called setCookie so when header is set then browser will automatically save the cookie
                                   browser can keep sending the cookies with the future request),
                                 local storage(browser local storage), sqlite, mobileapp storage etc. 
                             we can store JWT as cookies or sqlite ... etc.
                             
                    =   What type of token are ?
                            we will discuss what happen when login is done, how the user get redirected to the home page. 
                              Case 1:-
                                Server Side Token:- Implementation  
                                           Server Side Token the implementation is        
                                           
                                                               /login                                                       User Table                                      Token Table
                                                                                                                         Uid | Username | Password | ...                    token | uid | expiry date | ...
                                                               
                                                               
                                                               
                                                               /home      
                             
                                                                        here we = server side. 
                                            Step1:-  somebody send the POST request with username & password. they want to login. 
                                            Step2:-  we make query with username to check that this username does exists & we check weather the password is match or not. 
                                                     if its does not match then we send the response back to the client that username not found or password not match.
                                            Step3:-  we have another table called as token table where the token stored with the user id. 
                                                     so as soon as somebody does login and if username & password exists then we create new row in the token table with the user id.
                                            Step4:-  In the response message we send back the token to the client. 
                                            Step5:-  when somebody access the home page then they send the request then inside the header they send the token.
                                                     now the server see the token & server will validate the token with the token table that the token is exists or not.
                                                     if the token does not exists then we send the 401 response back to the client as unauthorized.
                                                           
                             
                             
                             
                             
                             
                             
                             
                             
                             
                             
                             
                             
```                 
