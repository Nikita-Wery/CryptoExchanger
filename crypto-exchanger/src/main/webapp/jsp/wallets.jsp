<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Кошельки</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/wallets.css">
</head>
<body>

<header class="header py-3">
    <div class="container d-flex align-items-center justify-content-between">
        <a href="${pageContext.request.contextPath}/" class="header_logo d-flex align-items-center">
            <img src="/img/Obsidian.svg" alt="Logo" class="logo me-2">
            <span class="fw-bold">CryptoX</span>
        </a>

        <nav>
            <ul class="nav">
                <li class="nav-item"><a href="#" class="nav-link">Правила</a></li>
                <li class="nav-item"><a href="#" class="nav-link">AML/KYC</a></li>
                <li class="nav-item"><a href="#" class="nav-link">Партнёрам</a></li>
                <li class="nav-item"><a href="#" class="nav-link">FAQ</a></li>
                <li class="nav-item"><a href="#" class="nav-link">Магазинам</a></li>
                <li class="nav-item"><a href="#" class="nav-link">Новости</a></li>
                <li class="nav-item"><a href="#" class="nav-link">Контакты</a></li>
            </ul>
        </nav>

        <button class="btn btn-outline-light btn-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                <path fill-rule="evenodd" d="M14 14s-1-1.5-6-1.5S2 14 2 14s1-4 6-4 6 4 6 4z"/>
            </svg>
        </button>
    </div>
</header>

<main class="py-5">
    <div class="container d-lg-flex gap-4">

        <div class="container d-lg-flex gap-4">

            <div class="account_left col-lg-3 mb-4">
                <ul class="options_list list-unstyled p-0">

                    <li class="option_item mb-2">
                        <a href="/profile/KYC"
                           class="account_btn account_link ${selectedPage == 'kyc' ? 'active' : ''}">KYC</a>
                    </li>

                    <li class="option_item mb-2">
                        <a href="/profile/orders"
                           class="account_btn account_link ${selectedPage == 'transactions' ? 'active' : ''}">Сделки</a>
                    </li>

                    <li class="option_item mb-2">
                        <a href="/profile/wallets"
                           class="account_btn account_link ${selectedPage == 'wallets' ? 'active' : ''}">Кошельки</a>
                    </li>

                </ul>
            </div>

        <div class="account_right flex-fill">
            <h3 class="mb-4">Ваши кошельки</h3>

            <ul class="wallets_list">

                <!-- Если кошельков нет -->
                <c:if test="${wallets == null or wallets.isEmpty()}">
                    <li class="no_wallets">У вас пока нет кошельков</li>
                </c:if>

                <!-- Если кошельки есть -->
                <c:if test="${wallets != null and not wallets.isEmpty()}">
                    <c:forEach var="wallet" items="${wallets}">
                        <li class="wallet_item">
                            <div class="wallet_icon">
                                <img src="/img/${wallet.currencyCode}.png" class="icon_currency">
                            </div>

                            <div class="wallet_info">
                                <div class="wallet_block">
                                    <div class="wallet_label">Валюта кошелька</div>
                                    <div class="wallet_value">${wallet.currencyCode}</div>
                                </div>

                                <div class="wallet_block">
                                    <div class="wallet_label">Баланс</div>
                                    <div class="wallet_value">${wallet.balance}</div>
                                </div>

                                <div class="wallet_block">
                                    <div class="wallet_label">Номер кошелька</div>
                                    <div class="wallet_value copy_wallet" onclick="copyToClipboard(this)">
                                            ${wallet.walletNumber}
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </c:if>

            </ul>
        </div>

    </div>
</main>

<script>
    function copyToClipboard(element) {
        const text = element.textContent.trim();
        navigator.clipboard.writeText(text).then(() => {
            element.style.color = '#6c63ff';
            setTimeout(() => element.style.color = '#ddd', 900);
        });
    }
</script>

</body>
</html>
