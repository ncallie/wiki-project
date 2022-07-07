# wiki-project
https://drive.google.com/file/d/13tzvAAExHgWxZODXu6vkSH8pzQx_RfCL/view?usp=sharing
- Ошибка, не работает из под Windows (только Linux, Mac)

- Настройка файла конфигурации `/Wiki/src/main/resources/application.properties`
  - указать данные для подключения к БД `url` `username` `password`
  - указать путь до папки хранения dump files `upload.path`

- Запуск
  - С помощью Maven из корневой директории проекта
    - `mvn clean package`
    - `java -jar target/Wiki-0.0.1-SNAPSHOT.jar`
    
  - С помощью среды разработки IntelliJ IDEA
    - запустить как обычный java-проект <img width="16" alt="Screen Shot 2022-07-07 at 1 08 12 PM" src="https://user-images.githubusercontent.com/92088165/177751204-a7c66906-f9c1-4037-b950-44e64872f665.png">

- Импорт данных
  - выгрузить [dump](https://dumps.wikimedia.org/other/cirrussearch/current/ruwikiquote-20220627-cirrussearch-general.json.gz)
  - Перейти по адресу `http://localhost:8080/`, добавить файл
