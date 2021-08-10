import org.jasypt.properties.EncryptableProperties;
import org.jasypt.util.text.StrongTextEncryptor;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Properties;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class GitHub {

  public String getGitHubContributions(ChromeDriver driver) throws IOException {
    driver.get("https://github.com/");

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(Duration.ofSeconds(5))
        .pollingEvery(Duration.ofMillis(250))
        .ignoring(WebDriverException.class);

    var signUpUsernameClick = wait.until(presenceOfElementLocated(
        By.xpath("/html/body/div[1]/header/div/div[2]/div[2]/div[2]/a")));
    signUpUsernameClick.click();

    var signUpUsername = wait.until(presenceOfElementLocated(By.id("login_field")));
    signUpUsername.sendKeys("sarafuentes3025");

    var password = wait.until(presenceOfElementLocated(By.id("password")));

    var encryptor = new StrongTextEncryptor();
    encryptor.setPassword(System.getProperty("masterPassword"));

    Properties props = new EncryptableProperties(encryptor);
    props.load(new FileInputStream("src/main/resources/application.properties"));
    password.sendKeys(props.getProperty("gh.pass"));

    var signIn = wait.until(presenceOfElementLocated(By.name("commit")));
    signIn.click();

    var profileButton = wait.until(presenceOfElementLocated(
        By.xpath("/html/body/div[1]/header/div[7]/details/summary/img")));
    profileButton.click();

    var menuItem = wait.until(presenceOfElementLocated(
        By.xpath("/html/body/div[1]/header/div[7]/details/details-menu/a[1]")));
    menuItem.click();

    var contributionsElement = wait.until(presenceOfElementLocated(
            By.xpath("//*[@id=\"js-pjax-container\"]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div/div")))
        .getScreenshotAs(OutputType.FILE);

    Files.copy(contributionsElement.toPath(), Paths.get("src/main/resources/contributions.png"),
        StandardCopyOption.REPLACE_EXISTING);

    return "src/main/resources/contributions.png";
  }
}
