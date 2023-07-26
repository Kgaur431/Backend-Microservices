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
                           2. Cryptographic Token
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
                                            Step6:-  now the server see the token & server will validate the token with the token table that the token is exists or not.
                                                     if the token does not exists then we send the 401 response back to the client as unauthorized.
                                                     if token exists then server will check the expiry date of the token. if the token is expired then we send the 401 response back to the client as unauthorized. 
                                            Step7:-  if token exists & not expired then we get the user id from the token table & from user id we get the user data from the user table. 
                                            Step8:-  based on user data we send back the appropriate Home page with the user specific feeds like Facebook, Twitter.
                                            
                                            Things to Remember:-
                                                            1. These tokens are Client specific not user specific.
                                                                means if we login from two different browser(chrome & safari) to the same website from the same account 
                                                                    then we will get two different token.
                                                            2. Everytime I login I will get new token. 
                                                                means server might have code internally written cronjobs, services code which will delete the old token from the token table.
                                                                    which are not valid anymore. || server would have such implementation.
                                                                but every different client I login with I will get separate different token.
                                                                so that if we log out from one device or client that does not mean that we are logged out from all the devices or clients.
                                                                Example:-
                                                                        if we visit Facebook, Whatsapp, Telegram... etc. website then we can see that list of logged in devices. 
                                                                            like 
                                                                                    Chrome: Windows
                                                                                     date & time 
                                                                                 
                                                                                    Safari: Mac
                                                                                     date & time
                                                                        that all are list of tokens actually.
                                                                        so if we logged in from 5 different devices then we will get 5 different tokens with the same account id.
                                           Conclusion:-
                                                    With server side token, server is storing every generated token & server is matching the token with the token table.
                                Case 2:-                                        
                                    Cryptographic Token:-
                                                       this tokens are not saved in the server.
                                                        Encoding:-
                                                                means it representing an information(any information) in a digital form using characters set.
                                                                  like if we save video file in mp4, mkv etc. format that is also an video encoding.
                                                                       if we save image file in png, jpg etc. format that is also an image encoding.
                                                                       if we save string in utf-8, ascii etc. format that is also an string encoding.
                                                                       
                                                                  when we writing encoding that means the data would be stored as binary like 0 & 1. 
                                                                    but humans need to make sense of it in terms of string or anything. 
                                                                    so we can create translation like A=65, B=66   that is encoding.     
                                                                    
                                                                    URL encoding means characters that we write in the URL.
                                                                        so in URL encoding space is not allowed so we can write %20 instead of space.
                                                                    
                                                                    In ASCII, we can represent things which are not strings
                                                                        like 
                                                                            \a is for alert
                                                                            \b is for backspace
                                                                            \n is for new line
                                                                            \r is for carriage return means go to the beginning of the line
                                                                            \t is for tab
                                                                                    ... etc.   
                                                                         that things we can't represent in strings but in ASCII we have escape sequence for that.
                                                                SO, Encoding means that we have character set (like bounded with character set) 
                                                                        like ASCII has small character set, UNICODE has large character set.
                                                                            using this character set we can represent any information in digital form.
                                                                            & the things which we can not represent in the character set we have escape sequence for that.
                                                                Encoding only needs the algorithm to encode & decode. like base64 to ascii, ascii to base64.
                                                                
                                                                If we encode something like format A to format B. then size can either increase or decrease depending on character set on each side 
                                                                    like how big is the character set of format A & how big is the character set of format B.
                                                                so ANY ENCODING SCHEM WHICH HAS BIGGER CHARACTER SET CAN STORE MORE DATA IN LESS SPACE.
                                                                   ANY ENCODING SCHEM WHICH HAS SMALLER CHARACTER SET WILL REQUIRE MORE ESCAPE SEQUENCES SO IT WILL TAKE MORE SPACE FOR THE SAME DATA.
                                                                   
                                                                   so, if we representing in binary & save it in string then binary thing will be longer than string.  binary has smaller character set like 2 characters.
                                                                       if we representing in string & save it in Hexadecimal then Hexadecimal thing will be shorter than string. becoz Hexadecimal has bigger character set like 16 characters.
                                                                
                                                                USING A CHARACTER SET(predefined) TO REPRESENT ANY INFORMATION IN DIGITAL WORLD IS CALLED ENCODING.
                                                        Decoding:-
                                                                   if we encode something then we can decode it.
                                                                   so whatever format we encode, we should decode it in the same format.
                                                                    like:-  those two fromats are digital itself.
                                                                        ASCII to Base64     Base64 to ASCII
                                                                        Analog to Digital   Digital to Analog 
                                                        Loosy Encoding:-
                                                                    Some encoding can be loosy encoding.
                                                                        let say we are encoding some music into mp3 so we can encode it into 128kbps, 256kbps, 320kbps etc.
                                                                            so if we encode it into 128kbps then we can't get the same quality of music as we get in 320kbps.
                                                                            so that is loosy encoding. 
             
                                                        Encryption:- 
                                                                    it is PARTICULARLY USED FOR THE PURPOSE OF OFFESCATING || HIDING THE DATA ONLY. 
                                                                        when we encrypting we are not changing character set necessarily. we might change but not necessarily.
                                                                        we are just hiding the data. Our Agenda is not to change the character set.
                                                                        some encryption algo is taking data in any xyz format & outputting the data in the different format.
                                                                        so the encryption includes encoding as well. ==> this is possible
                                                                        
                                                                    BASIC IDEA OF ENCRYPTION IS TO MODIFY THE DATA IN SUCH A WAY THAT PEOPLE READING THE MODIFIED VERSION OF THE DATA
                                                                        CAN'T MAKE SENSE OF IT. BUT WE CAN REVERSE IT, BUT TO REVERSE IT WE NEED SOME KEY.
                                                                        
                                                                    Encryption is the Caesar cypher, it rotates by certain x character. but the key is degree of rotation. 
                                                                        so if we rotate by 3 then we need to rotate back by 3 to get the original data.
                                                                        if we know the key then we can reverse it.
                                                                            by applying the same algorithm in reverse order with the same key we can get the original data.
                                                                            
                                                                    while encryption we are not loosing any data. 
                                                                        
                                                                    Two things that are important
                                                                        1. Encryption Algorithm
                                                                        2. Key
                                                                        
                                                                        A = original data
                                                                            sizeof(A) = x
                                                                            sizeof(encrypted(A)) = y
                                                                            so, the relationsip between x & y is that:- y >= x.  
                                                                            
                                                        Hashing:-
                                                                    Hashing is an function, this function has unbounded input & bounded output.
                                                                        means we can give any size of input(infinite) to the hashing function but it will always give the same size of output.
                                                                        so, the output size is fixed.
                                                                    if 32-bit hashing function will result the values into 0 to 2^32-1.
                                                                    if 64-bit hashing function will result the values into 0 to 2^64-1.
                                                                    The output should be as random as possible. & the output should not depend on the input. 
                                                                           like:-
                                                                                we have hashing function h(x1) = y1
                                                                                we have hashing function h(x2) = y2
                                                                                   assume if any relation between x1 & x2 then not lead to be relation between y1 & y2.
                                                                    Hash Function:-
                                                                            it can as better as possible if we take n number of x values 
                                                                            like:- 
                                                                                    20 x values & we create the hash. 
                                                                                    what is the probability of them having the same hash value.
                                                                                      the "lower the probability the better the hash function".
                                                                                      
                                                                   CONVERT DATA VARIABLE LENGTH INTO FIXED LENGTH DOING SUCH A WAY TO REMOVE AS MANY COLLISIONS AS POSSIBLE.
                                                                   NO COLLISION IS NOT POSSIBLE. BUT WE CAN REDUCE THE COLLISIONS.
                                                                        because we have infinite input & finite output. so, collision is possible.
                                                                            like if my hash function is of  n bits in size, 4 bits like 2^4 = 16 possible values.
                                                                                so, if we have 17 values then 2 values will have the same hash value. 

                                                                   Feature of Hashing:-
                                                                    1. Hashing will always create a data of fixed size. unlike encoding, encryption... 
                                                                    
                                                                   Hashing is useful for passwords, becoz we don't want the developers of the backend to find out the users password.
                                                                      but the benifit of hash function is "if we take the same password & apply the same hash function then we will get the same value".   
                                                                        like:- if we take x1 & apply the hash function then we get y1.
                                                                      so when user first time type the password, developer hash it & save it(y1) in the database.
                                                                      when user again type the password, developer hash it, it can give value y2. then we can compare y1 & y2.
                                                                        if y1 == y2 then x1 == x2 are same. means password is correct.
                                                                     
                                                                      Hashing function is going to be a bad hashing function for our given dataset if even x1 & x2 are different but y1 & y2 are same.
                                                                        mean somebody can give any password & it might collide with the correct password. 
                                                                        like:- 
                                                                                if we have 2^4 sized hashing function. so, it can have 16 unique values.
                                                                                so, if we have try 17 value/combination then atleast 1 will work. becoz it will collide with the correct password.
                                                        
                                                        Base64:-
                                                                    it is an encoding scheme. 
                                                                    it is representing the strings into new character set. 
                                                                      character set being 0-9, a-z, A-Z, /, =. 64 characters.
                                                                    it is used to encode binary data into ASCII string.     
                                                                    
                                                                    How base64 conversion happen ?  
                                                                         0 1 2 .... 9  a  b  c .... z  A  B  C ....  Z   / =
                                                                         0 1 2 .... 9 10 11 12 .... 35 36 37 38 .... 61 62 63                
                                                              
                                                       How Cryptographic Tokens look like
                                                            How does JWT Token get created ?
                                                                JWT TOKEN has three parts.
                                                                    1. Header
                                                                    2. Payload
                                                                    3. Signature
                                                                    
                                                                            JWT TOKEN look like:-
                                                                                                eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
                                                                                                eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
                                                                                                SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
                                                                                      if we want to read the data from the JWT TOKEN then we can decode it.
                                                                                        like:-
                                                                                              in browser terminal do like this:-
                                                                                                        atob("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
                                                                                                            then
                                                                                                        "{"alg":"HS256","typ":"JWT"}"
                                                                    
                                                                    Header:-    (ALGORITHM & TOKEN TYPE)
                                                                        it is a JSON object. it is encoded in base64. 
                                                                        it contains the information about the algorithm used to create the signature.
                                                                        Like which format this JWT token is used.
                                                                        eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
                                                                            this above string is the Base64 encoded version of the JSON object.
                                                                                JSON OBJECT is:- 
                                                                                    {
                                                                                        "alg": "HS256",
                                                                                        "typ": "JWT"
                                                                                    }
                                                                        
                                                                    Payload:-   (DATA)
                                                                        it is a JSON object. it is encoded in base64. 
                                                                        it contains the information about the user. 
                                                                        eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
                                                                            this above string is the Base64 encoded version of the JSON object.
                                                                                JSON OBJECT is:- 
                                                                                    {
                                                                                        "sub": "1234567890",
                                                                                        "name": "John Doe",
                                                                                        "iat": 1516239022
                                                                                    }
                                                                                     if we want we can add more information about the user. but token will change 
                                                                                    {
                                                                                      "sub": "1234567890",
                                                                                      "name": "John Doe",
                                                                                      "iat": 1516239022,
                                                                                      "exp": 1519239022
                                                                                    }   
                                                                        
                                                                    Signature:- (VERIFY SIGNATURE)
                                                                        SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
                                                                            this above string is created using the hashing function. 
                                                                            this above string is not Base64 encoded version of the JSON object.
                                                                                HMACSHA256(
                                                                                      base64UrlEncode(header) + "." +
                                                                                      base64UrlEncode(payload),
                                                                                      
                                                                                        your-256-bit-secret                 // we can type our own key here.
                                                                                    
                                                                                    ) secret base64 encoded
                                                                                    
                                                                                    HMACSHA256:-
                                                                                               SHA256 is an hashing algorithm.
                                                                                               HMAC converts into cryptographic signature algorithm.
                                                                                                  HMACSHA256 is an cryptographic signature algorithm.
                                                                                                        so HMACSHA256 HMAC is an cryptographic signing part of it & SHA256 is an hashing algorithm internally in it. 
                                                                            
                                                                            this signature part is created by combining the header & payload.
                                                                                like the HMACSHA256 function is take first argument as base64 encoding of header + . + second argument as base64 encoding of payload.   
                                                                                        then by using HMACSHA256 algorithm & encrypted with the given secret key to create the signature.
                                                                            here it is not doing ecryption, it is an cryptographic signature.
                                                                        Cryptographic Signature:-
                                                                                            it is an HASHING ALGORITHM. but like an ecryption this hashing algorithm is also uses a secret key.
                                                                                              like:-
                                                                                                    encryption:-
                                                                                                            1. ecryption(data1, key1) = EncryptedData1
                                                                                                            2. ecryption(data1, key2) = EncryptedData1`
                                                                                                            
                                                                                                    decryption:-
                                                                                                            1.  decryption(EncryptedData1, key1) = data1
                                                                                                            2.  decryption(EncryptedData1`, key2) = data1
                                                                                                            3.  decryption(EncryptedData1`, key1) = any random data
                                                                                                            
                                                                                                    In Cryptographic Signature:-
                                                                                                            1. signature(data1, key1) = signature1
                                                                                                            2. signature(data1, key2) = signature2
                                                                                                                signature1 & signature2 are like a hashes only thing unlike SHA1 || MD5 (these are conventional hashing algorithm
                                                                                                                        where we only put the data) but here we put the data & secret key.
                                                                                                                 we cannot get back from signature1 to data1.
                                                                                                                 signature1 > data1
                                                                                                                 signature1 & signature2 are fixed size, 2^128, regardless of sizeof(data1)
                                                                                                            Cryptographic Signature is an hashing with secret key.
                                                                                                                    in Hashing 
                                                                                                                            hashing(x1) = y1    // if we give x1 then we will get y1 everytime 
                                                                                                                    in Cryptographic Signature
                                                                                                                            sign (x1, key1) = y1    // we get y1
                                                                                                                            sign (x1, key2) = y2    // we will not get y1 if we don't have key1
                                                            Benefits of using JWT:-
                                                                                 When user logged in then we generates an JWT, when we generates an JWT then we take userId as subject (some user identifier we put in payload)
                                                                                    WE WILL NOT PUT ANY PERSONAL INDENTIFIABLE INFORMATION IN THE PAYLOAD like username, email, phone number, address, etc.  we can put userId. UUID. 
                                                                                    because anybody who see this JWT token can decode it & can see the information.    
                                                                                    we can give some part from which we can reverse calculate who is the user.
                                                                                    
                                                                                    so we generate JWT, we create the signature & which generate JWT that we gives to the client. server will not store the JWT becoz it does not need too.     
                                                                                        (like header + payload + signature)
                                                                                        becoz when client sends back the JWT to server then the server can take this part (header + payload) & run this algorithm (HMACSHA256) & check whether this
                                                                                        (header + payload) part is equal to the signature part or not. if it is equal then it is a valid JWT. if it is not equal then it is not a valid JWT.          
                                                                                    like:-
                                                                                            1. sign(header + payload1, secretKey) = signature1
                                                                                            2. sign(header + payload2, secretKey) != signature1  // becoz payload2 is different from payload1 
                                                                                            3. sign(header + payload2, secretKey) = signature2                
                                                                                   
                                                                                                In 2nd point we can change userid in payload BUT WE CAN'T GENERATE THE VALID SIGNATURE
                                                                                                    because we don't have the secret key. so we can't generate the valid signature.
                                                                                                    key is only server knows or developer knows 
                                                                                                    so if we take the JWT token & tamper with the payload & send back to the server then server will 
                                                                                                    throw invalid because the signature is not correct for the payload. 
                                                                                                    therefore server don't need to store the JWT token. becoz it can verify the JWT token by using the signature.
                                                                                                        like server generate the token give to client & forget it. when client sends back the token then server can verify it using cryptographical algorithm.
                                                                                                                if we change the payload then cryptographical algorithm will failed(not able to generate the same signature.)
                                                                                                    we can read the data but we can't change the data.
                                                                
                                                       In Cryptographic Token:-
                                                                        
                                                                        Step1:-  somebody send the POST request with username & password. they want to login. 
                                                                        Step2:-  we make query with username to check that this username does exists & we check weather the password is match or not. 
                                                                                 if its does not match then we send the response back to the client that username not found or password not match.
                                                                        Step3:-  so as soon as somebody does login and if username & password exists then we create JWT token.  
                                                                        Step4:-  In the response message we send back the JWT token to the client. 
                                                                        Step5:-  when somebody access the home page then they send the request then inside the header they send the JWT token.
                                                                        Step6:-  now the server see the JWT token & server will match the JWT token with the signature & verify the signature 
                                                                        Step7:-  if signature is verified then inside a JWT there is a payload & inside the payload there is a subject, which is the user id. 
                                                                                 from the user id, server can get the user data from the database.
                                                                        Step8:-  based on user data we send back the appropriate Home page with the user specific feeds like Facebook, Twitter.
                                                                        
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------                       
                    Series              SERVER SIDE TOKEN                                     VS                      CRYPOTOGRAPHIC TOKEN (JWT)
                      1.                  storage required is high.                                                     storage required is low.
                      2.                  db query(reads) is high                                                       db query(reads) is low.
                      3.                  cpu needs is low.                                                             cpu needs is high.     
                      4.                  here we can do server side logout.                                            here we can't do server side logout.
                      5.                  token size is small.                                                          token size is big.
                      6.                  storing user meta data is increase db size.                                   storing user meta data 
                      7.                  Decentralization is high                                                      Decentralization is low
                                                       
                                                        
                                                     
                                                           
                              
                     server side logout means we can see that list of devices where we are logged in & that is only possible with server side token.
                        becoz the server has the records of token, if its JWT token then server can't show the list of devices where we are logged in.
                        & we can't log out from one of those devices.   
                        
                        with crypto tokens if we want to user to logout forcefully then we generally used blacklisting table or something like that, 
                        the token when its generate whatever expiry date we give to it, or what is the issue date, by default token expiry in 7 days.
                        there is no way to logout before expiry dates, so server can't invalidate the logout process.       
                             
                     In mid company both are used.
                       like in case of google, account.google.com is used server side token & then every google product like youtube, gmail, google drive, etc. is used crypto token.    
                       so the sub domains of google like gmail, youtube, google drive, etc. they don't need to hit the main server to validate the request. 
                       so **In case of Distributed Computing the edges(gmail, youtube) work with the JWT**
                       
                     we can use public & private algorithms for JWT. like ES256, RS256... etc. 
                        which has public & private key.   
                           **In these algorithms the public key can be used to validate the signature & private key can be used to generate the signature.**
                             by doing this we can keep copy of the public key in the distributed server that we have & we can keep the private key in the main server. 
                                Examples:-
                                        when somebody loged in first time the JWT that has to generated that we can do it from main server. becoz login req are not that frequent.                                  // Distributed Computing
                                            but every req has to be authenticated so we have to read the  request then we have to validate the signature. validation happen with public key.
                                            here, validation is the fast process. but signature creation is very slow.
                                                means **Centeralised Signature Creation but Decentralised Signature Validation**
                             
                             
                             
                             
                             
                             
```                 
