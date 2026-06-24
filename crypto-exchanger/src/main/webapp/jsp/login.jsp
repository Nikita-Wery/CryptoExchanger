<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Войти в аккаунт</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>

<div class="auth_wrapper">
    <div class="auth_box">

        <h2 class="auth_title">Войти в аккаунт</h2>

        <form action="${pageContext.request.contextPath}/login" method="post" class="auth_form">

            <div class="input_group">
                <label for="email">Email</label>
                <input id="email" type="email" name="email" required>
            </div>

            <div class="input_group password_group">
                <label for="password">Пароль</label>

                <div class="password_wrapper">
                    <input id="password" type="password" name="password" required>
                    <span class="toggle_password" onclick="togglePassword()">
                        👁
                    </span>
                </div>
            </div>

            <button type="submit" class="btn_primary">Войти в аккаунт</button>
        </form>

        <p class="auth_hint">У вас ещё нет аккаунта?</p>

        <a href="${pageContext.request.contextPath}/register" class="btn_secondary">Создать аккаунт</a>

    </div>
</div>

<script>
    function togglePassword() {
        const pass = document.getElementById("password");
        pass.type = pass.type === "password" ? "text" : "password";
    }
</script>

</body>
</html>
