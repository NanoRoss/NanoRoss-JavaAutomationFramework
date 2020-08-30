package Baseclass;

/**
 * -----------------------------------------------------------------------------------------------------------
 * IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS
 * -----------------------------------------------------------------------------------------------------------
 */
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Helpers.Helpers;
import Helpers.Configurations;
import java.io.IOException;
import javax.mail.MessagingException;

/**
 * -----------------------------------------------------------------------------------------------------------
 * BASECLASS VARIABLE // BASECLASS VARIABLE // BASECLASS VARIABLE // BASECLASS VARIABLE // BASECLASS VARIABLE
 * -----------------------------------------------------------------------------------------------------------
 */

public class BaseClass {
    public  WebDriver driver;
    public  Helpers helpers;
    public Configurations configurations;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;


    /**
     * -----------------------------------------------------------------------------------------------------
     * BaseClass Structure @TestNG:
     *
     * 				BeforeTest       Se ejecuta una sola vez ANTES de ejecutar la suite de tests.
     * 				-------------
     * 				BeforeMethod     Se ejecuta ANTES de cada test.
     * 				TEST 1
     * 				AfterMethod      Se ejecuta DESPUES de cada Test.
     * 				-------------
     * 				BeforeMethod     Se ejecuta ANTES de cada test.
     * 				TEST 2
     * 				AfterMethod      Se ejecuta DESPUES de cada Test.
     * 				-------------
     * 			    AfterClass       Se ejecuta luego de que se ejecuten todos los test de la clase Actual.
     * 			    -------------
     * 				AfterTest        Se ejecuta DESPUES de toda la suite de test.
     * 				AfterSuite       Se ejecuta DESPUES de toda la suite de test.
     * -----------------------------------------------------------------------------------------------------
     */


    @BeforeClass//Se ejecuta una sola vez antes de ejecutar el primer método de prueba en la clase actual.
    public void setReportv2() {
        //ExtendReportsConfig
        String SuiteName = this.getClass().getSimpleName();
        htmlReporter = new ExtentHtmlReporter("src/reports/"+SuiteName+".html");
        htmlReporter.config().setEncoding("uft-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Automation Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation Tester", "Mariano Panella");
        extent.setSystemInfo("Organization", "Organization");
    }



    @BeforeMethod  //Se ejecuta antes de cada test.
    public void setUp(){

        configurations = new Configurations(driver);

        if(configurations.BrowserName.equalsIgnoreCase("Firefox"))
        {
            System.setProperty("webdriver.gecko.driver","src/drivers/geckodriver.exe");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            //firefoxOptions.setHeadless(true);
            firefoxOptions.setCapability("marionette", true);
            driver = new FirefoxDriver(firefoxOptions);

        }
        else if(configurations.BrowserName.equalsIgnoreCase("Chrome"))
        {
            System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            driver = new ChromeDriver(); //Creo nuestro Objeto Chromedriver
        }
        else if(configurations.BrowserName.equalsIgnoreCase("edge"))
        {
            System.setProperty("webdriver.edge.driver", "");
            driver = new EdgeDriver();
        }

        helpers = new Helpers(driver); //Creo nuestro Objeto Helpers
        helpers.SleepSeconds(2);
        driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        helpers.implicitlyWaitMethod(driver,40); //La espera implícita se establece para la vida de la instancia del objeto WebDriver
    }


    @AfterMethod //Se ejecuta luego de cada Test.
    public void getResult(ITestResult result) throws IOException {
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_GREEN = "\u001B[32m";
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "ERROR IN TEST: -->" + result.getName()); // to add name in extent report
            test.log(Status.FAIL, "ERROR IN TEST: -->" + result.getThrowable()); // to add error/exception in extent report
            test.log(Status.FAIL, "ERROR IN TEST: -->" + result.toString()); // to add error/exception in extent report
            test.log(Status.FAIL, "ERROR IN TEST: -->" + result.getSkipCausedBy()); // to add error/exception in extent report
            Helpers help = new Helpers(driver);
            //help.TakeScreenshot("AT FAIL--"+result.getName());
            System.out.println(ANSI_YELLOW+"El Test: "+result.getName()+", Objetivo: "+result.getMethod().getDescription()+"->ERROR | Se genero Captura)"+ANSI_YELLOW);
            driver.quit();
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "SKIPPED TEST: -->" + result.getName());
            Helpers help = new Helpers(driver);
            //help.TakeScreenshot("AT FAIL--"+result.getName());
            System.out.println(ANSI_YELLOW+"El Test: "+result.getName()+", Objetivo: "+result.getMethod().getDescription()+"->SKIPPED | Se genero Captura)"+ANSI_YELLOW);
            driver.quit();
        }
        else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "PASSED TEST: -->" + result.getName());
            System.out.println(ANSI_GREEN+"El Test: "+result.getName()+", Objetivo: "+result.getMethod().getDescription()+"->PASO OK"+ANSI_GREEN);
            driver.quit();

        }

    }

    @AfterClass
    public void endClass()throws MessagingException{
        System.out.println("--AfterClass--");
        extent.flush();
        driver.quit();
        String SuiteName = this.getClass().getSimpleName();
        String to[] = {"ingmarianopanella@gmail.com"};
        helpers.SendReportByEmail("AutomationFWK@gmail.com",to,"Automation Report "+SuiteName+" ("+helpers.GetSelectedEnviroment().toString()+")","",SuiteName);
        System.out.println("Enviando Reporte: "+SuiteName);
    }


    @AfterSuite // Se ejecuta luego de toda la suite de test.
    public void endSuite(){
        System.out.println("AfterSuite");
    }

}
