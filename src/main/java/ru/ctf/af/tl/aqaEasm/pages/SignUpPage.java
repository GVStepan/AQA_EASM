package ru.ctf.af.tl.aqaEasm.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.ctf.af.tl.aqaEasm.helpers.User;

import java.time.Duration;

public class SignUpPage {
    private final WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By fullName = By.id("userName");
    private final By email = By.id("email");
    private final By phoneNumber = By.id("phoneNumber");
    private final By password = By.id("password");
    private final By passwordValidation = By.id("passwordValidation");
    private final By submitButton = By.id("submitLogin");
    private final By personalDataCheckbox = By.cssSelector("#inputForAuth > div:nth-child(4) > label:nth-child(1)");
    private final By infoAgreeCheckbox = By.cssSelector("#inputForAuth > div:nth-child(4) > label:nth-child(2)");
    private final By fullNameFieldError = By.cssSelector("#inputForAuth > div:nth-child(2) > div:nth-child(1) > div.signUpForm__errText");
    private final By closeOtpConfirm = By.id("closeImg");
    private final By otpConfirmError = By.cssSelector("#loginModalAuth > div > div > div > div.signUpForm__errText");
    private final By confirmOtp = By.id("modalForAuth");

    private void enterFullName(String userName) {
        WebElement fullNameField = driver.findElement(fullName);
        driver.findElement(fullName).clear();
        fullNameField.sendKeys(userName);
    }

    private void enterEmail(String email) {
        WebElement emailField = driver.findElement(this.email);
        driver.findElement(phoneNumber).clear();
        emailField.sendKeys(email);
    }

    public void enterPhone(String phone) {
        WebElement phoneField = driver.findElement(phoneNumber);
        driver.findElement(phoneNumber).clear();
        phoneField.sendKeys(phone);
    }

    private void enterPassword(String password) {
        WebElement passwordField = driver.findElement(this.password);
        driver.findElement(this.password).clear();
        passwordField.sendKeys(password);
    }

    private void enterPasswordValidation(String password) {
        WebElement passwordField = driver.findElement(passwordValidation);
        driver.findElement(passwordValidation).clear();
        passwordField.sendKeys(password);
    }

    public void fillUserDataAndSubmit(User user) {
        enterFullName(user.getFullName());
        isSubmitDisabled();
        enterEmail(user.getEmail());
        isSubmitDisabled();
        enterPhone(user.getPhone());
        isSubmitDisabled();
        enterPassword(user.getPassword());
        isSubmitDisabled();
        enterPasswordValidation(user.getPassword());
        isSubmitEnabled();
    }

    private void clickCheckboxes() {
        driver.findElement(personalDataCheckbox).click();
        driver.findElement(infoAgreeCheckbox).click();
    }

    public void isSubmitDisabled() {
        Assert.assertFalse(driver.findElement(submitButton).isEnabled());
    }

    public void isSubmitEnabled() {
        Assert.assertTrue(driver.findElement(submitButton).isEnabled());
    }

    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    public void fillUserDataAndClickConfirm(User user) {
        fillUserDataAndSubmit(user);
        clickCheckboxes();
        clickSubmit();
    }

    public void checkAlertTextAndClose(String alertText) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = webDriverWait.until(ExpectedConditions.alertIsPresent());
        if (alertText != null) {
            Assert.assertEquals(alert.getText(), alertText);
        }
        alert.accept();
    }

    public void checkFullNameFieldError(String errorText) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(fullNameFieldError));
        Assert.assertEquals(driver.findElement(fullNameFieldError).getText(), errorText);
    }

    public void isSmsCodeConfirmDisabled() {
        Assert.assertFalse(driver.findElement(confirmOtp).isEnabled());
    }

    public void clickConfirmSmsCode() {
        Assert.assertTrue(driver.findElement(confirmOtp).isEnabled());
        driver.findElement(confirmOtp).click();
    }

    public void closeSmsCodeConfirmForm() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(closeOtpConfirm)).click();
    }

    public void fillSmsCode(String otp) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for (int i = 0; i < otp.length(); i++) {
            String otpInput = String.format("/html/body/main/div[2]/div/form/div/div/div/div[1]/input[%d]", i + 6);
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(otpInput))).sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

    public void checkSmsCodeConfirmError() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement otpError = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(otpConfirmError));
        Assert.assertEquals(otpError.getText(), "Неверный код");
    }
}