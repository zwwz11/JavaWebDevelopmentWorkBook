package org.zerock.b01.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("sample title")
                .content("sample content")
                .writer("sample writer")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("bno = {}", bno);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("updated test title")
                .content("updated test content")
                .build();

        boardService.modify(boardDTO);

        BoardDTO findedBoardDTO = boardService.readOne(101L);
        log.info("findedBoardDTO = {}", findedBoardDTO.toString());
    }

//    @Test
//    public void testRemove() {
//        boardService.remove(100L);
//
//        BoardDTO findedBoardDTO = boardService.readOne(100L);
//        log.info("findedBoardDTO = {}", findedBoardDTO.toString());
//    }

}