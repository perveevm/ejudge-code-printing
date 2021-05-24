# ejudge-code-printing

Source code printing client-server tool for ICPC-style competitions held in eJudge

## Build

Use `./gradlew build && ./gradlew buildJar` for building `.jar` file (located at `build/lib/ejudge-code-printing-VERSION.jar`)

## Run in server mode

You should use such command to run tool in server mode:

`java -jar ejudge-code-printing-VERSION.jar server --config <config_path>`

Here `config_path` is a path to server config file in `.xml` format.

My server config file is described below as an example.

### config.xml

```xml
<?xml version="1.0"?>

<config>
    <host>ejudge.strategy48.ru</host>
    <port>8083</port>

    <printer_html>/etc/ejudge-code-printing/printer.html</printer_html>
    <success_html>/etc/ejudge-code-printing/success.html</success_html>
    <fail_html>/etc/ejudge-code-printing/fail.html</fail_html>

    <users_list>/etc/ejudge-code-printing/users.csv</users_list>
    <tokens_list>/etc/ejudge-code-printing/tokens.xml</tokens_list>
</config>
```

* `host` is your host where server will be located.

* `port` is TCP port where server will be located.

* `printer_html` is `.html` page for printer main page, example is below.

* `success_html` is `.html` page for success message after printing.

* `fail_html` is `.html` page for fail message after printing.

* `users_list` is `.csv` file that describes the list of users permitted to use printer. There should be three columns in that file: login, password and name. Example is below.

* `tokens_list` is `.xml` file that describes clients' tokens.

After starting with described config file there will be created a TCP server at `ejudge.strategy48.ru:8083`. You can use apache proxies to redirect traffic to 80 port. For example, I redirected it to `ejudge.strategy48.ru/printer`.

### printer.html

You can place anything you want at this page, but there must be a `<form>` like this:

```html
<form action="http://ejudge.strategy48.ru/printer/print" method="post">
    <div>
        <label for="login">Логин в ejudge:</label>
        <input name="login" id="login">
    </div>
    <div>
        <label for="password">Пароль в ejudge:</label>
        <input name="password" id="password" type="password">
    </div>
    <div>
        <label for="source">Исходный код:</label>
        <textarea name="source" id="source"></textarea>
    </div>
    <button>Распечатать!</button>
</form>
```

All tag IDs must be the same as in the example. If your server is located at `<url>`, you should write `<url>/print` at form action.

### success.html and fail.html

You can place anything you want at these pages. These pages will be returned to contestant when successful and failed printer query respectively.

### users.csv

Here is an example of `users.csv` table.

```csv
login;password;name
ivan;ivan_password;Иванов Иван
petr;petr_password_998244353;Петров Петр
```

### tokens.xml

Here is an example of `tokens.xml` file.

```xml
<?xml version="1.0"?>
<tokens>
    <token value="abacaba" name="Printer 1"/>
    <token value="abcdefghijklmnopqrstuvwxyz123" name="Printer 2"/>
</tokens>
```

Here `value` is a unique token and `name` is description of token.

## Run in client mode

You should use such command to run tool in server mode:

`java -jar ejudge-code-printing-VERSION.jar client --config <config_path>`

Here `config_path` is a path to client config file in `.xml` format.

My server config file is described below as an example.

### config.xml

```xml
<?xml version="1.0" ?>
<config>
    <token>abacaba</token>
    <url>http://ejudge.strategy48.ru/printer/client</url>
</config>
```

If your server is located at `<url>`, you should write `<url>/client` at URL tag in config.

When you run tool in client mode, it sends POST query to the server every second. If server sends some source code as a response, it will be printed using default system printer. If server sends nothing, it will wait a second.
