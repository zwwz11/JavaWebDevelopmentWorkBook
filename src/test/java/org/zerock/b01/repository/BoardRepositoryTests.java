package org.zerock.b01.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title_" + i)
                    .content("content_" + i)
                    .writer("user_" + i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info("board = {}", board);
    }

    @Test
    public void testUpdate() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("updatedTitle_100", "updatedContent_100");
        boardRepository.save(board);
    }

    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging() {
        // 1 Page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : {}", result.getTotalElements());
        log.info("total pages : {}", result.getTotalPages());
        log.info("page number : {}", result.getNumber());
        log.info("page size : {}", result.getSize());

        List<Board> todoList = result.getContent();
        todoList.forEach(board -> log.info("board : {}", board));
    }

    @Test
    public void testSearch1() {
        // 2page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchAll2() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        log.info("result.getTotalPages() = {}", result.getTotalPages());
        log.info("result.getSize() = {}", result.getSize());
        log.info("result.getNumber() = {}", result.getNumber());
        log.info("result.hasPrevious() = {}, result.hasNext() = {}", result.hasPrevious(), result.hasNext());

        result.getContent().forEach(board -> log.info("board = {}", board));
    }
}
