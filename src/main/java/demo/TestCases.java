package demo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    
    ChromeDriver driver;

    public TestCases(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest(){
        driver.close();
        driver.quit();
    }

    public void testCase01() throws InterruptedException{
        
    }

    private static String calculateDateTimeToString(String formatString, long offsetInMs){
        LocalDateTime now = LocalDateTime.now();
        //Add offset in milliseconds to current date and time
        long seconds = offsetInMs / 1000;
        long nanos = (offsetInMs % 1000) * 1000000;
        LocalDateTime newDateTime = now.minus(Duration.ofSeconds(seconds,nanos));
        //format new date and time according to provided format string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString); 
        String formattedDateTime = newDateTime.format(formatter);
        return formattedDateTime;
    }


}
