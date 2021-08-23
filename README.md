## SOAP Веб сервис с использованием подхода WSDL-First для среды OSGI

![Java](https://img.shields.io/badge/-Java-05122A?style=flat&logo=Java&logoColor=FFA518) ![WebService](https://img.shields.io/badge/-WebService-05122A?style=flat) ![SOAP](https://img.shields.io/badge/-SOAP-05122A?style=flat) ![CXF](https://img.shields.io/badge/-CXF-05122A?style=flat) ![OSGI](https://img.shields.io/badge/-OSGI-05122A?style=flat) ![Maven](https://img.shields.io/badge/-Maven-05122A?style=flat&logo=apachemaven&logoColor=fffffb) ![ApacheServiceMix](https://img.shields.io/badge/-Apache_Service_Mix-05122A?style=flat) ![Apache Aries](https://img.shields.io/badge/-Apache_Aries-05122A?style=flat) ![SoapUI](https://img.shields.io/badge/-SoapUI-05122A?style=flat)

### Основные технологии:
* Java 8;
* Система управления зависимости и сборкой - Apache Maven 3;
* Библиотека для разработки веб-сервиса - Apache CXF 3.0.5;
* CXF codegen-plugin;
* Среда исполнения - OSGI;
* Сервер приложений на котором выполнялось тестирование - Apache Service Mix 5.4.1;
* DI библиотека - Apache Aries;
* Стандарты: JAX-WS 2.1; WSDL - 1.1; SOAP 1.1;
* Тестирование - SmartBear SoapUI-5.6.0 (+Java 11);

Сервис разработан в соответствии с подходом WSDL-first.
Генерация кода с использованием cxf-codegen-plugin.
Для генерации Java кода (типы данных, интерфейс) в target по созданному wsdl - выполнить `mvn clean install`. 
Результат будет в target\cxf.
Сборка в бандл для работы в среде OSGI с использованием maven-bundle-plugin.
Имплементация интерфейса указывается с помощью Blueprint конфига, библиотека Apache Aries.
Сообщения входящие и исходящие пишутся в лог (CXF feature - logging).

### Порядок работы с сервисом:
- Сборка - `mvn clean install`. В папке target создается готовый к деплою бандл.
- Запустить Apache Service Mix 5.4.1.
- Задеплоить бандл в сервис микс
`osgi:install -s file:/ПУТЬ_К_ФАЙЛУ/soap_service-1.0.0.jar`
- Посмотреть лог - `log:display`. Смотреть лог онлайн - `log:tail`.
- Проект для SoapUI - импортировать в SoapUI wsdl из папки resources.
- Сервис доступен по адресу `http://localhost:9090` соответственно wsdl - `http://localhost:9090/JavaInfoPort?wsdl`.