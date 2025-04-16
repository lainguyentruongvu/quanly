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

    // Hiển thị trang "Lịch khám đã xác nhận" với 2 bảng:
    // 1. Lịch khám đang chờ ("Đã xác nhận bởi bác sĩ")
    // 2. Lịch khám có bệnh nhân đã đến
    @GetMapping("/da-xac-nhan")
    public String hienThiLichKhamDaXacNhan(Model model, RedirectAttributes redirectAttributes) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập!");
            return "redirect:/bacsi/login";
        }

        List<LichKham> lichKhamsDaXacNhan = lichKhamService.getLichKhamDaXacNhanTheoBacSi(bacSiDangNhap);
        List<LichKham> benhNhanDaDen = lichKhamService.getLichKhamBenhNhanDaDenTheoBacSi(bacSiDangNhap);
        List<LichKham> hoanThanhLichKham = lichKhamService.getHoanThanhLichKham(bacSiDangNhap);
        model.addAttribute("lichKhams", lichKhamsDaXacNhan);
        model.addAttribute("benhNhanDen", benhNhanDaDen);
        model.addAttribute("hoanThanhLichKham", hoanThanhLichKham);
        return "bacsi/lichkham-da-xacnhan"; // Template nằm trong: src/main/resources/templates/bacsi/lichkham-da-xacnhan.html
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
    // // Đánh dấu lịch khám khi bệnh nhân đã đến
    // @GetMapping("/benhnhan-den")
    // public String danhDauBenhNhanDaDen(@RequestParam("maLichKham") Integer maLichKham,
    //                                     RedirectAttributes redirectAttributes) {
    //     boolean success = lichKhamService.danhDauBenhNhanDaDen(maLichKham);
    //     if (success) {
    //         redirectAttributes.addFlashAttribute("successMessage", "✅ Đã đánh dấu bệnh nhân đã đến!");
    //     } else {
    //         redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể đánh dấu bệnh nhân đã đến!");
    //     }
    //     return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    // }

    // // Huỷ lịch khám
    // @GetMapping("/huy-lich")
    // public String huyLichKham(@RequestParam("maLichKham") Integer maLichKham,
    //                           RedirectAttributes redirectAttributes) {
    //     boolean success = lichKhamService.huyLichKham(maLichKham);
    //     if (success) {
    //         redirectAttributes.addFlashAttribute("successMessage", "✅ Lịch khám đã được huỷ!");
    //     } else {
    //         redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể huỷ lịch khám!");
    //     }
    //     return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    // }

    // // Điều hướng đến trang thêm dịch vụ cho lịch khám (nếu cần)
    // @GetMapping("/dichvu/them")
    // public String themDichVu(@RequestParam("maLichKham") Integer maLichKham, Model model, RedirectAttributes redirectAttributes) {
    //     LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
    //     if (lichKham == null) {
    //         redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch khám!");
    //         return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    //     }
    //     model.addAttribute("lichKham", lichKham);
    //     return "bacsi/them-dichvu"; // Template thêm dịch vụ, ví dụ: src/main/resources/templates/bacsi/them-dichvu.html
    // }
    // Endpoint đánh dấu bệnh nhân đã đến
    @GetMapping("/benhnhan-den")
    public String danhDauBenhNhanDaDen(@RequestParam("maLichKham") Integer maLichKham,
                                        RedirectAttributes redirectAttributes) {
        boolean success = lichKhamService.danhDauBenhNhanDaDen(maLichKham);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Đã đánh dấu bệnh nhân đã đến!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể đánh dấu bệnh nhân đã đến!");
        }
        return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    }

    @GetMapping("/hoanthanh")
    public String hoanThanhLichKham(@RequestParam("maLichKham") Integer maLichKham,
                                        RedirectAttributes redirectAttributes) {
        boolean success = lichKhamService.hoanThanhLichKham(maLichKham);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Đã đánh dấu hoàn thành lịch khám!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể đánh dấu không hoàn thành !");
        }
        return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    }

    // Endpoint huỷ lịch khám
    @GetMapping("/huy-lich")
    public String huyLichKham(@RequestParam("maLichKham") Integer maLichKham,
                              RedirectAttributes redirectAttributes) {
        boolean success = lichKhamService.huyLichKham(maLichKham);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Lịch khám đã được huỷ!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể huỷ lịch khám!");
        }
        return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    }

    // Endpoint điều hướng đến trang thêm dịch vụ cho lịch khám
    @GetMapping("/dichvu/them")
    public String themDichVu(@RequestParam("maLichKham") Integer maLichKham,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
        if (lichKham == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch khám!");
            return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
        }
        model.addAttribute("lichKham", lichKham);
        return "bacsi/them-dichvu"; // Template thêm dịch vụ: src/main/resources/templates/bacsi/them-dichvu.html
    }

    // (Tùy chọn) Endpoint hiển thị trang lịch khám chờ xác nhận
    @GetMapping("/cho-xac-nhan")
    public String hienThiLichKhamChoXacNhan(Model model, RedirectAttributes redirectAttributes) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập!");
            return "redirect:/bacsi/login";
        }
        // Giả sử lichKhamService có phương thức lấy lịch khám chờ xác nhận theo bác sĩ
        model.addAttribute("lichKhams", lichKhamService.getLichKhamChoXacNhanTheoBacSi(bacSiDangNhap));
        return "bacsi/lichkham-xacnhan"; // Template hiển thị lịch khám chờ xác nhận
    }
}
