<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Заявка подтверждена</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/transaction.css">
</head>
<body>

<div class="page_wrapper">

    <div class="transaction_box">

        <!-- Заголовок -->
        <h2 class="tr_title">Заявка: ${transaction.orderId}</h2>

        <!-- Отдаёте / Получаете -->
        <div class="exchange_block">
            <div class="exchange_col">
                <div class="label">Отдаёте</div>
                <div class="value">
                    <img class="currency_icon" src="/img/${transaction.sellCurrency}.png" alt="">
                    ${transaction.amountSell}
                </div>
            </div>

            <div class="exchange_col">
                <div class="label">Получаете</div>
                <div class="value">
                    <img class="currency_icon" src="/img/${transaction.buyCurrency}.png" alt="">
                    ${transaction.amountBuy}
                </div>
            </div>
        </div>

        <!-- Блок Оплаты -->
        <h3 class="pay_title">Оплата</h3>

        <div class="payment_fields">

            <!-- Блок Кошелёк -->
            <div class="payment_block">
                <div class="payment_label">Кошелёк</div>
                <div class="payment_value copy_value" onclick="copyValue(this)">
                    ${transaction.wallet}
                </div>
            </div>

            <!-- Блок Сумма -->
            <div class="payment_block">
                <div class="payment_label">Сумма</div>
                <div class="payment_value copy_value" onclick="copyValue(this)">
                    ${transaction.amountSell}
                </div>
            </div>

        </div>

        <form method="post" action="${pageContext.request.contextPath}/confirmPayment">
            <input type="hidden" name="orderId" value="${transaction.orderId}">
            <button type="submit" class="btn_pay">Я оплатил</button>
        </form>

        <script>
            function copyValue(el) {
                const text = el.textContent.trim();
                navigator.clipboard.writeText(text).then(() => {
                    el.classList.add("copied");
                    setTimeout(() => el.classList.remove("copied"), 700);
                });
            }
        </script>
    </div>

</div>

</body>
</html>
