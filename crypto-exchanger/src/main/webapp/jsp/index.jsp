<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Crypto Exchange</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* визуал для выбранной валюты */
        .currency-item.selected, .currency-item.active {
            border: 2px solid #6c63ff;
            background-color: #383838;
        }
        /* чтобы btn-accent в фильтре выглядел явно */
        .currency-filter .btn-accent {
            background-color: #6c63ff;
            color: #fff;
            border-color: #6c63ff;
        }
    </style>
</head>
<body>
<header class="header py-3">
    <div class="container d-flex align-items-center justify-content-between">
        <a href="${pageContext.request.contextPath}/" class="header_logo d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/img/Obsidian.svg" alt="Logo" class="logo me-2">
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
        <a href="/profile/KYC" class="btn btn-outline-light btn-sm">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                <path fill-rule="evenodd" d="M14 14s-1-1.5-6-1.5S2 14 2 14s1-4 6-4 6 4 6 4z"/>
            </svg>
        </a>
    </div>
</header>

<main class="exchange py-5">
    <div class="container d-lg-flex gap-4">

        <!-- Блок: Отдаёте -->
        <div class="exchange-box flex-fill p-4" id="buyBox">
            <h5 class="mb-3 d-flex align-items-center justify-content-between">
                <span>Отдаёте</span>
                <small class="text-accent">Скидка 0%</small>
            </h5>

            <input id="amountGiveInput" type="text" class="form-control form-control-lg mb-2 numeric"
                   value="${sessionScope.amountGive != null ? sessionScope.amountGive : ''}"
                   placeholder="0.0000">

            <div class="d-flex justify-content-between mb-4 text-muted small">
                <span>Мин: 0.00038141</span>
                <span>Макс: 13.8921</span>
            </div>

            <div class="currency-filter mb-3" id="buyFilter">
                <button class="btn btn-sm me-2" data-filter="Все">Все</button>
                <button class="btn btn-sm btn-outline-light me-2" data-filter="USD">USD</button>
                <button class="btn btn-sm btn-outline-light me-2" data-filter="RUB">RUB</button>
                <button class="btn btn-sm btn-outline-light" data-filter="Coin">Coin</button>
            </div>

            <input type="text" class="form-control mb-3" placeholder="Поиск валюты">

            <ul id="buyList" class="currency-list list-unstyled mb-0">
                <c:set var="defaultGive" value="${sessionScope.selectedGive != null ? sessionScope.selectedGive : 'ETH'}" />
                <c:forEach var="currency" items="${currenciesForSell}">
                    <li class="currency-item d-flex align-items-center py-2 px-2 rounded hover-bg
                        <c:if test='${currency.effectiveCode == defaultGive}'> selected</c:if>"
                        data-type="give"
                        data-code="${currency.effectiveCode}">
                        <img src="${pageContext.request.contextPath}/img/${currency.effectiveCode}.svg" class="icon me-2" alt="${currency.effectiveCode}">
                        <div>
                            <div>${currency.name}</div>
                            <small class="custom-muted">${currency.code}</small>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Блок: Получаете -->
        <div class="exchange-box flex-fill p-4" id="sellBox">
            <h5 class="mb-3">Получаете</h5>

            <input id="amountReceiveInput" type="text" class="form-control form-control-lg mb-2 numeric"
                   value="${sessionScope.amountReceive != null ? sessionScope.amountReceive : ''}"
                   placeholder="0.0000">

            <div class="d-flex justify-content-between mb-4 text-muted small">
                <span>Курс: 1 BTC = 7 848 713 RUB</span>
            </div>

            <div class="currency-filter mb-3" id="sellFilter">
                <button class="btn btn-sm me-2" data-filter="Все">Все</button>
                <button class="btn btn-sm btn-outline-light me-2" data-filter="USD">USD</button>
                <button class="btn btn-sm btn-outline-light me-2" data-filter="RUB">RUB</button>
                <button class="btn btn-sm btn-outline-light" data-filter="Coin">Coin</button>
            </div>

            <input type="text" class="form-control mb-3" placeholder="Поиск валюты">

            <ul id="sellList" class="currency-list list-unstyled mb-0">
                <c:set var="defaultReceive" value="${sessionScope.selectedReceive != null ? sessionScope.selectedReceive : 'BTC'}" />
                <c:forEach var="currency" items="${currenciesForBuy}">
                    <li class="currency-item d-flex align-items-center py-2 px-2 rounded hover-bg
                        <c:if test='${currency.code == defaultReceive}'> selected</c:if>"
                        data-type="receive"
                        data-code="${currency.code}">
                        <img src="${pageContext.request.contextPath}/img/${currency.code}.svg" class="icon me-2" alt="${currency.code}">
                        <div>
                            <div>${currency.name}</div>
                            <small class="custom-muted">Резерв: ${currency.amount}</small>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Блок: Реквизиты (форма detailsForm отправляет все поля на /home) -->
        <div class="exchange-box details p-4">
            <h5 class="mb-4">Ваши реквизиты</h5>

            <form id="detailsForm" action="${pageContext.request.contextPath}/home" method="post">
                <div class="mb-3">
                    <c:choose>
                        <c:when test="${not empty sessionScope.email}">
                            <input type="email"
                                   class="form-control"
                                   name="email"
                                   value="${sessionScope.email}"
                                   readonly>
                        </c:when>
                        <c:otherwise>
                            <input type="email"
                                   class="form-control"
                                   name="email"
                                   placeholder="Email"
                                   required>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" name="fio" placeholder="ФИО получателя" required>
                </div>
                <div class="mb-3">
                    <input type="text" class="form-control" name="card" placeholder="Номер кошелька" required>
                </div>
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="agree" required>
                    <label class="form-check-label small" for="agree">
                        Я согласен с политикой AML/KYC и правилами пополнения кошелька
                    </label>
                </div>

                <!-- скрытые поля, заполняются скриптом перед отправкой -->
                <input type="hidden" name="currencyGive" id="details_currencyGive">
                <input type="hidden" name="currencyReceive" id="details_currencyReceive">
                <input type="hidden" name="amountGive" id="details_amountGive">
                <input type="hidden" name="amountReceive" id="details_amountReceive">

                <button class="btn-accent btn-submit w-100" type="submit">Далее</button>
            </form>
        </div>

    </div>

    <!-- Скрытая форма для POST при выборе валют (отправляется при клике на валюту) -->
    <form id="currencyForm" action="${pageContext.request.contextPath}/home" method="post" class="d-none">
        <input type="hidden" name="currencyGive" id="currencyGive">
        <input type="hidden" name="currencyReceive" id="currencyReceive">
        <input type="hidden" name="amountGive" id="currency_amountGive">
        <input type="hidden" name="amountReceive" id="currency_amountReceive">
    </form>
</main>

<script>
    // exchangeRate из request (преобразуем в число)
    const exchangeRate = parseFloat('<c:out value="${exchangeRate != null ? exchangeRate : 0}"/>') || 0;

    // элементы
    const amountGiveInput = document.getElementById('amountGiveInput');
    const amountReceiveInput = document.getElementById('amountReceiveInput');

    // элементы скрытой формы для клика на валюту
    const currencyForm = document.getElementById('currencyForm');
    const currencyGiveField = document.getElementById('currencyGive');
    const currencyReceiveField = document.getElementById('currencyReceive');
    const currencyAmountGive = document.getElementById('currency_amountGive');
    const currencyAmountReceive = document.getElementById('currency_amountReceive');

    // элементы скрытой формы для деталей (Далее)
    const detailsForm = document.getElementById('detailsForm');
    const details_currencyGive = document.getElementById('details_currencyGive');
    const details_currencyReceive = document.getElementById('details_currencyReceive');
    const details_amountGive = document.getElementById('details_amountGive');
    const details_amountReceive = document.getElementById('details_amountReceive');

    // numeric input validation helper
    function setupNumericInput(input) {
        if (!input) return;
        input.addEventListener('input', () => {
            let v = input.value.replace(/[^0-9.]/g, '');
            const parts = v.split('.');
            if (parts.length > 2) v = parts[0] + '.' + parts.slice(1).join('');
            if (v.startsWith('.')) v = '0' + v;
            input.value = v;
        });
        input.addEventListener('keydown', (e) => {
            if (e.key === '.' && input.value.includes('.')) e.preventDefault();
        });
    }

    setupNumericInput(amountGiveInput);
    setupNumericInput(amountReceiveInput);

    // восстановленные выбранные элементы (сервер пометил их class="selected")
    let selectedGive = document.querySelector('#buyList .currency-item.selected');
    let selectedReceive = document.querySelector('#sellList .currency-item.selected');

    // если не найдено (вдруг) — попробуем другой селектор active
    if (!selectedGive) selectedGive = document.querySelector('#buyList .currency-item.active');
    if (!selectedReceive) selectedReceive = document.querySelector('#sellList .currency-item.active');

    // обновление скрытых полей (для обеих форм)
    function updateHiddenFieldsForCurrencyForm() {
        currencyGiveField.value = selectedGive ? selectedGive.dataset.code : '';
        currencyReceiveField.value = selectedReceive ? selectedReceive.dataset.code : '';
        currencyAmountGive.value = amountGiveInput ? amountGiveInput.value : '';
        currencyAmountReceive.value = amountReceiveInput ? amountReceiveInput.value : '';
    }

    function updateHiddenFieldsForDetailsForm() {
        details_currencyGive.value = selectedGive ? selectedGive.dataset.code : '';
        details_currencyReceive.value = selectedReceive ? selectedReceive.dataset.code : '';
        details_amountGive.value = amountGiveInput ? amountGiveInput.value : '';
        details_amountReceive.value = amountReceiveInput ? amountReceiveInput.value : '';
    }

    // автопересчет: при вводе в amountGive заполняем amountReceive и наоборот
    if (amountGiveInput && amountReceiveInput) {
        amountGiveInput.addEventListener('input', () => {
            if (exchangeRate > 0) {
                const v = parseFloat(amountGiveInput.value || '0') || 0;
                amountReceiveInput.value = (v * exchangeRate).toFixed(6).replace(/\.?0+$/, '');
            } else {
                // если rate=0 — не меняем или очищаем
                // amountReceiveInput.value = '';
            }
        });

        amountReceiveInput.addEventListener('input', () => {
            if (exchangeRate > 0) {
                const v = parseFloat(amountReceiveInput.value || '0') || 0;
                // делим на коэффициент (guard)
                amountGiveInput.value = ((v / exchangeRate) || 0).toFixed(6).replace(/\.?0+$/, '');
            } else {
                // amountGiveInput.value = '';
            }
        });
    }

    // выбор валюты: ставим selected, обновляем hidden и отправляем POST (currencyForm)
    document.querySelectorAll('.currency-item').forEach(item => {
        item.addEventListener('click', (e) => {
            const type = item.dataset.type;
            if (type === 'give') {
                if (selectedGive) selectedGive.classList.remove('selected');
                item.classList.add('selected');
                selectedGive = item;
            } else {
                if (selectedReceive) selectedReceive.classList.remove('selected');
                item.classList.add('selected');
                selectedReceive = item;
            }

            // обновим скрытые поля и отправим валютную форму на сервер
            updateHiddenFieldsForCurrencyForm();

            // маленькая задержка, чтобы UI успел показать selection (опционально можно убрать)
            // но POST тут будет делать редирект и сервер должен сохранить selection в сессии
            currencyForm.submit();
        });
    });

    // Перед отправкой detailsForm (Далее) — обновляем скрытые поля, чтобы сервер получил ВСЕ значения
    if (detailsForm) {
        detailsForm.addEventListener('submit', (e) => {
            updateHiddenFieldsForDetailsForm();
            // форма отправится обычным POST'ом
        });
    }

    // Настройка фильтров (кнопки "Все", "USD", "RUB", "Coin")
    function setupFilter(filterContainerId, listId) {
        const container = document.getElementById(filterContainerId);
        if (!container) return;
        const buttons = Array.from(container.querySelectorAll('button'));
        const list = document.getElementById(listId);
        if (!list) return;
        const items = Array.from(list.querySelectorAll('.currency-item'));

        buttons.forEach(btn => {
            btn.addEventListener('click', () => {
                const filter = btn.dataset.filter;
                items.forEach(it => {
                    const code = (it.dataset.code || '').toUpperCase();
                    if (filter === 'Все' || (filter === 'Coin' && code !== 'USD' && code !== 'RUB') || code === filter) {
                        it.style.display = 'flex';
                    } else {
                        it.style.display = 'none';
                    }
                });
                buttons.forEach(b => b.classList.remove('btn-accent'));
                btn.classList.add('btn-accent');
            });
        });

        // по умолчанию "Все"
        const first = buttons[0];
        if (first) {
            buttons.forEach(b => b.classList.remove('btn-accent'));
            first.classList.add('btn-accent');
        }
    }

    setupFilter('buyFilter', 'buyList');
    setupFilter('sellFilter', 'sellList');

    // Инициализировать скрытые поля на загрузке
    updateHiddenFieldsForCurrencyForm();
    updateHiddenFieldsForDetailsForm();
</script>
</body>
</html>
