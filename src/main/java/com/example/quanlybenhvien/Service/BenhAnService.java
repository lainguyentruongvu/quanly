package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.BenhAnDao;
import com.example.quanlybenhvien.Entity.BenhAn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BenhAnService {
    @Autowired
    private BenhAnDao benhAnDao;
    public List<BenhAn> getAllBenhAn() {
        return benhAnDao.findAll();
    }
    public Optional<BenhAn> getBenhAnById(Integer id) {
        return benhAnDao.findById(id);
    }
    public void saveBenhAn(BenhAn benhAn) {
        benhAnDao.save(benhAn);
    }
    public void deleteBenhAn(Integer id) {
        benhAnDao.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return benhAnDao.existsById(id);
    }
    public List<BenhAn> getBenhAnByMaBenhNhan(Integer maBenhNhan) {
        return benhAnDao.findByBenhNhan_MaBenhNhan(maBenhNhan);
    }
}
