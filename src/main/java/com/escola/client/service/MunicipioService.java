package com.escola.client.service;


import com.escola.client.model.entity.User;
import com.escola.client.model.response.MunicipioResponse;
import com.escola.client.security.BaseException;

import java.io.IOException;
import java.util.List;

public interface MunicipioService {

    List<MunicipioResponse> findAll() throws BaseException, IOException;

    MunicipioResponse findByID(String codigo, User user) throws BaseException, IOException;

}
