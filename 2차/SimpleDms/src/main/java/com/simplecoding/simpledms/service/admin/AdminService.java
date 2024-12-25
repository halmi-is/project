package com.simplecoding.simpledms.service.admin;

import com.simplecoding.simpledms.mapper.admin.AdminMapper;
import com.simplecoding.simpledms.vo.admin.Admin;
import com.simplecoding.simpledms.vo.common.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper adminMapper;


    public List<Admin> selectAdminList(Criteria searchVO) {
        List<Admin> page = adminMapper.selectAdminList(searchVO);
        int count = adminMapper.selectAdminListTotCnt(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }


    public void insert(Admin admin) {
        adminMapper.insert(admin);
    }


    public Optional<Admin> selectAdmin(int fno) {
        return adminMapper.selectAdmin(fno);
    }


    public void update(Admin admin) {
        adminMapper.update(admin);
    }


    public void delete(int fno) {
        adminMapper.delete(fno);
    }
}
