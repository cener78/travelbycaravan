package com.newproject.travelbycaravan.service;

import com.newproject.travelbycaravan.excel.Excel;
import com.newproject.travelbycaravan.model.User;
import com.newproject.travelbycaravan.repository.CaravanRepository;
import com.newproject.travelbycaravan.repository.ReservationRepository;
import com.newproject.travelbycaravan.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelService {
    UserRepository userRepository;
    CaravanRepository caravanRepository;
    ReservationRepository reservationRepository;

    public ByteArrayInputStream loadUser() throws IOException {
       List<User> users=userRepository.findAll();

       return Excel.usersExel(users);
    }
}
