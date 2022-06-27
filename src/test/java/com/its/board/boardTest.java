package com.its.board;

import com.its.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class boardTest {
    @Autowired
    private BoardService boardService;



    @Test
    @Transactional
    @Rollback(value = false)
    public void saveTest() {

    }
}
