package com.example.quanlybenhvien.Controller.NhanVien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Service.LichKhamService;

@Controller
@RequestMapping("/nhanvien/trangchu/lichkham")
public class ThanhToanLichKhamController {
    @Autowired
    LichKhamService lichKhamService;

   
}
