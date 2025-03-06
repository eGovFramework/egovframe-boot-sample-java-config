package egovframework.example.sample.web;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class EgovSampleControllerTestSelenium {

	WebDriver driver;

	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}

	@Test
	void test() {
		if (log.isDebugEnabled()) {
			log.debug("test");
		}

		driver.get("http://localhost:8080/");

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
		regUser.sendKeys("test 이백행 등록자 " + now);

		sleep();
		javascriptExecutor.executeScript("sampleAdd();");

		// Switch to the alert
		Alert alert = driver.switchTo().alert();

		// Accept the alert (click "Yes" or "OK")
		alert.accept();

		// or Dismiss the alert (click "No" or "Cancel")
		// alert.dismiss();
	}

	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail("InterruptedException: Thread.sleep");
		}
	}

}