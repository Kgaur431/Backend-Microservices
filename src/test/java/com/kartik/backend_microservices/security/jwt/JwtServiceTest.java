package com.kartik.backend_microservices.security.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {

    private JwtService jwtService()
    {
        return new JwtService();
    }
    @Test
    public void createJwt_works_with_username()
    {
        var jwt = jwtService();
        var jwtString = jwt.createJwtToken("starc");
        var username = jwt.getUserFromToken(jwtString);
        assertEquals("starc", username);
    }

    /**
     *  there is seprate code for logic & seprate code for testing. so that it gives us an mindset that,
     *      we can think like JwtService is already exists now let me try & see what all things i need to check that where automatically
     *      comes in our mind that what about if we pass null or empty string or something else. so that we can write test cases for that.
     */

    /*
    @Test
    public void createJwt_throws_exception_when_token_is_null()
    {
        var jwt = jwtService();
        var username = jwt.createJwtToken(null);
        // by running this test it should throw an exception. but it is not throwing an exception.
    }
*/

    @Test
    public void createJwt_throws_exception_when_token_is_null()
    {
        var jwt = jwtService();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var username = jwt.createJwtToken(null);
        });
    }
    /**         Test Driven Development (TDD):- this is the process of writing test cases first & then writing the actual code.
     *
     *   Now, by run this test, the test fails becoz actual code does not give the errror that i am expecting.
     *      like we are expecting IllegalArgumentException but our logic is not throwing that exception.
     *           becoz we have not written any logic for to handle null value.
     *           so we have to write logic for that in our actual code. & then if we run this test then our code will throw the exception.
     *           if we pass the null value.
     */

}
