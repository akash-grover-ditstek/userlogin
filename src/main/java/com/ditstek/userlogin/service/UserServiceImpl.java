package com.ditstek.userlogin.service;

import com.ditstek.userlogin.dto.ChangePassword;
import com.ditstek.userlogin.dto.ErrorResponse;
import com.ditstek.userlogin.dto.loginDto.EmailLoginDto;
import com.ditstek.userlogin.dto.loginDto.MobileNoLoginDto;
import com.ditstek.userlogin.dto.loginDto.UsernameLoginDto;
import com.ditstek.userlogin.entity.User;
import com.ditstek.userlogin.helper.PasswordEncoder;
import com.ditstek.userlogin.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Override
    public ResponseEntity<Object> saveUser(User user) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        if (!user.getFirstName().equals("")) {
            char[] ch = user.getFirstName().toCharArray();
            for (char c : ch) {
                if (!Character.isLetter(c)) {
                    errorResponse.setMsg("Invalid First name");
                    errorResponse.setStatus(String.valueOf(400));
                    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
                }
            }
        }
        if (user.getFirstName().equals("")) {
            errorResponse.setMsg("First name should not blank");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (!user.getLastName().equals("")) {
            char[] ch = user.getLastName().toCharArray();
            for (char c : ch) {
                if (!Character.isLetter(c)) {
                    errorResponse.setMsg("Invalid Last name");
                    errorResponse.setStatus(String.valueOf(400));
                    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
                }
            }
        }

        if (user.getPassword().equals("")) {
            errorResponse.setMsg("Password not blank");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        User userName = userRepo.findByUserName(user.getUserName());
        if (userName != null) {
            errorResponse.setMsg("User name is not available");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        User email = userRepo.findByEmail(user.getEmail());
        if(email!=null){
            errorResponse.setMsg("Email already exists!");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        PasswordEncoder encode = new PasswordEncoder();
        String password = encode.encrypt(user.getPassword());
        user.setPassword(password);
        User userSave = userRepo.save(user);
        String decrypted = encode.decrypt(userSave.getPassword());
        userSave.setPassword(decrypted);
        return new ResponseEntity<>(userSave, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> emailLoginValidate(EmailLoginDto emailLoginDto) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        User userName = userRepo.findByEmail(emailLoginDto.getEmail());
        if (userName == null) {
            errorResponse.setMsg("Email is not available");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            PasswordEncoder encode = new PasswordEncoder();
            if (!encode.decrypt(userName.getPassword()).equals(emailLoginDto.getPassword())) {
                errorResponse.setMsg("Password Invalid");
                errorResponse.setStatus(String.valueOf(400));
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("You are logged in successfully", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<Object> usernameLoginValidate(UsernameLoginDto usernameLoginDto) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        User userName = userRepo.findByUserName(usernameLoginDto.getUserName());
        if (userName == null) {
            errorResponse.setMsg("User is not available");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            PasswordEncoder encode = new PasswordEncoder();
            if (!encode.decrypt(userName.getPassword()).equals(usernameLoginDto.getPassword())) {
                errorResponse.setMsg("Password Invalid");
                errorResponse.setStatus(String.valueOf(400));
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("You are logged in successfully", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<Object> mobileNoLoginValidate(MobileNoLoginDto mobileNoLoginDto) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        User userName = userRepo.findByUserName(mobileNoLoginDto.getMobileNo());
        if (userName == null) {
            errorResponse.setMsg("Mobile No is not available");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            PasswordEncoder encode = new PasswordEncoder();
            if (!encode.decrypt(userName.getPassword()).equals(mobileNoLoginDto.getPassword())) {
                errorResponse.setMsg("Password Invalid");
                errorResponse.setStatus(String.valueOf(400));
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("You are logged in successfully", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<Object> changePassword(ChangePassword changePassword) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        PasswordEncoder pe = new PasswordEncoder();
        if(!changePassword.getPassword().equals(changePassword.getConfirmPassword())){
            errorResponse.setMsg("Both Password is not equal");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        User user = userRepo.findByEmail(changePassword.getEmail());
        if(user == null){
            errorResponse.setMsg("Email not available");
            errorResponse.setStatus(String.valueOf(400));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }else{
            if(pe.decrypt(user.getPassword()).equals(changePassword.getPassword())){
                errorResponse.setMsg("Try again with a password you haven’t used before");
                errorResponse.setStatus(String.valueOf(400));
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }else{
                String password = pe.encrypt(changePassword.getPassword());
                user.setPassword(password);
                userRepo.save(user);
                return new ResponseEntity<>("Password updates successfully", HttpStatus.OK);
            }
        }

    }

    @Override
    public ResponseEntity<Object> updateUserById(Long id, User user) throws Exception{
        User existingUser = userRepo.updateUser(id);
        if(existingUser != null){
            existingUser.setUserName(user.getUserName());
            existingUser.setDesignation(user.getDesignation());
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.getUserName(user.getUserName());

        }
        return null;
    }

}
