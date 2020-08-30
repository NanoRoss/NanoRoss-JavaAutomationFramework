package Tests.Suite1;

/**
 * -----------------------------------------------------------------------------------------------------------
 * IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS // IMPORTS
 * -----------------------------------------------------------------------------------------------------------
 */

import Pages.Suite1.Page_Rosario3;
import org.testng.Assert;
import org.testng.annotations.*;
import Helpers.RetryAnalyzer;
import Baseclass.BaseClass;


public class Rosario3 extends BaseClass {

    @Test(description = "Ingreso a Rosario3 y valido que NO haga frio segun la temperatura en la ciudad",retryAnalyzer = RetryAnalyzer.class ,priority = 1)
    public void ValidarTemeratura() {
        test = extent.createTest("Ingreso a Rosario3 y valido que NO haga frio segun la temperatura en la ciudad");
        Pages.Suite1.Page_Rosario3 pg = new Page_Rosario3(driver); //Voy a buscar los WEBElements a la clases Pages (POM).
        driver.get("https://www.rosario3.com/");
        helpers.SleepSeconds(10);
        String A = driver.findElement(pg.Temperatura_text).getText();
        String sA = A.substring(0,2);
        Assert.assertTrue(Integer.parseInt(sA)>=17,"Si es >= 17 No hace frio, else Sí");

    }



}



