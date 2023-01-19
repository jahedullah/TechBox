package io.welldev.techbox.user.controller;


import io.welldev.techbox.constant.ADMIN_URL;
import io.welldev.techbox.user.dao.IUserDao;
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

    private final IUserDao userDao;

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
