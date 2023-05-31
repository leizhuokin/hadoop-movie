package org.example;

import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void AvgScore_four(){
        String a="香港";
        String[] country = a.trim().split("/");
        for (int i = 0; i < a.length(); i++) {
            System.out.println(country[i]);
        }
    }
}
