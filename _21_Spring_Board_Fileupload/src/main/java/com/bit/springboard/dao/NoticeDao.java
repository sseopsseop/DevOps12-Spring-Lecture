package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class NoticeDao {
    private SqlSessionTemplate mybatis;

    @Autowired
    public NoticeDao(SqlSessionTemplate sqlSessionTemplate) {
        this.mybatis = sqlSessionTemplate;
    }

    public void post(BoardDto boardDto, List<BoardFileDto> boardFileDtoList) {
        System.out.println("NoticeDao의 post 메소드 실행");

        mybatis.insert("NoticeDao.post", boardDto);

        System.out.println("insert 실행 후 id값: " + boardDto.getId());
        if(!boardFileDtoList.isEmpty()){
            boardFileDtoList.forEach(boardFileDto ->  boardFileDto.setBoard_id(boardDto.getId()));

            mybatis.insert("NoticeDao.uploadFiles", boardFileDtoList);
        }

        System.out.println("NoticeDao의 post 메소드 실행 종료");
    }

    public void modify(BoardDto boardDto, List<BoardFileDto> uFileList) {
        System.out.println("NoticeDao의 modify 메소드 실행");

        mybatis.update("NoticeDao.modify", boardDto);

        if(uFileList.size() > 0) {
            uFileList.forEach(boardFileDto -> {
                if(boardFileDto.getFilestatus().equals("I")) {
                    mybatis.insert("NoticeDao.postBoardFileOne", boardFileDto);
                } else if(boardFileDto.getFilestatus().equals("U")) {
                    mybatis.update("NoticeDao.modifyBoardFileOne", boardFileDto);
                } else if(boardFileDto.getFilestatus().equals("D")) {
                    mybatis.delete("NoticeDao.deleteBoardFileOne", boardFileDto);
                }
            });
        }
        System.out.println("NoticeDao의 modify 메소드 실행 종료");
    }

    public List<BoardDto> getNoticeList(Map<String, Object> paramMap) {
        System.out.println("NoticeDao의 getNoticeList 메소드 실행");

        return mybatis.selectList("NoticeDao.getNoticeList", paramMap);
    }
    
    public void delete(int id) {
        System.out.println("NoticeDao의 delete 메소드 실행");

        mybatis.delete("NoticeDao.deleteFiles", id);
        mybatis.delete("NoticeDao.delete", id);

        System.out.println("NoticeDao의 delete 메소드 실행 종료");
    }

    public BoardDto getNotice(int id) {
        System.out.println("NoticeDao의 getNotice 메소드 실행");

        return mybatis.selectOne("NoticeDao.getNotice", id);
    }

    public void updateCnt(int id) {
        mybatis.update("NoticeDao.updateCnt", id);
    }

    public int getBoardTotalCnt(Map<String, String> searchMap) {
        return mybatis.selectOne("NoticeDao.getBoardTotalCnt", searchMap);
    }

    public List<BoardFileDto> getFreeBoardFileList(int id){
        return mybatis.selectList("NoticeDao.getNoticeFileList", id);
    }
}
