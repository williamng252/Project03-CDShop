package cdshop.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import cdshop.business.Cart;
import cdshop.business.Product;
import cdshop.data.ProductCatalog;

public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String productCode = request.getParameter("productCode");
        if (action == null) {
            action = "add";
        }
        if (!"add".equals(action) && !"update".equals(action) && !"remove".equals(action)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported cart action.");
            return;
        }
        if (productCode == null || productCode.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A product code is required.");
            return;
        }
        Product product = ProductCatalog.findByCode(productCode);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown CD product code.");
            return;
        }

        int quantity = 0;
        if ("update".equals(action)) {
            String value = request.getParameter("quantity");
            if (value == null || !value.matches("[0-9]+")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Quantity must be a whole number from 0 to 2147483647.");
                return;
            }
            try {
                quantity = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Quantity is too large. Maximum quantity is 2147483647.");
                return;
            }
        }

        HttpSession session = request.getSession();
        synchronized (session) {
            Cart cart = (Cart) session.getAttribute("cart");
            switch (action) {
                case "add":
                    if (cart == null) {
                        cart = new Cart();
                        session.setAttribute("cart", cart);
                    }
                    try {
                        cart.add(product);
                    } catch (ArithmeticException ex) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                "This CD has reached the maximum quantity.");
                        return;
                    }
                    break;
                case "update":
                    if (cart == null || cart.findItem(productCode) == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                                "This CD is not in your cart. Add it before updating its quantity.");
                        return;
                    }
                    cart.update(productCode, quantity);
                    break;
                case "remove":
                    if (cart != null) {
                        cart.remove(productCode);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected cart action.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Cart cart = null;
        if (session != null) {
            synchronized (session) {
                cart = (Cart) session.getAttribute("cart");
            }
        }
        request.setAttribute("cart", cart);
        if ("checkout".equals(request.getParameter("action"))) {
            if (cart == null || cart.getItems().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        }
    }
}
