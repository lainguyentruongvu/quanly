package com.example.quanlybenhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.LichKhamDao;
import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Entity.NhanVien;

@Service
public class LichKhamService {
    @Autowired
    private LichKhamDao lichKhamDao;

    public void save(LichKham lichKham) {
        lichKhamDao.save(lichKham);
    }

    // Lấy danh sách lịch khám theo trạng thái
    public List<LichKham> layLichKhamTheoTrangThai(String trangThai) {
        return lichKhamDao.findByTrangThai(trangThai);
    }

    public List<LichKham> findByBenhNhan(BenhNhan benhNhan) {
        return lichKhamDao.findByBenhNhan(benhNhan);
    }
     // Lấy danh sách lịch khám chờ xác nhận (trạng thái "Chờ xác nhận")
    public List<LichKham> getLichKhamChoXacNhan() {
        return lichKhamDao.findByTrangThai("Chờ xác nhận");
    }

    // Phương thức xác nhận lịch khám, gán thông tin nhân viên vào lịch khám
    public void xacNhanLichKham(int maLichKham, String trangThai, String ghiChu, NhanVien nhanVien) {
        Optional<LichKham> optional = lichKhamDao.findById(maLichKham);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            lichKham.setTrangThai("chờ bác sĩ xác nhận");
            lichKham.setGhiChu(ghiChu);
            // Gán đối tượng nhân viên xác nhận vào lịch khám
            lichKham.setNhanVien(nhanVien);
            lichKhamDao.save(lichKham);
        } else {
            throw new RuntimeException("Không tìm thấy lịch khám với mã: " + maLichKham);
        }
    }

    public List<LichKham> getLichKhamChoXacNhanTheoBacSi(BacSi bacSi) {
        // Giả sử bạn đã định nghĩa phương thức trong Dao: findByBacSiAndTrangThai(bacSi, "Chờ bác sĩ xác nhận")
        return lichKhamDao.findByBacSiAndTrangThai(bacSi, "Chờ bác sĩ xác nhận");
    }

    // Cập nhật lịch khám khi bác sĩ xác nhận hoặc hủy
    public void xacNhanLichKhamTheoBacSi(int maLichKham, String trangThai, String ghiChu, BacSi bacSi) {
        Optional<LichKham> optional = lichKhamDao.findById(maLichKham);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            lichKham.setTrangThai(trangThai);
            lichKham.setGhiChu(ghiChu);
            // Gán bác sĩ vào lịch khám (nếu chưa được gán hoặc cập nhật lại)
            lichKham.setBacSi(bacSi);
            lichKhamDao.save(lichKham);
        } else {
            throw new RuntimeException("Không tìm thấy lịch khám với mã: " + maLichKham);
        }
    }
}
