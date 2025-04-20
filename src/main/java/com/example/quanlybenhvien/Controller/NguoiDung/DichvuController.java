package com.example.quanlybenhvien.Controller.NguoiDung;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.quanlybenhvien.Entity.DichVu;
import com.example.quanlybenhvien.Service.DichVuService;

@Controller
@RequestMapping("/nguoidung/dichvu")
public class DichvuController {
    
   @Autowired
    private DichVuService dichVuService;
    @GetMapping
    public String getDichVuPage(Model model) {
        List<DichVu> dichVuList = dichVuService.getAllDichVu();
        model.addAttribute("dichVuList", dichVuList);
        return "dichvu";
    }
}