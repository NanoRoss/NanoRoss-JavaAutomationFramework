
##Autor: Mariano Panella

## Contexto:

El siguiente repositorio contendrá pruebas automatizadas de circuitos funcionales básicos los cuales requieren de su ejecución manual siempre que un equipo de trabajo genere un deploy.
La entrega de valor radica principalmente en ahorrar tiempo de ejecución repetitiva y manual de estos procesos cores al negocio.

-------------------------------------------
## Tecnología:

El framework está compuesto principalmente por:

Java: Como lenguaje de programación.

TestNG: Como framework para assertions/configuration de los tests.

Maven: Como herramienta de build y gestor de dependencias.

WebDriver: como la herramienta de automatización del navegador

POM: Como patrón de diseño de pruebas.

IntelliJ: como IDE.

-------------------------------------------
## Ejecución:

De forma local teniendo el proyecto, sus dependencias y las variables de entorno tanto de Java como de Maven; se pueden ejecutar los test suites por consola:

Ej:
"cd C:\Users\mpanella\Dropbox\sarasa" --> Path donde se encuentra el archivo POM.XML del proyecto.

"mvn -Dtest=testName test" --> Comando Maven que rastrea el test segun nombre y lo ejecuta.

-------------------------------------------
## Reportes:

Luego de la Ejecución de un TestSuite se generará de forma automática un reporte donde se detallan los resultados.
Los mismos se guardan en el path \src\reports dentro del proyecto y si se requiere se puede configurar para que se envíen automáticamente por email. 

