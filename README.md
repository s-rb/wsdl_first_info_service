## SOAP Web service using the WSDL-First approach for OSGI environment

![Java](https://img.shields.io/badge/-Java-05122A?style=flat&logo=Java&logoColor=FFA518) ![WebService](https://img.shields.io/badge/-WebService-05122A?style=flat) ![SOAP](https://img.shields.io/badge/-SOAP-05122A?style=flat) ![CXF](https://img.shields.io/badge/-CXF-05122A?style=flat) ![OSGI](https://img.shields.io/badge/-OSGI-05122A?style=flat) ![Maven](https://img.shields.io/badge/-Maven-05122A?style=flat&logo=apachemaven&logoColor=fffffb) ![ApacheServiceMix](https://img.shields.io/badge/-Apache_Service_Mix-05122A?style=flat) ![Apache Aries](https://img.shields.io/badge/-Apache_Aries-05122A?style=flat) ![SoapUI](https://img.shields.io/badge/-SoapUI-05122A?style=flat)

### Main technologies:
- Java 8;
- Dependency management and build system - Apache Maven 3;
- Web service development library - Apache CXF 3.0.5;
- CXF codegen-plugin;
- Runtime environment - OSGI;
- Application server for testing - Apache Service Mix 5.4.1;
- DI library - Apache Aries;
- Standards: JAX-WS 2.1; WSDL - 1.1; SOAP 1.1;
- Testing - SmartBear SoapUI-5.6.0 (+Java 11);

The service is developed according to the WSDL-first approach.
Code generation using cxf-codegen-plugin.
To generate Java code (data types, interfaces) in the target directory based on the created WSDL - run `mvn clean install`.
The result will be in target\cxf.
Building a bundle to work in the OSGI environment using the maven-bundle-plugin.
The interface implementation is specified using Blueprint configuration, Apache Aries library.
Incoming and outgoing messages are logged (CXF feature - logging).

### Workflow with the service:
- Generate WSDL-first source code (wsdl2java) - `mvn cxf-codegen:wsdl2java`
- Build - `mvn clean install`. A deployable bundle is created in the target directory.
- Start Apache Service Mix 5.4.1.
- Deploy the bundle in Service Mix `osgi:install -s file:/PATH_TO_FILE/soap_service-1.0.0.jar`
- Start the bundle `osgi:start bundle_id`
- View the log - `log:display`. View log in real-time - `log:tail`.
- Project for SoapUI - import the WSDL from the resources folder into SoapUI.
- The service is available at http://localhost:9090, and the corresponding WSDL is available at `http://localhost:9090/JavaInfoPort?wsdl`.