<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Аккаунт</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/account.css">
    <link rel="stylesheet" href="/css/KYC.css">
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
        <!-- Левая колонка: меню подстраниц -->
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

        <!-- Правая колонка: содержимое подстраницы -->
        <div class="account_right flex-fill">
            <!-- Блок фото профиля -->
            <div class="profile_photo_block page_block mb-4 p-4 rounded-3">
                <h4 class="mb-3">Фото профиля</h4>

                <div class="d-flex align-items-center gap-4">

                    <!-- Фото профиля (заглушка или реальная картинка) -->
                    <div class="profile-image-wrapper">
                        <img src="${pageContext.request.contextPath}/download?id=${sessionScope.profile_img_id}"
                             onerror="this.src='/img/profile.png'"
                             class="rounded-circle border"
                             width="120" height="120">
                    </div>

                    <!-- Форма загрузки нового фото -->
                    <form action="${pageContext.request.contextPath}/profile/KYC"
                          method="post"
                          enctype="multipart/form-data">

                        <input type="file" class="form-control mb-2" name="file" accept="image/*" required>
                        <button class="btn-submit w-100">Загрузить фото</button>
                    </form>
                </div>
            </div>
            <!-- KYC блок -->
            <div class="kyc_block page_block">
                <h3 class="KYC_header mb-3">Введите персональные данные</h3>
                <form action="${pageContext.request.contextPath}/submitKYC" method="post" class="kyc_form p-4 rounded-3">
                    <div class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label">Имя</label>
                            <input type="text" class="form-control" name="first_name" placeholder="Введите имя" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Фамилия</label>
                            <input type="text" class="form-control" name="second_name" placeholder="Введите фамилию" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Отчество</label>
                            <input type="text" class="form-control" name="patronymic_name" placeholder="Введите отчество" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Дата рождения</label>
                            <input type="date" class="form-control" name="date_of_birth" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Номер паспорта</label>
                            <input type="text" class="form-control" name="passport_number" placeholder="Введите номер паспорта" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Серия паспорта</label>
                            <input type="text" class="form-control" name="passport_series" placeholder="Введите серию паспорта" required>
                        </div>
                    </div>

                    <div class="form-check mt-3">
                        <input class="form-check-input" type="checkbox" id="agree" required>
                        <label class="form-check-label small" for="agree">
                            Я согласен с политикой AML/KYC
                        </label>
                    </div>

                    <button type="submit" class="btn-submit mt-4 w-100">Отправить</button>
                </form>
            </div>

<%--            <!-- Другие блоки подстраниц: скрыты по умолчанию -->--%>
<%--            <div class="transactions_block page_block d-none">--%>
<%--                <h3>Сделки</h3>--%>
<%--                <p>Список совершённых сделок будет здесь...</p>--%>
<%--            </div>--%>
<%--            <div class="wallets_block page_block d-none">--%>
<%--                <h3>Кошельки</h3>--%>
<%--                <p>Список кошельков пользователя...</p>--%>
<%--            </div>--%>
        </div>
    </div>
</main>

<script>
    // Переключение подстраниц
    // document.querySelectorAll(".account_btn").forEach(btn => {
    //     btn.addEventListener("click", () => {
    //         // Делаем активной текущую кнопку
    //         document.querySelectorAll(".account_btn").forEach(b => b.classList.remove("active"));
    //         btn.classList.add("active");
    //
    //         // Скрываем все блоки
    //         document.querySelectorAll(".page_block").forEach(block => block.classList.add("d-none"));
    //
    //         // Показать нужный блок
    //         const page = btn.dataset.page;
    //         if(page === "kyc") document.querySelector(".kyc_block").classList.remove("d-none");
    //         if(page === "transactions") document.querySelector(".transactions_block").classList.remove("d-none");
    //         if(page === "wallets") document.querySelector(".wallets_block").classList.remove("d-none");
    //     });
    // });
</script>
</body>
</html>
