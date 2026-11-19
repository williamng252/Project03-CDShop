<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="en_US" />
<c:url var="cartUrl" value="/cart" />
<c:url var="homeUrl" value="/index.html" />
<c:url var="cssUrl" value="/styles/main.css" />
<c:url var="checkoutUrl" value="/cart"><c:param name="action" value="checkout" /></c:url>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your cart</title>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>
<h1>Your cart</h1>
<c:choose>
    <c:when test="${empty cart.items}">
        <p>Your cart is empty.</p>
    </c:when>
    <c:otherwise>
        <table class="cart-table">
            <thead>
            <tr>
                <th scope="col">Quantity</th>
                <th scope="col">Description</th>
                <th scope="col" class="price">Price</th>
                <th scope="col" class="price">Amount</th>
                <th scope="col" aria-label="Actions"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <form action="${cartUrl}" method="post" class="quantity-form">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="productCode" value="${item.product.id}">
                            <input type="number" name="quantity" value="${item.quantity}"
                                   min="0" max="2147483647" step="1" required
                                   aria-label="Quantity for ${item.product.id}">
                            <button type="submit">Update</button>
                        </form>
                    </td>
                    <td><c:out value="${item.product.description}" /></td>
                    <td class="price"><fmt:formatNumber value="${item.product.price}" type="currency" currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" /></td>
                    <td class="price"><fmt:formatNumber value="${item.amount}" type="currency" currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" /></td>
                    <td>
                        <form action="${cartUrl}" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productCode" value="${item.product.id}">
                            <button type="submit">Remove Item</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="3" scope="row">Total</th>
                <td class="price"><fmt:formatNumber value="${cart.total}" type="currency" currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" /></td>
                <td></td>
            </tr>
            </tfoot>
        </table>
    </c:otherwise>
</c:choose>
<nav class="cart-links" aria-label="Cart navigation">
    <a href="${homeUrl}">Continue Shopping</a>
    <c:if test="${not empty cart.items}"><a href="${checkoutUrl}">Checkout</a></c:if>
</nav>
</body>
</html>
