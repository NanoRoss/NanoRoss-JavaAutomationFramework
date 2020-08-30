package Helpers;

import org.openqa.selenium.WebDriver;


public class Configurations {

    /**
     * ------------------------------------------------------------------------------------------------------------------------------
     * Config de Ambiente: Si tenemos elementos que cambian entre los ambientes como puede ser una URL podemos setearlos acá.
     * Config de Browser: Si queremos ejecutar las pruebas en otro Browser, podemos especiicar en cual.
     * ------------------------------------------------------------------------------------------------------------------------------
     */

    public String actual_environment =  "QA";
    public String BrowserName = "Chrome"; // Chrome , Firefox , Edge

    private WebDriver driver; // Instancio un Objeto Driver para usar.

    public Configurations(WebDriver driver) // Metodo Constructor
    {
        this.driver = driver; //Mi Webdriver


        if (actual_environment == "QA") {
            VariableEnvirmoent1 = "sarasa QA" ;
        }

        
        if (actual_environment == "preproduction") {
            VariableEnvirmoent1 = "sarasa preproduction";
        }
    }


    public  String VariableEnvirmoent1;

}
