package Pages.Suite1;

import Helpers.Helpers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import Helpers.Configurations;


public class Page_Rosario3 {

    public WebDriver driver; //Declaro Objeto Driver.
    public  Helpers helpers;
    public Configurations configurations;

    public Page_Rosario3(WebDriver driver){  //Metodo Constructor de la Clase.
        this.driver = driver;  //
        helpers = new Helpers(driver);
        configurations = new Configurations(driver);
    }


    /**
     * ---------------------------------------------------------------------------------------------------------
     *  WebElements // WebElements // WebElements // WebElements // WebElements // WebElements // WebElements //
     * ---------------------------------------------------------------------------------------------------------
     */

    public By Temperatura_text = By.tagName("temperatura");




    /**
     * -----------------------------------------------------------------------------------------------------------
     *  Funciones // Funciones //  Funciones //  Funciones //  Funciones //  Funciones //  Funciones //  Funciones
     * -----------------------------------------------------------------------------------------------------------
     */





}
