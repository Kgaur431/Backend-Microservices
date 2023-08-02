
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
                            
            


































```