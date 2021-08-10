import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

  public static void main(String[] args) throws Exception {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    var driver = new ChromeDriver();

    var gitHub = new GitHub();
    String profilePicPath = gitHub.getGitHubContributions(driver);

    var twitter = new Twitter();
    twitter.editProfileOnTwitter(driver, profilePicPath);
    // TODO find a better way to wait for the picture to load
    Thread.sleep(2_000);
    driver.close();
  }
}
