package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Dao.BenhAnDao;
import com.example.quanlybenhvien.Dao.LichKhamDao;
import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Service.BacSiService;
import com.example.quanlybenhvien.Service.BenhAnService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/bacsi/benhan")
public class BenhAnController {
    @Autowired
    private  BenhAnDao benhAnDao;

    @Autowired
    private BenhAnService benhAnService;

    @Autowired
    private BacSiService bacSiService;

    @Autowired
    private LichKhamDao lichKhamDao;

  

    // Hiển thị danh sách bệnh án và form thêm/sửa
    @GetMapping("")
    public String listBenhAnByMaBenhAn(@RequestParam(value = "maBenhAn", required = false) Integer maBenhAn, Model model) {
        List<BenhAn> benhAns = (maBenhAn != null) ? benhAnService.getBenhAnByMaBenhAn(maBenhAn) : benhAnService.getAllBenhAn();
        model.addAttribute("benhAns", benhAns);
        model.addAttribute("benhAn", new BenhAn());
        model.addAttribute("dsLichKham", lichKhamDao.findAll());
        model.addAttribute("dsBacSi", bacSiService.getAllBacSi());
        return "bacsi/benhan";
    }

    // findById
    @GetMapping("/edit/{id}")
public String getBenhAnById(@PathVariable("id") Integer id, Model model) {
    // Lấy bệnh án theo ID
    Optional<BenhAn> benhAnOptional = benhAnService.getBenhAnById(id);
    if (benhAnOptional.isPresent()) {
        model.addAttribute("benhAn", benhAnOptional.get());
    } else {
        model.addAttribute("error", "Không tìm thấy bệnh án với ID: " + id);
        // Truyền danh sách bác sĩ và bệnh nhân để hiển thị dropdown
        model.addAttribute("dsLichKham", lichKhamDao.findAll());
        model.addAttribute("dsBacSi", bacSiService.getAllBacSi());
        return "bacsi/benhan"; // Trả về trang danh sách bệnh án
    }

    // Truyền danh sách bác sĩ và bệnh nhân để hiển thị dropdown
    model.addAttribute("dsLichKham", lichKhamDao.findAll());
    model.addAttribute("dsBacSi", bacSiService.getAllBacSi());
    return "bacsi/benhan"; // Trả về trang sửa bệnh án
}
    


    // Xử lý thêm hoặc cập nhật bệnh án
    @PostMapping("/save")
    public String saveBenhAn(@ModelAttribute BenhAn benhAn) {
        if (benhAn.getMaBenhAn() != null && benhAnDao.existsById(benhAn.getMaBenhAn())) {
            // Cập nhật bệnh án
            BenhAn existingBenhAn = benhAnDao.findById(benhAn.getMaBenhAn()).orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh án."));
            existingBenhAn.setTenBenhAn(benhAn.getTenBenhAn());
            existingBenhAn.setBacSi(benhAn.getBacSi());
            existingBenhAn.setNgayKham(benhAn.getNgayKham());
            existingBenhAn.setTrieuChung(benhAn.getTrieuChung());
            existingBenhAn.setDieuTri(benhAn.getDieuTri());
            existingBenhAn.setGhiChu(benhAn.getGhiChu());
            benhAnDao.save(existingBenhAn);
        } else {
            // Thêm mới bệnh án
            benhAnDao.save(benhAn);
        }
      
    return "redirect:/bacsi/benhan";
}
    

    // Xóa bệnh án
   @GetMapping("/delete/{id}")
    public String deleteBenhAn(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (id == null || !benhAnService.existsById(id)) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bệnh án với ID: " + id);
            return "redirect:/bacsi/benhan";
        }
        benhAnService.deleteBenhAn(id);
        redirectAttributes.addFlashAttribute("success", "Xóa bệnh án thành công!");
        return "redirect:/bacsi/benhan";
    }
}