## Проект по предмету "Конструирование компиляторов".
### Описание.
Приложение для трансляции файлов формата JSON в Java структуры данных, написанное с использованием фреймворка для генерации парсеров текста - ANTLR v4.
____
### Пример работы.
Для данного JSON'а:
```json
{
  "student1": {
    "id": 12345678,
    "prename": "Kirill",
    "surname": "Fedorov",
    "address": {
      "street": "Sovietskaya",
      "postcode": 345895
    },
    "email": "kirill@mail.ru"
  },

  "student2": {
    "id": 67454358,
    "prename": "Egor",
    "surname": "Vasiliev",
    "address": {
      "street": "Lenina",
      "postcode": 682396
    },
    "email": "vasiliev_egor@google.com"
  }
}
```
программа выведет следующий результат (при выводе полученной структуры данных в консоль):
```
{student2={address={street=Lenina, postcode=682396}, surname=Vasiliev, id=67454358, prename=Egor, email=vasiliev_egor@google.com}, student1={address={street=Sovietskaya, postcode=345895}, surname=Fedorov, id=12345678, prename=Kirill, email=kirill@mail.ru}
```
>>>> Примечание! При выводе структуры данных в консоль, объекты и их данные могут располагаться в хаотичном порядке, отличающемся от исходного (отличительная черта этой структуры данных). Но так как в Map значения получаются по уникальному ключу, порядок не важен, и получаемые данные будут корректными.
