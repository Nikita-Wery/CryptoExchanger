<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration.css">
    <style>
        /* Можешь перенести в CSS, но для примера оставляю тут */
        .error_message {
            background: #ffe6e6;
            border: 1px solid #ff4d4d;
            color: #d8000c;
            padding: 10px 15px;
            margin-bottom: 15px;
            border-radius: 6px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="auth_wrapper">
    <div class="auth_box">

        <h2 class="auth_title">Регистрация</h2>

        <%-- Блок ошибки, показывается только если атрибут error != null --%>
        <c:if test="${not empty error}">
            <div class="error_message">
                    ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" class="auth_form">

            <div class="input_group">
                <label for="email">Email</label>
                <input id="email" type="email" name="email" required>
            </div>

            <div class="input_group">
                <label for="password">Пароль</label>
                <input id="password" type="password" name="password" required>
            </div>

            <div class="input_group">
                <label for="repeat">Повторите пароль</label>
                <input id="repeat" type="password" name="repeatPassword" required>
            </div>

            <div class="checkbox_group">
                <input type="checkbox" id="rules" required>
                <label for="rules">Я согласен с правилами сайта</label>
            </div>

            <button type="submit" class="btn_primary">Зарегистрироваться</button>
        </form>

        <p class="auth_hint">У вас уже есть аккаунт?</p>

        <a href="${pageContext.request.contextPath}/login" class="btn_secondary">Войти</a>

    </div>
</div>

</body>
</html>
