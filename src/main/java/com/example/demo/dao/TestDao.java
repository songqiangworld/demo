package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestDao {
    @Update("UPDATE" +
            "            T_HIST_CLAIM_INFO" +
            "        SET ISNOTICE = -1" +
            "            WHERE CLAIMS_NO=#{04500102001807180200301}")
    int update1();
}

