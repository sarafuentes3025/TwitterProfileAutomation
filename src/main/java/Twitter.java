import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.text.StrongTextEncryptor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Twitter {
  public void editProfileOnTwitter(ChromeDriver driver, String profilePicPath) throws IOException {
    driver.get("https://twitter.com/login");

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(Duration.ofSeconds(5))
        .pollingEvery(Duration.ofMillis(250))
        .ignoring(WebDriverException.class);

    var signUpUsername = wait.until(presenceOfElementLocated(By.name("username")));
    signUpUsername.sendKeys("@sfuentes3025");

    var next = wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[2]/div/div/span/span")));
    next.click();

    var password = wait.until(presenceOfElementLocated(
        By.name("password")));

    var encryptor = new StrongTextEncryptor();
    encryptor.setPassword(System.getProperty("masterPassword"));

    Properties props = new EncryptableProperties(encryptor);
    props.load(new FileInputStream("src/main/resources/application.properties"));
    password.sendKeys(props.getProperty("t.pass"));

    var logIn = wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div[2]/div/div/span/span")));
    logIn.click();


    var profileButton = wait.until(presenceOfElementLocated(
        By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/header/div/div/div/div[1]/div[2]/nav/a[7]")));
    profileButton.click();

    var editProfileButton = wait.until(presenceOfElementLocated(
        By.xpath("//*[@id=\"react-root\"]/div/div/div[2]/main/div/div/div/div[1]/div/div[2]/div/div/div[1]/div/div[1]/div/a/div/span/span")));
    editProfileButton.click();

    var addPhoto = wait.until(presenceOfElementLocated(
        By.xpath("//*[@id=\"layers\"]/div[2]/div/div/div/div/div/div[2]/div[2]/div/div[2]/div/div/div[1]/div/div/div[3]/input")));
    addPhoto.sendKeys(profilePicPath);

    var apply = wait.until(presenceOfElementLocated(
        By.xpath("//span[contains(text(),'Apply')]")));
    apply.click();

    var savePhoto = wait.until(presenceOfElementLocated(
        By.xpath("//span[contains(text(),'Save')]")));
    savePhoto.click();
  }
}
