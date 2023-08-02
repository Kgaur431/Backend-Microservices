

``` 

            in production, CORS ans CSRF should be enabled
            CSRF is Cross Site Request Forgery
               It is a type of attack where a malicious user sends a request to a website to perform an action on behalf of the user

            CORS is Cross-Origin Resource Sharing
                It is a mechanism that allows restricted resources on a web page to be requested from another domain outside the domain from which the first resource was served
                means from google.com frontend we can't request data from facebook.com backend

            .httpBasic()
                    means page requires the username and password
                    it is by default provided by spring security
             
            so whatever pages we need to authenticate we need to add them in the antMatchers().authenticated()
            
            AppSecurityConfig class is a place where we can configure the security 
                like which page should be authenticated and which page guest can access
























```