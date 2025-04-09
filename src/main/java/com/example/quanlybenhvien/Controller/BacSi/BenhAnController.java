package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Service.BenhAnService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bacsi/benhan")
public class BenhAnController {

    @Autowired
    private BenhAnService benhAnService;

    // Hiển thị danh sách bệnh án và form thêm/sửa
    @GetMapping("")
    public String listBenhAnByMaBenhNhan(@RequestParam(value = "maBenhNhan", required = false) Integer maBenhNhan, Model model) {
        List<BenhAn> benhAns;
        if (maBenhNhan != null) {
            benhAns = benhAnService.getBenhAnByMaBenhNhan(maBenhNhan);
            model.addAttribute("maBenhNhan", maBenhNhan);
        } else {
            benhAns = benhAnService.getAllBenhAn(); // Lấy tất cả bệnh án nếu không có mã bệnh nhân
        }
        model.addAttribute("benhAns", benhAns);
        model.addAttribute("benhAn", new BenhAn());

        return "bacsi/benhan"; // Hiển thị danh sách bệnh án trong giao diện
    }
    

    // Xử lý thêm hoặc cập nhật bệnh án
    @PostMapping("/save")
    public String saveBenhAn(@ModelAttribute("benhAn") BenhAn benhAn, HttpSession session) {
        Integer maBacSi = (Integer) session.getAttribute("maBacSiDangNhap");
        
        if (maBacSi == null) {
            // Phiên đăng nhập không hợp lệ
            throw new RuntimeException("Không tìm thấy bác sĩ trong session. Vui lòng đăng nhập lại.");
        }
    
        BacSi bacSi = new BacSi();
        bacSi.setMaBacSi(String.valueOf(maBacSi)); // chú ý kiểu dữ liệu
        benhAn.setBacSi(bacSi);
    
        benhAnService.saveBenhAn(benhAn);
        return "redirect:/bacsi/benhan";
    }
    

    // Xóa bệnh án
    @GetMapping("/delete/{id}")
    public String deleteBenhAn(@PathVariable("id") Integer id) {
        if (benhAnService.existsById(id)) {
            benhAnService.deleteBenhAn(id);
        }
        return "redirect:/bacsi/benhan/"; // Chuyển hướng về danh sách bệnh án
    }
}