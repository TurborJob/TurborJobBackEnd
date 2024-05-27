package com.turborvip.core.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PaymentService {
    String doPostVnPay(HttpServletRequest req, HttpServletResponse resp, int amount) throws ServletException, IOException;
}
