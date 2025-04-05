package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Service.BenhAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/bacsi/benhan")
public class BenhAnController {

    @Autowired
    private BenhAnService benhAnService;

    // Hiển thị danh sách bệnh án và form thêm/sửa
    @GetMapping("")
    public String listBenhAn(Model model, @RequestParam(value = "id", required = false) Integer id) {
        // Nếu có ID, lấy thông tin bệnh án để sửa
        if (id != null) {
            Optional<BenhAn> benhAnOptional = benhAnService.getBenhAnById(id);
            if (benhAnOptional.isPresent()) {
                model.addAttribute("benhAn", benhAnOptional.get());
            } else {
                model.addAttribute("errorMessage", "Bệnh án không tồn tại!");
            }
        } else {
            model.addAttribute("benhAn", new BenhAn()); // Form thêm mới
        }

        // Hiển thị danh sách bệnh án
        model.addAttribute("benhAns", benhAnService.getAllBenhAn());
        return "bacsi/benhan"; // Sử dụng file HTML duy nhất
    }

    // Xử lý thêm hoặc cập nhật bệnh án
    @PostMapping("/save")
    public String saveBenhAn(@ModelAttribute("benhAn") BenhAn benhAn) {
        benhAnService.saveBenhAn(benhAn);
        return "redirect:/bacsi/benhan/"; // Chuyển hướng về danh sách bệnh án
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