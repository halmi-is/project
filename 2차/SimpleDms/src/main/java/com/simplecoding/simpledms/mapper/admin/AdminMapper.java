package com.simplecoding.simpledms.mapper.admin;

import com.simplecoding.simpledms.vo.admin.Admin;
import com.simplecoding.simpledms.vo.common.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {


    List<Admin> selectAdminList(Criteria searchVO);

    int selectAdminListTotCnt(Criteria searchVO);

    int insert(Admin admin);

    Optional<Admin> selectAdmin(int fno);

    int update(Admin admin);

    int delete(int fno);
}
