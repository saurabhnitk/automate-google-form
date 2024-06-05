package demo;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleForm {
    ChromeDriver driver;
    public GoogleForm()
    {
        System.out.println("Constructor: GoogleForm");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest()
    {
        System.out.println("End Test: GoogleForm");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01() throws InterruptedException{
        System.out.println("Start Test case: GoogleForm");
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        Thread.sleep(8000);
        try{
            writeText(driver,By.xpath("//div[@class='Xb9hP']/input[@type='text']"),"Saurabh Kumar");
            writeText(driver,By.xpath("//textarea[@aria-label='Your answer']"),"To make career in automation" + calculateEpochTimeToString(0));
            clickCheckBox(driver,By.xpath("(//span[normalize-space(text())='How much experience do you have in Automation Testing?']/ancestor::div[4]//div[@class='AB7Lab Id5V1'])[2]"));
            clickCheckBox(driver,By.xpath("(//span[normalize-space(text())='Which of the following have you learned in Crio.Do for Automation Testing?']/ancestor::div[4]//div[@class='AB7Lab Id5V1'])[2]"));
            clickCheckBox(driver,By.xpath("(//span[normalize-space(text())='Which of the following have you learned in Crio.Do for Automation Testing?']/ancestor::div[4]//div[@class='AB7Lab Id5V1'])[2]"));
            clickCheckBox(driver,By.xpath("(//span[normalize-space(text())='Which of the following have you learned in Crio.Do for Automation Testing?']/ancestor::div[4]//div[@class='AB7Lab Id5V1'])[2]"));
            selectFromDropdown(driver,By.xpath("//*[normalize-space(text())='How should you be addressed?']/ancestor::div[4]//div[@class='MocG8c HZ3kWc mhLiyf LMgvRb KKjvXb DEh1R']"),"Mr");
            writeText(driver,By.xpath("//*[normalize-space(text())='What was the date 7 days ago?']/ancestor::div[4]//input[@class='whsOnd zHQkBf']"),
                     calculateDateTimeToString("dd/MM/YYYY", (long) 6.048e+8));
            //Provide current time 
            writeText(driver,By.xpath("(//*[normalize-space(text())='What is the time right now?']/ancestor::div[4]//input[@class='whsOnd zHQkBf'])[1]"),
                     calculateDateTimeToString("HH", 0));
            writeText(driver,By.xpath("(//*[normalize-space(text())='What is the time right now?']/ancestor::div[4]//input[@class='whsOnd zHQkBf'])[2]"),
                    calculateDateTimeToString("mm", 0));
            //Try going to another website and you will find popup
            driver.get("https://www.amazon.in/");
            Thread.sleep(2000);
            handleAlert(driver,false);
            //submit the form
            driver.findElement(By.xpath("//span[text()='Submit']")).click();
            //Print the message upon successful completion
            System.out.println(driver.findElement(By.xpath("//div[@role='heading']/../div[3]")).getText());
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Failure");
            return;
        }
    }

    private static void writeText(ChromeDriver driver,By selector,String textToSend) throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        WebElement element = driver.findElement(selector);
        element.clear();
        element.sendKeys(textToSend);
        Thread.sleep(2000);
        System.out.println("success");
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

    private static String calculateEpochTimeToString(int offsetInMs){
        //Get current date and time as an Instant
        Instant now = Instant.now();
        //Apply offset in milliseconds to the current instant
        Instant newInstant = now.plusMillis(offsetInMs);
        //convert Instant to epoch time in milliseconds
        long epochMilli = newInstant.toEpochMilli();
        // return epoch time as string
        return String.valueOf(epochMilli);
    }

    private static void clickCheckBox(ChromeDriver driver, By selector) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        WebElement element = driver.findElement(selector);
        element.click();
        Thread.sleep(2000);

    }

    private static void selectFromDropdown(ChromeDriver driver, By dropDownSelector, String textToSelect) throws Exception{
        WebElement dropdownElement = driver.findElement(dropDownSelector);
        dropdownElement.click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//div[@data-value='"+textToSelect+"'])[2]")).click();
        Thread.sleep(2000);

    }

    private static void handleAlert(ChromeDriver driver, Boolean confirm)throws Exception{
        Alert alert = driver.switchTo().alert();
        Thread.sleep(2000);
        if(confirm){
            alert.accept();
        }else{
            alert.dismiss();
        }
    }

}
