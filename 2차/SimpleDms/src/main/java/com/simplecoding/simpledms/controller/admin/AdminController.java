package com.simplecoding.simpledms.controller.admin;

import com.simplecoding.simpledms.service.admin.AdminService;

import com.simplecoding.simpledms.vo.admin.Admin;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/api/admin/admin")
    public ResponseEntity<?> selectAdminList(Criteria searchVO) {
        log.info("테스트");
        List<?> admins = adminService.selectAdminList(searchVO);
        ResultDto resultDto
                = new ResultDto(admins, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/api/admin/admin")
    public ResponseEntity<?> insert(
            @RequestBody Admin admin
    ) {
        adminService.insert(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/api/admin/admin/{fno}")
    public ResponseEntity<?> selectAdmin(
            @PathVariable int fno
    ) {
        Optional<Admin> dept = adminService.selectAdmin(fno);
        if(dept.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dept.get(), HttpStatus.OK);
    }


    @PutMapping("/api/admin/admin/{fno}")
    public ResponseEntity<?> update(
            @PathVariable int fno,
            @RequestBody Admin admin
    ) {
        adminService.update(admin);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/api/admin/admin/deletion/{fno}")
    public ResponseEntity<?> delete(
            @PathVariable int fno
    ) {
        adminService.delete(fno);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}









