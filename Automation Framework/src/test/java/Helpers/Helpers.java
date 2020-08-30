package Helpers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Helpers {

    private WebDriver driver; // Instancio un Objeto Driver para usar.
    public Configurations configurations;

    public Helpers (WebDriver driver) // Metodo Constructor
    {
        this.driver = driver; //Mi Webdriver
        configurations = new Configurations(this.driver);
    }



/////////////////////////////////////////////////////////////////

    public String GetSelectedEnviroment(){
        String SelectedEnviroment = configurations.actual_environment;
        return SelectedEnviroment;
    }

    public void SleepSeconds(int seconds) //El método Thread.sleep (long millis) hace que el subproceso actualmente en ejecución se suspenda n milisegundos.
    {
        try { Thread.sleep(seconds*1000);}
        catch (InterruptedException e)
        {e.printStackTrace(); } // dice qué sucedió y en qué parte del código sucedió esto.
    }

    public void implicitlyWaitMethod(WebDriver driver,int seconds) //Espera que SE cargue una pagina 100%(DOOM)
    {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }


    public void ExplicitWait_presenceOfElement_Method(int seconds, By ElementoaEsperar) //Espera que se cumpla una condicion N durante un Tiempo
    {
        WebDriverWait wait = new WebDriverWait(driver,seconds);
        WebElement WebElement;
        WebElement = wait.until(ExpectedConditions.presenceOfElementLocated(ElementoaEsperar));

        if (WebElement.isDisplayed() == true)
        {System.out.println("Se Valido OK ExplicitWait_presenceOfElement_Method para"+WebElement); }
        else {
            System.out.println("ERROR DE VALIDACION ExplicitWait_presenceOfElement_Method Para:" + WebElement);
        }
    }


    public void TakeScreenshot(String nombre)
    {
        Date fecha = new Date();
        File MyScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {FileUtils.copyFile(MyScreenshot, new File("src\\test\\java\\Screenshots\\screenshot\\"+nombre+fecha.getTime()+".png"));
        System.out.println("Tomando Screenshot: "+nombre+" Fecha: "+fecha.getTime());}
        catch (IOException e) {e.printStackTrace();}
    }


    public boolean ValidarExistenciaByid(String element)
    {
        boolean present;
        try {
            driver.findElement(By.id(element));
            present = true;
        } catch (NoSuchElementException e) { present = false; }

        return present;
    }


    public boolean ValidarExistenciaByXpath(String element)
    {
        boolean present;
        try {
            driver.findElement(By.xpath(element)); present = true;
        } catch (NoSuchElementException e) { present = false; }

        return present;
    }


    public boolean ValidarExistenciaByName(String element)
    {
        boolean present;
        try {
            driver.findElement(By.name(element)); present = true;
        } catch (NoSuchElementException e) { present = false; }

        return present;
    }

    public void AgregarTab(String URL){
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor)driver;
        String NewTab =  "window.open('"+ URL +"')";
        javaScriptExecutor.executeScript(NewTab);
    }

    public String ObtenerHora(){
        LocalDateTime locaDate = LocalDateTime.now();
        int hours  = locaDate.getHour();
        int minutes = locaDate.getMinute();
        int seconds = locaDate.getSecond();
        String hora = + hours  + ":"+ minutes +":"+seconds;
        return hora;
    }


    public  void SendReportByEmail(String from, String tos[], String subject,
                                   String text,String ReportName) throws MessagingException {
        // Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "automationnano88@gmail.com",
                                "Auto88rosS");// change accordingly
                    }
                });

        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));// change accordingly
            for (String to : tos) {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));
            }
            /*
             * for (String cc : ccs)
             * message.addRecipient(Message.RecipientType.CC,new
             * InternetAddress(cc));
             */
            message.setSubject(subject);
            // Option 1: To send normal text message
            // message.setText(text);
            // Option 2: Send the actual HTML message, as big as you like
            // message.setContent("<h1>This is actual message</h1></br></hr>" +
            // text, "text/html");

            // Set the attachment path
            String filename = "src/reports/"+ReportName+".html";

            BodyPart objMessageBodyPart = new MimeBodyPart();
            // Option 3: Send text along with attachment
            objMessageBodyPart.setContent(
                    "<h1> Automation Test Report</h1></br> <h2>Automation  </br> </br>" + text, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(objMessageBodyPart);

            objMessageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            objMessageBodyPart.setDataHandler(new DataHandler(source));
            objMessageBodyPart.setFileName(filename);
            multipart.addBodyPart(objMessageBodyPart);
            message.setContent(multipart);

            // send message
            Transport.send(message);

            System.out.println("message sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }// End of SEND method


    public String ExecuteQueryBD(String database,String query,String ColumLabel)
    {
        //1 Se pasa base sin el Ambiente.
        //2 Se pasa el Query
        //3 Se pasa columna a leer.
        Helpers helpers = new Helpers(driver);
        database = database+"."+helpers.GetSelectedEnviroment();
        String ColumnQueryOutput = "";
        try
        {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://database.server...database="+database+";user=;password=;");
            System.out.println("Conectado a: " + connection.getMetaData().getDatabaseProductName());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                ColumnQueryOutput = resultSet.getString(ColumLabel);
                System.out.println("DB: El resultado es:" + ColumnQueryOutput);
            }

            connection.close();

        }

        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Problema de Conexion o la Query no retorna Resultado");

        }
        return ColumnQueryOutput;
    }

    public void SetZoomOutBrowser(WebDriver driver, int cantidad) throws AWTException {
        Robot robot = new Robot();
        for(int i=0; i<cantidad; i++)
        {   robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public int random_int(){
        int random_int = (int)(Math.random() * (9999 - 1000 + 1) + 1);
        return random_int;
    }




}
