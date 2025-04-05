package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Service.BacSiService;
import com.example.quanlybenhvien.Service.LichKhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/bacsi/trangchu/lichkham")
public class BacSiLichKhamController {

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private BacSiService bacSiService;

    @GetMapping("/cho-xac-nhan")
    public String hienThiLichKhamChoXacNhan(Model model) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            return "redirect:/bacsi/login";
        }
        List<LichKham> lichKhams = lichKhamService.getLichKhamChoXacNhanTheoBacSi(bacSiDangNhap);
        model.addAttribute("bacSi", bacSiDangNhap);
        model.addAttribute("lichKhams", lichKhams);
        return "bacsi/lichkham-xacnhan";  // file template trong: src/main/resources/templates/bacsi/lichkham-xacnhan.html
    }

    @PostMapping("/xac-nhan")
    public String xacNhanLichKham(@RequestParam int maLichKham,
                                  @RequestParam String trangThai,
                                  @RequestParam String ghiChu,
                                  RedirectAttributes redirectAttributes) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin bác sĩ đăng nhập!");
            return "redirect:/bacsi/login";
        }
        try {
            // Ví dụ: nếu bác sĩ xác nhận, trạng thái sẽ là "Đã xác nhận bởi bác sĩ"
            // nếu hủy, trạng thái là "Hủy bởi bác sĩ"
            lichKhamService.xacNhanLichKhamTheoBacSi(maLichKham, trangThai, ghiChu, bacSiDangNhap);
            redirectAttributes.addFlashAttribute("successMessage", "Xác nhận lịch khám thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/bacsi/trangchu/lichkham/cho-xac-nhan";
    }
}
