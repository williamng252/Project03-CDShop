<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="en_US" />
<c:url var="cartUrl" value="/cart" />
<c:url var="cssUrl" value="/styles/main.css" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>
<h1>Checkout</h1>
<p>Order summary</p>
<table class="checkout-table">
    <thead>
    <tr>
        <th scope="col">Quantity</th>
        <th scope="col">Description</th>
        <th scope="col" class="price">Amount</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${cart.items}">
        <tr>
            <td><c:out value="${item.quantity}" /></td>
            <td><c:out value="${item.product.description}" /></td>
            <td class="price"><fmt:formatNumber value="${item.amount}" type="currency" currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" /></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <th colspan="2" scope="row">Total</th>
        <td class="price"><fmt:formatNumber value="${cart.total}" type="currency" currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" /></td>
    </tr>
    </tfoot>
</table>
<nav class="cart-links" aria-label="Checkout navigation"><a href="${cartUrl}">Back to Cart</a></nav>
</body>
</html>
