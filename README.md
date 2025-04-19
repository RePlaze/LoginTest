# LoginTest

Автоматизированные тесты для проверки функциональности входа в систему.

## Требования

- Java
- Maven 3.8.0 или выше
- Google Chrome
- ChromeDriver

## Установка

1. Клонируйте репозиторий:
```bash
git clone [URL репозитория]
```

2. Установите ChromeDriver

3. Установите зависимости:
```bash
mvn clean install
```

## Запуск тестов
```bash
mvn test -Dtest=LoginTests
```

## Зависимости

- Selenium WebDriver
- TestNG
- SLF4J
- Logback