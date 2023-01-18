package com.welldev.TechBox.user.controller;


import com.welldev.TechBox.constant.ADMIN_URL;
import com.welldev.TechBox.user.dao.UserDao;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserDao userDao;

    @DeleteMapping(ADMIN_URL.USER_DELETE_BY_EMAIL)
    public ResponseEntity<HttpStatus> userDelete(@PathVariable int uid) {
        try {
            userDao.deleteById(uid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
