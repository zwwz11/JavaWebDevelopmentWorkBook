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
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository repository;

    @Test
    public void testInsert() {
        Long bno = 102L;
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder().board(board).replyText("댓글...").replyer("hansol").build();
        repository.save(reply);
    }

    @Test
    public void testBoardReplies() {
        Long bno = 102L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
        Page<Reply> result = repository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply -> {
            log.info("reply = {}", reply);
        });
    }

}