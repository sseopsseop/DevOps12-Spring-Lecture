package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.NoticeBoardDto;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDateTime;

public class NoticeBoardServiceRun {
    public static void main(String[] args) {
        AbstractApplicationContext factory =
                new GenericXmlApplicationContext("root-context.xml");

        BoardService noticeBoardService = factory.getBean("noticeBoardServiceImpl",
                BoardService.class);

//        POST
        BoardDto noticeBoardDto = new NoticeBoardDto();
        noticeBoardDto.setTitle("공지사항 1");
        noticeBoardDto.setContent("공지사항 1 내용");
        noticeBoardDto.setWRITER_ID(1);

        noticeBoardService.post(noticeBoardDto);

//        MODIFY
        BoardDto modifyBoardDto = new NoticeBoardDto();
        noticeBoardDto.setId(1);
        noticeBoardDto.setTitle("공지사항 2");
        noticeBoardDto.setContent("공지사항 2");
        noticeBoardDto.setModdate(LocalDateTime.now());

        noticeBoardService.modify(noticeBoardDto);
        
//        삭제
        noticeBoardService.delete(2);

//        전체 가져오기
        noticeBoardService.getBoardList().forEach(noticeBoard->{
           System.out.println(noticeBoard);
        });

//        일부 가져오기
        System.out.println(
                noticeBoardService.getBoard(1));

    }
}
