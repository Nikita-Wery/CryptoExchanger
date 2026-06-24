<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список валют</title>
    <link rel="stylesheet" type="text/css" href="/css/adminstyle.css">
</head>
<body>
<div class="container">

    <div class="user-count-widget">
        <p>Всего пользователей: <span id="usersCount">0</span></p>
    </div>

    <h1>Управление валютами</h1>
    <div class="controls">
        <button class="btn-get-all" onclick="getAllCurrencies()">Получить все валюты</button>
    </div>

    <c:if test="${not empty currencies}">
        <div class="currencies-list">
            <c:forEach var="currency" items="${currencies}">
                <div class="currency-card ${currency.active ? 'active' : 'inactive'}">
                    <div class="currency-header">
                        <h3>${currency.code} - ${currency.name}</h3>
                        <span class="status-badge ${currency.active ? 'active' : 'inactive'}">
                                ${currency.active ? 'Активна' : 'Неактивна'}
                        </span>
                    </div>

                    <div class="currency-details">
                        <div class="detail-row">
                            <span class="label">ID:</span>
                            <span class="value">${currency.currencyId}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Код:</span>
                            <span class="value">${currency.code}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Название:</span>
                            <span class="value">${currency.name}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Резерв:</span>
                            <span class="value amount">${currency.amount}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Описание:</span>
                            <span class="value">${currency.description}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Роль:</span>
                            <span class="value role-${currency.role}">${currency.role}</span>
                        </div>
                        <div class="detail-row">
                            <span class="label">Тип:</span>
                            <span class="value type-${currency.type}">${currency.type}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${empty currencies}">
        <div class="no-data">
            <p>Нет данных о валютах. Нажмите кнопку "Получить все валюты"</p>
        </div>
    </c:if>
</div>

<script>
    function getAllCurrencies() {
        // Здесь будет логика для получения данных с сервера
        // Например, отправка AJAX запроса или переход по ссылке
        window.location.href = '${pageContext.request.contextPath}/adminpanel?action=getAll';
    }
</script>
<script>
    function updateUsersCount() {
        fetch('${pageContext.request.contextPath}/adminpanel?action=getUsersCount')
            .then(response => response.json())
            .then(data => {
                document.getElementById('usersCount').innerText = data.count;
            })
            .catch(error => console.error('Ошибка при получении количества пользователей:', error));
    }

    // Обновляем каждые 5 секунд
    setInterval(updateUsersCount, 5000);

    // Вызываем сразу при загрузке страницы
    updateUsersCount();
</script>
</body>
</html>
