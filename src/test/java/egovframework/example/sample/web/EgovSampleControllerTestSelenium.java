package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EgovSampleControllerTestSelenium {

	private static final Duration WAIT_DURATION = Duration.ofSeconds(1);

	@LocalServerPort
	private int port;

	WebDriver driver;

	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}

	@AfterEach
	void tearDown() {
		if (driver != null) {
			sleep();
			driver.quit();
		}
	}

	@Test
	void test() {
		if (log.isDebugEnabled()) {
			log.debug("test");
		}

		driver.get("http://localhost:" + port + "/");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

		sleep();
		javascriptExecutor.executeScript("sampleCreate();");

		sleep();
		WebElement name = driver.findElement(By.id("name"));
		String now = LocalDateTime.now().toString();
		name.sendKeys("test 이백행 카테고리명 " + now);

		sleep();
		WebElement useYn = driver.findElement(By.id("useYn"));
		useYn.sendKeys("N");

		sleep();
		WebElement description = driver.findElement(By.id("description"));
		description.sendKeys("test 이백행 설명 " + now);

		sleep();
		WebElement regUser = driver.findElement(By.id("regUser"));
		regUser.sendKeys("test 이백행");

		sleep();
		javascriptExecutor.executeScript("sampleAdd();");

		// 확인 대화상자로 전환
		Alert alert = driver.switchTo().alert();

		// [확인] 클릭
		alert.accept();

	}

	private void sleep() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(WAIT_DURATION);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			fail("InterruptedException: Selenium Sleeper", e);
		}
	}

}
